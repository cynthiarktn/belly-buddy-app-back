package com.fr.eql.ai115.bellybuddyback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "recipe_ingredient")
@NoArgsConstructor
public class RecipeIngredient {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Double amount;
  private String unit;
  private String original;

  @ManyToOne
  @JoinColumn(name = "recipe_id")
  private Recipe recipe;
}
