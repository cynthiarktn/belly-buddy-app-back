package com.fr.eql.ai115.bellybuddyback.service;

import com.fr.eql.ai115.bellybuddyback.dto.IngredientDto;
import com.fr.eql.ai115.bellybuddyback.dto.RecipeDto;
import com.fr.eql.ai115.bellybuddyback.exception.RecipeExceptions;
import com.fr.eql.ai115.bellybuddyback.model.InventoryItem;
import com.fr.eql.ai115.bellybuddyback.repository.InventoryItemRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpoonacularService {

  @Value("${spoonacular.api.key}")
  private String apiKey;

  private final RestTemplate restTemplate;
  private InventoryItemRepository inventoryRepository;
  public SpoonacularService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public IngredientDto searchIngredient(String ingredient) {
    String url = "https://api.spoonacular.com/food/ingredients/search?query=" + ingredient + "&apiKey=" + apiKey;
    ResponseEntity<IngredientDto> response;
    try {
      response = restTemplate.getForEntity(url, IngredientDto.class);
    } catch (RestClientException e) {
      throw new RecipeExceptions.IngredientNotFoundException(ingredient);
    }
    if (response.getBody() == null) {
      throw new RecipeExceptions.IngredientNotFoundException(ingredient);
    }
    return response.getBody();
  }

  public List<RecipeDto> searchRecipesByIngredients(List<String> ingredients) {
    // Convertir la liste d'ingrédients en une seule chaîne séparée par des virgules
    String ingredientsString = String.join(",", ingredients);

    // Construire l'URL pour l'API Spoonacular
    String url = "https://api.spoonacular.com/recipes/findByIngredients?ingredients=" + ingredientsString + "&apiKey=" + apiKey;

    // Envoyer la requête à l'API Spoonacular et obtenir la réponse
    ResponseEntity<List<RecipeDto>> response;
    try {
      response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<RecipeDto>>() {});
    } catch (RestClientException e) {
      throw new RecipeExceptions.IngredientNotFoundException(ingredientsString);
    }

    // Si la réponse est vide, lancer une exception
    if (response.getBody() == null || response.getBody().isEmpty()) {
      throw new RecipeExceptions.IngredientNotFoundException(ingredientsString);
    }

    // Retourner la liste des recettes
    return response.getBody();
  }

  public List<RecipeDto> getRandomRecipes() {
    String url = "https://api.spoonacular.com/recipes/random?number=9&apiKey=" + apiKey;
    ResponseEntity<List<RecipeDto>> response;
    try {
      response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
      });
    } catch (RestClientException e) {
      throw new RecipeExceptions.NoRandomRecipeFoundException();
    }
    if (response.getBody() == null || response.getBody().isEmpty()) {
      throw new RecipeExceptions.NoRandomRecipeFoundException();
    }
    return response.getBody();
  }
  public List<RecipeDto> getRecipesByInventoryIngredients(List<String> ingredients) throws Exception {
    String url = "https://api.spoonacular.com/recipes/findByIngredients?ingredients=" + String.join(",", ingredients) + "&number=9&apiKey=" + apiKey;
    ResponseEntity<List<RecipeDto>> response;
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

  /* This method allows to get recipes based on the user's inventory,
  * if the inventory is empty, we call the method getRandomRecipes
  * if the inventory conntains ingredients, we call the method getRecipesByInventoryIngredients*/
  public List<RecipeDto> getRecipesBasedOnInventory(Long userId) throws Exception {
    List<InventoryItem> inventoryItems = inventoryRepository.findByUserId(userId);
    SpoonacularService spoonacularService = new SpoonacularService(restTemplate);
    if (inventoryItems.isEmpty()) {
      return spoonacularService.getRandomRecipes();
    } else {
      List<String> ingredients = inventoryItems.stream()
        .map(InventoryItem::getName)
        .collect(Collectors.toList());
      return spoonacularService.getRecipesByInventoryIngredients(ingredients);
    }
  }

}
