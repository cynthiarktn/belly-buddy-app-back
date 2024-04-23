package com.fr.eql.ai115.bellybuddyback.service;

import com.fr.eql.ai115.bellybuddyback.model.Ingredient;
import com.fr.eql.ai115.bellybuddyback.model.Inventory;
import com.fr.eql.ai115.bellybuddyback.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {
  private final InventoryRepository inventoryRepository;

  @Autowired
  public InventoryService(InventoryRepository inventoryRepository) {
    this.inventoryRepository = inventoryRepository;
  }

  public List<String> getInventoryIngredients(int userId) {
    Inventory inventory = getInventoryByUserId(userId);
    if (inventory.getIngredients().isEmpty()) {
      throw new RuntimeException("Inventory is empty for user with id " + userId);
    }
    return inventory.getIngredients().stream()
      .map(Ingredient::getName)
      .collect(Collectors.toList());
  }

  public void addIngredientToInventory(int userId, Ingredient ingredient) {
    Inventory inventory = getInventoryByUserId(userId);
    inventory.getIngredients().add(ingredient);
    inventoryRepository.save(inventory);
  }

  public void removeIngredientFromInventory(int userId, Ingredient ingredient) {
    Inventory inventory = getInventoryByUserId(userId);
    if (!inventory.getIngredients().remove(ingredient)) {
      throw new RuntimeException("Ingredient not found in inventory");
    }
    inventoryRepository.save(inventory);
  }

  public void updateIngredientInInventory(int userId, Ingredient ingredient) {
    Inventory inventory = getInventoryByUserId(userId);
    int index = inventory.getIngredients().indexOf(ingredient);
    if (index != -1) {
      inventory.getIngredients().set(index, ingredient);
      inventoryRepository.save(inventory);
    } else {
      throw new RuntimeException("Ingredient not found in inventory");
    }
  }

  private Inventory getInventoryByUserId(int userId) {
    return inventoryRepository.findByUser_UserId(userId)
      .orElseThrow(() -> new RuntimeException("No inventory found for user with id " + userId));
  }
}
