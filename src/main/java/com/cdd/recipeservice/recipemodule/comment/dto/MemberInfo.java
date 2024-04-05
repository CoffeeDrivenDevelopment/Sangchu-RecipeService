package com.cdd.recipeservice.recipemodule.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record MemberInfo(
	@JsonProperty("id")
	int id,
	@JsonProperty("profile_image")
	String image,
	@JsonProperty("nickname")
	String nickname
) {
	public static MemberInfo deleteComment() {
		return MemberInfo.builder()
			.id(-1)
			.build();
	}
}
