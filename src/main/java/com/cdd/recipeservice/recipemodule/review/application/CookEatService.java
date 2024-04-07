package com.cdd.recipeservice.recipemodule.review.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cdd.recipeservice.global.annotation.CheckPagingCond;
import com.cdd.recipeservice.infra.member.application.MemberClient;
import com.cdd.recipeservice.infra.member.dto.MemberInfoResponse;
import com.cdd.recipeservice.infra.storage.application.StorageClient;
import com.cdd.recipeservice.infra.storage.dto.response.ImageSaveResponse;
import com.cdd.recipeservice.recipemodule.comment.dto.MemberInfo;
import com.cdd.recipeservice.recipemodule.comment.dto.cond.PagingCond;
import com.cdd.recipeservice.recipemodule.recipe.domain.Recipe;
import com.cdd.recipeservice.recipemodule.recipe.domain.RecipeRepository;
import com.cdd.recipeservice.recipemodule.recipe.utils.RecipeServiceUtils;
import com.cdd.recipeservice.recipemodule.review.domain.*;
import com.cdd.recipeservice.recipemodule.review.dto.cond.FindCookEatCond;
import com.cdd.recipeservice.recipemodule.review.dto.request.CookEatSaveRequest;
import com.cdd.recipeservice.recipemodule.review.dto.response.*;
import com.cdd.recipeservice.recipemodule.review.exception.CookEatErrorCode;
import com.cdd.recipeservice.recipemodule.review.exception.CookEatException;
import com.cdd.recipeservice.recipemodule.review.utils.CookEatLikeServiceUtils;
import com.cdd.recipeservice.recipemodule.review.utils.CookEatServiceUtils;
import com.cdd.sangchupassport.Passport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CookEatService implements CookEatModifyService, CookEatLoadService {
	private final CookEatRepository cookEatRepository;
	private final CookEatLikeRepository cookEatLikeRepository;
	private final CookEatImageRepository cookEatImageRepository;
	private final RecipeRepository recipeRepository;

	private final StorageClient storageClient;
	private final MemberClient memberClient;

	@Transactional
	@Override
	public CookEatSaveResponse saveReview(
		final Passport passport,
		final int recipeId,
		final List<MultipartFile> files,
		final CookEatSaveRequest request
	) {
		validateImages(files);
		validateSaveRequest(request);

		Recipe findRecipe = RecipeServiceUtils.findById(recipeRepository, recipeId);
		CookEat cookEat = request.toCookEatWithMemberIdAndRecipe(passport.getMemberId(), findRecipe);
		List<CookEatImage> cookEatImages = getCookEatImages(files, cookEat);

		cookEatImageRepository.saveAll(cookEatImages);
		CookEat savedCookEat = cookEatRepository.save(cookEat);

		return new CookEatSaveResponse(savedCookEat.getId());
	}

	private List<CookEatImage> getCookEatImages(
		final List<MultipartFile> files,
		final CookEat cookEat
	) {
		List<ImageSaveResponse> imageSaveResponses = storageClient.saveImages(files);
		return imageSaveResponses.stream()
			.map(
				response -> {
					CookEatImage cookEatImage = CookEatImage.from(response.image());
					cookEat.addImage(cookEatImage);
					return cookEatImage;
				}
			).toList();
	}

	private void validateSaveRequest(CookEatSaveRequest request) {
		if (request.title().length() > 30) {
			throw new CookEatException(CookEatErrorCode.TOO_LONG_TITLE);
		}
		if (request.content().length() > 255) {
			throw new CookEatException(CookEatErrorCode.TOO_LONG_CONTENT);
		}
	}

	private void validateImages(List<MultipartFile> files) {
		if (files.isEmpty()) {
			throw new CookEatException(CookEatErrorCode.EMPTY_IAMGE);
		}
		if (files.size() > 5) {
			throw new CookEatException(CookEatErrorCode.TOO_MANY_IMAGES);
		}
	}

	@Override
	public CookEatMemberResponse loadMemberReview(Integer memberId) {
		List<CookEatLoadResponse> reviewListResponse = new ArrayList<>();
		List<CookEat> cookEatList = cookEatRepository.findByMemberId(memberId);
		for (CookEat cookEat : cookEatList) {
			String image = cookEatImageRepository.findImageByCookEatId(cookEat.getId());
			reviewListResponse.add(CookEatLoadResponse.of(cookEat, image, cookEat.getLikes().size()));
		}
		cookEatList.sort((o1, o2) -> Integer.compare(o2.getLikes().size(), o1.getLikes().size()));
		return CookEatMemberResponse.builder().cookEatList(reviewListResponse).build();
	}

	@CheckPagingCond
	@Override
	public FindCookEatsResponse findCookEats(Passport passport, int recipeId, PagingCond cond) {
		List<CookEat> findCookEats = cookEatRepository.findCookEatsByCond(new FindCookEatCond(recipeId, cond));
		long last = findCookEats.stream()
			.mapToLong(CookEat::getId)
			.min()
			.orElse(0L);
		boolean hasMore = cookEatRepository.hasMoreCookEatById(cond.last(), recipeId);
		long totalCount = cookEatRepository.findTotalCountByRecipeId(recipeId);
		List<CookEat> likedCookEats = CookEatLikeServiceUtils.isLikedIds(cookEatLikeRepository,
				passport.getMemberId(),
				findCookEats.stream()
					.map(CookEat::getId)
					.toList()
			).stream()
			.map(CookEatLike::getCookEat)
			.toList();

		List<MemberInfo> memberInfos = getMemberInfos(passport, findCookEats)
			.stream()
			.map(MemberInfoResponse::toMemberInfo)
			.toList();

		List<CookEatInfo> cookEatInfos = new ArrayList<>();
		findCookEats.forEach(cookEat -> memberInfos.stream()
			.filter(memberInfo -> cookEat.getMemberId() == memberInfo.id())
			.findFirst()
			.ifPresent(memberInfo -> cookEatInfos.add(
				CookEatInfo.builder()
					.id(cookEat.getId())
					.title(cookEat.getTitle())
					.content(cookEat.getContent())
					.image(cookEat.getImages().get(0).getImage())
					.likeCount(cookEat.getLikes().size())
					.isLiked(likedCookEats.contains(cookEat))
					.member(memberInfo)
					.build()))
		);
		return new FindCookEatsResponse(cookEatInfos, hasMore, last, totalCount, cookEatInfos.size());
	}

	private List<MemberInfoResponse> getMemberInfos(
		final Passport passport,
		final List<CookEat> cookEats
	) {
		List<Integer> memberIds = cookEats.stream()
			.map(CookEat::getMemberId)
			.distinct()
			.toList();
		return memberClient.findMemberInfos(passport, memberIds);
	}

	@Override
	public FindCookEatResponse findCookEat(Passport passport, long reviewId) {
		CookEat findCookEat = CookEatServiceUtils.findById(cookEatRepository, reviewId);

		MemberInfoResponse memberInfo = memberClient.findMemberInfo(passport, findCookEat.getMemberId());

		boolean isLiked = !CookEatLikeServiceUtils.isLikedIds(cookEatLikeRepository, passport.getMemberId(),
			List.of(findCookEat.getId())).isEmpty();

		return FindCookEatResponse.builder()
			.id(findCookEat.getId())
			.recipeName(findCookEat.getRecipe().getTitle())
			.title(findCookEat.getTitle())
			.content(findCookEat.getContent())
			.likeCount(findCookEat.getLikes().size())
			.isLiked(isLiked)
			.image(findCookEat.getImages()
				.stream()
				.map(CookEatImage::getImage)
				.toList())
			.member(memberInfo.toMemberInfo())
			.build();
	}

	@Transactional
	@Override
	public void removeReview(
		final Passport passport,
		final long reviewId
	) {
		CookEat findCookEat = CookEatServiceUtils.findById(cookEatRepository, reviewId);
		validateReviewOwner(findCookEat, passport.getMemberId());

		cookEatImageRepository.deleteAll(findCookEat.getImages());
		cookEatRepository.delete(findCookEat);
	}

	private void validateReviewOwner(
		final CookEat cookEat,
		final int memberId
	) {
		if (cookEat.getMemberId() != memberId) {
			throw new CookEatException(CookEatErrorCode.NOT_OWNER);
		}
	}
}
