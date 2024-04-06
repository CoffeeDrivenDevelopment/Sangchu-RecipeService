package com.cdd.recipeservice.recipemodule.comment.domain.query;

import java.util.List;

import com.cdd.recipeservice.recipemodule.comment.domain.Comment;
import com.cdd.recipeservice.recipemodule.comment.dto.cond.RecipeRootCommentFindCond;

public interface CommentRepositoryCustom {
	boolean hasMoreCommentById(Long id);
	List<Comment> findRootCommentByCond(RecipeRootCommentFindCond cond);
}
