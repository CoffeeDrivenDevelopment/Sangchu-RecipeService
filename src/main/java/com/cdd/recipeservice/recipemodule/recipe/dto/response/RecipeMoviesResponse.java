package com.cdd.recipeservice.recipemodule.recipe.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RecipeMoviesResponse(
	@JsonProperty("cooking_movies")
	List<CookingMovie> cookingMovies,
	@JsonProperty("total_count")
	int totalCount
) {

	public static RecipeMoviesResponse from(final List<CookingMovie> cookingMovies) {
		return new RecipeMoviesResponse(
			cookingMovies,
			cookingMovies.size()
		);
	}
}
