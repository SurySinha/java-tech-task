package com.rezdy.lunch.util;

import com.rezdy.lunch.entity.Ingredient;
import com.rezdy.lunch.entity.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rezdy.lunch.util.LunchConstants.YYYY_MM_DD_FORMATTER;
import static com.rezdy.lunch.util.LunchServiceUtil.sortRecipes;
import static org.assertj.core.api.Assertions.assertThat;

public class LunchServiceUtilTest {


    @Test
    public void sortRecipeBasedOnIngredientsHavingMostRecentBestByDate() {
        Recipe recipe1 = Recipe.builder().title("Hotdog").ingredients(Set.of(
                createIngredient("Cucumber", "2020-02-01", "2030-01-01"),
                createIngredient("Ketchup", "2020-02-02", "2030-01-01"))).build();

        Recipe recipe2 = Recipe.builder().title("Omelette").ingredients(Set.of(
                createIngredient("Milk", "2022-03-01", "2030-01-01"),
                createIngredient("Eggs", "2022-03-02", "2030-01-01"))).build();

        Recipe recipe3 = Recipe.builder().title("Salad").ingredients(Set.of(
                createIngredient("Lettuce", "2020-01-01", "2030-01-01"),
                createIngredient("Tomato", "2022-01-02", "2030-01-01"))).build();

        Recipe recipe4 =
                Recipe.builder().title("Hamburger").ingredients(Set.of(
                        createIngredient("Ham", "2020-04-01", "2030-01-01"),
                        createIngredient("Cheese", "2020-04-02", "2030-01-01"))).build();

        List<Recipe> recipes = new ArrayList<>(Set.of(recipe1, recipe2, recipe3, recipe4));
        List<Recipe> sortedRecipes = sortRecipes(recipes, LocalDate.of(2022, 1, 22));
        List<String> freshRecipes = sortedRecipes.subList(0, 1).stream().map(Recipe::getTitle).collect(Collectors.toList());
        List<String> staleRecipes = sortedRecipes.subList(1, 4).stream().map(Recipe::getTitle).collect(Collectors.toList());

        Assertions.assertEquals(4, sortedRecipes.size());
        assertThat(Arrays.asList( "Omelette")).hasSameElementsAs(freshRecipes);
        assertThat(Arrays.asList("Hamburger", "Salad","Hotdog")).hasSameElementsAs(staleRecipes);

    }

    @Test
    public void sortRecipeBasedOnIngredientsHavingMostRecentBestByDateWhenTheListIsEmpty() {
        List<Recipe> recipeList = new ArrayList<>();
        sortRecipes(recipeList, LocalDate.of(2020, 1, 22));
        Assertions.assertEquals(0, recipeList.size());
    }

    private Ingredient createIngredient(String title, String bestBeforeDate, String useByDate) {
        return Ingredient.builder().title(title).bestBefore(LocalDate.parse(bestBeforeDate, YYYY_MM_DD_FORMATTER)).useBy(
                LocalDate.parse(useByDate, YYYY_MM_DD_FORMATTER)).build();
    }
}
