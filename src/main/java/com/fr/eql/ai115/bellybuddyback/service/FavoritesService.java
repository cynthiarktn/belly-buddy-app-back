package com.fr.eql.ai115.bellybuddyback.service;

import com.fr.eql.ai115.bellybuddyback.dto.apiresponse.CompleteRecipeResponse;
import com.fr.eql.ai115.bellybuddyback.model.*;
import com.fr.eql.ai115.bellybuddyback.repository.DishTypeRepository;
import com.fr.eql.ai115.bellybuddyback.repository.FavoritesRepository;
import com.fr.eql.ai115.bellybuddyback.repository.RecipeRepository;
import com.fr.eql.ai115.bellybuddyback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FavoritesService {

  @Autowired
  private RecipeRepository recipeRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private DishTypeRepository dishTypeRepository;

  @Autowired
  private FavoritesRepository favoritesRepository;

  public Favorites findOrCreateFavoritesForUser(Long userId) {
    UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    Favorites favorites = user.getFavorites();
    if (favorites == null) {
      favorites = new Favorites();
      favorites.setUser(user);
      user.setFavorites(favorites);
      favoritesRepository.save(favorites);
    }
    return favorites;
  }

  public Recipe addRecipeToFavorites(Long userId, Long recipeId, CompleteRecipeResponse recipeInfo) throws Exception {
    Favorites favorites = findOrCreateFavoritesForUser(userId);
    Recipe recipe = new Recipe();
    recipe.setId(recipeInfo.getId());
    recipe.setSpoonacularId(recipeId);
    recipe.setTitle(recipeInfo.getTitle());
    recipe.setImage(recipeInfo.getImage());
    recipe.setServings(recipeInfo.getServings());
    recipe.setReadyInMinutes(recipeInfo.getReadyInMinutes());

    // Set dishTypes
    Set<DishType> dishTypes = recipeInfo.getDishTypes().stream()
      .map(dishTypeName -> {
        DishType dishType = new DishType();
        dishType.setName(dishTypeName);
        dishType = dishTypeRepository.save(dishType);
        return dishType;
      })
      .collect(Collectors.toSet());
    recipe.setDishTypes(dishTypes);

    // Set ingredients
    Set<RecipeIngredients> ingredients = recipeInfo.getExtendedIngredients().stream()
      .map(ingredientResponse -> {
        RecipeIngredients ingredient = new RecipeIngredients();
        ingredient.setName(ingredientResponse.getName());
        ingredient.setAmount(Double.valueOf(ingredientResponse.getAmount()));
        ingredient.setUnit(String.valueOf(ingredientResponse.getUnit()));
        ingredient.setOriginal(ingredientResponse.getOriginal());
        ingredient.setRecipe(recipe);
        return ingredient;
      })
      .collect(Collectors.toSet());
    recipe.setIngredients(ingredients);

    recipe.setFavorites(favorites);
    Recipe savedRecipe = recipeRepository.save(recipe);
    favorites.getRecipes().add(savedRecipe);
    favoritesRepository.save(favorites);
    return recipe;
  }

  @Transactional
  public void removeRecipeFromFavorites(Long userId, Long recipeId) {
    Favorites favorites = findOrCreateFavoritesForUser(userId);
    Recipe recipe = recipeRepository.findById(recipeId)
      .orElseThrow(() -> new RuntimeException("Recipe not found"));
    if (favorites.getRecipes().contains(recipe)) {
      favorites.getRecipes().remove(recipe);
      favoritesRepository.save(favorites);
      recipeRepository.delete(recipe);
    } else {
      throw new RuntimeException("Recipe not found in favorites");
    }
  }

}
