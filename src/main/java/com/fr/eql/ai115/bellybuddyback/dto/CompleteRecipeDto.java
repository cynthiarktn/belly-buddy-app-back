package com.fr.eql.ai115.bellybuddyback.dto;

import lombok.Data;
import java.util.List;

@Data
public class CompleteRecipeDto {
  private Long id;
  private String title;
  private String image;
  private int servings;
  private int readyInMinutes;
  private List<String> dishTypes;
  private List<RecipeIngredientDto> ingredients;
  private List<RecipeInstructionsDto> analyzedInstructions;
}
