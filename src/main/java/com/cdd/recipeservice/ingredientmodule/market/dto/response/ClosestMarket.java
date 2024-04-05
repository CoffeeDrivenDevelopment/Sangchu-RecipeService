package com.cdd.recipeservice.ingredientmodule.market.dto.response;

import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.PriceCalculator;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClosestMarket implements Comparable<ClosestMarket>, PriceCalculator, MarketInfoMaker {
	private int id;
	private String name;
	private int price;
	private double dist;
	private double lat;
	private double lng;

	@QueryProjection
	public ClosestMarket(
		int id,
		String name,
		double lat,
		double lng,
		double dist,
		int price
	) {
		this.id = id;
		this.name = name;
		this.lat = lat;
		this.lng = lng;
		this.dist = Math.round(dist / 10.0) / 100.0;
		this.price = price;
	}

	public ClosestMarket(int price) {
		this.price = price;
	}

	@Override
	public int compareTo(ClosestMarket o) {
		if (getDist() == o.getDist()) {
			return Integer.compare(getPrice(), o.getPrice());
		}
		return Double.compare(getDist(), o.getDist());
	}
}
