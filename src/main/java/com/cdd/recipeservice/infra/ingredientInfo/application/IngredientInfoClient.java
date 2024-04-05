package com.cdd.recipeservice.infra.ingredientInfo.application;

import com.cdd.recipeservice.infra.ingredientInfo.dto.response.IngredientInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class IngredientInfoClient {
    @Value("${openapi.ingredient-info.url}")
    private String INGREDIENT_INFO_URL;

    public IngredientInfoResponse getIngredientImgAndKnowHow(int start, int end) {
        return WebClient.create(INGREDIENT_INFO_URL
                        + start + "/" + end)
                .get()
                .retrieve()
                .bodyToMono(IngredientInfoResponse.class)
//                .flatMap(res -> res.getGrid().getResult().getCode().equals("INFO-000") ?
//                        Mono.just(res) : Mono.empty())
                .block()
                ;
    }

    public List<IngredientInfoResponse> getIngredientsInfo() {
        final int batchSize = 100;
        List<IngredientInfoResponse> ingredientInfoResponses = new ArrayList<>();
        IngredientInfoResponse initialResponse = getIngredientImgAndKnowHow(1, batchSize);
        ingredientInfoResponses.add(initialResponse);

        int totalIngredients = initialResponse.getGrid().getTotalCnt();
        for (int start = batchSize + 1; start <= totalIngredients; start += batchSize) {
            int end = Math.min(start + batchSize - 1, totalIngredients);
            ingredientInfoResponses.add(getIngredientImgAndKnowHow(start, end));
        }

        return ingredientInfoResponses;
    }
}
