package com.daniel.ingredient_td5.service;

import java.time.Instant;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import com.daniel.ingredient_td5.entities.ingredient.Ingredient;
import com.daniel.ingredient_td5.entities.ingredient.Unit;
import com.daniel.ingredient_td5.entities.stock.StockValue;
import com.daniel.ingredient_td5.erreur.RessourceNotFoundException;
import com.daniel.ingredient_td5.repository.IngredientRepository;
import com.daniel.ingredient_td5.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {
    
    private final StockMovementRepository stockMovementRepository;
    private final IngredientRepository ingredientRepository;

    public StockValue findIngredientStock(Integer id, Instant t, Unit unit) throws Exception{

        Ingredient ingredient = ingredientRepository.findById(id);

        if(ingredient.getId()==null){
            throw new RessourceNotFoundException("Ingredient.id={" + id+ "} is not found");
        }

        if(t == null || unit == null){
            throw new BadRequestException("Either mandatory query parameter 'at' or 'unit' is not provided");
        }

        return this.stockMovementRepository.findByIngredientId(id, t, unit);
    }
}
