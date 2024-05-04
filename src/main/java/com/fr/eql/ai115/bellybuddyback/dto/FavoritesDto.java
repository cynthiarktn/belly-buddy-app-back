package com.fr.eql.ai115.bellybuddyback.dto;

import com.fr.eql.ai115.bellybuddyback.dto.apiresponse.CompleteRecipeResponse;
import lombok.Data;

import java.util.Set;

@Data
public class FavoritesDto {
  private Long id;
  private Long userId;
  private Set<CompleteRecipeResponse> recipes;
}
