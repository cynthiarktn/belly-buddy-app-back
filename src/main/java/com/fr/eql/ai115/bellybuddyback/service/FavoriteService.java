package com.fr.eql.ai115.bellybuddyback.service;


import com.fr.eql.ai115.bellybuddyback.model.Favorite;
import com.fr.eql.ai115.bellybuddyback.model.UserEntity;
import com.fr.eql.ai115.bellybuddyback.repository.FavoriteRecipeRepository;
import com.fr.eql.ai115.bellybuddyback.repository.UserRepository;
import com.fr.eql.ai115.bellybuddyback.spoonaculardto.CompleteRecipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

  @Autowired
  private FavoriteRecipeRepository favoriteRecipeRepository;


  @Autowired
  private UserRepository userRepository;

  // Cette méthode récupère toutes les recettes favorites d'un utilisateur
  public List<CompleteRecipe> getFavoritesByUserId(Long userId) {
    List<Favorite> favorites = favoriteRecipeRepository.findByUserId(userId);
    return favorites.stream()
      .map(this::convertToDto)
      .collect(Collectors.toList());
  }

  // Cette méthode ajoute une recette aux favoris d'un utilisateur
  public Favorite addFavorite(CompleteRecipe recipe, Long userId) {
    UserEntity user = userRepository.findById(userId).orElseThrow();
    Favorite favorite = new Favorite();
    favorite.setRecipeName(recipe.getTitle());
    favorite.setUser(user);
    favoriteRecipeRepository.save(favorite);
    return favorite;
  }

  // Cette méthode supprime une recette des favoris d'un utilisateur
  public void removeFavorite(Long favoriteId, Long userId) throws Exception {
    Favorite favorite = favoriteRecipeRepository.findById(favoriteId)
      .orElseThrow(() -> new Exception("Favorite not found"));

    if (!favorite.getUser().getId().equals(userId)) {
      throw new Exception("User does not have permission to delete this favorite");
    }

    favoriteRecipeRepository.deleteById(favoriteId);
  }

  // Cette méthode convertit une entité FavoriteRecipe en un DTO pour l'envoyer au client
  private CompleteRecipe convertToDto(Favorite favorite) {
    CompleteRecipe dto = new CompleteRecipe();
    dto.setId(favorite.getId());
    dto.setTitle(favorite.getRecipeName());
    return dto;
  }
}
