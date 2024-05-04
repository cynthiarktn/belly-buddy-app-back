package com.fr.eql.ai115.bellybuddyback.dto.apiresponse;

import lombok.Data;

import java.util.Set;

@Data
public class IngredientResultsResponse {

  private Set<IngredientResponse> results;

}
