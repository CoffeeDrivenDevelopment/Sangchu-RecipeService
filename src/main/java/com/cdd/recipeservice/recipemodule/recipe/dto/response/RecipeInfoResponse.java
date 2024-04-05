package com.cdd.recipeservice.recipemodule.recipe.dto.response;

import java.util.List;

import com.cdd.recipeservice.recipemodule.recipe.domain.Recipe;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record RecipeInfoResponse(
	@JsonProperty("id")
	long id,
	@JsonProperty("name")
	String name,
	@JsonProperty("image")
	String image,
	@JsonProperty("recipe_type")
	String recipeType,
	@JsonProperty("food_style")
	String foodStyle,
	@JsonProperty("main_ingredient_type")
	String mainIngredientType,
	@JsonProperty("ingredient")
	String ingredient,
	@JsonProperty("food_portion")
	String foodPortion,
	@JsonProperty("cooking_time")
	String cookingTime,
	@JsonProperty("cooking_difficulty")
	String cookingDifficulty,
	@JsonProperty("serial_number")
	int serialNumber,
	@JsonProperty("ingredients")
	List<IngredientNameAndPrice> ingredients
) {

	public static RecipeInfoResponse from(final Recipe recipe) {

		return RecipeInfoResponse.builder()
			.id(recipe.getId())
			.name(recipe.getTitle())
			.image(recipe.getImage())
			.recipeType(recipe.getType().getDesc())
			.foodStyle(recipe.getRecipeCategory().getDesc())
			.mainIngredientType(recipe.getIngredientType().getDesc())
			.ingredient(recipe.getIngredients())
			.foodPortion(recipe.getPortion())
			.cookingTime(recipe.getCookingTime())
			.cookingDifficulty(recipe.getCookingDifficulty())
			.serialNumber(recipe.getSerialNumber())
			.build();
	}

	@Builder
	public record IngredientNameAndPrice(
		@JsonProperty("name")
		String name,
		@JsonProperty("price")
		String price
	) {
	}
}
