package com.mirea.Auth.repository;

import com.mirea.Auth.model.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface PersonRepository extends CrudRepository<Person, Integer> {

    List<Person> findAll();

    Optional<Person> findById(int id);

    Optional<Person> findByUsername(String username);
}
