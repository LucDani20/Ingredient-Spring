package com.daniel.ingredient_td5.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.daniel.ingredient_td5.entities.ingredient.CategoryEnum;
import com.daniel.ingredient_td5.entities.ingredient.Ingredient;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class IngredientRepository {
    
    private final DataSource dataSource;

    public List<Ingredient> findAll(){


        List<Ingredient> ingredients = new ArrayList<>();

        String query = """
                SELECT
                    id, name, price, category
                FROM ingredient;
                """;
        
        try(
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Ingredient ingredient = new Ingredient();

                ingredient.setId(rs.getInt("id"));
                ingredient.setName(rs.getString("name"));
                ingredient.setPrice(rs.getDouble("price"));
                ingredient.setCategory(CategoryEnum.valueOf(rs.getString("category")));

                ingredients.add(ingredient);
                
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        return ingredients;
    }
    
   public Ingredient findById(Integer id){
        String query = """
                SELECT
                    id, name, price, category
                FROM ingredient
                WHERE id = ?
                """;
        Ingredient ingredient = new Ingredient();

        try(
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                ingredient.setId(rs.getInt("id"));
                ingredient.setName(rs.getString("name"));
                ingredient.setPrice(rs.getDouble("price"));
                ingredient.setCategory(CategoryEnum.valueOf(rs.getString("category")));
            }
            

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ingredient;
   }
}
