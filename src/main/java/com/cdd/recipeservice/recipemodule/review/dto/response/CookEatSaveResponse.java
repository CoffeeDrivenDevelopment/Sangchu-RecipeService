package com.cdd.recipeservice.recipemodule.review.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CookEatSaveResponse(
	@JsonProperty("review_id")
	long reviewId
) {
}
