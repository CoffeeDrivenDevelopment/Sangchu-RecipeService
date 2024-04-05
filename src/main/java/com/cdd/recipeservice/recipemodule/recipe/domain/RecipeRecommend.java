package com.cdd.recipeservice.recipemodule.recipe.domain;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.redis.core.RedisHash;

import com.cdd.recipeservice.global.utils.LocalDateTimeUtils;
import com.cdd.recipeservice.recipemodule.recipe.dto.response.CookingMovie;
import com.cdd.recipeservice.recipemodule.recipe.dto.response.RecipeInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "recommend-recipe",
	timeToLive = 60 * 60 * 24)
public class RecipeRecommend implements Serializable {
	@Id
	private String key;
	@JsonProperty("recipe")
	private RecipeInfo recipeInfo;
	@JsonProperty("cooking-movies")
	private List<CookingMovie> cookingMovies;

	public static RecipeRecommend of(RecipeInfo recipeInfo, List<CookingMovie> cookingMovies) {
		return RecipeRecommend.builder()
			.recipeInfo(recipeInfo)
			.cookingMovies(cookingMovies)
			.build();
	}

	public RecipeRecommend updateKey(String key) {
		this.key = key;
		return this;
	}
}
