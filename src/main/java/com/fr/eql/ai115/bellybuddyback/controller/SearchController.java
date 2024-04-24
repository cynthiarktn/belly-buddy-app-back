package com.fr.eql.ai115.bellybuddyback.controller;

import com.fr.eql.ai115.bellybuddyback.dto.IngredientDto;
import com.fr.eql.ai115.bellybuddyback.dto.RecipeDto;
import com.fr.eql.ai115.bellybuddyback.service.SpoonacularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class SearchController {

  @Autowired
  private SpoonacularService spoonacularService;

  @GetMapping("/searchIngredient/{ingredient}")
  public IngredientDto searchIngredient(@PathVariable String ingredient) {
    return spoonacularService.searchIngredient(ingredient);
  }

  @GetMapping("/searchRecipesByIngredients/{ingredients}")
  public ResponseEntity<List<RecipeDto>> searchRecipesByIngredients(@PathVariable List<String> ingredients) {
    List<RecipeDto> recipes = spoonacularService.searchRecipesByIngredients(ingredients);
    return ResponseEntity.ok(recipes);
  }
}
