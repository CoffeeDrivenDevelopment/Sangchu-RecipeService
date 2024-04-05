package com.cdd.recipeservice.ingredientmodule.ingredient.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;

public class IngredientCategory {
	@JsonProperty("id")
	private Integer id;
	@JsonProperty("name")
	private String name;

	@QueryProjection
	public IngredientCategory(Integer id, String name){
		this.id = id;
		this.name = name;
	}
}
