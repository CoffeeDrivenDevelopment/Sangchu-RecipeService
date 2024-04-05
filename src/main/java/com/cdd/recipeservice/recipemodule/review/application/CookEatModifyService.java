package com.cdd.recipeservice.recipemodule.review.application;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cdd.recipeservice.recipemodule.review.dto.request.CookEatSaveRequest;
import com.cdd.recipeservice.recipemodule.review.dto.response.CookEatSaveResponse;
import com.cdd.sangchupassport.Passport;

public interface CookEatModifyService {
	CookEatSaveResponse saveReview(Passport passport, int recipeId, List<MultipartFile> files, CookEatSaveRequest request);

	void removeReview(Passport passport, long reviewId);
}
