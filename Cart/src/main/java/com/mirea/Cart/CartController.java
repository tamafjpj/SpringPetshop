package com.mirea.Cart;

import com.mirea.Cart.model.Cart;
import com.mirea.Cart.model.Status;
import com.mirea.Cart.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class CartController {
    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
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
    public Status purchaseItems(@RequestHeader(value = "Authorization") String token) {
        Status status;
        status = cartService.purchaseItems(token);
        return status;
    }

    @RequestMapping(value = "cart/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteItem(@PathVariable int id,@RequestHeader(value = "Authorization") String token) {
        cartService.deleteElem(id,token);
    }

    @RequestMapping(value = "cart/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public void addItem(@PathVariable int id,@RequestHeader(value = "Authorization") String token) {
        cartService.addElem(id,token);
    }
}
