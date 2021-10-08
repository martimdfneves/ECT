package com.example.weather;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherResult {

    private List<City> list;
    private String message;
    private String cod;
    private int count;

    public OpenWeatherResult(List<City> list, String message, String cod, int count) {
        this.list = list;
        this.message = message;
        this.cod = cod;
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public String getCod() {
        return cod;
    }

    public int getCount() {
        return count;
    }

    public List<City> getList() {
        return new ArrayList<City>(list);
    }

    @Override
    public String toString() {
        return "OpenWeatherResult{" +
                "list=" + list +
                '}';
    }
}
