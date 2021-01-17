package com.rezdy.lunch.util;

import java.time.format.DateTimeFormatter;

public interface LunchConstants {
    DateTimeFormatter YYYY_MM_DD_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String INVALID_INGREDIENTS_ERROR_MESSAGE =
            "Ingredients list is invalid, ingredients should be a list of comma separated values i.e Salad,Mushrooms";
    String RECIPE_NOT_FOUND = "Recipe not found with title ";
}
