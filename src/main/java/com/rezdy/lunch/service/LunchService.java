package com.rezdy.lunch.service;

import com.rezdy.lunch.dao.IngredientRepository;
import com.rezdy.lunch.dao.RecipeRepository;
import com.rezdy.lunch.entity.Recipe;
import com.rezdy.lunch.exception.ResourceNotFoundException;
import com.rezdy.lunch.util.LunchServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.rezdy.lunch.util.LunchConstants.RECIPE_NOT_FOUND;

@Service
public class LunchService {

    private RecipeRepository recipeRepository;
    private IngredientRepository ingredientRepository;

    @Autowired
    public LunchService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<Recipe> getRecipesWithNonExpiredIngredients(LocalDate date) {
        List<Recipe> recipes = recipeRepository.findAllNonExpiredRecipesByDate(date);
        return LunchServiceUtil.sortRecipes(recipes, date);
    }

    public Recipe getRecipesByTitle(String title) {
        return recipeRepository.findById(title).orElseThrow(
                () -> new ResourceNotFoundException(RECIPE_NOT_FOUND + title));
    }

    public List<Recipe> getRecipesNotHavingIngredients(List<String> ingredients) {
        return recipeRepository.findAllRecipesNotHavingIngredients(ingredients);
    }


}
