package com.daniel.ingredient_td5.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.daniel.ingredient_td5.entities.ingredient.Unit;
import com.daniel.ingredient_td5.entities.stock.StockValue;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StockMovementRepository {
    
    private final DataSource dataSource;

    public StockValue findByIngredientId(Integer id, Instant dateTime, Unit unit){

        StockValue stockValue = new StockValue(0d, unit);

        String query = """
                SELECT id_ingredient, unit,
                SUM( CASE 
                    WHEN type = 'IN' THEN quantity
                    WHEN type = 'OUT' THEN -quantity
                    ELSE 0 END
                    ) as qte
                FROM stockmovement
                WHERE id_ingredient = ?
                AND creation_datetime <= ?
                AND unit= ?::unit_type
                GROUP BY id_ingredient, unit;
                """;
        try(
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setInt(1, id);
            stmt.setTimestamp(2, Timestamp.from(dateTime));
            stmt.setString(3, unit.toString());

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                stockValue.setQuantity(rs.getDouble("qte"));
                stockValue.setUnit(Unit.valueOf(rs.getString("unit")));
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return stockValue;
    }
}
