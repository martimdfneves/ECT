package com.example.esp11;

public class MessageFormat {

    private String id;
    private FighterGPS gps;
    private FighterENV env;
    private FighterHR hr;

    public MessageFormat(){

    }
    public String getId(){
        return id;
    }
    public FighterGPS getGPS(){
        return gps;
    }
    public FighterENV getENV(){
        return env;
    }
    public FighterHR getHR(){
        return hr;
    }
    public void setID(String id){
        this.id = id;
    }
    public void setGPS(FighterGPS gps){
        this.gps = gps;
    }
    public void setENV(FighterENV env){
        this.env = env;
    }public void setHR(FighterHR hr){
        this.hr = hr;
    }


}