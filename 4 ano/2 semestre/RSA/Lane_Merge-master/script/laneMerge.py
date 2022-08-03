# Authors: Tiago Dias   NMEC: 88896
#          Martim Neves NMEC: 88904

from script.obu import OBU
from script.rsu import RSU
import threading
import geopy.distance

# Global variables
rsu_ip = "192.168.98.10"
obu_1_ip = "192.168.98.20"
obu_2_ip = "192.168.98.21"
obu_3_ip = "192.168.98.22"
obu_4_ip = "192.168.98.23"

# Coordinates RSU
rsu_coords = [40.640585, -8.663218]

# Coordinates of OBU_1's first position
obu_1_start = [40.640571, -8.663110]

# Coordinates of OBU_2's first position
obu_2_start = [40.640615, -8.662906]

# Coordinates of OBU_3's first position
obu_3_start = [40.640633, -8.662941]

# Coordinates of OBU_4's first position
obu_4_start = [40.640544, -8.663062]

# ---------------------------------------------------- Lane Merge Class ---------------------------------------------------- 
class LaneMerge(threading.Thread):
    OBUs: list
    rsu: RSU

    # The Lane Merge constructor
    def __init__(self):
        threading.Thread.__init__(self)

        # Create the default OBUs
        obu_1 = OBU(obu_1_ip, 1, obu_1_start, 80, "Driving")
        obu_2 = OBU(obu_2_ip, 2, obu_2_start, 120, "Driving")

        # Create the RSU
        self.rsu = RSU(rsu_ip, 0, rsu_coords)
        
        # An OBU list with all the defaults OBUs created
        self.OBUs = []
        self.OBUs.append(obu_1)
        self.OBUs.append(obu_2)
    
    # The method to run the threads of every OBU and RSU
    def run(self):
        # Create and start the RSU thread
        self.rsu_thread = threading.Thread(target = self.rsu.start)
        self.rsu_thread.start()

        # Create and start the OBUs threads
        self.OBUs_threads = []
        print("\n------------------------------------------------- Start Simulation -------------------------------------------------\n")
        for i in range(0, len(self.OBUs)):
            obu_thread = threading.Thread(target = self.OBUs[i].start)
            self.OBUs_threads.append(obu_thread)
            obu_thread.start()

    # Update the number of OBUs
    def updateNumOfObus(self, numObus):
        # Need to add some OBUs
        if(int(numObus) > len(self.OBUs)):  
            # Add OBU 3
            if(len(self.OBUs) == 2):
                obu_3 = OBU(obu_3_ip, 3, obu_3_start, 120, "Driving")
                self.OBUs.append(obu_3)
            # Add OBU 4
            elif(len(self.OBUs) == 3):
                obu_4 = OBU(obu_4_ip, 4, obu_4_start, 120, "Driving")
                self.OBUs.append(obu_4)

        # Need to remove some OBUs
        elif(int(numObus) < len(self.OBUs)):
            # Removing OBU 3
            if( (len(self.OBUs) >= 3) and (len(self.OBUs) <= 5) ):
                self.OBUs.pop(len(self.OBUs)-1)
            # Removing OBU 5
            if( (len(self.OBUs) >= 4) and (len(self.OBUs) <= 5) ):
                self.OBUs.pop(len(self.OBUs)-1)

    # To get the actual status of the Lane Merge application
    def get_status(self):
        msg = {}
        for obu in self.OBUs:
            msg["OBU_"+str(obu.id)] = obu.obu_status()

        return msg  

    # To reset to the initial state
    def reset(self):
        self.rsu.reset()
        for i in range(0, len(self.OBUs)):
            self.OBUs[i].reset()

    # To stop the simulation
    def stop(self):
        self.rsu.stop()
        for i in range(0, len(self.OBUs)):
            self.OBUs[i].stop()

# ------------------------------------------ Main Function ---------------------------------------- 
if __name__ == '__main__':
    lane_merge = LaneMerge()
    lane_merge_thread = threading.Thread(target = lane_merge.run)
    lane_merge_thread.start()