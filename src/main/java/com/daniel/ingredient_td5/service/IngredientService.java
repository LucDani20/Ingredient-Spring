package com.daniel.ingredient_td5.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.daniel.ingredient_td5.entities.ingredient.Ingredient;
import com.daniel.ingredient_td5.erreur.RessourceNotFoundException;
import com.daniel.ingredient_td5.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientService {
    
    private final IngredientRepository ingredientRepository;

    public List<Ingredient> findAll(){
        return this.ingredientRepository.findAll();
    }

    public Ingredient findById(Integer id){

        Ingredient ingredient = this.ingredientRepository.findById(id);
        
        if(ingredient.getId() == null){
            throw new RessourceNotFoundException("Ingredient.id={" + id + "} not found");
        }

        return ingredient;
    }
}
