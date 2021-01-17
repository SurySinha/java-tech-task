package com.rezdy.lunch.util;

import com.rezdy.lunch.dto.RecipeDto;
import com.rezdy.lunch.entity.Recipe;
import org.modelmapper.ModelMapper;

public class MapperUtil {
    public static RecipeDto convertRecipeToDto(Recipe recipe, ModelMapper modelMapper) {
        return modelMapper.map(recipe, RecipeDto.class);
    }
}
