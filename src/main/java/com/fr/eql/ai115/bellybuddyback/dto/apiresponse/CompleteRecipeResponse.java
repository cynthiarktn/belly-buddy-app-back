package com.fr.eql.ai115.bellybuddyback.dto.apiresponse;

import lombok.Data;

import java.util.Set;

@Data
public class CompleteRecipeResponse {
  private Long id;
  private String title;
  private String image;
  private int servings;
  private int readyInMinutes;
  private Set<String> dishTypes;
  private Set<RecipeIngredientResponse> extendedIngredients;
}
