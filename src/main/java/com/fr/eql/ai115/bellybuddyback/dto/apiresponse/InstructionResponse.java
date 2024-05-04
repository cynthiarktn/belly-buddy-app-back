package com.fr.eql.ai115.bellybuddyback.dto.apiresponse;

import lombok.Data;

import java.util.Set;

@Data
public class InstructionResponse {
  private String name;
  private Set<InstructionsStepsResponse> steps;
}
