package com.fr.eql.ai115.bellybuddyback.controller;
import com.fr.eql.ai115.bellybuddyback.dto.apiresponse.CompleteRecipeResponse;
import com.fr.eql.ai115.bellybuddyback.model.Recipe;
import com.fr.eql.ai115.bellybuddyback.service.FavoritesService;
import com.fr.eql.ai115.bellybuddyback.service.SpoonacularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user/favorites")
public class FavoritesController {

  @Autowired
  private FavoritesService favoritesService;

  @Autowired
  private SpoonacularService spoonacularService;

  @PostMapping("/add/{userId}/{recipeId}")
  public ResponseEntity<?> addRecipeToFavorites(@PathVariable Long userId, @PathVariable Long recipeId) throws Exception {
    try {
      CompleteRecipeResponse recipeInfo = spoonacularService.getCompleteRecipeById(recipeId);
      Recipe recipe = favoritesService.addRecipeToFavorites(userId, recipeId, recipeInfo);
      return ResponseEntity.ok(recipe);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Failed to add recipe to favorites: " + e.getMessage());
    }
  }

  @DeleteMapping("/remove/{userId}/{recipeId}")
  public ResponseEntity<?> removeRecipeFromFavorites(@PathVariable Long userId, @PathVariable Long recipeId) {
    try {
      favoritesService.removeRecipeFromFavorites(userId, recipeId);
      return ResponseEntity.ok("Recipe removed from favorites");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Failed to remove recipe from favorites: " + e.getMessage());
    }
  }
}
