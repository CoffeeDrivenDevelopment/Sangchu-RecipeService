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
public class OfflineMarketLowestPriceListResponse {
	@JsonProperty("updateAt")
	String updateAt;
	@JsonProperty("markets")
	List<ClosestMarket> markets;

	public static OfflineMarketLowestPriceListResponse from(
		final List<ClosestMarket> markets) {
		return OfflineMarketLowestPriceListResponse.builder()
			.updateAt(LocalDateTimeUtils.timePattern(LocalDateTime.now(ZoneId.of("Asia/Seoul"))))
			.markets(markets)
			.build();
	}
}
