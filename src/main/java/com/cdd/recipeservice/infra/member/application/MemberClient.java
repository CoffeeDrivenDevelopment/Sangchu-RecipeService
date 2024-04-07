package com.cdd.recipeservice.infra.member.application;

import java.util.List;

import com.cdd.recipeservice.infra.member.dto.MemberCoordinateResponse;
import com.cdd.recipeservice.infra.member.dto.MemberInfoResponse;
import com.cdd.sangchupassport.Passport;

public interface MemberClient {
	MemberInfoResponse findMemberInfo(Passport passport, int memberId);

	List<MemberInfoResponse> findMemberInfos(Passport passport, List<Integer> memberIds);

	MemberCoordinateResponse findMemberCoordinates(Passport passport);
}
