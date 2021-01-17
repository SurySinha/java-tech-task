package com.rezdy.lunch.dao;

import com.rezdy.lunch.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository  extends JpaRepository<Ingredient, String> {
    Ingredient findIngredientByTitle(@Param("title") String title);
}
