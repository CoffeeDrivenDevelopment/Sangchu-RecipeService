package com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response;

import java.time.LocalDate;
import java.util.List;

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
public class WeeklyPriceResponse {
	@JsonProperty("date")
	private LocalDate date;
	@JsonProperty("price")
	private int price;

	public static WeeklyPriceResponse from(WeeklyPrice data) {
		return WeeklyPriceResponse.builder()
			.date(data.getDate())
			.price(data.getPrice())
			.build();
	}

	public static List<WeeklyPriceResponse> toList(List<WeeklyPrice> data) {
		return data.stream()
			.map(WeeklyPriceResponse::from)
			.toList();
	}
}
