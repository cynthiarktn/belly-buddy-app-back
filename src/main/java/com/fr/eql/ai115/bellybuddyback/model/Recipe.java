package com.fr.eql.ai115.bellybuddyback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "recipe")
@Data
@NoArgsConstructor
public class Recipe {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long spoonacularId;
  private String title;
  private String image;
  private int servings;
  private int readyInMinutes;

  @ManyToOne
  private Favorites favorites;

  @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<RecipeIngredient> ingredients;

  @ManyToMany
  @JoinTable(
    name = "recipe_dish_type",
    joinColumns = @JoinColumn(name = "recipe_id"),
    inverseJoinColumns = @JoinColumn(name = "dish_type_id"))
  private Set<DishType> dishTypes;

  @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<RecipeInstructions> instructions;
}
