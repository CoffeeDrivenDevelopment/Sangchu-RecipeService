package com.cdd.recipeservice.recipemodule.comment.domain.query;

import java.util.List;

import com.cdd.recipeservice.recipemodule.comment.domain.Comment;
import com.cdd.recipeservice.recipemodule.comment.dto.cond.RecipeReplyCommentFindCond;
import com.cdd.recipeservice.recipemodule.comment.dto.cond.RecipeRootCommentFindCond;

public interface CommentRepositoryCustom {
	boolean hasMoreCommentByCond(RecipeRootCommentFindCond cond);

	boolean hasMoreReplyCommentById(RecipeReplyCommentFindCond cond);

	List<Comment> findRootCommentByCond(RecipeRootCommentFindCond cond);

	List<Comment> findReplyCommentByCond(RecipeReplyCommentFindCond cond);
}
