package com.mirea.Item.services;

import com.mirea.Item.model.Stuff;
import org.springframework.stereotype.Component;
import com.mirea.Item.repository.StuffRepository;

import java.util.List;

@Component
public class StuffService {
    private final StuffRepository repository;

    StuffService(StuffRepository repository) {
        this.repository = repository;
    }

    public List<Stuff> getAllStuff() {
        return repository.findAll();
    }

    public void addStuff(int count, String name, String type, int price) {
        repository.save(new Stuff(count, name, type, price));
    }

    public Stuff getItem(int id) {
        return repository.findStuffById(id);
    }

}
