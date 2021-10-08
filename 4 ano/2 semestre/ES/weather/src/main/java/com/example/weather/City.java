package com.example.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class City {

    private String name;
    private Main main;


    public City(String name, Main main) {
        this.name=name;
        this.main=main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @Override
    public String toString() {
        return "OpenWeather{" +
                "name='" + name + '\'' +
                ", temperature=" + main +
                '}';
    }
}