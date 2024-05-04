package com.fr.eql.ai115.bellybuddyback.service;

import com.fr.eql.ai115.bellybuddyback.model.Ingredient;
import com.fr.eql.ai115.bellybuddyback.model.Inventory;
import com.fr.eql.ai115.bellybuddyback.model.UserEntity;
import com.fr.eql.ai115.bellybuddyback.repository.IngredientRepository;
import com.fr.eql.ai115.bellybuddyback.repository.InventoryRepository;
import com.fr.eql.ai115.bellybuddyback.repository.UserRepository;
import com.fr.eql.ai115.bellybuddyback.dto.apiresponse.IngredientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
public class InventoryService {

  @Autowired
  private InventoryRepository inventoryRepository;

  @Autowired
  private IngredientRepository ingredientRepository;

  @Autowired
  private UserRepository userRepository;

  // Supposons que vous avez un méthode pour obtenir ou créer un inventaire
  public Inventory findOrCreateInventoryForUser(Long userId) {
    UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    Inventory inventory = user.getInventory();
    if (inventory == null) {
      inventory = new Inventory();
      inventory.setUser(user);
      user.setInventory(inventory);
      inventoryRepository.save(inventory);
    }
    return inventory;
  }

  public Ingredient addIngredientToInventory(Long userId, Long ingredientId, IngredientResponse ingredientInfo) {
    Inventory inventory = findOrCreateInventoryForUser(userId);
    Ingredient ingredient = new Ingredient();
    ingredient.setName(ingredientInfo.getName());
    ingredient.setImage(ingredientInfo.getImage());
    ingredient.setSpoonacularId(ingredientId);
    ingredient.setInventory(inventory);
    Ingredient addedIngredient = ingredientRepository.save(ingredient);
    inventory.getIngredients().add(addedIngredient);
    inventoryRepository.save(inventory);
    return ingredient;
  }

  @Transactional
  public void removeIngredientFromInventory(Long userId, Long ingredientId) {
    Inventory inventory = findOrCreateInventoryForUser(userId);
    Ingredient ingredient = ingredientRepository.findById(ingredientId)
      .orElseThrow(() -> new RuntimeException("Ingredient not found"));
    if (inventory.getIngredients().contains(ingredient)) {
      inventory.getIngredients().remove(ingredient);
      inventoryRepository.save(inventory);
      ingredientRepository.delete(ingredient);
    } else {
      throw new RuntimeException("Ingredient not found in inventory");
    }
  }
}



