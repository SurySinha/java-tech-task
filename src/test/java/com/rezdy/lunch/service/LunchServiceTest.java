package com.rezdy.lunch.service;

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {LunchApplication.class, LunchApplicationTestConfig.class})
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@Sql({"/lunch-test-data.sql"})
@ActiveProfiles("test")
public class LunchServiceTest {

    @Autowired
    LunchService service;

    @Test
    public void getNonExpiredRecipesOnlyBasedOnLunchDateExcludesAllRecipesWithExpiredIngredients() {
        var now = LocalDate.of(2020, 11, 27);
        var results = service.getRecipesWithNonExpiredIngredients(now);
        Assertions.assertEquals(3, results.size());
        List<String> recipeTitles = results.stream().map(Recipe::getTitle).collect(Collectors.toList());
        assertThat(Arrays.asList("Hotdog", "Fry-up", "Ham and Cheese Toastie")).hasSameElementsAs(recipeTitles);
    }

    @Test
    public void getRecipesBasedOnRecipeTitleReturnsAMatchingRecipeBasedOnTitle() {
        var results = service.getRecipesByTitle("Salad");
        Assertions.assertEquals("Salad", results.getTitle());
        Assertions.assertEquals(5, results.getIngredients().size());
    }

    @Test
    public void getRecipesNotHavingIngredientsExcludesAllTheRecipesHavingUnwantedIngredients() {
        List<String> excludedIngredients = new ArrayList<>();
        excludedIngredients.add("Bread");
        excludedIngredients.add("Cucumber");
        var results = service.getRecipesNotHavingIngredients(excludedIngredients);
        List<String> recipeTitles = results.stream().map(Recipe::getTitle).collect(Collectors.toList());
        assertThat(Arrays.asList("Hotdog", "Omelette")).hasSameElementsAs(recipeTitles);
    }


}
