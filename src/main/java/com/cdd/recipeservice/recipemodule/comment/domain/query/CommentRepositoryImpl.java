package com.cdd.recipeservice.recipemodule.comment.domain.query;

import static com.cdd.recipeservice.recipemodule.comment.domain.QComment.*;
import static com.cdd.recipeservice.recipemodule.recipe.domain.QRecipe.*;

import java.util.List;

import com.cdd.recipeservice.recipemodule.comment.domain.Comment;
import com.cdd.recipeservice.recipemodule.comment.dto.cond.RecipeReplyCommentFindCond;
import com.cdd.recipeservice.recipemodule.comment.dto.cond.RecipeRootCommentFindCond;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {
	private final JPAQueryFactory query;

	@Override
	public List<Comment> findRootCommentByCond(final RecipeRootCommentFindCond cond) {
		return query.selectFrom(comment)
			.join(comment.recipe, recipe)
			.where(recipe.id.eq(cond.recipeId())
				.and(comment.parentComment.isNull())
				.and(comment.id.lt(cond.pagingCond().last())
				))
			.orderBy(comment.id.desc())
			.limit(cond.pagingCond().size())
			.fetch();
	}

	@Override
	public boolean hasMoreCommentByCond(RecipeRootCommentFindCond cond) {
		return cond.pagingCond().last() != -1 && query.selectFrom(comment)
			.join(comment.recipe, recipe)
			.where(recipe.id.eq(cond.recipeId())
				.and(comment.parentComment.isNull())
				.and(comment.id.lt(cond.pagingCond().last())
				))
			.fetchFirst() != null;
	}

	@Override
	public List<Comment> findReplyCommentByCond(RecipeReplyCommentFindCond cond) {
		return query.selectFrom(comment)
			.where(comment.parentComment.id.eq(cond.commentId())
				.and(comment.id.lt(cond.pagingCond().last())))
			.orderBy(comment.id.desc())
			.limit(cond.pagingCond().size())
			.fetch();
	}

	@Override
	public boolean hasMoreReplyCommentById(RecipeReplyCommentFindCond cond) {
		return cond.pagingCond().last() != -1 && query.selectFrom(comment)
			.where(comment.parentComment.id.eq(cond.commentId())
				.and(comment.id.lt(cond.pagingCond().last())))
			.fetchFirst() != null;
	}
}
