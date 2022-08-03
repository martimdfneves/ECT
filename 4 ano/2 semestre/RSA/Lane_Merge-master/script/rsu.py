# Authors: Tiago Dias   NMEC: 88896
#          Martim Neves NMEC: 88904

import paho.mqtt.client as mqttClient
import string, threading, json, geopy
from script.msg.cam import *
from script.msg.denm import *
from time import sleep

# Merge Location
merge_coords = [40.6402812, -8.6634647]

# Delta distance - the default move distance that a OBU mades 
delta_dist = 0.05

# DENM "causeCodes" and "subCauseCodes"
causeCodes = {"Approaching Merge": 31, "Merge situation": 32, "Change position": 33, 
              "Reduce the speed": 34, "Increase the speed": 35, "Mantain speed": 36}

# The causeCode "Merge situation" has the following subCauseCodes
subCauseCodes = {"Wants to merge": 41, "Going to merge": 42, "Merge done": 43}

# The class taht represents the RSUs
class RSU(threading.Thread):
    ip: string
    id: int
    rsu_coords: list
    done: bool
    stationType : int
    client: mqttClient

    # The RSU constructor
    def __init__(self, ip: string, id: int, rsu_coords: list):
        threading.Thread.__init__(self)
        self.ip = ip
        self.id = id
        self.rsu_coords = rsu_coords
        self.done = False
        self.stationType = 15                    # RSUs are all station type = 15
        self.client = self.connect_mqtt()

    # Method to connect to MQTT
    def connect_mqtt(self):
        client = mqttClient.Client("RSU_"+str(self.id))
        client.connect(self.ip)
        return client

    # Method to publish the CAM messages at a 1Hz frequency
    # data -> list with the variables data 
    # data[0]: latitude;
    # data[1]: longitude;
    # data[2]: speed;
    def publish_CAM(self, data: list):
        msg = CAM(True, 0, 8, 15, True, True, True, 1023, "FORWARD", True, False, 3601, 127, self.truncate(data[0], 7), 100,
                  self.truncate(data[1], 7), 4095, 3601, 4095, SpecialVehicle(PublicTransportContainer(False)), data[2],
                  127, True, self.id, self.stationType, 10, 0)

        result = self.client.publish("vanetza/in/cam", repr(msg))
        status = result[0]

        if status == 0:
            print("RSU_"+str(self.id)+" sent CAM: Latitude: "+str(self.truncate(data[0], 7))+
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
            print("RSU_"+str(self.id)+" sent DENM: Latitude: "+str(self.truncate(data[0], 7))+", Longitude: "
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
            print("RSU_"+str(self.id)+" received DENM: "+str(data))

        # If it's a CAM msg
        elif(msg_type == "vanetza/out/cam"):
            data = { "stationID": msg["stationID"],
                     "latitude": msg["latitude"],
                     "longitude": msg["longitude"],
                     "speed": msg["speed"]
                   }
            print("RSU_"+str(self.id)+" received CAM: "+str(data))
            self.approachingMergePoint(data)

    # The routine of the RSU - "main" method of this class
    def start(self):        
        # Connect to the MQTT
        self.client = mqttClient.Client("RSUU_"+str(self.id))
        self.client.connect(self.ip)

        # Subscribes the CAM and DENM topic
        self.client.on_message = self.get_sub_msg
        self.client.loop_start()
        self.client.subscribe([("vanetza/out/cam", 0), ("vanetza/out/denm", 1)])
        
        # Auxiliary variable
        i = 0

        # Begin the life cycle of the RSU
        while not self.done:
            # Publish the CAM msg
            self.publish_CAM([self.rsu_coords[0], self.rsu_coords[1], 0])

            # End of simulation
            if(i == 20):
                self.done = True
                print("\x1b[0;37;41m"+"RSU_"+str(self.id)+": simulation done"+"\x1b[0m")

            i+=1
            # Send the CAMs at a 1Hz frequency
            sleep(1)
        
        # Disconnect the MQTT subscription 
        self.client.loop_stop()
        self.client.disconnect()

    # Check if there's a vehicle approaching the merge point
    def approachingMergePoint(self, data):
        # Unfactorizing the coordinates received
        unfactor_coords = [data["latitude"]/(10**7), data["longitude"]/(10**7)]

        # Last position before the merge point (merge_point - delta_dist)
        approaching_merge = geopy.Point(merge_coords[0], merge_coords[1])
        merge_dist = geopy.distance.geodesic(meters = -data["speed"]*delta_dist).destination(approaching_merge, 223)
        appr_merge_coords = [self.truncate(merge_dist.latitude, 7), self.truncate(merge_dist.longitude, 7)]

        # There's a vehicle approaching the merge point
        if((appr_merge_coords[0] == unfactor_coords[0]) and (appr_merge_coords[1] == unfactor_coords[1])):
            print("\x1b[0;37;43m"+"RSU_"+str(self.id)+": warns OBU_"+str(data["stationID"])
                                 +" that he's approaching the merge point"+"\x1b[0m")
            
            self.publish_DENM([(unfactor_coords[0]), unfactor_coords[1], 
                               causeCodes["Approaching Merge"], causeCodes["Approaching Merge"]])

    # To truncate an number with the number of decimals passed as argument
    def truncate(self, num, dec_plc):
        return int(num * 10**dec_plc) / 10**dec_plc

    # Used to stop the simulation
    def stop(self):
        self.done = True

    # To reset to the initial state
    def reset(self):
        self.done = False
        self.client.loop_stop()
        self.client.disconnect()
