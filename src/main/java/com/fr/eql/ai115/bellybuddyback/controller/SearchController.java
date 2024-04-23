package com.fr.eql.ai115.bellybuddyback.controller;

import com.fr.eql.ai115.bellybuddyback.dto.IngredientSearchResponse;
import com.fr.eql.ai115.bellybuddyback.dto.RecipeSearchResponse;
import com.fr.eql.ai115.bellybuddyback.service.SpoonacularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user")
public class SearchController {

  @Autowired
  private SpoonacularService spoonacularService;

  @GetMapping("/searchIngredient")
  public Mono<IngredientSearchResponse> searchIngredient(@RequestParam String query) {
    return spoonacularService.searchIngredient(query);
  }

  @GetMapping("/searchRecipes")
  public Mono<RecipeSearchResponse> searchRecipes(@RequestParam String query) {
    return spoonacularService.searchRecipesByIngredients(query);
  }
}
