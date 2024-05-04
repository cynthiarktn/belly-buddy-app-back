package com.fr.eql.ai115.bellybuddyback.service;

import com.fr.eql.ai115.bellybuddyback.dto.apiresponse.CompleteRecipeResponse;
import com.fr.eql.ai115.bellybuddyback.model.*;
import com.fr.eql.ai115.bellybuddyback.repository.DishTypeRepository;
import com.fr.eql.ai115.bellybuddyback.repository.FavoritesRepository;
import com.fr.eql.ai115.bellybuddyback.repository.RecipeRepository;
import com.fr.eql.ai115.bellybuddyback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FavoritesService {

  @Autowired
  private RecipeRepository recipeRepository;

  @Autowired
  private SpoonacularService spoonacularService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private DishTypeRepository dishTypeRepository;

  public Recipe addRecipeToFavorites(Long userId, Long recipeId) throws Exception {
    // Get the complete recipe information from Spoonacular API
    CompleteRecipeResponse recipeInfo = spoonacularService.getCompleteRecipeById(recipeId);

    // Create a new Recipe object and set its fields based on the recipeInfo
    Recipe recipe = new Recipe();
    recipe.setId(recipeInfo.getId());
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
    Set<RecipeIngredient> ingredients = recipeInfo.getExtendedIngredients().stream()
      .map(ingredientResponse -> {
        RecipeIngredient ingredient = new RecipeIngredient();
        ingredient.setName(ingredientResponse.getName());
        return ingredient;
      })
      .collect(Collectors.toSet());
    recipe.setIngredients(ingredients);

    // Set instructions
    Set<RecipeInstructions> instructions = recipeInfo.getAnalyzedInstructions().stream()
      .map(instructionResponse -> {
        RecipeInstructions recipeInstructions = new RecipeInstructions();
        Set<InstructionSteps> steps = instructionResponse.getSteps().stream()
          .map(stepResponse -> {
            InstructionSteps step = new InstructionSteps();
            step.setStep(stepResponse.getStep());
            // Set other fields as necessary...
            return step;
          })
          .collect(Collectors.toSet());
        recipeInstructions.setSteps(steps);
        return recipeInstructions;
      })
      .collect(Collectors.toSet());
    recipe.setInstructions(instructions);

    // Save the recipe to the database
    Recipe savedRecipe = recipeRepository.save(recipe);

    // Add the saved recipe to the user's favorites
    // This assumes that you have a method to add a recipe to a user's favorites
    addUserFavorite(userId, savedRecipe);

    return savedRecipe;
  }

  // Method to add a recipe to a user's favorites
  private void addUserFavorite(Long userId, Recipe recipe) {
    UserEntity user = userRepository.findById(userId)
      .orElseThrow(() -> new RuntimeException("User not found"));

    Favorites favorites = user.getFavorites();
    if (favorites == null) {
      favorites = new Favorites();
      user.setFavorites(favorites);
    }

    favorites.getRecipes().add(recipe);
    userRepository.save(user);
  }
}
