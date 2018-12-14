package com.mirea.petshop.model;

import javax.persistence.*;

@Entity
@Table(name = "CART")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int personId;
    private int itemId;

    public Cart(int personId, int itemId) {
        this.personId = personId;
        this.itemId = itemId;
    }

    protected Cart() {
    }

    public int getId() {
        return id;
    }

    public int getPersonId() {
        return personId;
    }

    public int getItemId() {
        return itemId;
    }

}
