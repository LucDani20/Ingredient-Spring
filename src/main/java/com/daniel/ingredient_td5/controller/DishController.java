package com.daniel.ingredient_td5.controller;


import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.ingredient_td5.entities.dish.Dish;
import com.daniel.ingredient_td5.entities.dish.DishCreateRequest;
import com.daniel.ingredient_td5.entities.ingredient.Ingredient;
import com.daniel.ingredient_td5.erreur.RessourceNotFoundException;
import com.daniel.ingredient_td5.service.DishService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dishes")
public class DishController {
    
    private final DishService dishService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") Integer id){
        try {
             return ResponseEntity
                .status(200)
                .header("Content-Type", "application/json")
                .body(this.dishService.findById(id));
        } catch (RessourceNotFoundException e){
            return ResponseEntity
                    .status(404)
                    .header("Content-Type", "text/plain")
                    .body(e.getMessage());
        }
         catch (Exception e){
            return ResponseEntity
                    .status(500)
                    .header("Content-Type", "text/plain")
                    .body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> findAll(){
        try {
             return ResponseEntity
                .status(200)
                .header("Content-Type", "application/json")
                .body(this.dishService.findAll());
        } catch (Exception e){
            return ResponseEntity
                    .status(500)
                    .header("Content-Type", "text/plain")
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}/ingredients")
    public ResponseEntity<?> attachOrDetach(
        @PathVariable(name = "id") Integer id,
        @RequestBody List<Ingredient> ingredients
    ){
        try {
            return ResponseEntity
                .status(200)
                .header("Content-Type", "application/json")
                .body(this.dishService.attachAndDetach(id, ingredients));
        } catch(RessourceNotFoundException e){
            return ResponseEntity
                .status(404)
                .header("Content-Type","text/plain")
                .body(e.getMessage());
        } catch(BadRequestException e){
            return ResponseEntity
                .status(400)
                .header("Content-Type","text/plain")
                .body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity
                .status(500)
                .header("Content-Type","text/plain")
                .body(e.getMessage());
        }
    }


    @PostMapping("")
    public ResponseEntity<?> createDishes(@RequestBody List<DishCreateRequest> requests) {
        try {
            List<Dish> created = dishService.createDishes(requests);
            return ResponseEntity
                .status(201)
                .header("Content-Type", "application/json")
                .body(created);
        } catch (BadRequestException e) {
            return ResponseEntity
                .status(400)
                .header("Content-Type", "text/plain")
                .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                .status(500)
                .header("Content-Type", "text/plain")
                .body(e.getMessage());
        }
    }

        @GetMapping("")
        public ResponseEntity<?> findAll(
            @RequestParam(name = "priceUnder", required = false) Double priceUnder,
            @RequestParam(name = "priceOver", required = false) Double priceOver,
            @RequestParam(name = "name", required = false) String name
        ) {
            try {
                List<Dish> dishes;
                
                if (priceUnder != null || priceOver != null || (name != null && !name.isEmpty())) {
                    dishes = dishService.findAllWithFilters(priceUnder, priceOver, name);
                } else {
                    dishes = dishService.findAll();
                }
                
                return ResponseEntity
                    .status(200)
                    .header("Content-Type", "application/json")
                    .body(dishes);
            } catch (Exception e) {
                return ResponseEntity
                    .status(500)
                    .header("Content-Type", "text/plain")
                    .body(e.getMessage());
            }
        }
}
