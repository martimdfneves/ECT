package com.example.esp11;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryENV extends CrudRepository<FighterENV,Integer> {
    List<FighterENV> findByName(String name);
}
