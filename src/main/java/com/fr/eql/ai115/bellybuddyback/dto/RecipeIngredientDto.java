package com.fr.eql.ai115.bellybuddyback.dto;

import lombok.Data;

@Data
public class RecipeIngredientDto {
  private Long id;
  private String name;
  private String image;
  private Double amount;
  private String unit;
  private String original;
}
