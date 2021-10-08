package com.example.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Main {

    private double temp;
    private double feels_like;
    private double temp_max;
    private double temp_min;
    private double pressure;
    private double humidity;


    public Main(Double temp, Double feels_like, Double temp_max, Double temp_min, Double pressure, Double humidity) {
        this.temp=temp;
        this.feels_like=feels_like;
        this.temp_max=temp_max;
        this.temp_min=temp_min;
        this.pressure=pressure;
        this.humidity=humidity;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(double feels_like) {
        this.feels_like = feels_like;
    }

    @Override
    public String toString() {
        return "{" +
                "temp='" + temp +
                ", feels_like=" + feels_like +  '\'' +
                '}';
    }
}