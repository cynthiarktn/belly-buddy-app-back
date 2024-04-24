package com.fr.eql.ai115.bellybuddyback.integration.spoonacular;

import lombok.Data;

import java.util.List;

@Data
public class SpoonacularInstruction {
  private String name;
  private List<SpoonacularStep> steps;

}
