package com.example.esp11.Consumer;

import com.example.esp11.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.json.JSONObject;
import com.google.gson.Gson;

@Component
public class KafkaConsumer {

    @Autowired
    private RepositoryGPS repo_gps;
    @Autowired
    private RepositoryENV repo_env;
    @Autowired
    private RepositoryHR repo_hr;

    @KafkaListener(topics = "esp11_hr", groupId = "group_id")
    public void consume_HR(String message) throws JsonProcessingException {
        JSONObject jsonObj = new JSONObject(message);
        Gson gson = new Gson();

        FighterHR f = gson.fromJson(jsonObj.toString(), FighterHR.class);
        f.setDate(String.format("%d", (int)Double.parseDouble(f.getDate())));
        repo_hr.save(f);

        System.out.println("message = " + message);
    }

    @KafkaListener(topics = "esp11_gps", groupId = "group_id")
    public void consume_GPS(String message) throws JsonProcessingException{
        JSONObject jsonObj = new JSONObject(message);
        Gson gson = new Gson();

        FighterGPS f = gson.fromJson(jsonObj.toString(), FighterGPS.class);
        f.setDate(String.format("%d", (int)Double.parseDouble(f.getDate())));
        repo_gps.save(f);

        System.out.println("message = " + message);
    }

    @KafkaListener(topics = "esp11_env", groupId = "group_id")
    public void consume_ENV(String message) throws JsonProcessingException{
        JSONObject jsonObj = new JSONObject(message);
        Gson gson = new Gson();

        FighterENV f = gson.fromJson(jsonObj.toString(), FighterENV.class);
        f.setDate(String.format("%d", (int)Double.parseDouble(f.getDate())));
        repo_env.save(f);

        System.out.println("message = " + message);
    }




}
