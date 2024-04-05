package com.cdd.recipeservice.ingredientmodule.targetprice.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
public class TargetPriceInfo implements Comparable<TargetPriceInfo> {
	private int price;
	private long cnt;

	@QueryProjection
	public TargetPriceInfo(int price, long cnt) {
		this.price = price;
		this.cnt = cnt;
	}

	@Override
	public int compareTo(TargetPriceInfo o) {
		if (getCnt()== o.getCnt()) {
			return Integer.compare(getPrice(), o.getPrice());
		}
		return Long.compare(getCnt(), o.getCnt());
	}
}
