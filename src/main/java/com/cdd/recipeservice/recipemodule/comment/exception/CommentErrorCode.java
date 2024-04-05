package com.cdd.recipeservice.recipemodule.comment.exception;

import com.cdd.common.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommentErrorCode implements ErrorCode {
	NO_EXISTED_COMMENT(400, "COMMENT_001", "존재하지 않는 댓글입니다."),
	NOT_OWNER(403, "COMMENT_002", "댓글의 소유자가 아닙니다."),
	TOO_LONG_COMMENT(400, "COMMENT_003", "댓글의 내용이 너무 깁니다."),
	;
	private final int statusCode;
	private final String errorCode;
	private final String message;
}
