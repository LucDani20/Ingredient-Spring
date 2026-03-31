package com.daniel.ingredient_td5.controller;

import java.time.Instant;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.ingredient_td5.entities.ingredient.Ingredient;
import com.daniel.ingredient_td5.entities.ingredient.Unit;
import com.daniel.ingredient_td5.erreur.RessourceNotFoundException;
import com.daniel.ingredient_td5.service.IngredientService;
import com.daniel.ingredient_td5.service.StockService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientController {
    
    private final IngredientService ingredientService;
    private final StockService stockService;

    @GetMapping("/{id}/stock")
    public ResponseEntity<?> findIngredientStock(
        @PathVariable(name = "id") Integer id,
        @RequestParam(name = "at", required = false) Instant at,
        @RequestParam(name = "unit", required = false) Unit unit
    ) 
    {
        try{

            return ResponseEntity.status(200)
            .header("Content-Type", "application/json")
            .body(this.stockService.findIngredientStock(id, at, unit));

        } catch(RessourceNotFoundException e){

            return ResponseEntity.status(404)
            .header("Content-Type", "text/plain")
            .body(e.getMessage());

        } catch(BadRequestException e){

            return ResponseEntity.status(400)
            .header("Content-Type", "text/plain")
            .body(e.getMessage());

        } catch(Exception e){
            
            return ResponseEntity.status(500)
            .header("Content-Type", "text/plain")
            .body(e.getMessage());
            
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable(name = "id") Integer id){
        try {
            Ingredient ingredient = this.ingredientService.findById(id);
            return ResponseEntity
                    .status(200)
                    .header("Content-Type", "application/json")
                    .body(ingredient);
        } catch (RessourceNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header("Content-Type", "text/plain")
                    .body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> findAll(){
        try {
            List<Ingredient> ingredients = this.ingredientService.findAll();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(ingredients);
                    
        } catch (Exception e){
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("Content-Type", "text/plain")
                .body(e.getMessage());
        }
    }
    
}
