package com.fr.eql.ai115.bellybuddyback.controller;

import com.fr.eql.ai115.bellybuddyback.dto.CompleteRecipeDto;
import com.fr.eql.ai115.bellybuddyback.dto.FavoriteRecipeDto;
import com.fr.eql.ai115.bellybuddyback.model.FavoriteRecipe;
import com.fr.eql.ai115.bellybuddyback.model.Recipe;
import com.fr.eql.ai115.bellybuddyback.model.UserEntity;
import com.fr.eql.ai115.bellybuddyback.repository.UserRepository;
import com.fr.eql.ai115.bellybuddyback.service.CustomUserDetailsService;
import com.fr.eql.ai115.bellybuddyback.service.FavoriteService;
import com.fr.eql.ai115.bellybuddyback.spoonaculardto.CompleteRecipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user/favorite")
public class FavoriteController {
  @Autowired
  private FavoriteService favoriteService;

  @Autowired
  private UserRepository userRepository;

  @PostMapping("/addToList")
  public ResponseEntity<FavoriteRecipeDto> addFavorite(@RequestBody Recipe recipe, Principal principal) throws Exception {
    UserEntity user = userRepository.findByUsername(principal.getName())
      .orElseThrow(() -> new Exception("User not found"));
    FavoriteRecipeDto addedFavorite = favoriteService.addFavorite(recipe, user);
    return ResponseEntity.ok(addedFavorite);
  }

  @DeleteMapping("/{favoriteId}/removeToList")
  public ResponseEntity<Void> removeFavorite(@PathVariable Long favoriteId, @AuthenticationPrincipal UserEntity user) throws Exception {
    favoriteService.removeFavorite(favoriteId, user.getId());
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/all")
  public ResponseEntity<List<FavoriteRecipeDto>> getAllFavorites() {
    List<FavoriteRecipeDto> favorites = favoriteService.getAllFavorites();
    return ResponseEntity.ok(favorites);
  }

  @GetMapping("/recipe/{id}")
  public ResponseEntity<FavoriteRecipeDto> getFavoriteById(@PathVariable Long id) {
    FavoriteRecipeDto favorite = favoriteService.getFavoriteById(id);
    return ResponseEntity.ok(favorite);
  }
}
