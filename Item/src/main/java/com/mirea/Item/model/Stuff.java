package com.mirea.Item.model;


import javax.persistence.*;

@Entity
@Table(name = "STUFF")
public class Stuff {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UID_SEQ")
    private int id;
    private int count;
    private String name;
    private String type;
    private int price;

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    protected Stuff() {
    }

    public Stuff(int count, String name, String type, int price) {
        this.count = count;
        this.name = name;
        this.type = type;
        this.price = price;
    }
}
