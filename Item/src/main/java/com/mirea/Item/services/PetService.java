package com.mirea.Item.services;

import com.mirea.Item.model.Pet;
import org.springframework.stereotype.Component;
import com.mirea.Item.repository.PetsRepository;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class PetService {
    private final PetsRepository repository;

    public PetService(PetsRepository repository) {
        this.repository = repository;
    }

    public List<Pet> getAllPets() {
        return repository.findAll();
    }

    public Pet getPet(int id) {
        return repository.findPetById(id);
    }

    @Transactional
    public void addPet(int count, String type, int price) {
        repository.save(new Pet(count, type, price));
    }
}
