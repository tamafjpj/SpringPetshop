package com.mirea.Item.repository;

import com.mirea.Item.model.Stuff;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StuffRepository extends CrudRepository<Stuff, Integer> {
    Stuff findStuffById(int id);

    List<Stuff> findAll();
}