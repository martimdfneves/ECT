# Authors: Tiago Dias   NMEC: 88896
#          Martim Neves NMEC: 88904

import paho.mqtt.client as mqttClient
import time
from script.msg.cam import *
from script.msg.denm import *
from script.msg.cpm import *

# Global variables
id = "Lane Merge"
rsu_ip = "192.168.98.10"

# Subscribe Topics
cam_subscribe_topic = "vanetza/out/cam"
denm_subscribe_topic = "vanetza/out/denm"
cpm_subscribe_topic = "vanetza/out/cpm"

# Publish Topics
cam_publish_topic = "vanetza/in/cam"
denm_publish_topic = "vanetza/in/denm"
cpm_publish_topic = "vanetza/in/cpm"

# Method to connect to MQTT -> returns an client of the mqttClient type
def connect_mqtt() -> mqttClient:
    def on_connect(client, userdata, flags, rc):
        if rc == 0:
            print("Connected to MQTT Broker!")
        else:
            print("Failed to connect, return code %d\n", rc)

    client = mqttClient.Client(id)
    client.on_connect = on_connect
    client.connect(rsu_ip)
    return client

# Publish a message to the topic -> it receives an client of the mqttClient type
def publish(client):
    while True:
        time.sleep(1)

        # CAM message to test
        cam_msg = CAM(True, 0, 800001, 15, True, True, True, 1023, "FORWARD", True, False, 3601, 127,
                  40.640551, 100, -8.663130, 4095, 3601, 4095, 
                  SpecialVehicle(PublicTransportContainer(False)), 16383, 127, True, 1, 15, 30, 0)
        
        # DENM message to test
        denm_msg = DENM(Management(ActionID(1798587532, 0), 1626453837.658, 1626453837.658, 
                   EventPosition(40.640551, -8.663130, PositionConfidenceEllipse_DENM(0, 0, 0), 
                   Altitude_DENM(0, 1)), 0, 0), Situation(7, EventType(14, 14)))

        # CPM message to test
        sensors_list = [SensorInformationContainer(1, 1, DetectionArea(StationarySensorRectangle(750, 20, 3601))), 
                        SensorInformationContainer(2, 12, DetectionArea(None, StationarySensorRadial(100, 3601, 3601)))]
        
        classification_list = [Classification(0, Class(Vehicle(3, 0))), Classification(0, Class(None, Other(0, 0)))]

        objects_list = [PerceivedObjectContainer(1, 0, 0, Axis(1216.281028098143, 1), 
                        Axis(1216.281028098143, 1), Axis(11.273405440822678, 1), 
                        Axis(7.9592920392978215, 1), XAcceleration(-0.0011505823066771444, 0),
                        YAcceleration(-0.000812338440426394, 0), 0, [Classification(0, Class(Vehicle(3, 0)))]), 
                        
                        PerceivedObjectContainer(1, 0, 0, Axis(1216.281028098143, 1), 
                        Axis(1216.281028098143, 1), Axis(11.273405440822678, 1), 
                        Axis(7.9592920392978215, 1), XAcceleration(-0.0011505823066771444, 0),
                        YAcceleration(-0.000812338440426394, 0), 0, classification_list),

                        PerceivedObjectContainer(4, 0, 0, Axis(1216.281028098143, 1),
                        Axis(1216.281028098143, 1), Axis(-0.5581092564671636, 1),
                        Axis(-0.7060552795962012, 1), XAcceleration(-0.0, 0),
                        YAcceleration(-0.0, 0), 0, [Classification(0, Class(None, Other(0, 0)))])]

        cpm_msg = CPM(44258, CpmParameters(ManagementContainer(15, 
                  ReferencePosition(40.64088, -8.65397, PositionConfidenceEllipse_CPM(4095, 4095, 0.0), 
                  Altitude_CPM(2.9, 14))), StationDataContainer(
                  OriginatingVehicleContainer(Heading(3601, 127), Speed(16383, 127), 0)), 
                  list(sensors_list), list(objects_list), len(objects_list)))

        result = client.publish(cpm_publish_topic, repr(cpm_msg))
    
        #result: [0, 1]
        status = result[0]

        if status == 0:
            print("Send msg to topic "+cpm_publish_topic)
        else:
            print("Failed to send message to topic "+cpm_publish_topic)

# Subscribes the topic -> it receives an client of the mqttClient type
def subscribe(client):
    def on_message(client, userdata, msg):
        print(f"Received `{msg.payload.decode()}`")

    client.subscribe("#")
    client.on_message = on_message

# To run the main methods of the mqttClient
def run():
    client = connect_mqtt()
    #publish(client)
    subscribe(client)
    client.loop_forever()

# ------------------------------------------ Main Function ---------------------------------------- 
if __name__ == '__main__':
    run()