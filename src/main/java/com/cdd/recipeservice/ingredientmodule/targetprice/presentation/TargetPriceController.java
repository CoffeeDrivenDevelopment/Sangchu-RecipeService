package com.cdd.recipeservice.ingredientmodule.targetprice.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdd.common.response.MessageBody;
import com.cdd.common.response.ResponseEntityFactory;
import com.cdd.recipeservice.ingredientmodule.targetprice.application.TargetPriceInfoService;
import com.cdd.recipeservice.ingredientmodule.targetprice.application.TargetPriceLoadService;
import com.cdd.recipeservice.ingredientmodule.targetprice.application.TargetPriceSaveService;
import com.cdd.recipeservice.ingredientmodule.targetprice.dto.request.TargetPriceRequest;
import com.cdd.recipeservice.ingredientmodule.targetprice.dto.response.TargetPriceInfoResponse;
import com.cdd.recipeservice.ingredientmodule.targetprice.dto.response.TargetPriceItemListResponse;
import com.cdd.sangchupassport.Passport;
import com.cdd.sangchupassport.support.RequestPassport;

import lombok.RequiredArgsConstructor;

@RequestMapping("/recipe-service")
@RequiredArgsConstructor
@RestController
public class TargetPriceController {
	private final TargetPriceSaveService targetPriceSaveService;
	private final TargetPriceInfoService targetPriceInfoService;
	private final TargetPriceLoadService targetPriceLoadService;

	@PostMapping("/v1/ingredients/{id}/prices/target")
	public ResponseEntity<MessageBody<Void>> pickTargetPrice(
		@RequestPassport Passport passport,
		@PathVariable("id") int id,
		@RequestBody TargetPriceRequest request
	) {
		targetPriceSaveService.pickTargetPrice(passport.getMemberId(), id, request.TargetPrice());
		return ResponseEntityFactory.ok("목표가 설정 성공");
	}

	@GetMapping("/v1/ingredients/{id}/prices/target")
	public ResponseEntity<MessageBody<TargetPriceInfoResponse>> getTargetPriceList(
		@RequestPassport Passport passport,
		@PathVariable("id") int ingredientId
	) {
		TargetPriceInfoResponse targetPriceInfoResponse = targetPriceInfoService.getTargetPriceInfoList(
			passport.getMemberId(),
			ingredientId);
		return ResponseEntityFactory.ok("목표가 조회 성공", targetPriceInfoResponse);
	}

	@PostMapping("/v1/ingredients/prices/target")
	public ResponseEntity<MessageBody<Void>> updateTargetPrices() {
		targetPriceSaveService.updateTargetPriceList();
		return ResponseEntityFactory.ok("목표가 정보 업데이트 성공");
	}

	@GetMapping("/v1/targetprice")
	public ResponseEntity<MessageBody<TargetPriceItemListResponse>> getMyTargetPrice(
		@RequestPassport Passport passport) {
		return ResponseEntityFactory.ok("나의 목표가 리스트 조회 성공",
			targetPriceLoadService.getMyTargetPrice(passport.getMemberId()));
	}
}
