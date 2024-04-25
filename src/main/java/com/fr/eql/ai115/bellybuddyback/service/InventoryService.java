package com.fr.eql.ai115.bellybuddyback.service;

import com.fr.eql.ai115.bellybuddyback.model.Ingredient;
import com.fr.eql.ai115.bellybuddyback.model.InventoryItem;
import com.fr.eql.ai115.bellybuddyback.model.UserEntity;
import com.fr.eql.ai115.bellybuddyback.repository.IngredientRepository;
import com.fr.eql.ai115.bellybuddyback.repository.InventoryItemRepository;
import com.fr.eql.ai115.bellybuddyback.spoonaculardto.IngredientResponse;
import com.fr.eql.ai115.bellybuddyback.spoonaculardto.IngredientResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

  @Autowired
  private InventoryItemRepository inventoryRepository;

  @Autowired
  private IngredientRepository ingredientRepository;

  @Autowired
  private SpoonacularService spoonacularService;

  // Cette méthode appelle l'API Spoonacular pour récupérer les ingrédients correspondant à une recherche
  public IngredientResults getIngredients(String ingredientName) throws Exception {
    return spoonacularService.searchIngredient(ingredientName);
  }

  // Cette méthode récupère tous les éléments de l'inventaire
  public List<InventoryItem> getAllInventoryItems(String username) throws Exception {
    if (username == null) {
      throw new Exception("No user found");
    }
    return inventoryRepository.findAll();
  }

  // Cette méthode ajoute un nouvel élément à l'inventaire
  public InventoryItem addIngredientToInventory(Ingredient ingredient, UserEntity user) {
    InventoryItem item = new InventoryItem();
    item.setIngredient(ingredient);
    item.setUser(user);
    return inventoryRepository.save(item);
  }

  // Cette méthode met à jour un élément de l'inventaire
  public InventoryItem updateInventoryItem(InventoryItem item) throws Exception {
    if (!inventoryRepository.existsById(item.getId())) {
      throw new Exception("Item not found");
    }
    return inventoryRepository.save(item);
  }

  // Cette méthode supprime un élément de l'inventaire
  public void deleteInventoryItem(Long id) throws Exception {
    if (!inventoryRepository.existsById(id)) {
      throw new Exception("Item not found");
    }
    inventoryRepository.deleteById(id);
  }
}
