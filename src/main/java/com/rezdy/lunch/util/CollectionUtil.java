package com.rezdy.lunch.util;

import com.rezdy.lunch.exception.InvalidIngredientsException;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rezdy.lunch.util.LunchConstants.INVALID_INGREDIENTS_ERROR_MESSAGE;

public class CollectionUtil {

    public static List<String> handleIngredientsList(String ingredients) {
        Pattern pattern = Pattern.compile("^\\w+[,]?\\w+$");
        Matcher matcher = pattern.matcher(ingredients);
        if (!matcher.matches()) {
            throw new InvalidIngredientsException(INVALID_INGREDIENTS_ERROR_MESSAGE);
        }
        return Arrays.asList(ingredients.split(","));
    }

}

