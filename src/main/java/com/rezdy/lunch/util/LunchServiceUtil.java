package com.rezdy.lunch.util;

import com.rezdy.lunch.entity.Recipe;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LunchServiceUtil {

    /**
     * This method identifies recipe with stale ingredients i.e. recipes having best before date before the
     * lunch date and moves the recipeWithStaleIngredient to bottom of the list.
     *
     * @param recipes   list of recipes
     * @param lunchDate lunch date
     * @return list of sorted recipes
     */

    public static List<Recipe> sortRecipes(final List<Recipe> recipes, final LocalDate lunchDate) {
        List<Recipe> sortedList = new ArrayList<>();
        Predicate<Recipe> recipeWithStaleIngredient =
                recipe -> recipe.getIngredients().stream()
                        .anyMatch(ingredient -> ingredient.getBestBefore().isBefore(lunchDate));

        Map<Boolean, List<Recipe>> partitions =
                recipes.stream().collect(Collectors.partitioningBy(recipeWithStaleIngredient));
        sortedList.addAll(partitions.get(false));
        sortedList.addAll(partitions.get(true));
        return sortedList;
    }
}
