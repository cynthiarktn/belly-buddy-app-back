package com.fr.eql.ai115.bellybuddyback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "favorite_list")
@Data
@NoArgsConstructor
public class FavoriteList {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int favoriteListId;

  @OneToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @ManyToMany
  @JoinTable(
    name = "favorite_list_recipe",
    joinColumns = @JoinColumn(name = "favorite_list_id"),
    inverseJoinColumns = @JoinColumn(name = "recipe_id"))
  private List<Recipe> recipes;
}
