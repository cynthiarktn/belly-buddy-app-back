package com.fr.eql.ai115.bellybuddyback.service;

import com.fr.eql.ai115.bellybuddyback.exception.InventoryExceptions;
import com.fr.eql.ai115.bellybuddyback.model.InventoryItem;
import com.fr.eql.ai115.bellybuddyback.model.UserEntity;
import com.fr.eql.ai115.bellybuddyback.repository.InventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

  private final InventoryItemRepository inventoryRepository;

  @Autowired
  public InventoryService(InventoryItemRepository inventoryRepository) {
    this.inventoryRepository = inventoryRepository;
  }

  // Cette méthode récupère tous les éléments de l'inventaire
  public List<InventoryItem> getAllInventoryItems(String username) throws Exception {
    if (username == null) {
      throw new Exception("No user found");
    }
    return inventoryRepository.findAll();
  }

  // Cette méthode ajoute un nouvel élément à l'inventaire
  public InventoryItem addInventoryItem(InventoryItem item) {
    if (inventoryRepository.existsById(item.getId())) {
      throw new InventoryExceptions.InventoryItemAlreadyExistsException(item.getId());
    }
    return inventoryRepository.save(item);
  }

  // Cette méthode met à jour un élément de l'inventaire
  public InventoryItem updateInventoryItem(InventoryItem item) {
    if (!inventoryRepository.existsById(item.getId())) {
      throw new InventoryExceptions.InventoryItemNotFoundException(item.getId());
    }
    return inventoryRepository.save(item);
  }

  // Cette méthode supprime un élément de l'inventaire
  public void deleteInventoryItem(Long id) {
    if (!inventoryRepository.existsById(id)) {
      throw new InventoryExceptions.InventoryItemNotFoundException(id);
    }
    inventoryRepository.deleteById(id);
  }
}
