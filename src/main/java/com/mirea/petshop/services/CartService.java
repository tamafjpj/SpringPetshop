package com.mirea.petshop.services;

import com.mirea.petshop.model.*;
import com.mirea.petshop.repository.PersonRepository;
import com.mirea.petshop.repository.PetsRepository;
import com.mirea.petshop.repository.StuffRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.mirea.petshop.repository.CartRepository;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class CartService {

    private final CartRepository repository;
    private final PetsRepository petRep;
    private final StuffRepository stuffRep;
    private final PersonRepository personRep;

    CartService(CartRepository repository, PetsRepository petRep, StuffRepository stuffRep, PersonRepository personRep) {
        this.petRep = petRep;
        this.stuffRep = stuffRep;
        this.repository = repository;
        this.personRep = personRep;
    }

    public void addElem(int itemId) {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        repository.save(new Cart(person.getId(), itemId));
    }

    public void deleteElem(int itemId) {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        repository.deleteByPersonIdAndItemId(person.getId(), itemId);
    }

    public List<Cart> getAllItems() {
        return repository.findAll();
    }

    public List<Cart> getAllItemsbyPersonId(int personId) {
        return repository.findAllByPersonId(personId);
    }

    @Transactional
    public Status purchaseItems() {
        Status status = new Status();
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int totalPrice = 0;
        for (Cart item : repository.findAllByPersonId(person.getId())) {
            Pet pet = petRep.findPetById(item.getItemId());
            Stuff stuff = stuffRep.findStuffById(item.getItemId());
            if (pet != null) totalPrice += pet.getPrice();
            if (stuff != null) totalPrice += stuff.getPrice();
        }
        if (totalPrice > 0) {
            if (person.getBalance() < totalPrice) {
                status.setStatus("error");
                status.setDescription("Not enough funds");
                return status;
            } else {
                person.setBalance(person.getBalance() - totalPrice);
                for (Cart item : repository.findAllByPersonId(person.getId())) {
                    Pet pet = petRep.findPetById(item.getItemId());
                    Stuff stuff = stuffRep.findStuffById(item.getItemId());
                    if (pet != null) {
                        pet.setCount(pet.getCount() - 1);
                        petRep.save(pet);
                        repository.deleteByPersonIdAndItemId(person.getId(), item.getItemId());
                    }
                    if (stuff != null) {
                        stuff.setCount(stuff.getCount() - 1);
                        stuffRep.save(stuff);
                        repository.deleteByPersonIdAndItemId(person.getId(), item.getItemId());
                    }
                }
                person.setBalance(person.getBalance() - totalPrice);
                personRep.save(person);
                status.setStatus("ok");
                status.setDescription("Items successfully purchased");
            }
        } else {
            status.setStatus("error");
            status.setDescription("Cart is empty");
        }
        return status;
    }

}