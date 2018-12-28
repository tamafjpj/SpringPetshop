package com.mirea.Item.model;

import javax.persistence.*;

@Entity
@Table(name = "PETS")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UID_SEQ")
    private int id;
    @Column(name = "COUNT")
    private int count;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "PRICE")
    private int price;

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Pet(int count, String type, int price) {
        this.count = count;
        this.type = type;
        this.price = price;
    }

    protected Pet() {
    }
}
