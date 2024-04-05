package com.cdd.recipeservice.ingredientmodule.market.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class OnlineMarket implements MarketInfoMaker{
	private int id;
	private String name;
	private int price;
	@QueryProjection
	public OnlineMarket(int id, String name, int price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}
}
