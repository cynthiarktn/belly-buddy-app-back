package com.fr.eql.ai115.bellybuddyback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
public class InventoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int inventoryId;

  @ManyToOne
  @JoinColumn(name="user_id", nullable=false)
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name="ingredient_id", nullable=false)
  private IngredientEntity ingredient;

  private int quantity;
}
