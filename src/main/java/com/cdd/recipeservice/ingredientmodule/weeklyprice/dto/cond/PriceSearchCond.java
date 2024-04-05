package com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.cond;

import java.time.*;

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

	public PriceSearchCond(int id, String type, int week) {
		ZoneId zoneId = ZoneId.of("Asia/Seoul");
		this.week = LocalDate.now(zoneId).minusWeeks(week).atStartOfDay();
		this.id = id;
		this.type = MarketType.valueOf(type.toUpperCase());
	}
}
