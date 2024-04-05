package com.cdd.recipeservice.recipemodule.review.dto.response;

import java.util.List;

import com.cdd.recipeservice.recipemodule.comment.dto.MemberInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record FindCookEatResponse(
	@JsonProperty("id")
	long id,
	@JsonProperty("recipe_name")
	String recipeName,
	@JsonProperty("title")
	String title,
	@JsonProperty("content")
	String content,
	@JsonProperty("images")
	List<String> image,
	@JsonProperty("like_count")
	int likeCount,
	@JsonProperty("is_liked")
	boolean isLiked,
	@JsonProperty("member")
	MemberInfo member
) {
}
