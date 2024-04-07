package com.cdd.recipeservice.infra.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MemberCoordinateResponse(
	@JsonProperty("lng")
	double lng,
	@JsonProperty("lat")
	double lat
) {
}