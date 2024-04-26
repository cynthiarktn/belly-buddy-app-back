package com.fr.eql.ai115.bellybuddyback.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeInstructionsDto {
  private Long id;
  private List<InstructionsStepsDto> steps;
}
