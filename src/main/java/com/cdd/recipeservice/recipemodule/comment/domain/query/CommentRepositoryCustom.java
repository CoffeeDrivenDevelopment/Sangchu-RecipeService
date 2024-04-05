package com.cdd.recipeservice.recipemodule.comment.domain.query;

import java.util.List;

import com.cdd.recipeservice.recipemodule.comment.domain.Comment;
import com.cdd.recipeservice.recipemodule.comment.dto.cond.RecipeCommentFindCond;

public interface CommentRepositoryCustom {
	boolean hasMoreCommentById(Long id);
	List<Comment> findCommentByCond(RecipeCommentFindCond cond);
}
