package com.cdd.recipeservice.ingredientmodule.market.domain;

import com.cdd.recipeservice.global.domain.*;
import com.cdd.recipeservice.infra.kakao.dto.response.KakaoPlotResponse;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.*;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "market_tbl")
public class Market extends BaseTime implements MarketAddr{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "market_id")
	private Integer id;

	@Column(name = "market_name")
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "market_type")
	private MarketType type;

	@Embedded
	@Column
	private Address address;

	public static Market from(MarketInfoResponse marketInfoResponse) {
		return Market.builder()
			.name(marketInfoResponse.getName())
			.type(MarketType.OFFLINE)
			.address(Address.builder()
				.addr(marketInfoResponse.getAddr())
				.lat(marketInfoResponse.getLat())
				.lng(marketInfoResponse.getLng())
				.build())
			.build();
	}

	public Market updateAddr(KakaoPlotResponse kakaoPlotResponse) {
		this.address = Address.builder()
			.addr(kakaoPlotResponse.getDocuments()[0].getRoad_address_name())
			.lat(kakaoPlotResponse.getDocuments()[0].getY())
			.lng(kakaoPlotResponse.getDocuments()[0].getX())
			.build();
		return this;
	}
	@Override
	public String getAddr() {
		if(getAddress()==null){
			return null;
		}
		return getAddress().getAddr();
	}
}
