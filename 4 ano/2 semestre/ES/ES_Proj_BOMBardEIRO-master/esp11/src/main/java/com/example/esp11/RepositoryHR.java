package com.example.esp11;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryHR extends CrudRepository<FighterHR,Integer>{
    List<FighterHR> findByName(String name);

}