package com.fr.eql.ai115.bellybuddyback.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class InstructionsSteps {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String step;

  @ManyToOne
  @JoinColumn(name = "instruction_id")
  private RecipeInstructions recipeInstructions;
}
