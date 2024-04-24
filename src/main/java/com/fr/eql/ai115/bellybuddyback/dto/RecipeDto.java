package com.fr.eql.ai115.bellybuddyback.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeDto {
  private Long id;
  private String title;
  private String imageUrl;

  private int readyInMinutes;
  private int servings;
  private String sourceUrl;

  private String summary;
  private List<RecipeStepsDto> analyzedInstructions;
  private List<IngredientDto> ingredients;
}
