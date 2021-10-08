package com.example.esp11;

import java.util.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FighterGPS {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String date;
    private String name;
    private String type;
    private String gps_alt_tag;
    private double gps_tag_lat;
    private double gps_tag_long;
    private String gps_time_tag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }


    public void setDate(String date){
        this.date = date;
    }

    /*public void setDate(Date date) {
        String d = date.toString();
        String[] temp = d.split(" ");
        String[] date_tmp = temp[0].split("/");
        String[] hour_tmp = temp[1].split(":");

        Date tmp = new Date(Integer.parseInt(date_tmp[2]), Integer.parseInt(date_tmp[1]), Integer.parseInt(date_tmp[0]), Integer.parseInt(hour_tmp[1]), Integer.parseInt(hour_tmp[0]));
        System.out.println("DATA E HORA -> " + tmp);
        this.date = tmp;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FighterGPS() {
    }

    public String getType() {
        return type;
    }

    public String getGps_alt_tag() {
        return gps_alt_tag;
    }

    public double getGps_tag_lat() {
        return gps_tag_lat;
    }

    public double getGps_tag_long() {
        return gps_tag_long;
    }

    public String getGps_time_tag() {
        return gps_time_tag;
    }
}