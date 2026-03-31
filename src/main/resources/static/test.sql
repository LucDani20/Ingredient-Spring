SELECT id_ingredient,unit, 
SUM( CASE 
    WHEN type = 'IN' THEN quantity
    WHEN type = 'OUT' THEN -quantity
    ELSE 0 END
    ) as qte
FROM stockmovement
WHERE id_ingredient = 1 AND creation_datetime <= '2024-01-06 12:00:00'
GROUP BY id_ingredient, unit;


SELECT id, quantity, unit, type, creation_datetime
FROM stockmovement
WHERE id_ingredient = 1 AND creation_datetime < '2024-01-06 12:00:00'

select dish.id as dish_id, dish.name as dish_name, dish_type, dish.price as dish_price
from dish
where dish.id = 1

SELECT i.id as ingredient_id,
        i.name as ingredient_name,
        i.price as ingredient_price,
        i.category as ingredient_category
FROM ingredient i
JOIN dishingredients di ON i.id = di.id_ingredient
JOIN dish d ON d.id = di.id_dish
WHERE di.id_dish = 1

SELECT setval('dishingredients_id_seq', (SELECT COALESCE(MAX(id), 0) FROM dishingredients));


SELECT pg_get_serial_sequence('dishingredients', 'id')