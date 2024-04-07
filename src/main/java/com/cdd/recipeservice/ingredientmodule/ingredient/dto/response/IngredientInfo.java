package com.cdd.recipeservice.ingredientmodule.ingredient.dto.response;

import java.util.List;

import com.cdd.recipeservice.global.utils.LocalDateTimeUtils;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.RecommendIngredient;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IngredientInfo {
	@JsonProperty("updateAt")
	private String updateAt;
	@JsonProperty("ingredients")
	private List<RecommendIngredient> ingredients;

	public static IngredientInfo from(List<RecommendIngredient> recommendIngredient) {
		return IngredientInfo.builder()
			.updateAt(LocalDateTimeUtils.timePattern(recommendIngredient.get(0).getUpdatedAt()))
			.ingredients(recommendIngredient)
			.build();
	}
}
