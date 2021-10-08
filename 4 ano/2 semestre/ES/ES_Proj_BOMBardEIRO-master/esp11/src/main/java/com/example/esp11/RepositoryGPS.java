package com.example.esp11;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryGPS extends CrudRepository<FighterGPS,Integer>{

    List<FighterGPS> findByName(String name);
}