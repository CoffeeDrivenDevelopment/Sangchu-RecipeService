package com.cdd.recipeservice.ingredientmodule.weeklyprice.domain;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.data.redis.core.*;

import com.cdd.recipeservice.global.utils.LocalDateTimeUtils;
import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@RedisHash(timeToLive = 60 * 60 * 24)
public class IngredientWeeklyPrice {
	@Id
	private String key;
	@JsonProperty("updatedAt")
	private LocalDateTime updateAt;
	@JsonProperty("weekly_prices")
	private Map<Integer, List<WeeklyPrice>> weeklyPrices;

	public static IngredientWeeklyPrice of(String type,
		int id,
		Map<Integer, List<WeeklyPrice>> weeklyPrices
	) {
		return IngredientWeeklyPrice.builder()
			.key(type + "-" + id)
			.updateAt(LocalDateTimeUtils.today())
			.weeklyPrices(weeklyPrices)
			.build();
	}

	public List<WeeklyPrice> getData(int week) {
		return weeklyPrices.get(week);
	}
}
