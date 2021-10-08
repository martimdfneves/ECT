package com.example.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.emitter.ScalarAnalysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WeatherController {

    private static final Logger log = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/")
    public ModelAndView getTemp(){

        String viewName = "index";

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("OpenWeatherResult", ScheduledTask.weather.getList());

        return new ModelAndView(viewName, model);
    }
}