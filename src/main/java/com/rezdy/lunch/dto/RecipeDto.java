package com.rezdy.lunch.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class RecipeDto implements Serializable {
        private String title;
        private Set<IngredientDto> ingredients;
}
