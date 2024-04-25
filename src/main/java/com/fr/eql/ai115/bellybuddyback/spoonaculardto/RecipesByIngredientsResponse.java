package com.fr.eql.ai115.bellybuddyback.spoonaculardto;

import lombok.Data;

import java.util.List;

@Data
public class RecipesByIngredientsResponse {
  private Long id;
  private String title;
  private String image;
  private int usedIngredientCount;
  private int missedIngredientCount;
  private int likes;

  private List<RecipeIngredient> usedIngredients;
  private List<RecipeIngredient> missedIngredients;
}
