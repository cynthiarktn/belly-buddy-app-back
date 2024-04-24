package com.fr.eql.ai115.bellybuddyback.integration.spoonacular;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SpoonacularIngredient {
  private Long id;
  private String name;

  @JsonProperty("amount")
  private double amount;

  @JsonProperty("unit")
  private String unit;

  @JsonProperty("image")
  private String image;

}

