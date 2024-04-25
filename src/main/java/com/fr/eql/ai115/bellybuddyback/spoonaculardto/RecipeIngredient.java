package com.fr.eql.ai115.bellybuddyback.spoonaculardto;

import lombok.Data;

@Data
public class RecipeIngredient {

  private Long id;
  private String aisle;
  private String name;
  private String image;
  private Double amount;
  private String unit;
  private String original;
}
