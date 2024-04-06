package com.cdd.recipeservice.ingredientmodule.market.dto.response;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import com.cdd.recipeservice.global.utils.LocalDateTimeUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class MarketLowestPriceListResponse<T> {
	@JsonProperty("updateAt")
	String updateAt;
	@JsonProperty("markets")
	List<T> markets;

	public static <T> MarketLowestPriceListResponse<T> from(
		final List<T> markets) {
		return MarketLowestPriceListResponse.<T>builder()
			.updateAt(LocalDateTimeUtils.nowTimePattern("Asia/Seoul"))
			.markets(markets)
			.build();
	}
}
