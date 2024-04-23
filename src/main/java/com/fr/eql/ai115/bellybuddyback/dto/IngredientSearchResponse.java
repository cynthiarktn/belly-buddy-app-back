package com.fr.eql.ai115.bellybuddyback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
public class IngredientSearchResponse {
    private List<IngredientResult> results;

    @Data
    @AllArgsConstructor
    public static class IngredientResult {
        private String name;
        private String image;
    }
}
