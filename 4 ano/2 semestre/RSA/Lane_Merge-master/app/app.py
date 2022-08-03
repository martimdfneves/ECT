# Authors: Tiago Dias   NMEC: 88896
#          Martim Neves NMEC: 88904

from flask import Flask, render_template, jsonify, request
from script.laneMerge import LaneMerge
import threading, sys

app = Flask(__name__, static_url_path='/static')

lane_merge = LaneMerge()
lane_merge_thread = threading.Thread(target = lane_merge.run)

# The homepage of the web app
@app.route("/")
def home():
    # 1000ms = 1s refresh rate
    refresh_rate = 1000

    return render_template("index.html", refresh_rate = refresh_rate, rsu_pos = lane_merge.rsu.rsu_coords,
                           obu_1_start_pos = lane_merge.OBUs[0].start_pos, obu_2_start_pos = lane_merge.OBUs[1].start_pos)

# To update the actual state of the web app
@app.route("/update_map", methods=["GET", "POST"])
def update_map():
    status = lane_merge.get_status()
    return jsonify(status)

# To update the number of OBUs in the simulation
@app.route("/update_num_obus", methods=["POST"])
def updateNumOfObus():
    lane_merge.stop()
    lane_merge.reset()
    lane_merge.updateNumOfObus(request.form["msg"])
    
    # To not return the HHTP error code 500
    return ""

# The interaction between the action buttons
@app.route("/update_start_state", methods=["POST"])
def update_start_state():
    # When the start button is pressed
    if (request.form["msg"] == "Start Pressed"): 
        # The first interaction
        if(int(request.form["count"]) == 1):
            lane_merge_thread.start()
        
        # The even interaction
        elif(int(request.form["count"]) % 2 == 0):
            new_thread = threading.Thread(target = lane_merge.run)
            new_thread.start()

        # The odd interaction
        elif( (int(request.form["count"]) % 2 != 0) and (int(request.form["count"]) > 1) ):
            new_thread_2 = threading.Thread(target = lane_merge.run)
            new_thread_2.start()

    # When the stop button is pressed
    elif (request.form["msg"] == "Stop Pressed"):
        lane_merge.stop()

    # When the reset button is pressed
    elif (request.form["msg"] == "Reset Pressed"):
        lane_merge.reset()

    # To not return the HHTP error code 500
    return "" 

# To run the web app
if __name__ == "__main__":
    app.run(debug=True, threaded=True)