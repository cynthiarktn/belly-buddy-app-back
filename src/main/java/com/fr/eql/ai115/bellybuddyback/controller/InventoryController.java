package com.fr.eql.ai115.bellybuddyback.controller;

import com.fr.eql.ai115.bellybuddyback.dto.apiresponse.IngredientResponse;
import com.fr.eql.ai115.bellybuddyback.model.Ingredient;
import com.fr.eql.ai115.bellybuddyback.service.InventoryService;
import com.fr.eql.ai115.bellybuddyback.service.SpoonacularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/inventory")
public class InventoryController {

  @Autowired
  private InventoryService inventoryService;

  @Autowired
  private SpoonacularService spoonacularService;

  // Endpoint to add an ingredient to the user's inventory
  @PostMapping("/add/{userId}/{ingredientId}")
  public ResponseEntity<?> addIngredientToInventory(@PathVariable Long userId, @PathVariable Long ingredientId) {
    try {
      IngredientResponse ingredientInfo = spoonacularService.getIngredientInformation(ingredientId);
      Ingredient ingredient = inventoryService.addIngredientToInventory(userId, ingredientId, ingredientInfo);
      return ResponseEntity.ok(ingredient);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add ingredient to inventory: " + e.getMessage());
    }
  }

  @DeleteMapping("/remove/{userId}/{ingredientId}")
  public ResponseEntity<?> removeIngredientFromInventory(@PathVariable Long userId, @PathVariable Long ingredientId) {
    try {
      inventoryService.removeIngredientFromInventory(userId, ingredientId);
      return ResponseEntity.ok("Ingredient removed from inventory");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to remove ingredient from inventory: " + e.getMessage());
    }
  }
}

