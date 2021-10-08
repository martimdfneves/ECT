package com.example.esp11;

import java.util.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FighterENV {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String date;
    private String name;
    private String type;
    private String co;
    private String temp;
    private String hgt;
    private String no2;
    private String hum;
    private String lum;
    private String battery;

    public FighterENV(){

    }
    public Integer getId(){
        return id;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getName(){
        return name;
    }
    public String getType(){
        return type;
    }
    public String getCo(){
        return co;
    }
    public String getTemp(){
        return temp;
    }
    public String getHgt(){
        return hgt;
    }
    public String getNo2(){
        return no2;
    }
    public String getHum(){
        return hum;
    }
    public String getLum(){
        return lum;
    }
    public String getBattery(){
        return battery;
    }
    public void setId(){
        this.id = id;
    }

}