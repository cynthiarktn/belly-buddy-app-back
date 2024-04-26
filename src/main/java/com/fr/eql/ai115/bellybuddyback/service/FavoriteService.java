package com.fr.eql.ai115.bellybuddyback.service;


import com.fr.eql.ai115.bellybuddyback.dto.FavoriteRecipeDto;
import com.fr.eql.ai115.bellybuddyback.model.FavoriteRecipe;
import com.fr.eql.ai115.bellybuddyback.model.Recipe;
import com.fr.eql.ai115.bellybuddyback.model.UserEntity;
import com.fr.eql.ai115.bellybuddyback.repository.FavoriteRecipeRepository;
import com.fr.eql.ai115.bellybuddyback.repository.RecipeRepository;
import com.fr.eql.ai115.bellybuddyback.repository.UserRepository;
import com.fr.eql.ai115.bellybuddyback.spoonaculardto.CompleteRecipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

  @Autowired
  private FavoriteRecipeRepository favoriteRecipeRepository;

  @Autowired
  private RecipeRepository recipeRepository;

  @Autowired
  private UserRepository userRepository;



  // Cette méthode récupère toutes les recettes favorites d'un utilisateur
  public List<CompleteRecipe> getFavoritesByUserId(Long userId) {
    List<FavoriteRecipe> favoriteRecipes = favoriteRecipeRepository.findByUserId(userId);
    return favoriteRecipes.stream()
      .map(this::convertToCompleteRecipeDto)
      .collect(Collectors.toList());
  }

  // Cette méthode ajoute une recette aux favoris d'un utilisateur
  public FavoriteRecipeDto addFavorite(Recipe recipe, UserEntity user) {
    // Check if the recipe is already in the database
    Optional<Recipe> existingRecipe = recipeRepository.findById(recipe.getId());
    if (!existingRecipe.isPresent()) {
      // If the recipe does not exist, save it
      recipe = recipeRepository.save(recipe);
    } else {
      // If the recipe exists, use the existing one
      recipe = existingRecipe.get();
    }

    // Create the new favorite recipe
    FavoriteRecipe favoriteRecipe = new FavoriteRecipe();
    favoriteRecipe.setRecipe(recipe);
    favoriteRecipe.setUser(user);
    FavoriteRecipe savedFavoriteRecipe = favoriteRecipeRepository.save(favoriteRecipe);

    // Convert to DTO
    return convertToFavoriteRecipeDto(savedFavoriteRecipe);
  }

  // Cette méthode supprime une recette des favoris d'un utilisateur
  public void removeFavorite(Long favoriteId, Long userId) throws Exception {
    FavoriteRecipe favoriteRecipe = favoriteRecipeRepository.findById(favoriteId)
      .orElseThrow(() -> new Exception("Favorite not found"));

    if (!favoriteRecipe.getUser().getId().equals(userId)) {
      throw new Exception("User does not have permission to delete this favorite");
    }

    favoriteRecipeRepository.deleteById(favoriteId);
  }


  // Cette méthode convertit une entité FavoriteRecipe en un DTO CompleteRecipe pour l'envoyer au client
  private CompleteRecipe convertToCompleteRecipeDto(FavoriteRecipe favoriteRecipe) {
    CompleteRecipe dto = new CompleteRecipe();
    dto.setId(favoriteRecipe.getId());
    dto.setTitle(favoriteRecipe.getRecipe().getTitle());
    return dto;
  }

  // Cette méthode récpère toutes les recettes favorites d'un utilisateur
  public List<FavoriteRecipeDto> getAllFavorites() {
    List<FavoriteRecipe> favoriteRecipes = favoriteRecipeRepository.findAll();
    return favoriteRecipes.stream()
      .map(this::convertToFavoriteRecipeDto)
      .collect(Collectors.toList());
  }

  public FavoriteRecipeDto getFavoriteById(Long id) {
    FavoriteRecipe favoriteRecipe = favoriteRecipeRepository.findById(id).orElseThrow();
    return convertToFavoriteRecipeDto(favoriteRecipe);
  }

  // Cette méthode convertit une entité FavoriteRecipe en un DTO FavoriteRecipeDto pour l'envoyer au client
  private FavoriteRecipeDto convertToFavoriteRecipeDto(FavoriteRecipe favoriteRecipe) {
    FavoriteRecipeDto dto = new FavoriteRecipeDto();
    dto.setId(favoriteRecipe.getId());
    dto.setTitle(favoriteRecipe.getRecipe().getTitle());
    return dto;
  }
}

