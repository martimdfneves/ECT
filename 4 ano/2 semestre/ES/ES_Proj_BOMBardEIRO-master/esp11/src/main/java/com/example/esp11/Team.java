package com.example.esp11;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Team {

    private int time;

    private String[][] fireFighters;

    public Team(){

    }
    public void setTime(){
        this.time = time;
    }

    public void setFireFighters(){
        this.fireFighters = fireFighters;
    }

    public int getTime(){
        return time;
    }

    public String[][] getFighters(){
        return fireFighters;
    }

    @Override
    public String toString() {
        return

                "Value{" +
                        "Time=" + time + "states: " + Arrays.toString(fireFighters[0]) +
                        '}';
    }
}