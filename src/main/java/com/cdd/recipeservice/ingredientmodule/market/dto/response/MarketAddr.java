package com.cdd.recipeservice.ingredientmodule.market.dto.response;

import com.cdd.recipeservice.infra.kakao.dto.response.KakaoPlotResponse;
import com.cdd.recipeservice.ingredientmodule.market.domain.Market;

public interface MarketAddr {
	String getAddr();

	String getName();
	Market updateAddr(KakaoPlotResponse res);
}
