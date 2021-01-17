package com.rezdy.lunch.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rezdy.lunch.LunchApplication;
import com.rezdy.lunch.config.LunchApplicationTestConfig;
import com.rezdy.lunch.entity.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {LunchApplication.class, LunchApplicationTestConfig.class})
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@Sql({"/lunch-test-data.sql"})
@ActiveProfiles("test")
public class LunchControllerTest {
    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void callingLunchAPIWithIncorrectDateFormatResultsIn5XXException() throws Exception {
        String dateInIncorrectFormat = "11-12-2019";
        var response = mockMvc.perform(get("/lunch?date=" + dateInIncorrectFormat))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andReturn()
                .getResponse().getContentAsString();

        Assertions.assertEquals(response, "{\"errorMessage\":\"Text '11-12-2019' could not be parsed at index 0\"}");
    }

    @Test
    public void callingLunchEndPointWithoutDateResultsInBadRequestException() throws Exception {
        var response = mockMvc.perform(get("/lunch"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse().getContentAsString();

        Assertions.assertEquals(response, "");
    }

    @Test
    public void callingLunchEndPointWithDateInYYYYMMDDFormatResultsInSuccessResponseWhenDataIsReturned() throws Exception {
        var dateInYYYYMMDDFormat = LocalDate.of(2020, 1, 22).toString();
        var response = mockMvc.perform(get("/lunch?date=" + dateInYYYYMMDDFormat))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        var recipeList = mapper.readValue(response, new TypeReference<List<Recipe>>() {});
        Assertions.assertEquals(3, recipeList.size());
    }

    @Test
    public void callingLunchEndPointWithDateInYYYYMMDDFormatResultsInSuccessResponseEvenWhenNoDataIsReturned() throws Exception {
        var dateInYYYYMMDDFormat = LocalDate.of(2040, 1, 22).toString();
        var response = mockMvc.perform(get("/lunch?date=" + dateInYYYYMMDDFormat))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        var recipeList = mapper.readValue(response, new TypeReference<List<Recipe>>() {});
        Assertions.assertEquals(0, recipeList.size());
    }

    @Test
    public void callingRecipeEndPointsWithNoTitleResultsInBadRequestException() throws Exception {
        var response = mockMvc.perform(get("/recipe"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse().getContentAsString();

        Assertions.assertEquals(response, "");
    }

    @Test
    public void callingRecipeEndPointsWithNoMatchingRecipeThrow404Exception() throws Exception {
        var response = mockMvc.perform(get("/recipe?title=abc"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse().getContentAsString();

        Assertions.assertEquals(response, "{\"errorMessage\":\"Recipe not found with title abc\"}");
    }

    @Test
    public void callingRecipeEndPointWithAMatchingRecipeReturnASuccessResponseWhenDataIsReturned() throws Exception {
        var response = mockMvc.perform(get("/recipe?title=Salad"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        var recipe = mapper.readValue(response, new TypeReference<Recipe>() {});
        Assertions.assertEquals("Salad",recipe.getTitle());
    }

    @Test
    public void callingExcludeIngredientsEndPointsWithNoIngredientsResultsInBadRequestException() throws Exception {
        var response = mockMvc.perform(get("/excludeIngredients"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse().getContentAsString();

        Assertions.assertEquals(response, "");
    }

    @Test
    public void callingExcludeIngredientsEndPointsWithBlankIngredientsResultsInBadRequestException() throws Exception {
        var response = mockMvc.perform(get("/excludeIngredients?ingredients"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse().getContentAsString();

        Assertions.assertEquals(response, "");
    }


    @Test
    public void callingExcludeIngredientsEndPointsWithInvalidIngredientsResultsIn5XXException() throws Exception {
        var response = mockMvc.perform(get("/excludeIngredients?ingredients=Salad$Mushrooms"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andReturn()
                .getResponse().getContentAsString();

        Assertions.assertEquals(response, "{\"errorMessage\":\"Ingredients list is invalid, ingredients should be a list of comma separated values i.e Salad,Mushrooms\"}");
    }

    @Test
    public void callingExcludeIngredientsEndPointsWithValidIngredientsResultsInSuccess() throws Exception {
        var response = mockMvc.perform(get("/excludeIngredients?ingredients=Salad,Mushrooms"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse().getContentAsString();

        var recipeList = mapper.readValue(response, new TypeReference<List<Recipe>>() {});
        Assertions.assertEquals(3, recipeList.size());
    }





}