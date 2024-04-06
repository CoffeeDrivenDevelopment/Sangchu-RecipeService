package com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.cond;

import java.time.*;

import com.cdd.recipeservice.global.utils.LocalDateTimeUtils;
import com.cdd.recipeservice.ingredientmodule.market.domain.*;
import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Getter
public class PriceSearchCond {
	@JsonProperty("id")
	private int id;
	@JsonProperty("type")
	private MarketType type;
	@JsonProperty("week")
	private LocalDateTime week;

	public PriceSearchCond(int id, String type, long week) {
		this.week = LocalDateTimeUtils.today("Asia/Seoul").minusWeeks(week);
		this.id = id;
		this.type = MarketType.valueOf(type.toUpperCase());
	}
}
