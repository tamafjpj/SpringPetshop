package com.mirea.petshop.web;

import com.mirea.petshop.model.*;
import com.mirea.petshop.services.CartService;
import com.mirea.petshop.services.PersonService;
import com.mirea.petshop.services.PetService;
import com.mirea.petshop.services.StuffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class PetController {
    private PetService petService;
    private CartService cartService;
    private StuffService stuffService;
    private PersonService personService;

    @Autowired
    public PetController(CartService cartService, PetService petService, StuffService stuffService, PersonService personService) {
        this.cartService = cartService;
        this.stuffService = stuffService;
        this.petService = petService;
        this.personService = personService;
    }

    @RequestMapping(value = "pet", method = GET)
    @ResponseBody
    public List<Pet> pets() {
        return petService.getAllPets();
    }

    @RequestMapping(value = "stuff", method = GET)
    @ResponseBody
    public List<Stuff> stuff() {
        return stuffService.getAllStuff();
    }

    @RequestMapping(value = "pet/{id}", method = GET)
    @ResponseBody
    public Pet pet(@PathVariable int id) {
        return petService.getPet(id);
    }

    @RequestMapping(value = "stuff/{id}", method = GET)
    @ResponseBody
    public Stuff item(@PathVariable int id) {
        return stuffService.getItem(id);
    }

    @RequestMapping(value = "cart", method = GET)
    @ResponseBody
    public List<Cart> cartItems() {
        return cartService.getAllItems();
    }

    @RequestMapping(value = "cart/{pesonId}", method = GET)
    @ResponseBody
    public List<Cart> cartItem(@PathVariable int personId) {
        return cartService.getAllItemsbyPersonId(personId);
    }

    @RequestMapping(value = "cart", method = POST)
    @ResponseBody
    public Status purchaseItems() {
        Status status;
        status = cartService.purchaseItems();
        return status;
    }

    @RequestMapping(value = "cart/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteItem(@PathVariable int id) {
        cartService.deleteElem(id);
    }

    @RequestMapping(value = "cart/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public void addItem(@PathVariable int id) {
        cartService.addElem(id);
    }

    @RequestMapping(value = "/person", method = GET)
    @ResponseBody
    public List<Person> getAllPerson() {
        return personService.getAllPersons();
    }

}
