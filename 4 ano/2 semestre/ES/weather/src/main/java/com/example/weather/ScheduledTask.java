package com.example.weather;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduledTask {

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    protected static OpenWeatherResult weather;

    @Scheduled(fixedRate = 60000)
    public void reportWeather() {
        weather = restTemplate.getForObject(
                "http://api.openweathermap.org/data/2.5/find?lat=40.728188&lon=-8.174227&cnt=50&appid=349edc396e975de542dbc24b4d939aa7&units=metric", OpenWeatherResult.class);
        log.info(weather.toString());
    }
}
