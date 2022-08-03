# Authors: Tiago Dias   NMEC: 88896
#          Martim Neves NMEC: 88904

import paho.mqtt.client as mqttClient
import string, threading, json, geopy, random
from script.msg.cam import *
from script.msg.cpm import *
from script.msg.denm import *
from time import sleep
from shapely.geometry import Point, Polygon

# Speed values
speed_values = [60, 80, 100, 120, 140]

# Delta distance - the default move distance of an OBU  
delta_dist = 0.05

# DENM "causeCodes" and "subCauseCodes"
causeCodes = {"Approaching Merge": 31, "Merge situation": 32, "Change position": 33, 
              "Reduce the speed": 34, "Increase the speed": 35, "Mantain speed": 36}

# The causeCode "Merge situation" has the following subCauseCodes
subCauseCodes = {"Wants to merge": 41, "Going to merge": 42, "Merge done": 43}

# The class that represents the OBU
class OBU(threading.Thread):
    ip: string
    id: int
    start_pos: list
    initial_speed: int
    speed: int
    state: string
    actual_pos: list
    done: bool
    stationType: int
    client: mqttClient
    wants_to_merge: bool
    tot_obus: int
    second_iteraction: bool
    merging: bool
    lane_clear_2: bool
    lane_clear_3: bool
    blocking_obu_id: int
    reducing: int  
    change_pos: bool
    second_lane_clear: bool
    lane_clear_4: bool
    have_an_obu_behind: bool
    obu_id_behind_me: int

    # The OBU constructor
    def __init__(self, ip: string, id: int, start_pos: list, speed: int, state: string):
        threading.Thread.__init__(self)
        self.ip = ip
        self.id = id
        self.start_pos = start_pos
        self.initial_speed = speed
        self.speed = speed
        self.state = state
        self.done = False
        self.stationType = 5                    # OBUs are all station type = 5
        self.client = self.connect_mqtt()
        self.wants_to_merge = False
        self.tot_obus = 0
        self.second_iteraction = False
        self.merging = False
        self.lane_clear_2 = False
        self.lane_clear_3 = False
        self.lane_clear = False
        self.blocking_obu_id = -1
        self.reducing = 0           # 0) not reducing | 1) reducing | 2) reducing to change | -1) to increase speed to 140km/h  
        self.change_pos = False
        self.second_lane_clear = False
        self.lane_clear_4 = False
        self.have_an_obu_behind = False
        self.obu_id_behind_me = -1

    # Method to connect to MQTT
    def connect_mqtt(self):
        client = mqttClient.Client("OBU_"+str(self.id))
        client.connect(self.ip)
        return client

    # Method to publish the CAM messages
    # data -> list with the variables data 
    # data[0]: latitude;
    # data[1]: longitude;
    # data[2]: speed;
    def publish_CAM(self, data: list):
        msg = CAM(True, 0, 8, 15, True, True, True, 1023, "FORWARD", True, False, 3601, 127, self.truncate(data[0], 7), 100,
                  self.truncate(data[1], 7), 4095, 3601, 4095, SpecialVehicle(PublicTransportContainer(False)), 
                  data[2], 127, True, self.id, self.stationType, 4, 0)

        result = self.client.publish("vanetza/in/cam", repr(msg))
        status = result[0]

        if status == 0:
            print("OBU_"+str(self.id)+" sent CAM: Latitude: "+str(self.truncate(data[0], 7))+
                  ", Longitude: "+str(self.truncate(data[1], 7))+", Speed: "+str(data[2]))

    # Method to publish the DENM messages
    # data -> list with the variables data 
    # data[0]: latitude;
    # data[1]: longitude;
    # data[2]: causeCode;
    # data[3]: subCauseCode;
    def publish_DENM(self, data: list):
        msg = DENM(Management(ActionID(self.id, 0), 1626453837.658, 1626453837.658,
                   EventPosition(self.truncate(data[0], 7), self.truncate(data[1], 7), PositionConfidenceEllipse_DENM(0, 0, 0), 
                   Altitude_DENM(8, 1)), 0, 0), Situation(7, EventType(data[2], data[3])))

        result = self.client.publish("vanetza/in/denm", repr(msg))
        status = result[0]

        if status == 0:
            print("OBU_"+str(self.id)+" sent DENM: Latitude: "+str(self.truncate(data[0], 7))+", Longitude: "
                  +str(self.truncate(data[1], 7))+", CauseCode: "+str(data[2])+", SubCauseCode: "+str(data[3]))

    # Gets the message received on the subscribes topics
    def get_sub_msg(self, client, userdata, msg):
        msg_type = msg.topic
        msg = json.loads(msg.payload.decode())

        # If it's a DENM msg
        if(msg_type == "vanetza/out/denm"):
            data = { "stationID": msg["stationID"],
                     "latitude": msg["fields"]["denm"]["management"]["eventPosition"]["latitude"],
                     "longitude": msg["fields"]["denm"]["management"]["eventPosition"]["longitude"],
                     "causeCode": msg["fields"]["denm"]["situation"]["eventType"]["causeCode"],
                     "subCauseCode": msg["fields"]["denm"]["situation"]["eventType"]["subCauseCode"],
                   }
            print("OBU_"+str(self.id)+" received DENM: "+str(data))
            self.processEvents(data)

        # If it's a CAM msg
        elif(msg_type == "vanetza/out/cam"):
            data = { "stationID": msg["stationID"],
                     "latitude": msg["latitude"],
                     "longitude": msg["longitude"],
                     "speed": msg["speed"]
                   }
            print("OBU_"+str(self.id)+" received CAM: "+str(data))

            # To know how many OBUs exist on the highway
            if(self.second_iteraction):
                if(data["speed"] > 0):
                    self.tot_obus+=1

            # To gather some information about the state of the first main lane near the merge point (OBU_1)
            if(self.id == 1):
                if(data["speed"] > 0):
                    self.checkIfFirstLaneIsClear(data)

            # To gather some information about the state of the second main lane near the merge point (OBU_3)
            if(self.id == 3):
                if(data["speed"] > 0):
                    self.checkIfSecondLaneIsClear(data)  

            # To inform the OBU_4 about the OBU behind him near the merge point 
            if(self.id == 4):
                if(data["speed"] > 0):
                    self.iGotAnObuBehind(data)

    # The routine of the OBU objects - "main" method of this class
    def start(self): 
        # Connect to the MQTT
        self.client = mqttClient.Client("OBU_"+str(self.id))
        self.client.connect(self.ip)

        # Subscribes the CAM and DENM topic
        self.client.on_message = self.get_sub_msg
        self.client.loop_start()
        self.client.subscribe([("vanetza/out/cam", 0), ("vanetza/out/denm", 1)])

        # Create the start postion of the OBU
        start = geopy.Point(self.start_pos[0], self.start_pos[1])

        # Auxiliary variables
        i, j, k, l = 0, 0, 0, 0

        # Begin the life cycle of the OBU
        while not self.done:
            if (i == 1):
                self.second_iteraction = True
                print("\x1b[0;37;43m"+"OBU_"+str(self.id)+" initial state: \""+str(self.state)+"\""+"\x1b[0m")
            if(i > 1):
                self.second_iteraction = False

            # If it's the OBU that starts runing on the merge lane of the highway
            if(self.id == 1):
                if (i < 7):
                    pos = geopy.distance.geodesic(meters = self.speed*delta_dist*i).destination(start, 222)
                # Adjust the position after the curve
                else:
                    pos = geopy.distance.geodesic(meters = self.speed*delta_dist*i).destination(start, 223)

                # Adjust the direction when merging
                if(self.merging):
                    # If the merge OBU is reducing his speed
                    if(self.reducing == 1):
                        pos = self.reducingToMergeSpeedPosMergeOBU(start, i, j)
                        j+=1
                    # If the merge OBU is mantaining his speed
                    elif(self.reducing == 0):
                        pos = self.mergingPos(start, i)

            # If the OBU starts on the main lane of the highway
            else:
                # The OBU is maintaining the speed
                if(self.reducing == 0):
                    pos = geopy.distance.geodesic(meters = self.speed*delta_dist*i).destination(start, 225)
                # The OBU is reducing the speed                
                elif(self.reducing == 1):
                    pos = self.reducingSpeedPosMainOBU(start, i, j)
                    j+=1
                # The OBU is reducing the speed to change position       
                elif(self.reducing == 2):
                    pos = self.reduceSpeedToChange(start, i, k)
                    k+=1
                # The OBU is inreasing his speed  
                elif(self.reducing == -1):
                    pos = self.increseSpeedAfterChange(start, i, l)
                    l+=1

            # End of simulation
            if(i == 20):
                self.merging = False
                self.reducing = 0
                self.done = True
                print("\x1b[0;37;41m"+"OBU_"+str(self.id)+": simulation done"+"\x1b[0m")

            # Publish the CAM msg
            self.publish_CAM([pos.latitude, pos.longitude, self.speed])
            # Updating the actual position of the OBU
            self.updatePos([self.truncate(pos.latitude, 7), self.truncate(pos.longitude, 7)])

            i+=1
            # Send the CAMs at a 1Hz frequency
            sleep(1)

        # Disconnect the MQTT subscription 
        self.client.loop_stop()
        self.client.disconnect()

    # Processes the events received by the DENM messages
    def processEvents(self, data):
        # Unfactorizing the coordinates received
        unfactor_coords = self.unfactorCoords([data["latitude"], data["longitude"]])

        # If an OBU is approaching the merge point
        if(data["causeCode"] == causeCodes["Approaching Merge"]):
            # Processing the data from the OBU that is on the merge lane
            if( (unfactor_coords[0] == self.actual_pos[0])  and (unfactor_coords[1] == self.actual_pos[1]) ):
                print("\x1b[0;37;46m"+"OBU_"+str(self.id)+": i'm approaching an merge point"+"\x1b[0m")

                # The OBU sends an DENM message with his merge intention
                print("\x1b[0;37;46m"+"OBU_"+str(self.id)+": i want to merge"+"\x1b[0m")
                self.publish_DENM( [self.actual_pos[0], self.actual_pos[1], 
                                    causeCodes["Merge situation"], subCauseCodes["Wants to merge"]] )

                self.wants_to_merge = True   
        
        #  If the OBU wants to merge and receives an DENM message 
        if(self.wants_to_merge == True):
            if(self.tot_obus == 3):
                if(self.lane_clear == False):

                    # The OBU who's on the way informs that he's gonna mantain his speed -> The merge OBU needs to reduce his speed
                    if(data["causeCode"] ==  causeCodes["Mantain speed"]):
                        if(data["stationID"] == self.blocking_obu_id):
                            self.wants_to_merge = False
                        
                            print("\x1b[0;37;46m"+"OBU_"+str(self.id)+": i'm gonna reduce my speed"+"\x1b[0m")
                            self.publish_DENM( [self.actual_pos[0], self.actual_pos[1], 
                                                causeCodes["Reduce the speed"], causeCodes["Reduce the speed"]] )
                            self.reducing = 1
                            self.reduceSpeed()

                            print("\x1b[0;37;42m"+"OBU_"+str(self.id)+": merge approved"+"\x1b[0m")
                            self.publish_DENM( [self.actual_pos[0], self.actual_pos[1], 
                                                causeCodes["Merge situation"], subCauseCodes["Going to merge"]] )
                            self.merging = True
                            self.state = "Merging"
                            print("\x1b[0;37;43m"+"OBU_"+str(self.id)+" change state: \""+str(self.state)+"\""+"\x1b[0m")

                    # The OBU who's on the way informs that he's gonna reduce his speed -> The merge OBU needs to increase his speed
                    elif(data["causeCode"] ==  causeCodes["Reduce the speed"]):
                        if(data["stationID"] == self.blocking_obu_id):
                            self.wants_to_merge = False

                            print("\x1b[0;37;46m"+"OBU_"+str(self.id)+": i'm gonna increase my speed"+"\x1b[0m")
                            self.publish_DENM( [self.actual_pos[0], self.actual_pos[1], 
                                                causeCodes["Increase the speed"], causeCodes["Increase the speed"]] )
                            self.increaseSpeed()

                            print("\x1b[0;37;42m"+"OBU_"+str(self.id)+": merge approved"+"\x1b[0m")
                            self.publish_DENM( [self.actual_pos[0], self.actual_pos[1], 
                                                causeCodes["Merge situation"], subCauseCodes["Going to merge"]] )
                            self.merging = True
                            self.state = "Merging"
                            print("\x1b[0;37;43m"+"OBU_"+str(self.id)+" change state: \""+str(self.state)+"\""+"\x1b[0m")

            else:
                # If the lane on the right side of the merge OBU is clear
                if(self.lane_clear):
                    self.wants_to_merge = False

                    print("\x1b[0;37;42m"+"OBU_"+str(self.id)+": merge approved!"+"\x1b[0m")
                    self.publish_DENM( [self.actual_pos[0], self.actual_pos[1], 
                                            causeCodes["Merge situation"], subCauseCodes["Going to merge"]] )
                    self.merging = True
                    self.state = "Merging"
                    print("\x1b[0;37;43m"+"OBU_"+str(self.id)+" change state: \""+str(self.state)+"\""+"\x1b[0m")
                    print("\x1b[0;37;46m"+"OBU_"+str(self.id)+": i'm gonna merge and increase my speed"+"\x1b[0m")
                    self.increaseSpeed()

                # If the lane on the right side of the merge OBU is blocked
                else:
                    # The OBU who's on the way informs that he's gonna mantain his speed -> The merge OBU needs to reduce his speed
                    if(data["causeCode"] ==  causeCodes["Mantain speed"]):
                        if(data["stationID"] == self.blocking_obu_id):
                            self.wants_to_merge = False
                        
                            print("\x1b[0;37;46m"+"OBU_"+str(self.id)+": i'm gonna reduce my speed"+"\x1b[0m")
                            self.publish_DENM( [self.actual_pos[0], self.actual_pos[1], 
                                                causeCodes["Reduce the speed"], causeCodes["Reduce the speed"]] )
                            self.reducing = 1
                            self.reduceSpeed()

                            print("\x1b[0;37;42m"+"OBU_"+str(self.id)+": merge approved"+"\x1b[0m")
                            self.publish_DENM( [self.actual_pos[0], self.actual_pos[1], 
                                                causeCodes["Merge situation"], subCauseCodes["Going to merge"]] )
                            self.merging = True
                            self.state = "Merging"
                            print("\x1b[0;37;43m"+"OBU_"+str(self.id)+" change state: \""+str(self.state)+"\""+"\x1b[0m")

                    # The OBU who's on the way informs that he's gonna reduce his speed -> The merge OBU will increase his speed
                    if(data["causeCode"] == causeCodes["Reduce the speed"]):
                        if(data["stationID"] == self.blocking_obu_id):
                            self.wants_to_merge = False

                            print("\x1b[0;37;46m"+"OBU_"+str(self.id)+": i'm gonna increase my speed"+"\x1b[0m")
                            self.publish_DENM( [self.actual_pos[0], self.actual_pos[1], 
                                                causeCodes["Increase the speed"], causeCodes["Increase the speed"]] )
                            self.increaseSpeed()

                            print("\x1b[0;37;42m"+"OBU_"+str(self.id)+": merge approved"+"\x1b[0m")
                            self.publish_DENM( [self.actual_pos[0], self.actual_pos[1], 
                                                causeCodes["Merge situation"], subCauseCodes["Going to merge"]] )
                            self.merging = True
                            self.state = "Merging"
                            print("\x1b[0;37;43m"+"OBU_"+str(self.id)+" change state: \""+str(self.state)+"\""+"\x1b[0m")

        # The OBU receives the intention of merge by another OBU
        if((data["causeCode"] == causeCodes["Merge situation"]) and (data["subCauseCode"] == subCauseCodes["Wants to merge"])):
            print("\x1b[0;37;46m"+"OBU_"+str(self.id)+": knows that the OBU_"
                                        +str(data["stationID"])+" wants to merge"+"\x1b[0m")

            # Check if this OBU is on the near lane of the merge OBU
            if(self.checkIfImBlocking()):
                if(self.id == 3):
                    print("\x1b[0;37;41m"+"OBU_"+str(self.id)+": i'm on the way of OBU_"+str(data["stationID"])+"\x1b[0m")

                    # Creates an random option to do the merge situation 
                    option = random.randint(0, 2)
                    print("\x1b[0;37;43m"+"Merge situation number: "+str(option)+"\x1b[0m")

                    # 0) I'm gonna mantain my speed -> The merge OBU needs to reduce his speed
                    if (option == 0):
                        print("\x1b[0;37;46m"+"OBU_"+str(self.id)+": i'm gonna mantain my speed"+"\x1b[0m")
                        self.publish_DENM( [self.actual_pos[0], self.actual_pos[1], 
                                            causeCodes["Mantain speed"], causeCodes["Mantain speed"]] )

                    # 1) I'm gonna reduce my speed -> The merge OBU increases his speed
                    elif (option == 1):
                        print("\x1b[0;37;46m"+"OBU_"+str(self.id)+": i'm gonna reduce my speed"+"\x1b[0m")
                        self.publish_DENM( [self.actual_pos[0], self.actual_pos[1], 
                                            causeCodes["Reduce the speed"], causeCodes["Reduce the speed"]] )

                        self.reducing = 1
                        self.reduceSpeed()

                    # 2) I'm gonna change my position to the next lane -> The merge OBU increases his speed 
                    elif (option == 2):
                        self.state = "Change Position" 
                        print("\x1b[0;37;43m"+"OBU_"+str(self.id)+" change state: \""+str(self.state)+"\""+"\x1b[0m")  
                        print("\x1b[0;37;46m"+"OBU_"+str(self.id)+": i'm gonna change my position"+"\x1b[0m")
                        self.publish_DENM( [self.actual_pos[0], self.actual_pos[1], 
                                            causeCodes["Change position"], causeCodes["Change position"]] )
                        self.change_pos = True

            # If this OBU is not on the way of the merge OBU
            else:
                print("\x1b[0;37;46m"+"OBU_"+str(self.id)+": i'm gonna mantain my speed"+"\x1b[0m")
                self.publish_DENM( [self.actual_pos[0], self.actual_pos[1], 
                                    causeCodes["Mantain speed"], causeCodes["Mantain speed"]] )

        # If the OBU wants to change the position for the next lane
        if(self.change_pos):
            # Informs that he's gonna reduce his speed -> The merge OBU will increase his speed
            if(self.second_lane_clear == False):
                print("\x1b[0;37;46m"+"OBU_"+str(self.id)+": i'm gonna reduce my speed"+"\x1b[0m")
                self.publish_DENM( [self.actual_pos[0], self.actual_pos[1], 
                                    causeCodes["Reduce the speed"], causeCodes["Reduce the speed"]] )

                self.reducing = 2
                self.reduceSpeed()
                self.change_pos = False

        # If the OBU receives an change position of other OBU that is not the merge OBU
        if(self.id > 1):
            if((data["causeCode"] == causeCodes["Change position"])):
                print("\x1b[0;37;46m"+"OBU_"+str(self.id)+
                      ": i'm gonna increase my speed because i have an OBU behind me"+"\x1b[0m")
                self.publish_DENM( [self.actual_pos[0], self.actual_pos[1], 
                                    causeCodes["Increase the speed"], causeCodes["Increase the speed"]] )
                self.increaseSpeed()
                self.reducing = -1

            # If the OBU_4 has an car behind
            if(self.have_an_obu_behind):
                if((data["causeCode"] ==  causeCodes["Reduce the speed"]) and (data["stationID"] == self.obu_id_behind_me)):
                    print("\x1b[0;37;46m"+"OBU_"+str(self.id)+": i'm gonna mantain my speed"+"\x1b[0m")
                    self.publish_DENM( [self.actual_pos[0], self.actual_pos[1], 
                                        causeCodes["Mantain speed"], causeCodes["Mantain speed"]] )
                    
    # To check if the an main OBU has an OBU behind him
    def iGotAnObuBehind(self, data):
        # Create Point objects
        unfactor_coords = self.unfactorCoords([data["latitude"], data["longitude"]])
        point = Point(self.truncate(unfactor_coords[0], 7), self.truncate(unfactor_coords[1], 7))

        # Create a polygon
        coords = [(40.6404040, -8.6632890), (40.6403800, -8.6632360), (40.6402510, -8.6634820), (40.6402370, -8.6634330)]
        poly = Polygon(coords)

        # Check if the coordinates are inside the polygon
        if(data["stationID"] == 3):
            if(poly.contains(point) == False):
                print("\x1b[0;37;42m"+"OBU_"+str(self.id)+": i don't have nobody behind me near an merge point"+"\x1b[0m")
                self.have_an_obu_behind = False
            else:
                print("\x1b[0;37;41m"+"OBU_"+str(self.id)+": i have OBU_"+str(data["stationID"])
                                            +" behind me near an merge point"+"\x1b[0m")
                self.have_an_obu_behind = True
                self.obu_id_behind_me = 3

    # To check if the second main lane is clear
    def checkIfSecondLaneIsClear(self, data):
        # Create Point objects
        unfactor_coords = self.unfactorCoords([data["latitude"], data["longitude"]])
        point = Point(self.truncate(unfactor_coords[0], 7), self.truncate(unfactor_coords[1], 7))
        
        # Create a polygon
        coords = [(40.6403720, -8.6632580), (40.6403560, -8.6632180), (40.6401630, -8.6635340), (40.6401410, -8.6634910)]
        poly = Polygon(coords)

        # Check if the coordinates are inside the polygon
        if(data["stationID"] == 2):
            if(poly.contains(point) == False):
                print("\x1b[0;37;42m"+"OBU_"+str(self.id)+
                      ": i know that the second lane is clear for a merge situation (analysing OBU_"
                      +str(data["stationID"])+" position)"+"\x1b[0m")
                self.second_lane_clear = True
            else:
                print("\x1b[0;37;41m"+"OBU_"+str(self.id)+" ATTENTION: the second lane is not clear (analysing OBU_"
                            +str(data["stationID"])+" position)"+"\x1b[0m")
                self.second_lane_clear = False

    # To check if the lane on the right side of the merge OBU is clear
    def checkIfFirstLaneIsClear(self, data):
        # Create Point objects
        unfactor_coords = self.unfactorCoords([data["latitude"], data["longitude"]])
        point = Point(self.truncate(unfactor_coords[0], 7), self.truncate(unfactor_coords[1], 7))

        # Create a polygon
        coords = [(40.6403890, -8.6633100), (40.6403660, -8.6632630), (40.6401800, -8.6635690), (40.6401600, -8.6635250)]
        poly = Polygon(coords)

        # Check if the coordinates are inside the polygon
        if(self.tot_obus == 1):
            if(data["stationID"] == 2):
                if(poly.contains(point) == False):
                    self.lane_clear_2 = True
                    print("\x1b[0;37;42m"+"OBU_"+str(self.id)+
                          ": i know that the first lane is clear for a merge situation (analysing OBU_"
                          +str(data["stationID"])+" position)"+"\x1b[0m")
                else:
                    self.lane_clear_2 = False
                    self.blocking_obu_id = 2
                    print("\x1b[0;37;41m"+"OBU_"+str(self.id)+" ATTENTION: the first lane is not clear (analysing OBU_"
                                         +str(data["stationID"])+" position)"+"\x1b[0m")
            self.lane_clear = self.lane_clear_2
        elif(self.tot_obus == 2):
            if(data["stationID"] == 2):
                if(poly.contains(point) == False):
                    self.lane_clear_2 = True
                    print("\x1b[0;37;42m"+"OBU_"+str(self.id)+
                          ": i know that the first lane is clear for a merge situation (analysing OBU_"
                          +str(data["stationID"])+" position)"+"\x1b[0m")
                else:
                    self.lane_clear_2 = False
                    self.blocking_obu_id = 2
                    print("\x1b[0;37;41m"+"OBU_"+str(self.id)+" ATTENTION: the first lane is not clear (analysing OBU_"
                                         +str(data["stationID"])+" position)"+"\x1b[0m")
            elif(data["stationID"] == 3):
                if(poly.contains(point) == False):
                    self.lane_clear_3 = True
                    print("\x1b[0;37;42m"+"OBU_"+str(self.id)+
                          ": i know that the first lane is clear for a merge situation (analysing OBU_"
                          +str(data["stationID"])+" position)"+"\x1b[0m")
                else:
                    self.lane_clear_3 = False
                    self.blocking_obu_id = 3
                    print("\x1b[0;37;41m"+"OBU_"+str(self.id)+" ATTENTION: the first lane is not clear (analysing OBU_"
                                         +str(data["stationID"])+" position)"+"\x1b[0m")
            self.lane_clear = self.lane_clear_2 and self.lane_clear_3
        
        elif(self.tot_obus == 3):
            if(data["stationID"] == 2):
                if(poly.contains(point) == False):
                    self.lane_clear_2 = True
                    print("\x1b[0;37;42m"+"OBU_"+str(self.id)+
                          ": i know that the first lane is clear for a merge situation (analysing OBU_"
                          +str(data["stationID"])+" position)"+"\x1b[0m")
                else:
                    self.lane_clear_2 = False
                    self.blocking_obu_id = 2
                    print("\x1b[0;37;41m"+"OBU_"+str(self.id)+" ATTENTION: the first lane is not clear (analysing OBU_"
                                         +str(data["stationID"])+" position)"+"\x1b[0m")
            elif(data["stationID"] == 3):
                if(poly.contains(point) == False):
                    self.lane_clear_3 = True
                    print("\x1b[0;37;42m"+"OBU_"+str(self.id)+
                          ": i know that the first lane is clear for a merge situation (analysing OBU_"
                          +str(data["stationID"])+" position)"+"\x1b[0m")
                else:
                    self.lane_clear_3 = False
                    self.blocking_obu_id = 3
                    print("\x1b[0;37;41m"+"OBU_"+str(self.id)+" ATTENTION: the first lane is not clear (analysing OBU_"
                                         +str(data["stationID"])+" position)"+"\x1b[0m")
            elif(data["stationID"] == 4):
                if(poly.contains(point) == False):
                    self.lane_clear_4 = True
                    print("\x1b[0;37;42m"+"OBU_"+str(self.id)+
                          ": i know that the first lane is clear for a merge situation (analysing OBU_"
                          +str(data["stationID"])+" position)"+"\x1b[0m")
                else:
                    self.lane_clear_4 = False
                    self.blocking_obu_id = 4
                    print("\x1b[0;37;41m"+"OBU_"+str(self.id)+" ATTENTION: the first lane is not clear (analysing OBU_"
                                         +str(data["stationID"])+" position)"+"\x1b[0m")
            self.lane_clear = self.lane_clear_2 and self.lane_clear_3 and self.lane_clear_4

    # To check if the OBU is blocking the way of the merge OBU
    def checkIfImBlocking(self):
        # Create Point objects
        point = Point(self.actual_pos[0], self.actual_pos[1])

        # Create a polygon
        coords = [(40.6403890, -8.6633100), (40.6403660, -8.6632630), (40.6401800, -8.6635690), (40.6401600, -8.6635250)]
        poly = Polygon(coords)

        # Check if the coordinates are inside the polygon
        return poly.contains(point)

    # To increase the speed of an main OBU when it receives an "change position" DENM message from another main OBU
    def increseSpeedAfterChange(self, start, i, l):
        if(l == 0):
            pos = geopy.distance.geodesic(meters = (self.speed-20)*delta_dist*i).destination(start, 225)
        elif(l == 1):
            pos = geopy.distance.geodesic(meters = (self.speed-15)*delta_dist*i).destination(start, 225)  
        elif(l >= 2):
            pos = geopy.distance.geodesic(meters = (self.speed-12)*delta_dist*i).destination(start, 225)   

        return pos

    # To reduce the speed of an main OBU to change position
    def reduceSpeedToChange(self, start, i, k):
        if(k == 0):
            pos = geopy.distance.geodesic(meters = (self.speed+15)*delta_dist*i).destination(start, 225)
        elif(k == 1):
            pos = geopy.distance.geodesic(meters = (self.speed+10)*delta_dist*i).destination(start, 223) 
        elif(k == 2):
            pos = geopy.distance.geodesic(meters = (self.speed+7)*delta_dist*i).destination(start, 223)  
            self.state = "Driving" 
            print("\x1b[0;37;43m"+"OBU_"+str(self.id)+" change state: \""+str(self.state)+"\""+"\x1b[0m")
        elif(k == 3):
            pos = geopy.distance.geodesic(meters = (self.speed+5)*delta_dist*i).destination(start, 223)
        elif(k >= 3):
            pos = geopy.distance.geodesic(meters = self.speed*delta_dist*i).destination(start, 223)

        return pos

    # The interaction between the reducing speed event and the graphical position of the OBU on the main lane
    def reducingSpeedPosMainOBU(self, start, i, j):
        if(j == 0):
            pos = geopy.distance.geodesic(meters = (self.speed+15)*delta_dist*i).destination(start, 225)
        elif(j == 1):
            pos = geopy.distance.geodesic(meters = (self.speed+10)*delta_dist*i).destination(start, 225)  
        elif(j == 2):
            pos = geopy.distance.geodesic(meters = (self.speed+5)*delta_dist*i).destination(start, 225)  
            self.reducing = 0  

        return pos

    # The interaction between the reducing speed event and the graphical position of the OBU on the merge lane
    def reducingToMergeSpeedPosMergeOBU(self, start, i, j):
        if(j == 0):
            pos = geopy.distance.geodesic(meters = (self.speed+25)*delta_dist*i).destination(start, 223)
        elif(j == 1):
            pos = geopy.distance.geodesic(meters = (self.speed+20)*delta_dist*i).destination(start, 223)  
        elif(j >= 2 and j <= 4):
            pos = geopy.distance.geodesic(meters = (self.speed+20)*delta_dist*i).destination(start, 221.3)
            if (j == 2):
                self.publish_DENM( [pos.latitude, pos.longitude, 
                                    causeCodes["Merge situation"], subCauseCodes["Merge done"]] )      
                print("\x1b[0;37;46m"+"OBU_"+str(self.id)+": i finished my merge"+"\x1b[0m")  
                self.state = "Driving"
                print("\x1b[0;37;43m"+"OBU_"+str(self.id)+" change state: \""+str(self.state)+"\""+"\x1b[0m")
        elif(j == 5):
            print("\x1b[0;37;46m"+"OBU_"+str(self.id)+": i'm gonna increase my speed because i have space"+"\x1b[0m")
            self.publish_DENM( [self.actual_pos[0], self.actual_pos[1], 
                                causeCodes["Increase the speed"], causeCodes["Increase the speed"]] )
            self.increaseSpeed()
            pos = geopy.distance.geodesic(meters = self.speed*delta_dist*i).destination(start, 221.3)  
        elif(j == 6):
            print("\x1b[0;37;46m"+"OBU_"+str(self.id)+": i'm gonna increase my speed because i have space"+"\x1b[0m")
            self.publish_DENM( [self.actual_pos[0], self.actual_pos[1], 
                                causeCodes["Increase the speed"], causeCodes["Increase the speed"]] )
            self.increaseSpeed()
            pos = geopy.distance.geodesic(meters = (self.speed-15)*delta_dist*i).destination(start, 221.3)
        elif(j > 6):
            pos = geopy.distance.geodesic(meters = (self.speed-10)*delta_dist*i).destination(start, 221.3) 

        return pos

    # The interaction between the merging event and the graphical position of the OBU 
    def mergingPos(self, start, i):
        pos = geopy.distance.geodesic(meters = self.speed*delta_dist*i).destination(start, 223)
        if(i == 12):
            pos = geopy.distance.geodesic(meters = self.speed*delta_dist*i).destination(start, 221.3)                
        if(i >= 13 and i < 17):
            pos = geopy.distance.geodesic(meters = self.speed*delta_dist*i).destination(start, 221.3)
            if (i == 13):
                self.publish_DENM( [pos.latitude, pos.longitude, 
                                causeCodes["Merge situation"], subCauseCodes["Merge done"]] )      
                print("\x1b[0;37;46m"+"OBU_"+str(self.id)+": i finished my merge"+"\x1b[0m")
                self.state = "Driving"
                print("\x1b[0;37;43m"+"OBU_"+str(self.id)+" change state: \""+str(self.state)+"\""+"\x1b[0m")
        elif(i >= 17 and i <= 20):
            pos = geopy.distance.geodesic(meters = self.speed*delta_dist*i).destination(start, 221.7)

        return pos

    # Reduces the OBU speed
    def reduceSpeed(self):
        for i in range(0, len(speed_values)):
            if(speed_values[i] == self.speed):
                index = i
        
        if(index != 0):
            self.speed = speed_values[index - 1]

    # Increases the OBU speed
    def increaseSpeed(self):
        for i in range(0, len(speed_values)):
            if(speed_values[i] == self.speed):
                index = i
        
        if(index != (len(speed_values)-1)):
            self.speed = speed_values[index + 1]

    # To update the actual positions coordinates
    def updatePos(self, actual_pos):
        self.actual_pos = actual_pos

    # Developer-friendly string representation of the object
    def obu_status(self):
        repr = {"actual_pos": self.actual_pos,
                "speed": self.speed,
                "state": self.state}
        return repr

    # To truncate an number with the number of decimals passed as argument
    def truncate(self, num, dec_plc):
        return int(num * 10**dec_plc) / 10**dec_plc

    # To unfactor the coordinates
    def unfactorCoords(self, coords):
        return [coords[0]/(10**7), coords[1]/(10**7)]

    # To reset to the initial state
    def reset(self):
        self.actual_pos = self.start_pos
        self.speed = self.initial_speed
        self.state = "Driving"
        self.done = False
        self.tot_obus = 0
        self.blocking_obu_id = -1
        self.reducing = 0
        self.obu_id_behind_me = -1
        self.client.loop_stop()
        self.client.disconnect()

    # Used to stop the simulation
    def stop(self):
        self.done = True