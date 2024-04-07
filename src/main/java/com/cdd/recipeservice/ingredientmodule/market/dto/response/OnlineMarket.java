package com.cdd.recipeservice.ingredientmodule.market.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OnlineMarket implements MarketInfoMaker {
	@JsonProperty("market_id")
	private int id;
	@JsonProperty("market_name")
	private String name;
	@JsonProperty("price")
	private int price;
	@JsonProperty("market_link")
	private String link;

	@QueryProjection
	public OnlineMarket(int id, String name, int price, String link) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.link = link;
	}
}
