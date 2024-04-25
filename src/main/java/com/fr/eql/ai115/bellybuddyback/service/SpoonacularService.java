package com.fr.eql.ai115.bellybuddyback.service;

import com.fr.eql.ai115.bellybuddyback.spoonaculardto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SpoonacularService {

  @Value("${spoonacular.api.key}")
  private String apiKey;

  private final RestTemplate restTemplate;
  public SpoonacularService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  // RecipesServices

  // Searching for recipes by ingredients
  public List<RecipesByIngredientsResponse> searchRecipesByIngredients(List<String> ingredients) throws Exception {
    String ingredientsString = String.join(",", ingredients);
    String url = "https://api.spoonacular.com/recipes/findByIngredients?ingredients=" + ingredientsString + "&apiKey=" + apiKey;
    ResponseEntity<List<RecipesByIngredientsResponse>> response;
    try {
      response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<RecipesByIngredientsResponse>>() {});
    } catch (RestClientException e) {
      throw new Exception("Error while fetching recipes by ingredients");
    }
    if (response.getBody() == null || response.getBody().isEmpty()) {
      throw new Exception("No recipes found by ingredients");
    }
    return response.getBody();
  }


  // Getting a complete recipe
  public CompleteRecipe getRecipeInformation(Long id) throws Exception {
    String url = "https://api.spoonacular.com/recipes/" + id + "/information?apiKey=" + apiKey;
    ResponseEntity<CompleteRecipe> response;
    try {
      response = restTemplate.getForEntity(url, CompleteRecipe.class);
    } catch (RestClientException e) {
      throw new Exception("Error while fetching recipe");
    }
    if (response.getBody() == null) {
      throw new Exception("No recipe found for this id");
    }
    return response.getBody();
  }


  // Getting recipe instructions
  public List<RecipeInstructions> getAnalyzedInstructions(Long recipeId) throws Exception {
    String url = "https://api.spoonacular.com/recipes/" + recipeId + "/analyzedInstructions?apiKey=" + apiKey;
    ResponseEntity<List<RecipeInstructions>> response;
    try {
      response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
      });
    } catch (RestClientException e) {
      throw new Exception("Error while fetching recipe instructions");

    }
    if (response.getBody() == null || response.getBody().isEmpty()) {
      throw new Exception("No instructions found for this recipe");
    }
    return response.getBody();
  }

  // Getting a random recipe if no ingredients are provided
  public List<CompleteRecipe> getRandomRecipes() throws Exception {
    String url = "https://api.spoonacular.com/recipes/random?number=9&apiKey=" + apiKey;
    ResponseEntity<List<CompleteRecipe>> response;
    try {
      response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
      });
    } catch (RestClientException e) {
      throw new Exception("Error while fetching random recipes");
    }
    if (response.getBody() == null || response.getBody().isEmpty()) {
      throw new Exception("No random recipes found");
    }
    return response.getBody();
  }

  // Getting recipes by inventory ingredients
  public List<RecipesByIngredientsResponse> getRecipesByInventoryIngredients(List<String> ingredients) throws Exception {
    String url = "https://api.spoonacular.com/recipes/findByIngredients?ingredients=" + String.join(",", ingredients) + "&number=9&apiKey=" + apiKey;
    ResponseEntity<List<RecipesByIngredientsResponse>> response;
    try {
      response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
      });
    } catch (RestClientException e) {
      throw new Exception("Error while fetching recipes by inventory ingredients");
    }
    if (response.getBody() == null || response.getBody().isEmpty()) {
      throw new Exception("No recipes found by inventory ingredients");
    }
    return response.getBody();
  }

  // Getting recipes based on inventory

  public IngredientResults searchIngredient(String ingredient) throws Exception {
    String url = "https://api.spoonacular.com/food/ingredients/search?query=" + ingredient + "&apiKey=" + apiKey;
    ResponseEntity<IngredientResults> response;
    try {
      response = restTemplate.getForEntity(url, IngredientResults.class);
    } catch (RestClientException e) {
      throw new Exception("Error while fetching ingredient");
    }
    if (response.getBody() == null) {
      throw new Exception("No ingredient found for this name");
    }
    return response.getBody();
  }


}
