package com.fr.eql.ai115.bellybuddyback.controller;

import com.fr.eql.ai115.bellybuddyback.model.Ingredient;
import com.fr.eql.ai115.bellybuddyback.model.InventoryItem;
import com.fr.eql.ai115.bellybuddyback.model.UserEntity;
import com.fr.eql.ai115.bellybuddyback.repository.UserRepository;
import com.fr.eql.ai115.bellybuddyback.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/user/inventory")
public class InventoryController {
  private final InventoryService inventoryService;
  private final UserRepository userRepository;

  @Autowired
  public InventoryController(InventoryService inventoryService, UserRepository userRepository) {
    this.inventoryService = inventoryService;
    this.userRepository = userRepository;
  }

  @GetMapping("/getIngredients")
  public ResponseEntity<List<InventoryItem>> getInventoryIngredients(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
    List<InventoryItem> ingredients = inventoryService.getAllInventoryItems(userDetails.getUsername());
    return ResponseEntity.ok(ingredients);
  }

  @PostMapping("/addIngredient")
  public ResponseEntity<InventoryItem> addIngredient(@RequestBody Ingredient ingredient, Principal principal) throws Exception {
    UserEntity user = userRepository.findByUsername(principal.getName())
      .orElseThrow(() -> new Exception("User not found"));
    InventoryItem addedItem = inventoryService.addIngredientToInventory(ingredient, user);
    return ResponseEntity.ok(addedItem);
  }

  @DeleteMapping("/deleteIngredient/{id}")
  public ResponseEntity<String> deleteIngredient(@PathVariable Long id) throws Exception {
    inventoryService.deleteInventoryItem(id);
    return ResponseEntity.ok("Ingredient deleted");
  }


}
