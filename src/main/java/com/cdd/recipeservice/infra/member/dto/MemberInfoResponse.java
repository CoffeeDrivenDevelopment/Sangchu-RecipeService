package com.cdd.recipeservice.infra.member.dto;

import com.cdd.recipeservice.recipemodule.comment.dto.MemberInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

public record MemberInfoResponse(
	@JsonProperty("id")
	int id,
	@JsonProperty("profile_image")
	String image,
	@JsonProperty("nickname")
	String nickname
) {
	public MemberInfo toMemberInfo() {
		return MemberInfo.builder()
			.id(id)
			.image(image)
			.nickname(nickname)
			.build();
	}
}
