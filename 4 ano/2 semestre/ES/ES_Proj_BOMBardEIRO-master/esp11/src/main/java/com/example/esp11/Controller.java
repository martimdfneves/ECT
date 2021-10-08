package com.example.esp11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import com.google.common.collect.Iterables;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "*")
@RestController
public class Controller {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RepositoryENV repo_env;
    @Autowired
    private RepositoryGPS repo_gps;
    @Autowired
    private RepositoryHR repo_hr;
    @Autowired
    private KafkaTemplate<String, String> kafkaTmp;

    public final String origin_dev = "http://localhost:3000";

    String[] array = {"a1","a2","vr12"};

    @GetMapping("/welcome")
    public String welcome(){
        return "ESP11 BOMBEIROS SIGA SIGA";
    }

    @GetMapping("/fighters/gps")
    public String fightersLocation() throws JsonProcessingException {

        ObjectMapper map = new ObjectMapper();
        List<String> l = new ArrayList<String>();

        for (int i = 0; i < array.length;i++) {

            List<FighterGPS> list = repo_gps.findByName(array[i]);

            if(list.size() > 0){
                FighterGPS last = list.get(list.size() - 1);
                l.add(map.writeValueAsString(last));
            }
        }

        Object[] array = l.toArray();
        String str = Arrays.toString(array);

        return str;
    }

    @GetMapping("/fighters/env")
    public String fightersEnvironment() throws JsonProcessingException {

        ObjectMapper map = new ObjectMapper();
        List<String> l = new ArrayList<String>();

        for (int i = 0; i < array.length;i++) {

            List<FighterENV> list = repo_env.findByName(array[i]);

            if(list.size() > 0){
                FighterENV last = list.get(list.size() - 1);
                l.add(map.writeValueAsString(last));

            }

        }

        Object[] array = l.toArray();
        String str = Arrays.toString(array);

        return str;
    }

    @GetMapping("/fighters/hr")
    public String fightersHeartRate() throws JsonProcessingException {

        ObjectMapper map = new ObjectMapper();
        List<String> l = new ArrayList<String>();

        for (int i = 0; i < array.length;i++) {

            List<FighterHR> list = repo_hr.findByName(array[i]);

            if(list.size() > 0){
                FighterHR last = list.get(list.size() - 1);
                l.add(map.writeValueAsString(last));

            }

        }

        Object[] array = l.toArray();
        String strr = Arrays.toString(array);

        return strr;
    }

    @GetMapping("/fighters/all")
    public String fightersINFO() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        List<String> actual = new ArrayList<>();

        for (int i = 0; i < array.length;i++) {
            List<FighterGPS> gps = repo_gps.findByName(array[i]);
            List<FighterENV> env = repo_env.findByName(array[i]);
            List<FighterHR> hr = repo_hr.findByName(array[i]);

            if(gps.size() > 0){

                MessageFormat fighter = new MessageFormat();
                FighterGPS last_gps = gps.get(gps.size() - 1);
                fighter.setID(array[i]);
                fighter.setGPS(last_gps);

                if(env.size() > 0){
                    FighterENV last_env = env.get(env.size() - 1);

                    fighter.setENV(last_env);
                }
                if(hr.size() > 0){
                    FighterHR last_hr = hr.get(hr.size() - 1);
                    fighter.setHR(last_hr);
                }
                actual.add(mapper.writeValueAsString(fighter));
            }
        }
        Object[] arr2 = actual.toArray();
        String send = Arrays.toString(arr2);

        return send;
    }

    @GetMapping("/fighters/gpsInfo")
    public String fightersGPS() throws JsonProcessingException {


        ObjectMapper mapper = new ObjectMapper();
        Iterable<FighterGPS> ff = repo_gps.findAll();//.forEach(x -> log.info(x.toString()));
        FighterGPS[] fs = Iterables.toArray(ff, FighterGPS.class);
        String[] gps = new String[fs.length];
        for(int i =0; i < fs.length;i++){
            gps[i] = mapper.writeValueAsString(fs[i]);
        }
        String str = Arrays.toString(gps);
        return str;
    }

    @GetMapping("/fighters/hrInfo")
    public String fightersHR() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Iterable<FighterHR> ff = repo_hr.findAll();//.forEach(x -> log.info(x.toString()));
        FighterHR[] fs = Iterables.toArray(ff, FighterHR.class);
        String[] hr = new String[fs.length];
        for(int i =0; i < fs.length;i++){
            hr[i] = mapper.writeValueAsString(fs[i]);
        }
        String str = Arrays.toString(hr);
        return str;
    }

    @GetMapping("/fighters/envInfo")
    public String fightersENV() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Iterable<FighterENV> ff = repo_env.findAll();//.forEach(x -> log.info(x.toString()));
        FighterENV[] fs = Iterables.toArray(ff, FighterENV.class);
        String[] env = new String[fs.length];
        for(int i =0; i < fs.length;i++){
            env[i] = mapper.writeValueAsString(fs[i]);
        }
        String str = Arrays.toString(env);
        return str;

    }



}
