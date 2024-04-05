package com.cdd.recipeservice.infra.seoul.dto.response;

import java.util.List;

import com.cdd.recipeservice.infra.kakao.dto.response.KakaoPlotResponse;
import com.cdd.recipeservice.ingredientmodule.market.domain.Market;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.MarketAddr;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class MarketOpenApiResponse {
	@JsonProperty("ListTraditionalMarket")
	private ListTraditionalMarket listTraditionalMarket;

	public List<ListTraditionalMarket.Row> getMarketList() {
		return listTraditionalMarket.getRow();
	}

	@AllArgsConstructor
	@Getter
	@NoArgsConstructor
	public static class ListTraditionalMarket {
		@JsonProperty("list_total_count")
		private int totalCount;
		private List<Row> row;

		@AllArgsConstructor
		@Getter
		@NoArgsConstructor
		public static class Row implements MarketAddr {
			@JsonProperty("ITEM_NM")
			private String name;
			@JsonProperty("ITEM_ADDR")
			private String addr;

			@Override
			public Market updateAddr(KakaoPlotResponse res) {
				return null;
			}
		}
	}
}
