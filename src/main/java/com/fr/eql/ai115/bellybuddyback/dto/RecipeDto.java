package com.fr.eql.ai115.bellybuddyback.dto;

import lombok.Data;
import java.util.Set;

@Data
public class RecipeDto {
  private Long id;
  private String title;
  private String image;
  private int servings;
  private int readyInMinutes;
  private Set<String> dishTypes;
}
