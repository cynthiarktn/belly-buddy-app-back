package com.fr.eql.ai115.bellybuddyback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String username;
  private String email;
  private String password;

  @OneToMany(mappedBy = "user")
  private List<InventoryItem> inventory;

  @OneToMany(mappedBy = "user")
  private List<FavoriteRecipe> favoriteRecipes;
}
