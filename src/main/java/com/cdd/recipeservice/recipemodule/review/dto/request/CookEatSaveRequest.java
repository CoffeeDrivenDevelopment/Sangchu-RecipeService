package com.cdd.recipeservice.recipemodule.review.dto.request;

import java.util.ArrayList;

import com.cdd.recipeservice.recipemodule.recipe.domain.Recipe;
import com.cdd.recipeservice.recipemodule.review.domain.CookEat;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CookEatSaveRequest(
	@JsonProperty("title")
	String title,
	@JsonProperty("content")
	String content
) {
	public CookEat toCookEatWithMemberIdAndRecipe(
		final int memberId,
		final Recipe recipe
	) {
		return CookEat.builder()
			.memberId(memberId)
			.title(title)
			.content(content)
			.recipe(recipe)
			.images(new ArrayList<>())
			.likes(new ArrayList<>())
			.build();
	}
}
