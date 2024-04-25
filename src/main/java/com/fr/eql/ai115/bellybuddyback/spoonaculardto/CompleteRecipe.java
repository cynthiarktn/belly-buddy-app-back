package com.fr.eql.ai115.bellybuddyback.spoonaculardto;

import lombok.Data;

import java.util.List;

@Data
public class CompleteRecipe {
  private Long id;
  private String title;
  private String image;
  private int servings;
  private int readyInMinutes;
  private List<String> dishTypes;
  private List<RecipeIngredient> extendedIngredients;
  private List<RecipeInstructions> analyzedInstructions;
}
