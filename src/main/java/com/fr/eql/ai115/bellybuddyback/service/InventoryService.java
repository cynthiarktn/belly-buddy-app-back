package com.fr.eql.ai115.bellybuddyback.service;

import com.fr.eql.ai115.bellybuddyback.dto.IngredientSearchResponse;
import com.fr.eql.ai115.bellybuddyback.model.Ingredient;
import com.fr.eql.ai115.bellybuddyback.model.Inventory;
import com.fr.eql.ai115.bellybuddyback.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {
  private final InventoryRepository inventoryRepository;

  @Autowired
  private SpoonacularService spoonacularService;

  @Autowired
  public InventoryService(InventoryRepository inventoryRepository) {
    this.inventoryRepository = inventoryRepository;
  }

  public List<String> getInventoryIngredients(String username) {
    Inventory inventory = getInventoryByUsername(username);
    if (inventory.getIngredients().isEmpty()) {
      throw new RuntimeException("Inventory is empty for user with username " + username);
    }
    return inventory.getIngredients().stream()
      .map(Ingredient::getName)
      .collect(Collectors.toList());
  }

  public void addIngredientToInventory(String username, Ingredient ingredient) {
    Inventory inventory = getInventoryByUsername(username);
    inventory.getIngredients().add(ingredient);
    inventoryRepository.save(inventory);
  }

  public void removeIngredientFromInventory(String username, Ingredient ingredient) {
    Inventory inventory = getInventoryByUsername(username);
    if (!inventory.getIngredients().remove(ingredient)) {
      throw new RuntimeException("Ingredient not found in inventory");
    }
    inventoryRepository.save(inventory);
  }

  public void updateIngredientInInventory(String username, Ingredient ingredient) {
    Inventory inventory = getInventoryByUsername(username);
    int index = inventory.getIngredients().indexOf(ingredient);
    if (index != -1) {
      inventory.getIngredients().set(index, ingredient);
      inventoryRepository.save(inventory);
    } else {
      throw new RuntimeException("Ingredient not found in inventory");
    }
  }

  private Inventory getInventoryByUsername(String username) {
    return inventoryRepository.findByUser_Username(username)
      .orElseThrow(() -> new RuntimeException("No inventory found for user " + username));
  }
}
