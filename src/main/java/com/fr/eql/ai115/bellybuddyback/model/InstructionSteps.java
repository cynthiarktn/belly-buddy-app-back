package com.fr.eql.ai115.bellybuddyback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "instruction_steps")
public class InstructionSteps {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String step;

  @ManyToOne
  @JoinColumn(name = "recipe_instructions_id")
  private RecipeInstructions recipeInstructions;
}
