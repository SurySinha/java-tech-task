package com.rezdy.lunch.controller;

import com.rezdy.lunch.dto.RecipeDto;
import com.rezdy.lunch.entity.Recipe;
import com.rezdy.lunch.service.LunchService;
import com.rezdy.lunch.util.CollectionUtil;
import com.rezdy.lunch.util.MapperUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
public class LunchController {

    private final LunchService lunchService;

    private final ModelMapper modelMapper;

    @Autowired
    public LunchController(LunchService lunchService, ModelMapper modelMapper) {
        this.lunchService = lunchService;
        this.modelMapper = modelMapper;
    }


    @ApiOperation(value = "Get the recipes with non expired ingredients based on lunch date")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of recipes"),
    })
    @GetMapping("/lunch")
    @ResponseBody
    public List<RecipeDto> getRecipesWithNonExpiredIngredients(@RequestParam(value = "date") String date) {
        List<Recipe> recipes = lunchService.getRecipesWithNonExpiredIngredients(LocalDate.parse(date));
        return recipes.stream()
                .map(recipe -> MapperUtil.convertRecipeToDto(recipe, modelMapper))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Get the recipes based on the title")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved recipe for the title"),
            @ApiResponse(code = 404, message = "Recipe not found for the title")
    })
    @GetMapping("/recipe")
    @ResponseBody
    public RecipeDto getRecipesByTitle(@RequestParam(value = "title") String title) {
        return MapperUtil.convertRecipeToDto(lunchService.getRecipesByTitle(title), modelMapper);
    }

    @ApiOperation(value = "Get the recipes after excluding ingredients")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved recipe after excluding ingredients")
    })
    @GetMapping("/excludeIngredients")
    @ResponseBody
    public List<RecipeDto> getRecipesNotHavingIngredients(@RequestParam(value = "ingredients") String ingredients) {


        List<String> ingredientsList = CollectionUtil.handleIngredientsList(ingredients);
        List<Recipe> recipes = lunchService.getRecipesNotHavingIngredients(ingredientsList);
        return recipes.stream()
                .map(recipe -> MapperUtil.convertRecipeToDto(recipe, modelMapper))
                .collect(Collectors.toList());
    }
}

