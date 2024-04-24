package com.fr.eql.ai115.bellybuddyback.repository;

import com.fr.eql.ai115.bellybuddyback.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

  List<InventoryItem> findByUserId(Long userId);
}
