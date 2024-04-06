package com.cdd.recipeservice.recipemodule.comment.dto.response;

import com.cdd.recipeservice.global.utils.LocalDateTimeUtils;
import com.cdd.recipeservice.infra.member.dto.MemberInfoResponse;
import com.cdd.recipeservice.recipemodule.comment.domain.Comment;
import com.cdd.recipeservice.recipemodule.comment.dto.MemberInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeRootCommentResponse implements RecipeCommentResponse {
	@JsonProperty("id")
	private long commentId;
	@JsonProperty("content")
	private String content;
	@JsonProperty("last_updated_time")
	private String lastUpdatedTime;
	@JsonProperty("member")
	private MemberInfo member;
	@JsonProperty("reply_count")
	private int replyCount;

	public static RecipeRootCommentResponse of(Comment comment, MemberInfoResponse member) {
		return RecipeRootCommentResponse.builder()
			.commentId(comment.getId())
			.content(comment.getContent())
			.lastUpdatedTime(LocalDateTimeUtils.pattern(comment.getUpdatedAt()))
			.replyCount(getReplyCount(comment))
			.member(member.toMemberInfo())
			.build();
	}

	public static RecipeRootCommentResponse deleteComment(Comment comment) {
		return RecipeRootCommentResponse.builder()
			.commentId(comment.getId())
			.content(comment.getContent())
			.lastUpdatedTime(LocalDateTimeUtils.pattern(comment.getUpdatedAt()))
			.replyCount(getReplyCount(comment))
			.member(MemberInfo.deleteComment())
			.build();
	}

	private static int getReplyCount(Comment comment) {
		return comment.getReplyCount() == null ? 0 : comment.getReplyCount();
	}
}
