package com.cdd.recipeservice.ingredientmodule.weeklyprice.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cdd.recipeservice.ingredientmodule.ingredient.domain.Ingredient;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.IngredientRepository;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.IngredientSalesDailyPriceStatRepository;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response.IngredientPriceGap;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response.IngredientPriceGapResponse;
import com.cdd.recipeservice.recipemodule.recipe.exception.RecipeErrorCode;
import com.cdd.recipeservice.recipemodule.recipe.exception.RecipeException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class IngredientGapService {
	private final IngredientRepository ingredientRepository;
	private final IngredientSalesDailyPriceStatRepository ingredientSalesDailyPriceStatRepository;

	public IngredientPriceGapResponse getAscGapList() {
		List<IngredientPriceGap> responseList = ingredientSalesDailyPriceStatRepository.findIngredientDailyPrice(true);
		for (IngredientPriceGap response : responseList) {
			Ingredient ingredient = ingredientRepository.findById(response.getIngredientId())
				.orElseThrow(() -> new RecipeException(RecipeErrorCode.NO_EXISTED_INGREDIENT));
			response.updateInfo(ingredient.getName(), ingredient.getImg());
		}
		log.info("#####SVC ASC " + responseList.size());
		return IngredientPriceGapResponse.from(responseList);
	}

	public IngredientPriceGapResponse getDescGapList() {
		List<IngredientPriceGap> responseList = ingredientSalesDailyPriceStatRepository.findIngredientDailyPrice(false);
		for (IngredientPriceGap response : responseList) {
			Ingredient ingredient = ingredientRepository.findById(response.getIngredientId())
				.orElseThrow(() -> new RecipeException(RecipeErrorCode.NO_EXISTED_INGREDIENT));
			response.updateInfo(ingredient.getName(), ingredient.getImg());
		}
		log.info("#####SVC ASC " + responseList.size());
		return IngredientPriceGapResponse.from(responseList);
	}
}
