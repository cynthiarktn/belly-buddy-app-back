package com.fr.eql.ai115.bellybuddyback.service;

import com.fr.eql.ai115.bellybuddyback.dto.RecipeDto;
import com.fr.eql.ai115.bellybuddyback.exception.InventoryExceptions;
import com.fr.eql.ai115.bellybuddyback.model.Favorite;
import com.fr.eql.ai115.bellybuddyback.model.UserEntity;
import com.fr.eql.ai115.bellybuddyback.repository.FavoriteRecipeRepository;
import com.fr.eql.ai115.bellybuddyback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

  @Autowired
  private FavoriteRecipeRepository favoriteRecipeRepository;

  @Autowired
  private UserRepository userRepository;

  // Cette méthode récupère toutes les recettes favorites d'un utilisateur
  public List<RecipeDto> getFavoritesByUserId(Long userId) {
    List<Favorite> favorites = favoriteRecipeRepository.findByUserId(userId);
    return favorites.stream()
      .map(this::convertToDto)
      .collect(Collectors.toList());
  }

  // Cette méthode ajoute une recette aux favoris d'un utilisateur
  public void addFavorite(Long id, int spoonacularId, String recipeName) {
    UserEntity user = userRepository.findById(id)
      .orElseThrow(() -> new InventoryExceptions.InventoryItemNotFoundException(id));

    Favorite favorite = new Favorite();
    favorite.setUser(user);
    favorite.setSpoonacularId(spoonacularId);
    favorite.setRecipeName(recipeName);

    favoriteRecipeRepository.save(favorite);
  }

  // Cette méthode supprime une recette des favoris d'un utilisateur
  public void removeFavorite(Long favoriteId, Long userId) throws Exception {
    Favorite favorite = favoriteRecipeRepository.findById(favoriteId)
      .orElseThrow(() -> new InventoryExceptions.InventoryItemNotFoundException(favoriteId));

    if (!favorite.getUser().getId().equals(userId)) {
      throw new Exception("User does not have permission to delete this favorite");
    }

    favoriteRecipeRepository.deleteById(favoriteId);
  }

  // Cette méthode convertit une entité FavoriteRecipe en un DTO pour l'envoyer au client
  private RecipeDto convertToDto(Favorite favorite) {
    RecipeDto dto = new RecipeDto();
    dto.setId(favorite.getId());
    dto.setTitle(favorite.getRecipeName());
    return dto;
  }
}
