package com.fr.eql.ai115.bellybuddyback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
public class Inventory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int inventoryId;

  @OneToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @ManyToMany
  @JoinTable(
    name = "inventory_ingredient",
    joinColumns = @JoinColumn(name = "inventory_id"),
    inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
  private List<Ingredient> ingredients;
}
