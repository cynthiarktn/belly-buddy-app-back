package com.fr.eql.ai115.bellybuddyback.controller;

import com.fr.eql.ai115.bellybuddyback.spoonaculardto.CompleteRecipe;
import com.fr.eql.ai115.bellybuddyback.spoonaculardto.IngredientResults;
import com.fr.eql.ai115.bellybuddyback.spoonaculardto.RecipesByIngredientsResponse;
import com.fr.eql.ai115.bellybuddyback.service.SpoonacularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class SearchController {

  @Autowired
  private SpoonacularService spoonacularService;

  @GetMapping("/searchIngredient/{ingredient}")
  public IngredientResults searchIngredient(@PathVariable String ingredient) throws Exception {
    return spoonacularService.searchIngredient(ingredient);
  }

  @GetMapping("/searchRecipesByIngredients/{ingredients}")
  public ResponseEntity<List<RecipesByIngredientsResponse>> searchRecipesByIngredients(@PathVariable List<String> ingredients) throws Exception {
    List<RecipesByIngredientsResponse> recipes = spoonacularService.searchRecipesByIngredients(ingredients);
    return ResponseEntity.ok(recipes);
  }

  @GetMapping("/recipeInformation/{id}")
  public ResponseEntity<CompleteRecipe> getRecipeInformation(@PathVariable Long id) throws Exception {
    CompleteRecipe recipe = spoonacularService.getRecipeInformation(id);
    return ResponseEntity.ok(recipe);
  }
}
