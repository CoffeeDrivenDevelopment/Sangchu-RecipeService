package com.cdd.recipeservice.recipemodule.comment.utils;

import com.cdd.common.exception.CallConstructorException;
import com.cdd.recipeservice.recipemodule.comment.domain.Comment;
import com.cdd.recipeservice.recipemodule.comment.domain.CommentRepository;
import com.cdd.recipeservice.recipemodule.comment.exception.CommentErrorCode;
import com.cdd.recipeservice.recipemodule.comment.exception.CommentException;

public class RecipeCommentServiceUtils {

	private RecipeCommentServiceUtils() {
		throw new CallConstructorException();
	}

	public static Comment findById(CommentRepository repository, Long id) {
		return repository.findById(id)
			.orElseThrow(() -> new CommentException(CommentErrorCode.NO_EXISTED_COMMENT));
	}
}
