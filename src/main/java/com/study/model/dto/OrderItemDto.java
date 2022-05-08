package com.study.model.dto;

import com.study.model.domain.OrderItem;
import lombok.Data;

@Data
public class OrderItemDto {
    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemDto(OrderItem item) {
        itemName = item.getItem().getName();
        orderPrice = item.getOrderPrice();
        count = item.getCount();
    }
}