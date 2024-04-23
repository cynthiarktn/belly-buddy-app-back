package com.fr.eql.ai115.bellybuddyback.service;

import com.fr.eql.ai115.bellybuddyback.dto.IngredientSearchResponse;
import com.fr.eql.ai115.bellybuddyback.dto.RecipeSearchResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SpoonacularService {
    private static final String API_KEY = "fbc5e5cf916f4d74b677ad84a474d9f5";
    private static final String BASE_URL = "https://api.spoonacular.com";
    private WebClient webClient;

    public SpoonacularService() {
        this.webClient = WebClient.create();
    }

    public Mono<RecipeSearchResponse> searchRecipes(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL + "/recipes/complexSearch")
                        .queryParam("query", query)
                        .queryParam("apiKey", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(RecipeSearchResponse.class);
    }

    public Mono<IngredientSearchResponse> searchIngredient(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL + "/food/ingredients/search")
                        .queryParam("query", query)
                        .queryParam("apiKey", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(IngredientSearchResponse.class);
    }
}
