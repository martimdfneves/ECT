# Authors: Tiago Dias   NMEC: 88896
#          Martim Neves NMEC: 88904

from typing import Optional, List
from xmlrpc.client import Boolean, boolean

# CPM: multi attribute 'Altitude_CPM'
class Altitude_CPM:
    altitude_value: float
    altitude_confidence: int

    def __init__(self, altitude_value: float, altitude_confidence: int):
        self.altitude_value = altitude_value
        self.altitude_confidence = altitude_confidence

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"altitudeValue\":"+str(self.altitude_value)+",\
                \"altitudeConfidence\":"+str(self.altitude_confidence)+""

# CPM: multi attribute 'PositionConfidenceEllipse_CPM'
class PositionConfidenceEllipse_CPM:
    semi_major_confidence: int
    semi_minor_confidence: int
    semi_major_orientation: float

    def __init__(self, semi_major_confidence: int, semi_minor_confidence: int, 
                 semi_major_orientation: float):
        self.semi_major_confidence = semi_major_confidence
        self.semi_minor_confidence = semi_minor_confidence
        self.semi_major_orientation = semi_major_orientation

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"semiMajorConfidence\":"+str(self.semi_major_confidence)+",\
               \"semiMinorConfidence\":"+str(self.semi_minor_confidence)+",\
               \"semiMajorOrientation\":"+str(self.semi_major_orientation)+""

# CPM: multi attribute 'ReferencePosition'
class ReferencePosition:
    latitude: float
    longitude: float
    position_confidence_ellipse: PositionConfidenceEllipse_CPM
    altitude: Altitude_CPM

    def __init__(self, latitude: float, longitude: float, 
                 position_confidence_ellipse: PositionConfidenceEllipse_CPM, altitude: Altitude_CPM):
        self.latitude = latitude
        self.longitude = longitude
        self.position_confidence_ellipse = position_confidence_ellipse
        self.altitude = altitude

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"latitude\":"+str(self.latitude * (10**7))+",\
                \"longitude\":"+str(self.longitude * (10**7))+",\
                \"positionConfidenceEllipse\":{"+repr(self.position_confidence_ellipse)+"},\
                \"altitude\":{"+repr(self.altitude)+"}"

# CPM: multi attribute 'Other'
class Other:
    type: int
    confidence: int

    def __init__(self, type: int, confidence: int):
        self.type = type
        self.confidence = confidence

     # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"type\":"+str(self.type)+",\
                \"confidence\":"+str(self.confidence)

# CPM: multi attribute 'Vehicle'
class Vehicle:
    type: int
    confidence: int

    def __init__(self, type: int, confidence: int):
        self.type = type
        self.confidence = confidence

     # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"type\":"+str(self.type)+",\
                \"confidence\":"+str(self.confidence)

# CPM: multi attribute 'Class'
class Class:
    vehicle: Optional[Vehicle]
    other: Optional[Other]

    def __init__(self, vehicle = None, other = None):
        self.vehicle = vehicle
        self.other = other

    # Developer-friendly string representation of the object
    def __repr__(self):
        # If it has the 'vehicle' atribute
        if (self.vehicle != None):
            return "\"vehicle\":{"+repr(self.vehicle)+"}"

        # If it has the 'other' atribute
        return "\"other\":{"+repr(self.other)+"}"

# CPM: multi attribute 'Classification'
class Classification:
    confidence: int
    classification_class: Class

    def __init__(self, confidence: int, classification_class: Class):
        self.confidence = confidence
        self.classification_class = classification_class

    def __iter__(self):
        return self

    def __next__(self):
        return repr(self)

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "{\
                \"confidence\":"+str(self.confidence)+",\
                \"class\":{"+repr(self.classification_class)+"}\
                }"

# CPM: multi attribute 'XAcceleration'
class XAcceleration:
    longitudinal_acceleration_value: float
    longitudinal_acceleration_confidence: int

    def __init__(self, longitudinal_acceleration_value: float, 
                longitudinal_acceleration_confidence: int):
        self.longitudinal_acceleration_value = longitudinal_acceleration_value
        self.longitudinal_acceleration_confidence = longitudinal_acceleration_confidence

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"longitudinalAccelerationValue\":"+str(self.longitudinal_acceleration_value)+",\
                \"longitudinalAccelerationConfidence\":"+str(self.longitudinal_acceleration_confidence)

# CPM: multi attribute 'Axis' -> xDistance, yDistance, xSpeed and ySpeed
class Axis:
    value: float
    confidence: int

    def __init__(self, value: float, confidence: int):
        self.value = value
        self.confidence = confidence

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"value\":"+str(self.value)+",\
                \"confidence\":"+str(self.confidence)

# CPM: multi attribute 'YAcceleration'
class YAcceleration:
    lateral_acceleration_value: float
    lateral_acceleration_confidence: int

    def __init__(self, lateral_acceleration_value: float, lateral_acceleration_confidence: int):
        self.lateral_acceleration_value = lateral_acceleration_value
        self.lateral_acceleration_confidence = lateral_acceleration_confidence

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"lateralAccelerationValue\":"+str(self.lateral_acceleration_value)+",\
                \"lateralAccelerationConfidence\":"+str(self.lateral_acceleration_confidence)

# CPM: multi attribute 'StationarySensorRadial'
class StationarySensorRadial:
    range: int
    stationary_horizontal_opening_angle_start: int
    stationary_horizontal_opening_angle_end: int

    def __init__(self, range: int, stationary_horizontal_opening_angle_start: int, 
                stationary_horizontal_opening_angle_end: int):
        self.range = range
        self.stationary_horizontal_opening_angle_start = stationary_horizontal_opening_angle_start
        self.stationary_horizontal_opening_angle_end = stationary_horizontal_opening_angle_end

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"range\":"+str(self.range)+",\
                \"stationaryHorizontalOpeningAngleStart\":"+str(self.stationary_horizontal_opening_angle_start)+",\
                \"stationaryHorizontalOpeningAngleEnd\":"+str(self.stationary_horizontal_opening_angle_end)+""

# CPM: multi attribute 'StationarySensorRectangle'
class StationarySensorRectangle:
    semi_major_range_length: int
    semi_minor_range_length: int
    semi_major_range_orientation: int

    def __init__(self, semi_major_range_length: int, semi_minor_range_length: int, 
                semi_major_range_orientation: int):
        self.semi_major_range_length = semi_major_range_length
        self.semi_minor_range_length = semi_minor_range_length
        self.semi_major_range_orientation = semi_major_range_orientation

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"semiMajorRangeLength\":"+str(self.semi_major_range_length)+",\
                \"semiMinorRangeLength\":"+str(self.semi_minor_range_length)+",\
                \"semiMajorRangeOrientation\":"+str(self.semi_major_range_orientation)+""

# CPM: multi attribute 'DetectionArea'
class DetectionArea:
    stationary_sensor_rectangle: Optional[StationarySensorRectangle]
    stationary_sensor_radial: Optional[StationarySensorRadial]
    
    def __init__(self, stationary_sensor_rectangle = None, stationary_sensor_radial = None):
        self.stationary_sensor_rectangle = stationary_sensor_rectangle
        self.stationary_sensor_radial = stationary_sensor_radial
        
    # Developer-friendly string representation of the object
    def __repr__(self):
        # If it has the 'stationary_sensor_rectangle' atribute
        if(self.stationary_sensor_rectangle != None):  
            return "\"stationarySensorRectangle\": {"+repr(self.stationary_sensor_rectangle)+"}}"

        # If it has the 'stationary_sensor_radial' atribute
        if(self.stationary_sensor_radial != None):
            return "\"stationarySensorRadial\": {"+repr(self.stationary_sensor_radial)+"}}"

# CPM: multi attribute 'Heading'
class Heading:
    heading_value: int
    heading_confidence: int

    def __init__(self, heading_value: int, heading_confidence: int):
        self.heading_value = heading_value
        self.heading_confidence = heading_confidence

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"headingValue\":"+str(self.heading_value)+",\
               \"headingConfidence\":"+str(self.heading_confidence)

# CPM: multi attribute 'Speed'
class Speed:
    speed_value: int
    speed_confidence: int

    def __init__(self, speed_value: int, speed_confidence: int):
        self.speed_value = speed_value
        self.speed_confidence = speed_confidence

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"speedValue\":"+str(self.speed_value)+",\
                \"speedConfidence\":"+str(self.speed_confidence)

# CPM: multi attribute 'OriginatingVehicleContainer'
class OriginatingVehicleContainer:
    heading: Heading
    speed: Speed
    drive_direction: int

    def __init__(self, heading: Heading, speed: Speed, drive_direction: int):
        self.heading = heading
        self.speed = speed
        self.drive_direction = drive_direction

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"heading\":{"+repr(self.heading)+"},\
                \"speed\":{"+repr(self.speed)+"},\
                \"driveDirection\":"+str(self.drive_direction)

# CPM: multi attribute 'PerceivedObjectContainer'
class PerceivedObjectContainer:
    object_id: int
    time_of_measurement: int
    object_confidence: int
    x_distance: Axis
    y_distance: Axis
    x_speed: Axis
    y_speed: Axis
    x_acceleration: XAcceleration
    y_acceleration: YAcceleration
    object_ref_point: int
    classification: List[Classification]

    def __init__(self, object_id: int, time_of_measurement: int, object_confidence: int, 
                x_distance: Axis, y_distance: Axis, x_speed: Axis, y_speed: Axis, 
                x_acceleration: XAcceleration, y_acceleration: YAcceleration, object_ref_point: int, 
                classification: List[Classification]):
        self.object_id = object_id
        self.time_of_measurement = time_of_measurement
        self.object_confidence = object_confidence
        self.x_distance = x_distance
        self.y_distance = y_distance
        self.x_speed = x_speed
        self.y_speed = y_speed
        self.x_acceleration = x_acceleration
        self.y_acceleration = y_acceleration
        self.object_ref_point = object_ref_point
        self.classification = classification

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "{\"objectID\":"+str(self.object_id)+",\
                \"timeOfMeasurement\":"+str(self.time_of_measurement)+",\
                \"objectConfidence\":"+str(self.object_confidence)+",\
                \"xDistance\":{"+repr(self.x_distance)+"},\
                \"yDistance\":{"+repr(self.y_distance)+"},\
                \"xSpeed\":{"+repr(self.x_speed)+"},\
                \"ySpeed\":{"+repr(self.y_speed)+"},\
                \"xAcceleration\":{"+repr(self.x_acceleration)+"},\
                \"yAcceleration\":{"+repr(self.y_acceleration)+"},\
                \"objectRefPoint\":"+str(self.object_ref_point)+",\
                \"classification\":"+repr(self.classification)+"}"

# CPM: multi attribute 'SensorInformationContainer'
class SensorInformationContainer:
    sensor_id: int
    type: int
    detection_area: DetectionArea

    def __init__(self, sensor_id: int, type: int, detection_area: DetectionArea):
        self.sensor_id = sensor_id
        self.type = type
        self.detection_area = detection_area

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "{\"sensorID\":"+str(self.sensor_id)+",\
               \"type\":"+str(self.type)+",\
               \"detectionArea\":{"+repr(self.detection_area)+"}"

# CPM: multi attribute 'StationDataContainer'
class StationDataContainer:
    originating_vehicle_container: OriginatingVehicleContainer

    def __init__(self, originating_vehicle_container: OriginatingVehicleContainer):
        self.originating_vehicle_container = originating_vehicle_container

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"originatingVehicleContainer\":{"+repr(self.originating_vehicle_container)+"}"

# CPM: multi attribute 'ManagementContainer'
class ManagementContainer:
    station_type: int
    reference_position: ReferencePosition

    def __init__(self, station_type: int, reference_position: ReferencePosition):
        self.station_type = station_type
        self.reference_position = reference_position

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"stationType\":"+str(self.station_type)+",\
                \"referencePosition\":{"+repr(self.reference_position)+"}"

# CPM: multi attribute 'CpmParameters'
class CpmParameters:
    management_container: ManagementContainer
    station_data_container: StationDataContainer
    sensor_information_container: List[SensorInformationContainer]
    perceived_object_container: List[PerceivedObjectContainer]
    number_of_perceived_objects: int

    def __init__(self, management_container: ManagementContainer, 
                station_data_container: StationDataContainer, 
                sensor_information_container: List[SensorInformationContainer], 
                perceived_object_container: List[PerceivedObjectContainer], 
                number_of_perceived_objects: int):
        self.management_container = management_container
        self.station_data_container = station_data_container
        self.sensor_information_container = sensor_information_container
        self.perceived_object_container = perceived_object_container
        self.number_of_perceived_objects = number_of_perceived_objects
    
    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"managementContainer\":{"+repr(self.management_container)+"},\
                \"stationDataContainer\":{"+repr(self.station_data_container)+"},\
                \"sensorInformationContainer\":"+repr(self.sensor_information_container)+",\
                \"perceivedObjectContainer\":"+repr(self.perceived_object_container)+",\
                \t  \"numberOfPerceivedObjects\":"+str(self.number_of_perceived_objects)

# CPM messages class
class CPM:
    generation_delta_time: int
    cpm_parameters: CpmParameters

    def __init__(self, generation_delta_time: int, cpm_parameters: CpmParameters):
        self.generation_delta_time = generation_delta_time
        self.cpm_parameters = cpm_parameters

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "{\
                \"generationDeltaTime\": "+str(self.generation_delta_time)+",\
                \"cpmParameters\": {"+repr(self.cpm_parameters)+"}\
                }"
