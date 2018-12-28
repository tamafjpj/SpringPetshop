package com.mirea.Item;

import com.mirea.Item.model.Pet;
import com.mirea.Item.model.Stuff;
import com.mirea.Item.services.PetService;
import com.mirea.Item.services.StuffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class ItemController {
    private PetService petService;
    private StuffService stuffService;
    private Environment env;

    @Autowired
    public ItemController(PetService petService, StuffService stuffService,Environment env) {
        this.stuffService = stuffService;
        this.petService = petService;
        this.env = env;
    }

    @RequestMapping("/")
    public String home() {
        return "Hello from Item-Service running at port: " + env.getProperty("local.server.port");
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
}
