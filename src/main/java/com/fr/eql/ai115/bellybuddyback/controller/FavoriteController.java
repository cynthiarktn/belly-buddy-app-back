package com.fr.eql.ai115.bellybuddyback.controller;

import com.fr.eql.ai115.bellybuddyback.model.Favorite;
import com.fr.eql.ai115.bellybuddyback.model.UserEntity;
import com.fr.eql.ai115.bellybuddyback.service.FavoriteService;
import com.fr.eql.ai115.bellybuddyback.spoonaculardto.CompleteRecipe;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/favorite")
public class FavoriteController {
  private final FavoriteService favoriteService;

  public FavoriteController(FavoriteService favoriteService) {
    this.favoriteService = favoriteService;
  }

  @GetMapping("/getFavorites")
  public ResponseEntity<List<CompleteRecipe>> getFavorites(@AuthenticationPrincipal UserDetails userDetails) {
    UserEntity user = (UserEntity) userDetails;
    List<CompleteRecipe> favorites = favoriteService.getFavoritesByUserId(user.getId());
    return ResponseEntity.ok(favorites);
  }

  @PostMapping("/addFavorite")
  public ResponseEntity<Favorite> addFavorite(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CompleteRecipe recipe) {
    UserEntity user = (UserEntity) userDetails;
    Favorite favorite = favoriteService.addFavorite(recipe, user.getId());
    return ResponseEntity.ok(favorite);
  }

  @DeleteMapping("/removeFavorite/{favoriteId}")
  public ResponseEntity<Void> removeFavorite(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long favoriteId) throws Exception {
    UserEntity user = (UserEntity) userDetails;
    favoriteService.removeFavorite(favoriteId, user.getId());
    return ResponseEntity.ok().build();
  }
}
