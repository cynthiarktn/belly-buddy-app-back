package com.fr.eql.ai115.bellybuddyback.dto;

import com.fr.eql.ai115.bellybuddyback.dto.apiresponse.IngredientResponse;
import lombok.Data;
import java.util.Set;

@Data
public class InventoryDto {
  private Long id;
  private Long userId;
  private Set<IngredientResponse> ingredients;
}
