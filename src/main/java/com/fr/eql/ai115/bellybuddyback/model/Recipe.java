package com.fr.eql.ai115.bellybuddyback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "recipe")
@Data
@NoArgsConstructor
public class Recipe {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String image;
  private int servings;
  private int readyInMinutes;

  @ElementCollection
  private Set<String> dishTypes;

  @OneToMany(mappedBy = "recipe")
  private List<RecipeIngredient> ingredients;

  @OneToMany(mappedBy = "recipe")
  private List<RecipeInstructions> analyzedInstructions;
}
