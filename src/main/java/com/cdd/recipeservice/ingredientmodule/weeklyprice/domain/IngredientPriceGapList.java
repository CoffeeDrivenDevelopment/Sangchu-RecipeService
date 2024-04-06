package com.cdd.recipeservice.ingredientmodule.weeklyprice.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.RedisHash;

import com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response.IngredientPriceGap;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@RedisHash(timeToLive = 60 * 60 * 24)
public class IngredientPriceGapList {
	@Id
	private String key;
	@JsonProperty("ingredient_gap_list")
	private List<IngredientPriceGap> ingredientPriceGaps;

	public IngredientPriceGapList(String key) {
		this.key = key;
		ingredientPriceGaps = new ArrayList<>();
	}

	public void addGap(IngredientPriceGap ingredientPriceGap) {
		ingredientPriceGaps.add(ingredientPriceGap);
	}

	public IngredientPriceGapList sortAndLimit(int limit, int order) {
		ingredientPriceGaps.sort((b, a) -> Double.compare(a.getPercent() * order, b.getPercent() * order));
		if (ingredientPriceGaps.size() > limit) {
			ingredientPriceGaps = ingredientPriceGaps.subList(0, limit);
		}
		return this;
	}
}
