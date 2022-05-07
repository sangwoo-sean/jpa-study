package com.study.model.dto;

import lombok.Data;

@Data
public class UpdateItemDto {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;
}
