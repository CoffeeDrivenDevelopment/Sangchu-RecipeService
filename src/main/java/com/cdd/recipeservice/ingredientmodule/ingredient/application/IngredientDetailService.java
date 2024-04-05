package com.cdd.recipeservice.ingredientmodule.ingredient.application;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.cdd.recipeservice.infra.ingredientInfo.application.IngredientInfoClient;
import com.cdd.recipeservice.infra.ingredientInfo.dto.response.IngredientInfoResponse;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.Ingredient;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.IngredientRepository;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.IngredientDetails;
import com.cdd.recipeservice.ingredientmodule.ingredient.utils.IngredientUtils;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class IngredientDetailService {
	private final IngredientRepository ingredientRepository;
	private final IngredientInfoClient ingredientInfoClient;

	public IngredientDetails getIngredientDetails(Integer id) {
		Ingredient ingredient = IngredientUtils.findById(ingredientRepository, id);
		return IngredientDetails.from(ingredient);
	}

	@Transactional
	public void updateIngredientDetails() {
		Map<String, Ingredient> ingredients = findAllAsMap();
		List<IngredientInfoResponse> ingredientInfo = ingredientInfoClient.getIngredientsInfo();
		if (ObjectUtils.isEmpty(ingredientInfo)) {
			return;
		}
		updateIngredientImgAndKnowHow(ingredients, ingredientInfo);
	}

	public void updateIngredientImgAndKnowHow(Map<String, Ingredient> ingredients,
		List<IngredientInfoResponse> ingredientInfoResponses) {
		Flux.fromIterable(ingredientInfoResponses)
			.flatMap(response -> Flux.fromIterable(response.getGrid().getRow()))
			.filter(row -> ingredients.containsKey(row.getProductName()))
			.doOnNext(row -> {
				Ingredient ingredient = ingredients.get(row.getProductName());
				ingredient.update(row.getKnowHow());
			})
			.then()
			.subscribe();
	}

	private Map<String, Ingredient> findAllAsMap() {
		return ingredientRepository.findAll().stream()
			.filter(ingredient -> ingredient.getKnowHow() == null)
			.collect(Collectors.toMap(Ingredient::getName, Function.identity()));
	}
}
