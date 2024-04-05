package com.cdd.recipeservice.recipemodule.review.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.cdd.recipeservice.global.utils.LocalDateTimeUtils;
import com.cdd.recipeservice.recipemodule.review.domain.CookEat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record CookEatLoadResponse(
	@JsonProperty("review_id")
	Long id,
	@JsonProperty("title")
	String title,
	@JsonProperty("content")
	String content,
	@JsonProperty("recipe_id")
	Integer recipeId,
	@JsonProperty("image")
	String image,
	@JsonProperty("likes")
	Integer likes,
	@JsonProperty("created_at")
	String createdAt
) {
	public static CookEatLoadResponse of(CookEat cookEat, String image, Integer likes) {
		return CookEatLoadResponse.builder()
			.id(cookEat.getId())
			.title(cookEat.getTitle())
			.recipeId(cookEat.getRecipe().getId())
			.image(image)
			.likes(likes)
			.createdAt(LocalDateTimeUtils.pattern(cookEat.getCreatedAt()))
			.build();
	}
}
