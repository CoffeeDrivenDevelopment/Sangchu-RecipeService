package com.cdd.recipeservice.recipemodule.comment.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdd.recipeservice.global.annotation.CheckPagingCond;
import com.cdd.recipeservice.global.utils.LocalDateTimeUtils;
import com.cdd.recipeservice.infra.member.application.MemberClient;
import com.cdd.recipeservice.infra.member.dto.MemberInfoResponse;
import com.cdd.recipeservice.recipemodule.comment.domain.Comment;
import com.cdd.recipeservice.recipemodule.comment.domain.CommentRepository;
import com.cdd.recipeservice.recipemodule.comment.dto.MemberInfo;
import com.cdd.recipeservice.recipemodule.comment.dto.cond.PagingCond;
import com.cdd.recipeservice.recipemodule.comment.dto.cond.RecipeReplyCommentFindCond;
import com.cdd.recipeservice.recipemodule.comment.dto.cond.RecipeRootCommentFindCond;
import com.cdd.recipeservice.recipemodule.comment.dto.request.RecipeCommentSaveRequest;
import com.cdd.recipeservice.recipemodule.comment.dto.request.RecipeCommentUpdateRequest;
import com.cdd.recipeservice.recipemodule.comment.dto.response.*;
import com.cdd.recipeservice.recipemodule.comment.exception.CommentErrorCode;
import com.cdd.recipeservice.recipemodule.comment.exception.CommentException;
import com.cdd.recipeservice.recipemodule.comment.utils.RecipeCommentServiceUtils;
import com.cdd.recipeservice.recipemodule.recipe.domain.Recipe;
import com.cdd.recipeservice.recipemodule.recipe.domain.RecipeRepository;
import com.cdd.recipeservice.recipemodule.recipe.utils.RecipeServiceUtils;
import com.cdd.sangchupassport.Passport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@Service
public class RecipeCommentServiceImpl
	implements RecipeCommentModifyService, RecipeCommentLoadService {
	private static final long ROOT_COMMENT = -1L;
	private static final long DELETE_COMMENT_MEMBER_OWNER_ID = -1L;

	private final CommentRepository commentRepository;
	private final RecipeRepository recipeRepository;
	private final MemberClient memberClient;

	@Transactional
	@Override
	public RecipeCommentResponse commentSave(
		final Passport passport,
		final RecipeCommentSaveRequest request
	) {
		Recipe findRecipe = RecipeServiceUtils.findById(recipeRepository, request.recipeId());
		Comment comment = makeRecipeComment(passport.getMemberId(), request, findRecipe);
		Comment savedComment = commentRepository.save(comment);

		MemberInfo recipeCommentMember = memberClient.findMemberInfo(passport, passport.getMemberId())
			.toMemberInfo();
		return makeSaveCommentResponse(savedComment, recipeCommentMember);
	}

	private Comment makeRecipeComment(
		final int memberId,
		final RecipeCommentSaveRequest request,
		final Recipe recipe
	) {
		if (request.parentCommentId() == ROOT_COMMENT) {
			return Comment.builder()
				.memberId(memberId)
				.recipe(recipe)
				.content(request.content())
				.replyCount(0)
				.build();
		}
		Comment findParentComment = RecipeCommentServiceUtils.findById(commentRepository, request.parentCommentId());
		findParentComment.updateReplyCount(findParentComment.getReplyCount() + 1);
		return Comment.builder()
			.memberId(memberId)
			.parentComment(findParentComment)
			.recipe(recipe)
			.content(request.content())
			.build();
	}

	private RecipeCommentResponse makeSaveCommentResponse(
		final Comment savedComment,
		final MemberInfo member
	) {
		if (savedComment.getReplyCount() == null) {
			return RecipeReplyCommentResponse.builder()
				.commentId(savedComment.getId())
				.content(savedComment.getContent())
				.lastUpdatedTime(LocalDateTimeUtils.pattern(savedComment.getUpdatedAt()))
				.member(member)
				.build();
		}
		return RecipeRootCommentResponse.builder()
			.commentId(savedComment.getId())
			.content(savedComment.getContent())
			.lastUpdatedTime(LocalDateTimeUtils.pattern(savedComment.getUpdatedAt()))
			.replyCount(savedComment.getReplyCount())
			.member(member)
			.build();
	}

	@Transactional
	@Override
	public RecipeCommentUpdateResponse commentUpdate(
		final int memberId,
		final long commentId,
		final RecipeCommentUpdateRequest request
	) {
		Comment findComment = RecipeCommentServiceUtils.findById(commentRepository, commentId);
		validateCommentOwner(memberId, findComment);

		findComment.updateContent(request.content());
		return new RecipeCommentUpdateResponse(findComment.getContent());
	}

	@Transactional
	@Override
	public RecipeCommentRemoveResponse commentRemove(int memberId, long commentId) {
		Comment findComment = RecipeCommentServiceUtils.findById(commentRepository, commentId);
		validateCommentOwner(memberId, findComment);

		findComment.remove();

		// TODO: Kafka 통신으로 사용자의 작성한 댓글의 수를 줄이는 로직 필요

		return RecipeCommentRemoveResponse.create();
	}

	private void validateCommentOwner(
		final int memberId,
		final Comment comment
	) {
		if (comment.getMemberId() != memberId) {
			throw new CommentException(CommentErrorCode.NOT_OWNER);
		}
	}

	@CheckPagingCond
	@Override
	public FindRecipeCommentResponse findRecipeComment(
		final Passport passport,
		final PagingCond cond,
		final int recipeId
	) {
		List<Comment> findComments = commentRepository
			.findRootCommentByCond(RecipeRootCommentFindCond.of(cond, recipeId));

		List<MemberInfoResponse> memberInfos = getMemberInfos(passport, findComments);

		List<RecipeRootCommentResponse> recipeRootCommentResponses = getRecipeRootComments(findComments, memberInfos);
		long lastCommentId = getLastCommentId(findComments);
		boolean hasMore = commentRepository.hasMoreCommentByCond(
			RecipeRootCommentFindCond.of(new PagingCond(lastCommentId, 1), recipeId));

		return FindRecipeCommentResponse.of(recipeRootCommentResponses, hasMore, lastCommentId);
	}

	private List<RecipeRootCommentResponse> getRecipeRootComments(
		final List<Comment> findComments,
		final List<MemberInfoResponse> memberInfos
	) {
		List<RecipeRootCommentResponse> commentResponses = new ArrayList<>();
		for (Comment comment : findComments) {
			if (comment.getMemberId() == DELETE_COMMENT_MEMBER_OWNER_ID) {
				commentResponses.add(RecipeRootCommentResponse.deleteComment(comment));
			}
			memberInfos.stream()
				.filter(member -> comment.getMemberId() == member.id())
				.findFirst()
				.ifPresent(member -> commentResponses.add(RecipeRootCommentResponse.of(comment, member)));
		}
		return commentResponses;
	}

	@CheckPagingCond
	@Override
	public FindRecipeReplyCommentResponse findRecipeReplyComment(
		final Passport passport,
		final PagingCond cond,
		final long commentId
	) {
		List<Comment> findReplyComments = commentRepository
			.findReplyCommentByCond(RecipeReplyCommentFindCond.of(cond, commentId));

		List<MemberInfoResponse> memberInfos = getMemberInfos(passport, findReplyComments);

		List<RecipeReplyCommentResponse> recipeReplyCommentsResponse =
			getRecipeReplyComments(findReplyComments, memberInfos);
		long lastCommentId = getLastCommentId(findReplyComments);
		boolean hasMore = commentRepository.hasMoreReplyCommentById(
			RecipeReplyCommentFindCond.of(new PagingCond(lastCommentId, 1), commentId));

		return FindRecipeReplyCommentResponse.of(recipeReplyCommentsResponse, hasMore, lastCommentId);
	}

	private List<RecipeReplyCommentResponse> getRecipeReplyComments(
		final List<Comment> findComments,
		final List<MemberInfoResponse> memberInfos
	) {
		List<RecipeReplyCommentResponse> commentResponses = new ArrayList<>();
		for (Comment comment : findComments) {
			if (comment.getMemberId() == DELETE_COMMENT_MEMBER_OWNER_ID) {
				commentResponses.add(RecipeReplyCommentResponse.deleteComment(comment));
			}
			memberInfos.stream()
				.filter(member -> comment.getMemberId() == member.id())
				.findFirst()
				.ifPresent(member -> commentResponses.add(RecipeReplyCommentResponse.of(comment, member)));
		}
		return commentResponses;
	}

	private List<MemberInfoResponse> getMemberInfos(
		final Passport passport,
		final List<Comment> findComments
	) {
		List<Integer> memberIds = findComments.stream()
			.map(Comment::getMemberId)
			.filter(id -> id != -1)
			.distinct()
			.toList();
		return memberClient.findMemberInfos(passport, memberIds);
	}

	private long getLastCommentId(List<Comment> findComments) {
		return findComments.stream()
			.mapToLong(Comment::getId)
			.min()
			.orElse(-1L);
	}
}
