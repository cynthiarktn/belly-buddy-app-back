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

  @PostMapping("/{userId}/{recipeId}")
  public ResponseEntity<Recipe> addRecipeToFavorites(@PathVariable Long userId, @PathVariable Long recipeId) throws Exception {
    Recipe recipe = favoritesService.addRecipeToFavorites(userId, recipeId);
    return ResponseEntity.ok(recipe);
  }
}
