package com.cdd.recipeservice.infra.kakao.application;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cdd.recipeservice.infra.kakao.dto.response.KakaoPlotResponse;
import com.cdd.recipeservice.ingredientmodule.market.domain.Market;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.MarketAddr;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class KakaoMapClient {
	@Value("${openapi.kakao.api-key}")
	private String KAKAO_API_KEY;
	@Value("${openapi.kakao.url}")
	private String KAKAOMAP_URL;

	public Mono<KakaoPlotResponse> getKakaoPlot(String search, String keyword) {
		StringBuilder url = new StringBuilder(KAKAOMAP_URL)
				.append(search)
				.append(".json?query=")
				.append(keyword);

		return WebClient.create(url.toString())
				.get()
				.header("Authorization", "KakaoAK " + KAKAO_API_KEY)
				.retrieve()
				.bodyToMono(KakaoPlotResponse.class)
				.flatMap(res -> res.isEmpty() ? Mono.empty() : Mono.just(res));
	}

	private <T extends MarketAddr> Mono<Market> matchMarketPlot(T market) {
		return getKakaoPlot("keyword", market.getName())
			.map(market::updateAddr);
		// return getKakaoPlot("address", market.getAddr())
		// 		.switchIfEmpty(getKakaoPlot("keyword", market.getName()))
		// 		.map(res -> Market.from(MarketInfoResponse.of(market, res)));
	}

	public <T extends MarketAddr> List<Market> matchMarketPlotList(List<T> markets, int delay) {
		return Flux
				.fromIterable(markets)
				.delayElements(Duration.ofMillis(delay))
				.flatMap(this::matchMarketPlot)
				.collectList()
				.block();
	}
}
