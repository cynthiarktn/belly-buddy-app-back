package com.fr.eql.ai115.bellybuddyback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "recipe_ingredient")
@NoArgsConstructor
public class RecipeIngredients {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Double amount;
  private String unit;
  private String original;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "recipe_id")
  private Recipe recipe;
}
