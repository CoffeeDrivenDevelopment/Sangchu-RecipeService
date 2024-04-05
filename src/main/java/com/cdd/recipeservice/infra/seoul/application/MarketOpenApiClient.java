package com.cdd.recipeservice.infra.seoul.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cdd.recipeservice.infra.seoul.dto.response.MarketOpenApiResponse;
import com.cdd.recipeservice.infra.seoul.dto.response.MarketOpenApiResponse.ListTraditionalMarket.Row;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MarketOpenApiClient {
	@Value("${openapi.market.api-key}")
	private String MARKET_API_KEY;
	@Value("${openapi.market.url}")
	private String MARKET_URL;

	public List<Row> getMarketInfo() {
		MarketOpenApiResponse marketOpenApiResponse = getMarketOpenApi();
		return marketOpenApiResponse.getMarketList();
	}

	private MarketOpenApiResponse getMarketOpenApi() {
		return WebClient
				.create(MARKET_URL)
				.get()
				.retrieve()
				.bodyToMono(MarketOpenApiResponse.class)
				.block();
	}
}
