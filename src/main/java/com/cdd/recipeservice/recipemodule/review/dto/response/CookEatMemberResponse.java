package com.cdd.recipeservice.recipemodule.review.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record CookEatMemberResponse(
	@JsonProperty("reviews")
	List<CookEatLoadResponse> cookEatList
) {
}
