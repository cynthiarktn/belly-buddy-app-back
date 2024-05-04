package com.fr.eql.ai115.bellybuddyback.controller;

import com.fr.eql.ai115.bellybuddyback.dto.apiresponse.*;
import com.fr.eql.ai115.bellybuddyback.service.SpoonacularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class SearchController {

  private final SpoonacularService spoonacularService;

  @Autowired
  public SearchController(SpoonacularService spoonacularService) {
    this.spoonacularService = spoonacularService;
  }


// INGREDIENTS
  @GetMapping("/searchIngredient/{ingredient}")
  public IngredientResultsResponse searchIngredient(@PathVariable String ingredient) throws Exception {
    return spoonacularService.searchIngredient(ingredient);
  }

  @GetMapping("/recipeInformation/{id}")
  public ResponseEntity<CompleteRecipeResponse> getRecipeInformation(@PathVariable Long id) throws Exception {
    CompleteRecipeResponse recipe = spoonacularService.getCompleteRecipeById(id);
    return ResponseEntity.ok(recipe);
  }

  @GetMapping("/autocompleteIngredientSearch/{partialIngredient}")
  public ResponseEntity<Set<String>> autocompleteIngredientSearch(@PathVariable String partialIngredient) throws Exception {
    Set<String> ingredients = spoonacularService.autocompleteIngredientSearch(partialIngredient);
    return ResponseEntity.ok(ingredients);
  }

  @GetMapping("/getIngredientInformation/{id}")
  public ResponseEntity<IngredientResponse> getIngredientInformation(@PathVariable Long id) throws Exception {
    IngredientResponse ingredient = spoonacularService.getIngredientInformation(id);
    return ResponseEntity.ok(ingredient);
  }


  // RECIPES
  @GetMapping("/searchRecipesByIngredients/{ingredients}")
  public ResponseEntity<Set<RecipesByIngredientsResponse>> searchRecipesByIngredients(@PathVariable Set<String> ingredients) throws Exception {
    Set<RecipesByIngredientsResponse> recipes = spoonacularService.searchRecipesByIngredients(ingredients);
    return ResponseEntity.ok(recipes);
  }

  @GetMapping("/autocompleteRecipeSearch/{partialRecipe}")
  public ResponseEntity<Set<String>> autocompleteRecipeSearch(@PathVariable String partialRecipe) throws Exception {
    Set<String> recipes = spoonacularService.autocompleteRecipeSearch(partialRecipe);
    return ResponseEntity.ok(recipes);
  }

  @GetMapping("/searchRecipeName/{recipeName}")
  public ResponseEntity<Set<RecipesByNameResponse>> searchRecipesByName(@PathVariable String recipeName) throws Exception {
    Set<RecipesByNameResponse> recipes = spoonacularService.searchRecipesByName(recipeName);
    return ResponseEntity.ok(recipes);
  }
}
