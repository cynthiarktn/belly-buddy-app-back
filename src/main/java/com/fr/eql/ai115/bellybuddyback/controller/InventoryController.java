package com.fr.eql.ai115.bellybuddyback.controller;

import com.fr.eql.ai115.bellybuddyback.model.InventoryItem;
import com.fr.eql.ai115.bellybuddyback.model.UserEntity;
import com.fr.eql.ai115.bellybuddyback.service.InventoryService;
import com.fr.eql.ai115.bellybuddyback.service.SpoonacularService;
import org.springframework.beans.factory.annotation.Autowired;
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

  @GetMapping("/getIngredients")
  public ResponseEntity<List<InventoryItem>> getInventoryIngredients(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
    List<InventoryItem> ingredients = inventoryService.getAllInventoryItems(userDetails.getUsername());
    return ResponseEntity.ok(ingredients);
  }

  @PostMapping("/addIngredient")
  public ResponseEntity<InventoryItem> addIngredient(@RequestBody InventoryItem inventoryItem, @AuthenticationPrincipal UserDetails userDetails) {
    UserEntity user = (UserEntity) userDetails;
    InventoryItem addedItem = inventoryService.addInventoryItem(inventoryItem);
    return ResponseEntity.ok(addedItem);
  }


}
