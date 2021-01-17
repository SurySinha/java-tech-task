package com.rezdy.lunch.dao;

import com.rezdy.lunch.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, String> {

    @Query(value = "SELECT recipieOuter from Recipe recipieOuter where recipieOuter.title " +
            "not in (SELECT DISTINCT recipieInner.title FROM Recipe recipieInner join recipieInner.ingredients i where i.useBy < :useBy) ")
    List<Recipe> findAllNonExpiredRecipesByDate(@Param("useBy") LocalDate useBy);

    @Query(value = "SELECT recipieOuter from Recipe recipieOuter where recipieOuter.title " +
            "not in (SELECT DISTINCT recipieInner.title " +
            "FROM Recipe recipieInner join recipieInner.ingredients i where i.title in(:ingredients)) ")
    List<Recipe> findAllRecipesNotHavingIngredients(@Param("ingredients") List<String> ingredients);
}
