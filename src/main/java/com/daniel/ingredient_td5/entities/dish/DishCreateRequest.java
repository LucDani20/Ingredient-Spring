package com.daniel.ingredient_td5.entities.dish;

import com.daniel.ingredient_td5.entities.dish.DishCreateRequest;
import lombok.Data;

@Data
public class DishCreateRequest {
    private String name;
    private DishTypeEnum dishType;  // STARTER, MAIN, DESSERT
    private Double price;
}