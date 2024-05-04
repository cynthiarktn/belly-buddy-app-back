package com.fr.eql.ai115.bellybuddyback.dto.apiresponse;

import lombok.Data;

import java.util.Set;

@Data
public class RecipesByIngredientsResponse {
  private Long id;
  private String title;
  private String image;
  private int usedIngredientCount;
  private int missedIngredientCount;
  private int likes;
  private Set<RecipeIngredientResponse> usedIngredients;
  private Set<RecipeIngredientResponse> missedIngredients;
}
