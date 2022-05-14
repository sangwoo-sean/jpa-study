package com.study.model.dto;

import com.study.model.domain.Cart;
import lombok.Data;

@Data
public class CartDto {
    private Long id;
    private String item_name;
    private int price;
    private int quantity;
    private int total_price;

    public CartDto(Cart cart) {
        id = cart.getId();
        item_name = cart.getItem().getName();
        price = cart.getItem().getPrice();
        quantity = cart.getQuantity();
        total_price = quantity * price;
    }
}
