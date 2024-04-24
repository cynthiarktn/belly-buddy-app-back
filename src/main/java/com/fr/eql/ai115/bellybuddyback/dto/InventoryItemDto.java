package com.fr.eql.ai115.bellybuddyback.dto;

import lombok.Data;

import java.util.List;

@Data
public class InventoryItemDto {

  private Long id;
  private List<IngredientDto> ingredients;
  private String name;
  private Double amount;
  private String unit;
  private String imageUrl;
}
