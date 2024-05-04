package com.fr.eql.ai115.bellybuddyback.dto.apiresponse;

import lombok.Data;

@Data
public class RecipeIngredientResponse {
  private Long id;
  private String image;
  private String name;
  private String original;
  private Double amount;
  private String unit;
}
