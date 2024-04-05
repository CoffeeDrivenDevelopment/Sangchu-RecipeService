package com.cdd.recipeservice.recipemodule.comment.exception;

import com.cdd.common.exception.ErrorCode;
import com.cdd.common.exception.SangChuException;

public class CommentException extends SangChuException {
	public CommentException(ErrorCode errorCode) {
		super(errorCode);
	}
}
