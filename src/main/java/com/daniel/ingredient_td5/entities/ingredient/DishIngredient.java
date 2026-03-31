package com.daniel.ingredient_td5.entities.ingredient;

import com.daniel.ingredient_td5.entities.dish.Dish;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DishIngredient {
    private int id;
    private Dish dish;
    private Ingredient ingredient;
    private double quantity;
    private Unit unit;
}
