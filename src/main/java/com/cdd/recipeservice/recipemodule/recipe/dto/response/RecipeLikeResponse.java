package com.cdd.recipeservice.recipemodule.recipe.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeLikeResponse {
	@JsonProperty("is_liked")
	private boolean isLiked;

	public static RecipeLikeResponse like() {
		return new RecipeLikeResponse(true);
	}

	public static RecipeLikeResponse notLike() {
		return new RecipeLikeResponse(false);
	}
}
