package com.cdd.recipeservice.infra.kakao.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class KakaoPlotResponse {
	private Documents[] documents;

	@AllArgsConstructor
	@Getter
	@NoArgsConstructor
	public static class Documents {
		private String road_address_name;
		private double y;
		private double x;
	}

	public boolean isEmpty() {
		return documents.length == 0;
	}
}
