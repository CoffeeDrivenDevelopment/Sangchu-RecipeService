package com.cdd.recipeservice.infra.member.application;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import feign.Response;

@FeignClient(name = "member")
@RequestMapping(value = "/member-service", consumes = "application/json")
public interface MemberFeignClient {

	@GetMapping(value = "/v1/members/{member_id}")
	Response getMemberInfo(
		@RequestHeader("Sangchu-Passport") String passport,
		@PathVariable("member_id") Long memberId
	);
}
