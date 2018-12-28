package com.mirea.Cart.services;

import com.mirea.Cart.model.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.mirea.Cart.repository.CartRepository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class CartService {

    private final CartRepository repository;
    private RestTemplate restTemplate;
    CartService(CartRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public void addElem(int itemId,String token) {
        HttpHeaders authHeader = new HttpHeaders();
        System.out.println(token);
        authHeader.set("Authorization",token);
        HttpEntity entity = new HttpEntity(authHeader);
        ResponseEntity<Person> re = restTemplate.exchange("http://auth-service/person", HttpMethod.GET,entity,Person.class);
        Person person = re.getBody();
        repository.save(new Cart(person.getId(), itemId));
    }

    public void deleteElem(int itemId,String token) {
        HttpHeaders authHeader = new HttpHeaders();
        authHeader.set("Authorization",token);
        HttpEntity entity = new HttpEntity(authHeader);
        ResponseEntity<Person> re = restTemplate.exchange("http://auth-service/person", HttpMethod.GET,entity,Person.class);
        Person person = re.getBody();
        repository.deleteByPersonIdAndItemId(person.getId(), itemId);
    }

    public List<Cart> getAllItems() {
        return repository.findAll();
    }

    public List<Cart> getAllItemsbyPersonId(int personId) {
        return repository.findAllByPersonId(personId);
    }

    @Transactional
    public Status purchaseItems(String token) {
        HttpHeaders authHeader = new HttpHeaders();
        authHeader.set("Authorization",token);
        HttpEntity entity = new HttpEntity(authHeader);
        Status status = new Status();
        ResponseEntity<Person> re = restTemplate.exchange("http://auth-service/person", HttpMethod.GET,entity,Person.class);
        Person person = re.getBody();
        int totalPrice = 0;
        for (Cart item : repository.findAllByPersonId(person.getId())) {
            ResponseEntity<Pet> pe = restTemplate.exchange("http://items-service/pet"+item.getItemId(),
                    HttpMethod.GET,entity,Pet.class);
            Pet pet = pe.getBody();
            ResponseEntity<Stuff> se = restTemplate.exchange("http://items-service/stuff"+item.getItemId(),
                    HttpMethod.GET,entity,Stuff.class);
            Stuff stuff = se.getBody();
            if (pet != null) totalPrice += pet.getPrice();
            if (stuff != null) totalPrice += stuff.getPrice();
        }
        if (totalPrice > 0) {
            if (person.getBalance() < totalPrice) {
                status.setStatus("error");
                status.setDescription("Not enough funds");
                return status;
            } else {
                for (Cart item : repository.findAllByPersonId(person.getId())) {
                    ResponseEntity<Pet> pe = restTemplate.exchange("http://items-service/pet"+item.getItemId(),
                            HttpMethod.GET,entity,Pet.class);
                    Pet pet = pe.getBody();
                    ResponseEntity<Stuff> se = restTemplate.exchange("http://items-service/stuff"+item.getItemId(),
                            HttpMethod.GET,entity,Stuff.class);
                    Stuff stuff = se.getBody();
                    if (pet != null) {
                        pet.setCount(pet.getCount() - 1);
                        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
                        headers.add("Authorization", token);
                        headers.add("Content-Type", "application/json");
                        HttpEntity<Pet> req = new HttpEntity<>(pet,headers);
                        restTemplate.postForObject("http://items-service/pet/"+pet.getId(),req,Pet.class);
                        repository.deleteByPersonIdAndItemId(person.getId(), item.getItemId());
                    }
                    if (stuff != null) {
                        stuff.setCount(stuff.getCount() - 1);
                        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
                        headers.add("Authorization", token);
                        headers.add("Content-Type", "application/json");
                        HttpEntity<Stuff> req = new HttpEntity<>(stuff,headers);
                        restTemplate.postForObject("http://items-service/stuff/"+stuff.getId(),req,Stuff.class);
                        repository.deleteByPersonIdAndItemId(person.getId(), item.getItemId());
                    }
                }
                person.setBalance(person.getBalance() - totalPrice);
                MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
                headers.add("Authorization", token);
                headers.add("Content-Type", "application/json");
                HttpEntity<Person> req = new HttpEntity<>(person,headers);
                restTemplate.postForObject("http://auth-service/person/"+person.getId(),req,Person.class);
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