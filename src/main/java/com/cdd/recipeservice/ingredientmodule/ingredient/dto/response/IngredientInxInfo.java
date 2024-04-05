package com.cdd.recipeservice.ingredientmodule.ingredient.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
public class IngredientInxInfo {
	private int firstId;
	private long totalCnt;

	@QueryProjection
	public IngredientInxInfo(int firstId, long totalCnt) {
		this.firstId = firstId;
		this.totalCnt = totalCnt;
	}
}
