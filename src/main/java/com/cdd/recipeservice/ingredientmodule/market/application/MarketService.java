package com.cdd.recipeservice.ingredientmodule.market.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdd.recipeservice.infra.kakao.application.KakaoMapClient;
import com.cdd.recipeservice.infra.seoul.application.MarketOpenApiClient;
import com.cdd.recipeservice.ingredientmodule.market.domain.Market;
import com.cdd.recipeservice.ingredientmodule.market.domain.MarketRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MarketService {
	private final MarketRepository marketRepository;
	private final MarketOpenApiClient marketOpenApiClient;
	private final KakaoMapClient kakaoMapClient;

	@Transactional
	public void getMarketInfo() {
		// List<Row> marketInfos = marketOpenApiClient.getMarketInfo();
		List<Market> marketInfos = marketRepository.findAllOfflineMarket();
		kakaoMapClient.matchMarketPlotList(marketInfos, 1);
	}
}
