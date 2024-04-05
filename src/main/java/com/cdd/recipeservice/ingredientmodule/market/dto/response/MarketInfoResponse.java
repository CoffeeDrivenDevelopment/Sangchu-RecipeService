package com.cdd.recipeservice.ingredientmodule.market.dto.response;

import com.cdd.recipeservice.infra.kakao.dto.response.KakaoPlotResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class MarketInfoResponse {
	private String name;
	private String addr;
	private double lat;
	private double lng;

	public static <T extends MarketAddr> MarketInfoResponse of(T market,
			KakaoPlotResponse kakaoPlotResponse) {
		return MarketInfoResponse.builder()
				.name(market.getName())
				.addr(kakaoPlotResponse.getDocuments()[0].getRoad_address_name())
				.lat(kakaoPlotResponse.getDocuments()[0].getY())
				.lng(kakaoPlotResponse.getDocuments()[0].getX())
				.build();
	}
}
