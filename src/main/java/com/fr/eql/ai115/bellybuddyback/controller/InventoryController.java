package com.fr.eql.ai115.bellybuddyback.controller;

import com.fr.eql.ai115.bellybuddyback.dto.IngredientSearchResponse;
import com.fr.eql.ai115.bellybuddyback.model.Ingredient;
import com.fr.eql.ai115.bellybuddyback.service.InventoryService;
import com.fr.eql.ai115.bellybuddyback.service.SpoonacularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/inventory")
public class InventoryController {
  private final InventoryService inventoryService;
  private final SpoonacularService spoonacularService;

  @Autowired
  public InventoryController(InventoryService inventoryService, SpoonacularService spoonacularService) {
    this.inventoryService = inventoryService;
    this.spoonacularService = spoonacularService;
  }

  @GetMapping
  public ResponseEntity<List<String>> getInventoryIngredients(@AuthenticationPrincipal UserDetails userDetails) {
    int userId = Integer.parseInt(userDetails.getUsername());
    List<String> ingredients = inventoryService.getInventoryIngredients(userId);
    return ResponseEntity.ok(ingredients);
  }

  @PostMapping("/add")
  public ResponseEntity<?> addIngredientToInventory(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Ingredient ingredient) {
    if (userDetails == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
    }
    int userId = Integer.parseInt(userDetails.getUsername());

    // Search for the ingredient in the Spoonacular API
    IngredientSearchResponse response = spoonacularService.searchIngredient(ingredient.getName()).block();

    // If the ingredient is not found in the Spoonacular API, return a bad request response
    if (response == null || response.getResults().isEmpty()) {
      return ResponseEntity.badRequest().body("Ingredient not found in Spoonacular API");
    }

    // If the ingredient is found, add it to the inventory
    inventoryService.addIngredientToInventory(userId, ingredient);

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/remove")
  public ResponseEntity<Void> removeIngredientFromInventory(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Ingredient ingredient) {
    int userId = Integer.parseInt(userDetails.getUsername());
    inventoryService.removeIngredientFromInventory(userId, ingredient);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/update")
  public ResponseEntity<Void> updateIngredientInInventory(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Ingredient ingredient) {
    int userId = Integer.parseInt(userDetails.getUsername());
    inventoryService.updateIngredientInInventory(userId, ingredient);
    return ResponseEntity.ok().build();
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }
}
