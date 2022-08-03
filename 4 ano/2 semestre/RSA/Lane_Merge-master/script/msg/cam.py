# Authors: Tiago Dias   NMEC: 88896
#          Martim Neves NMEC: 88904

# CAM: multi attribute 'PublicTransportContainer'
class PublicTransportContainer:
    embarkation_status: bool

    def __init__(self, embarkation_status: bool):
        self.embarkation_status = embarkation_status

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "\"publicTransportContainer\":{\
                    \"embarkationStatus\":"+str(self.embarkation_status).lower()+"\
                }"

# CAM: multi attribute 'SpecialVehicle'
class SpecialVehicle:
    public_transport_container: PublicTransportContainer

    def __init__(self, public_transport_container: PublicTransportContainer):
        self.public_transport_container = public_transport_container

    # Developer-friendly string representation of the object
    def __repr__(self):
        return repr(self.public_transport_container)

# CAM messages class
class CAM:
    acc_engaged: bool
    acceleration: int
    altitude: int
    altitude_conf: int
    brake_pedal: bool
    collision_warning: bool
    cruise_control: bool
    curvature: int
    drive_direction: str
    emergency_brake: bool
    gas_pedal: bool
    heading: int
    heading_conf: int
    latitude: float
    length: int
    longitude: float
    semi_major_conf: int
    semi_major_orient: int
    semi_minor_conf: int
    special_vehicle: SpecialVehicle
    speed: int
    speed_conf: int
    speed_limiter: bool
    station_id: int
    station_type: int
    width: int
    yaw_rate: int

    def __init__(self, acc_engaged: bool, acceleration: int, altitude: int, altitude_conf: int, 
                 brake_pedal: bool, collision_warning: bool, cruise_control: bool, curvature: int, 
                 drive_direction: str, emergency_brake: bool, gas_pedal: bool, heading: int, 
                 heading_conf: int, latitude: float, length: int, longitude: float, semi_major_conf: int, 
                 semi_major_orient: int, semi_minor_conf: int, special_vehicle: SpecialVehicle, 
                 speed: int, speed_conf: int, speed_limiter: bool, station_id: int, station_type: int, 
                 width: int, yaw_rate: int):

        self.acc_engaged = acc_engaged
        self.acceleration = acceleration
        self.altitude = altitude
        self.altitude_conf = altitude_conf
        self.brake_pedal = brake_pedal
        self.collision_warning = collision_warning
        self.cruise_control = cruise_control
        self.curvature = curvature
        self.drive_direction = drive_direction
        self.emergency_brake = emergency_brake
        self.gas_pedal = gas_pedal
        self.heading = heading
        self.heading_conf = heading_conf
        self.latitude = latitude
        self.length = length
        self.longitude = longitude
        self.semi_major_conf = semi_major_conf
        self.semi_major_orient = semi_major_orient
        self.semi_minor_conf = semi_minor_conf
        self.special_vehicle = special_vehicle
        self.speed = speed
        self.speed_conf = speed_conf
        self.speed_limiter = speed_limiter
        self.station_id = station_id
        self.station_type = station_type
        self.width = width
        self.yaw_rate = yaw_rate

    # Developer-friendly string representation of the object
    def __repr__(self):
        return "{\
                \"accEngaged\":"+str(self.acc_engaged).lower()+",\
                \"acceleration\":"+str(self.acceleration)+",\
                \"altitude\":"+str(self.altitude)+",\
                \"altitudeConf\":"+str(self.altitude_conf)+",\
                \"brakePedal\":"+str(self.brake_pedal).lower()+",\
                \"collisionWarning\":"+str(self.collision_warning).lower()+",\
                \"cruiseControl\":"+str(self.cruise_control).lower()+",\
                \"curvature\":"+str(self.curvature)+",\
                \"driveDirection\":\""+str(self.drive_direction)+"\",\
                \"emergencyBrake\":"+str(self.emergency_brake).lower()+",\
                \"gasPedal\":"+str(self.gas_pedal).lower()+",\
                \"heading\":"+str(self.heading)+",\
                \"headingConf\":"+str(self.heading_conf)+",\
                \"latitude\":"+str(self.latitude * (10**7))+",\
                \"length\":"+str(self.length)+",\
                \"longitude\":"+str(self.longitude * (10**7))+",\
                \"semiMajorConf\":"+str(self.semi_major_conf)+",\
                \"semiMajorOrient\":"+str(self.semi_major_orient)+",\
                \"semiMinorConf\":"+str(self.semi_minor_conf)+",\
                \"specialVehicle\":{"+repr(self.special_vehicle)+"},\
                \"speed\":"+str(self.speed)+",\
                \"speedConf\":"+str(self.speed_conf)+",\
                \"speedLimiter\":"+str(self.speed_limiter).lower()+",\
                \"stationID\":"+str(self.station_id)+",\
                \"stationType\":"+str(self.station_type)+",\
                \"width\":"+str(self.width)+",\
                \"yawRate\":"+str(self.yaw_rate)+"\
            }"