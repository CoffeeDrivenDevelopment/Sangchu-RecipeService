package com.cdd.recipeservice.ingredientmodule.weeklyprice.domain;

import java.time.*;

import com.fasterxml.jackson.annotation.*;
import com.querydsl.core.annotations.QueryProjection;

import lombok.*;
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class WeeklyPrice implements PriceCalculator{
	@JsonProperty("date")
	private LocalDate date;
	@JsonProperty("price")
	private int price;
	@QueryProjection
	public WeeklyPrice(LocalDateTime date, int price) {
		this.date = date.toLocalDate();
		this.price = price;
	}

	public WeeklyPrice(LocalDate date) {
		this.date = date;
	}

	public void addPrice(int price) {
		this.price += price;
	}

	public void avgPrice(int unit) {
		this.price /= unit;
	}
}
