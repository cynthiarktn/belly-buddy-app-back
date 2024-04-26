package com.fr.eql.ai115.bellybuddyback.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class RecipeInstructions {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToMany(mappedBy = "recipeInstructions")
  private List<InstructionsSteps> steps;

  @ManyToOne
  @JoinColumn(name = "recipe_id")
  private Recipe recipe;
}
