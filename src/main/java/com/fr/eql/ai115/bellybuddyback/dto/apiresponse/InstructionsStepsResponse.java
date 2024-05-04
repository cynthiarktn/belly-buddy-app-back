package com.fr.eql.ai115.bellybuddyback.dto.apiresponse;

import lombok.Data;

import java.util.Set;

@Data
public class InstructionsStepsResponse {
    private int number;
    private String step;
    private Set<Ingredient> ingredients;

    @Data
    public static class Ingredient {
        private int id;
        private String name;
        private String image;
    }

}
