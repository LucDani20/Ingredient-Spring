package com.daniel.ingredient_td5.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import com.daniel.ingredient_td5.entities.dish.Dish;
import com.daniel.ingredient_td5.entities.dish.DishCreateRequest;
import com.daniel.ingredient_td5.entities.ingredient.Ingredient;
import com.daniel.ingredient_td5.erreur.RessourceNotFoundException;
import com.daniel.ingredient_td5.repository.DishRepository;
import com.daniel.ingredient_td5.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DishService {
    
    private final DishRepository dishRepository;
    private final IngredientRepository ingredientRepository;

    public List<Dish> findAll(){
        return this.dishRepository.findAll();
    }

    public Dish findById(Integer dishId){
        Dish dish = this.dishRepository.findById(dishId);
        if (dish == null) {
            throw new RessourceNotFoundException("Dish.id={"+dishId+"} is not found");
        }
        return dish;
    }

    public Dish attachAndDetach(Integer dishId, List<Ingredient> ingredients) throws BadRequestException{

        if(ingredients.isEmpty()){
            throw new BadRequestException("You must have a request body");
        }

        Dish thisDish = this.dishRepository.findById(dishId);

        if(thisDish == null){
            throw new RessourceNotFoundException("Dish.id={" + dishId+ "} is not found");
        };

        for(Ingredient ing : ingredients){

            Ingredient isExisting = this.ingredientRepository.findById(ing.getId());
            
            if(isExisting.getId() != null) {
                if(thisDish.getIngredients().stream().map(Ingredient::getId).toList().contains(ing.getId())){
                    this.dishRepository.detachIngredient(dishId, ing.getId());
                } else {
                    this.dishRepository.attach(dishId, ing.getId());
                }

            }

        }

        return this.dishRepository.findById(dishId);
    }


        public List<Dish> createDishes(List<DishCreateRequest> requests) throws BadRequestException {
            List<Dish> createdDishes = new ArrayList<>();
            
            for (DishCreateRequest request : requests) {
                if (dishRepository.existsByName(request.getName())) {
                    throw new BadRequestException("Dish.name=" + request.getName() + " already exists");
                }

                Dish newDish = new Dish();
                newDish.setName(request.getName());
                newDish.setDishType(request.getDishType());
                newDish.setPrice(request.getPrice());
                newDish.setIngredients(new ArrayList<>());
                
                Dish saved = dishRepository.save(newDish);
                createdDishes.add(saved);
            }
            
            return createdDishes;
        }

        public List<Dish> findAllWithFilters(Double priceUnder, Double priceOver, String name) {
            return this.dishRepository.findAllWithFilters(priceUnder, priceOver, name);
        }

}
