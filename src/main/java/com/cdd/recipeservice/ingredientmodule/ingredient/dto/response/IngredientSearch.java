package com.cdd.recipeservice.ingredientmodule.ingredient.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;

public class IngredientSearch {
	@JsonProperty("ingredient_id")
	private Integer ingredientId;
	@JsonProperty("ingredient_name")
	private String ingredientName;
	@JsonProperty("ingredient_image")
	private String ingredientImage;
	
	@QueryProjection
	public IngredientSearch(Integer ingredientId, String ingredientName, String ingredientImage) {
		this.ingredientId = ingredientId;
		this.ingredientName = ingredientName;
		this.ingredientImage = ingredientImage;
	}
}
