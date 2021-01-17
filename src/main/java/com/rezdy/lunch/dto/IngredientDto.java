package com.rezdy.lunch.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class IngredientDto implements Serializable {
    private String title;
    private String bestBefore;
    private String useBy;
}
