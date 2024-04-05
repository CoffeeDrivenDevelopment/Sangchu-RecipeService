package com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class IngredientCauseResponse {
	@JsonProperty("img")
	private String img;
	@JsonProperty("type")
	private String type;
	private int days;
	@JsonProperty("content")
	private List<String> content;

	public static IngredientCauseResponse of(String img, String type, int days) {
		return IngredientCauseResponse.builder()
			.img(img)
			.type(type)
			.days(days)
			.content(List.of(days + "일 연속", "중이에요."))
			.build();
	}
}
