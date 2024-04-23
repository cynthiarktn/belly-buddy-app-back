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

  @GetMapping("/ingredients")
  public ResponseEntity<List<String>> getInventoryIngredients(@AuthenticationPrincipal UserDetails userDetails) {
    int userId = Integer.parseInt(userDetails.getUsername());
    List<String> ingredients = inventoryService.getInventoryIngredients(userDetails.getUsername());
    return ResponseEntity.ok(ingredients);
  }

  @GetMapping("/search/ingredient")
  public ResponseEntity<IngredientSearchResponse> searchIngredient(@RequestParam String name) {
    IngredientSearchResponse response = spoonacularService.searchIngredient(name).block();
    return ResponseEntity.ok(response);
  }

  @PostMapping("/add")
  public ResponseEntity<?> addIngredientToInventory(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Ingredient ingredient) {
    inventoryService.addIngredientToInventory(userDetails.getUsername(), ingredient);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/remove")
  public ResponseEntity<Void> removeIngredientFromInventory(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Ingredient ingredient) {
    inventoryService.removeIngredientFromInventory(userDetails.getUsername(), ingredient);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/update")
  public ResponseEntity<Void> updateIngredientInInventory(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Ingredient ingredient) {
    inventoryService.updateIngredientInInventory(userDetails.getUsername(), ingredient);
    return ResponseEntity.ok().build();
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }
}
