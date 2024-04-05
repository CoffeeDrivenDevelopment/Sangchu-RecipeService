package com.cdd.recipeservice.ingredientmodule.ingredient.domain;

import java.util.List;

import org.springframework.data.redis.core.RedisHash;

import com.cdd.recipeservice.global.utils.LocalDateTimeUtils;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.WeeklyPrice;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response.WeeklyPriceResponse;
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
@RedisHash(timeToLive = 60 * 60 * 24)
public class RecommendIngredient {
	@Id
	private String key;
	@JsonProperty("ingredients")
	private Ingredients ingredients;

	public static RecommendIngredient from(Ingredient ingredient) {
		return RecommendIngredient.builder()
			.ingredients(Ingredients.builder()
				.id(ingredient.getId())
				.name(ingredient.getName())
				.img(ingredient.getImg())
				.build())
			.build();
	}

	public RecommendIngredient update(
		List<WeeklyPrice> online,
		List<WeeklyPrice> offline,
		String key) {
		this.ingredients.online = WeeklyPriceResponse.toList(online);
		this.ingredients.offline = WeeklyPriceResponse.toList(offline);
		this.key = key;
		return this;
	}

	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	@Builder
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Ingredients {
		@JsonProperty("id")
		private int id;
		@JsonProperty("name")
		private String name;
		@JsonProperty("img")
		private String img;
		@JsonProperty("online")
		private List<WeeklyPriceResponse> online;
		@JsonProperty("offline")
		private List<WeeklyPriceResponse> offline;
	}
}
