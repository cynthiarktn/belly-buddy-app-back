package com.fr.eql.ai115.bellybuddyback.service;

import com.fr.eql.ai115.bellybuddyback.dto.apiresponse.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

@Service
public class SpoonacularService {

  @Value("${spoonacular.api.key}")
  private String apiKey;

  private final RestTemplate restTemplate;
  public SpoonacularService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  // IngredientsServices

  // Searching for ingredients by name
  public IngredientResultsResponse searchIngredient(String ingredient) throws Exception {
    String url = "https://api.spoonacular.com/food/ingredients/search?query=" + ingredient + "&apiKey=" + apiKey;
    ResponseEntity<IngredientResultsResponse> response;
    try {
      response = restTemplate.getForEntity(url, IngredientResultsResponse.class);
    } catch (RestClientException e) {
      throw new Exception("Error while fetching ingredient");
    }
    if (response.getBody() == null) {
      throw new Exception("No ingredient found for this name");
    }
    return response.getBody();
  }


  // Autocomplete ingredients search

  public Set<String> autocompleteIngredientSearch(String partialIngredient) throws Exception {
    String url = "https://api.spoonacular.com/food/ingredients/autocomplete?query=" + partialIngredient + "&number=5&apiKey=" + apiKey;
    ResponseEntity<Set<String>> response;
    try {
      response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Set<String>>() {});
    } catch (RestClientException e) {
      throw new Exception("Error while fetching ingredient suggestions");
    }
    if (response.getBody() == null || response.getBody().isEmpty()) {
      throw new Exception("No ingredient suggestions found for this partial name");
    }
    return response.getBody();
  }

  // Getting ingredient information
  public IngredientResponse getIngredientInformation(Long ingredientId) throws Exception {
    String url = String.format("https://api.spoonacular.com/food/ingredients/%d/information?apiKey=%s", ingredientId, apiKey);
    try {
      ResponseEntity<IngredientResponse> response = restTemplate.getForEntity(url, IngredientResponse.class);
      if (response.getBody() != null) {
        return response.getBody();
      } else {
        throw new Exception("No ingredient found for ID: " + ingredientId);
      }
    } catch (RestClientException e) {
      throw new Exception("API call to Spoonacular failed: " + e.getMessage(), e);
    }
  }


  // RecipesServices

  // Searching for recipes by ingredients
  public Set<RecipesByIngredientsResponse> searchRecipesByIngredients(Set<String> ingredients) throws Exception {
    String ingredientsString = String.join(",", ingredients);
    String url = "https://api.spoonacular.com/recipes/findByIngredients?ingredients=" + ingredientsString + "&apiKey=" + apiKey;
    ResponseEntity<Set<RecipesByIngredientsResponse>> response;
    try {
      response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
      });
    } catch (RestClientException e) {
      throw new Exception("Error while fetching recipes by ingredients");
    }
    if (response.getBody() == null || response.getBody().isEmpty()) {
      throw new Exception("No recipes found by ingredients");
    }
    return response.getBody();
  }

  // Searching for recipes by name
  public Set<RecipesByNameResponse> searchRecipesByName(String recipeName) throws Exception {
    String url = "https://api.spoonacular.com/recipes/complexSearch?query=" + recipeName + "&apiKey=" + apiKey;
    ResponseEntity<RecipesByNameResponseWrapper> response;
    try {
      response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
      });
    } catch (RestClientException e) {
      throw new Exception("Error while fetching recipes by name");
    }
    if (response.getBody() == null || response.getBody().getResults().isEmpty()) {
      throw new Exception("No recipes found for this name");
    }
    return response.getBody().getResults();
  }

// Auto complete recipe search
public Set<String> autocompleteRecipeSearch(String partialRecipe) throws Exception {
  String url = "https://api.spoonacular.com/recipes/autocomplete?query=" + partialRecipe + "&number=5&apiKey=" + apiKey;
  ResponseEntity<Set<String>> response;
  try {
    response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Set<String>>() {});
  } catch (RestClientException e) {
    throw new Exception("Error while fetching recipe suggestions");
  }
  if (response.getBody() == null || response.getBody().isEmpty()) {
    throw new Exception("No recipe suggestions found for this partial name");
  }
  return response.getBody();
}

  // Getting recipe information
  public CompleteRecipeResponse getCompleteRecipeById(Long recipeId) throws Exception {
    String url = "https://api.spoonacular.com/recipes/" + recipeId + "/information?apiKey=" + apiKey;
    ResponseEntity<CompleteRecipeResponse> response;
    try {
      response = restTemplate.getForEntity(url, CompleteRecipeResponse.class);
    } catch (RestClientException e) {
      throw new Exception("Error while fetching recipe");
    }
    if (response.getBody() == null) {
      throw new Exception("No recipe found for this id");
    }
    return response.getBody();
  }


}
