package com.cdd.recipeservice.recipemodule.comment.domain.query;

import static com.cdd.recipeservice.recipemodule.comment.domain.QComment.*;
import static com.cdd.recipeservice.recipemodule.recipe.domain.QRecipe.*;

import java.util.List;
import java.util.Optional;

import com.cdd.recipeservice.recipemodule.comment.domain.Comment;
import com.cdd.recipeservice.recipemodule.comment.dto.cond.RecipeCommentFindCond;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {
	private final JPAQueryFactory query;

	@Override
	public boolean hasMoreCommentById(final Long id) {
		return id != -1 && Optional.ofNullable(query.selectFrom(comment)
				.where(comment.id.lt(id))
				.fetchFirst())
			.isPresent();
	}

	@Override
	public List<Comment> findCommentByCond(final RecipeCommentFindCond cond) {
		return query.selectFrom(comment)
			.join(comment.recipe, recipe)
			.where(recipe.id.eq((int)cond.recipeId())
				.and(comment.id.lt(cond.pagingCond().last()))
				.and(searchCommentType(cond.canRootCommentSearch()))
			)
			.orderBy(comment.id.desc())
			.limit(cond.pagingCond().size())
			.fetch();
	}

	/**
	 * 댓글과 대댓글 조회하는 방식 선택
	 *
	 * @param b 부모 댓글의 유무
	 * @return `b`가 `true`이면  `parentComment`는 `null`이며 `false`인 경우 `parentComment`는 존재합니다.
	 */
	private BooleanExpression searchCommentType(boolean b) {
		return b ? comment.parentComment.isNull() : comment.parentComment.isNotNull();
	}
}
