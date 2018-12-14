package com.mirea.petshop.repository;

import com.mirea.petshop.model.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartRepository extends CrudRepository<Cart, Integer> {
    Cart findCartItemById(int id);

    List<Cart> findAllByPersonId(int personId);

    void deleteByPersonIdAndItemId(int personId, int itemId);

    List<Cart> findAll();
}