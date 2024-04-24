package com.fr.eql.ai115.bellybuddyback.integration.spoonacular;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SpoonacularRecipeResponse {
  private Long id;
  private String title;
  private String image;

  @JsonProperty("readyInMinutes")
  private int readyInMinutes;

  private int servings;
  private String sourceUrl;
  private String summary;

  @JsonProperty("analyzedInstructions")
  private List<SpoonacularInstruction> analyzedInstructions;

  @JsonProperty("extendedIngredients")
  private List<SpoonacularIngredient> ingredients;

}
