package com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import com.cdd.recipeservice.global.utils.LocalDateTimeUtils;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.WeeklyPrice;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class IngredientWeeklyPriceResponse {
	@JsonProperty("updateAt")
	private String updateAt;
	@JsonProperty("data")
	private List<WeeklyPriceResponse> data;
	@JsonProperty("today")
	private Today today;

	public static IngredientWeeklyPriceResponse of(
		List<WeeklyPrice> data,
		double percent) {
		return IngredientWeeklyPriceResponse.builder()
			.updateAt(LocalDateTimeUtils.timePattern(LocalDateTime.now(ZoneId.of("Asia/Seoul"))))
			.data(WeeklyPriceResponse.toList(data))
			.today(Today.builder()
				.price(data.get(0).getPrice())
				.percent(percent)
				.build())
			.build();
	}

	@AllArgsConstructor
	@Builder
	@Getter
	@NoArgsConstructor
	static class Today {
		@JsonProperty("price")
		private int price;
		@JsonProperty("percent")
		private double percent;
	}
}
