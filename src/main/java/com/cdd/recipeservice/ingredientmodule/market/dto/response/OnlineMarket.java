package com.cdd.recipeservice.ingredientmodule.market.dto.response;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class OnlineMarket implements MarketInfoMaker{
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
