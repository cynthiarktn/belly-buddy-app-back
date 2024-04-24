package com.fr.eql.ai115.bellybuddyback.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeStepsDto {
  private int number;
  private String step;
  private List<IngredientDto> ingredients;
}
