package com.fr.eql.ai115.bellybuddyback.integration.spoonacular;

import lombok.Data;

import java.util.List;

@Data
public class SpoonacularStep {
  private int number;
  private String step;
  private List<SpoonacularIngredient> ingredients;

}
