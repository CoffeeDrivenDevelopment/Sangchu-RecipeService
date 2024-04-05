package com.cdd.recipeservice.infra.member.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.cdd.common.exception.SangChuException;
import com.cdd.common.exception.ServerErrorCode;
import com.cdd.recipeservice.infra.config.InfraClientProperties;
import com.cdd.recipeservice.infra.member.dto.MemberInfoResponse;
import com.cdd.sangchupassport.Passport;
import com.cdd.sangchupassport.support.SangchuHeader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class MemberClient {
	private final String url;
	private final ObjectMapper mapper;

	@Autowired
	public MemberClient(InfraClientProperties properties) {
		url = properties.getMemberServiceUrl();
		mapper = new ObjectMapper();
	}

	public MemberInfoResponse findMemberInfo(
		final Passport passport,
		final int memberId
	) {
		String passportAsString = getWritePassportAsString(passport);
		return fetchMemberInfo(passportAsString, memberId).block();
	}

	public List<MemberInfoResponse> findMemberInfos(
		final Passport passport,
		final List<Integer> memberIds
	) {
		String passportAsString = getWritePassportAsString(passport);
		return Flux.fromIterable(memberIds)
			.flatMap(memberId -> fetchMemberInfo(passportAsString, memberId))
			.collectList()
			.block();
	}

	private Mono<MemberInfoResponse> fetchMemberInfo(
		final String passportAsString,
		final int memberId
	) {
		return WebClient.create(url)
			.get()
			.uri(uriBuilder -> uriBuilder.path("/v1/members/{member_id}")
				.build(memberId))
			.header(SangchuHeader.SANGCHU_PASSPORT.getName(), passportAsString)
			.retrieve()
			.bodyToMono(MemberInfoResponse.class);
	}

	private String getWritePassportAsString(Passport passport) {
		try {
			return mapper.writeValueAsString(passport);
		} catch (JsonProcessingException e) {
			log.warn("\nPassport 문자열 변환 실패 : [{}]", passport);
			throw new SangChuException(ServerErrorCode.BAD_REQUEST);
		}
	}
}
