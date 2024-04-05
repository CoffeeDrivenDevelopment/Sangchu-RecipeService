package com.cdd.recipeservice.recipemodule.review.dto.response;

import com.cdd.recipeservice.recipemodule.comment.dto.MemberInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record CookEatInfo(
	@JsonProperty("id")
	long id,
	@JsonProperty("title")
	String title,
	@JsonProperty("content")
	String content,
	@JsonProperty("image")
	String image,
	@JsonProperty("like_count")
	int likeCount,
	@JsonProperty("is_liked")
	boolean isLiked,
	@JsonProperty("member")
	MemberInfo member
) {
}
