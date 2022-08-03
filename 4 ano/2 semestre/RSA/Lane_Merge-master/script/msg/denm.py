# Authors: Tiago Dias   NMEC: 88896
#          Martim Neves NMEC: 88904

# DENM: multi attribute 'ActionID'
class ActionID:
    originating_station_id: int
    sequence_number: int

    def __init__(self, originating_station_id: int, sequence_number: int):
        self.originating_station_id = originating_station_id
        self.sequence_number = sequence_number

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"originatingStationID\":"+str(self.originating_station_id)+",\
                \"sequenceNumber\":"+str(self.sequence_number)+""


# DENM: multi attribute 'Altitude'
class Altitude_DENM:
    altitude_value: int
    altitude_confidence: int

    def __init__(self, altitude_value: int, altitude_confidence: int):
        self.altitude_value = altitude_value
        self.altitude_confidence = altitude_confidence

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"altitudeValue\":"+str(self.altitude_value)+",\
               \"altitudeConfidence\":"+str(self.altitude_confidence)+""

# DENM: multi attribute 'PositionConfidenceEllipse'
class PositionConfidenceEllipse_DENM:
    semi_major_confidence: int
    semi_minor_confidence: int
    semi_major_orientation: int

    def __init__(self, semi_major_confidence: int, semi_minor_confidence: int, 
                 semi_major_orientation: int):
        self.semi_major_confidence = semi_major_confidence
        self.semi_minor_confidence = semi_minor_confidence
        self.semi_major_orientation = semi_major_orientation

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"semiMajorConfidence\":"+str(self.semi_major_confidence)+",\
                \"semiMinorConfidence\":"+str(self.semi_minor_confidence)+",\
                \"semiMajorOrientation\":"+str(self.semi_major_orientation)+""

# DENM: multi attribute 'EventPosition'
class EventPosition:
    latitude: float
    longitude: float
    position_confidence_ellipse: PositionConfidenceEllipse_DENM
    altitude: Altitude_DENM

    def __init__(self, latitude: float, longitude: float, 
                 position_confidence_ellipse: PositionConfidenceEllipse_DENM, altitude: Altitude_DENM):
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

# DENM: multi attribute 'Management'
class Management:
    action_id: ActionID
    detection_time: float
    reference_time: float
    event_position: EventPosition
    validity_duration: int
    station_type: int

    def __init__(self, action_id: ActionID, detection_time: float, reference_time: float, 
                 event_position: EventPosition, validity_duration: int, station_type: int):
        self.action_id = action_id
        self.detection_time = detection_time
        self.reference_time = reference_time
        self.event_position = event_position
        self.validity_duration = validity_duration
        self.station_type = station_type

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"actionID\":{"+repr(self.action_id)+"},\
               \"detectionTime\":"+str(self.detection_time)+",\
               \"referenceTime\":"+str(self.reference_time)+",\
               \"eventPosition\":{"+repr(self.event_position)+"},\
               \"validityDuration\":"+str(self.validity_duration)+",\
               \"stationType\":"+str(self.station_type)+""

# DENM: multi attribute 'EventType'
class EventType:
    cause_code: int
    sub_cause_code: int

    def __init__(self, cause_code: int, sub_cause_code: int):
        self.cause_code = cause_code
        self.sub_cause_code = sub_cause_code

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"causeCode\":"+str(self.cause_code)+",\
               \"subCauseCode\":"+str(self.sub_cause_code)+""

# DENM: multi attribute 'Situation'
class Situation:
    information_quality: int
    event_type: EventType

    def __init__(self, information_quality: int, event_type: EventType):
        self.information_quality = information_quality
        self.event_type = event_type

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"informationQuality\":"+str(self.information_quality)+",\
                \"eventType\":{\
                    "+repr(self.event_type)+"\
                }"

# DENM messages class
class DENM:
    management: Management
    situation: Situation

    def __init__(self, management: Management, situation: Situation):
        self.management = management
        self.situation = situation

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "{\
                \"management\":{"+repr(self.management)+"},\
                \"situation\":{"+repr(self.situation)+"}\
                }"