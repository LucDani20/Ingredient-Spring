package com.daniel.ingredient_td5.entities.stock;

import com.daniel.ingredient_td5.entities.ingredient.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockValue {
    private Double quantity;
    private Unit unit;
}
