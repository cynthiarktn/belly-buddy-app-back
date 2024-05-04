package com.fr.eql.ai115.bellybuddyback.dto.apiresponse;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientResponse {
  private Long id;
  private String name;
  private String image;
}
