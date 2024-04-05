package com.cdd.recipeservice.recipemodule.review.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FindCookEatsResponse(
	@JsonProperty("reviews")
	List<CookEatInfo> cookEats,
	@JsonProperty("has_more")
	boolean hasMore,
	@JsonProperty("last")
	long last,
	@JsonProperty("total_count")
	long totalCount,
	@JsonProperty("size")
	int size
) {
}
