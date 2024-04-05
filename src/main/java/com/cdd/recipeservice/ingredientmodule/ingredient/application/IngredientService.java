package com.cdd.recipeservice.ingredientmodule.ingredient.application;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cdd.recipeservice.ingredientmodule.ingredient.domain.IngredientRepository;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.IngredientType;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.IngredientCategoryResponse;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.IngredientPopularResponse;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.IngredientSearchResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IngredientService {
	private final IngredientRepository ingredientRepository;
	public IngredientCategoryResponse findAllCategory(){
		return IngredientCategoryResponse.builder()
			.categoryList(ingredientRepository.findAllCategory())
			.build();
	}

	public IngredientSearchResponse findIngredient(IngredientType category, String ingredientName) {
		List<IngredientType> categories = new ArrayList<>();
		if(category != null){
			categories.add(category);

			if (category.equals(IngredientType.MEAT)) {
				categories.add(IngredientType.PORK);
				categories.add(IngredientType.CHICKEN);
				categories.add(IngredientType.BEEF);
			}

			if (category.equals(IngredientType.GRAINS)) {
				categories.add(IngredientType.RICE);
				categories.add(IngredientType.BEANS_NUTS);
			}

			if (category.equals(IngredientType.PROCESSED_FOOD)) {
				categories.add(IngredientType.DRIED_SEAFOOD);
			}

			if (category.equals(IngredientType.VEGETABLE)) {
				categories.add(IngredientType.MUSHROOM);
			}
		}

		return IngredientSearchResponse.builder()
			.searchList(ingredientRepository.findIngredient(category == null ? null : categories, ingredientName))
			.build();
	}

	public IngredientPopularResponse findPopularIngredient() {
		return IngredientPopularResponse.builder()
			.popularList(ingredientRepository.findPopularIngredient())
			.build();
	}
}
