package com.cdd.recipeservice.recipemodule.recipe.dto.response;

import java.time.LocalDateTime;

import com.cdd.recipeservice.recipemodule.recipe.domain.RecipeCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;

public class RecipeLikeDetailResponse {
	@JsonProperty("recipe_id")
	private Integer recipeId;
	@JsonProperty("recipe_title")
	private String title;
	@JsonProperty("recipe_image")
	private String image;
	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@JsonProperty("food_category")
	private String recipeCategory;
	@JsonProperty("cooking_difficulty")
	private String cookingDifficulty;
	@JsonProperty("cooking_time")
	private String cookingTime;

	@QueryProjection
	public RecipeLikeDetailResponse(Integer recipeId, String title, String image, LocalDateTime createdAt,
		RecipeCategory recipeCategory, String cookingDifficulty, String cookingTime) {
		this.recipeId = recipeId;
		this.title = title;
		this.image = image;
		this.createdAt = createdAt;
		this.recipeCategory = recipeCategory.getDesc();
		this.cookingDifficulty = cookingDifficulty;
		this.cookingTime = cookingTime;
	}

	@Builder
	public RecipeLikeDetailResponse(Integer recipeId, String title, String image, LocalDateTime createdAt,
		String recipeCategory, String cookingDifficulty, String cookingTime) {
		this.recipeId = recipeId;
		this.title = title;
		this.image = image;
		this.createdAt = createdAt;
		this.recipeCategory = recipeCategory;
		this.cookingDifficulty = cookingDifficulty;
		this.cookingTime = cookingTime;
	}
}
