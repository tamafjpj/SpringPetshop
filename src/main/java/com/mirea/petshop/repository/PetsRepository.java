package com.mirea.petshop.repository;

import com.mirea.petshop.model.Pet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PetsRepository extends CrudRepository<Pet, Integer> {
    Pet findPetById(int id);

    List<Pet> findAll();
}
