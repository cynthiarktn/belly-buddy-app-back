package com.fr.eql.ai115.bellybuddyback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "recipe_instructions")
public class RecipeInstructions {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "recipeInstructions")
  private Set<InstructionSteps> steps;

  @ManyToOne
  @JoinColumn(name = "recipe_id")
  private Recipe recipe;
}
