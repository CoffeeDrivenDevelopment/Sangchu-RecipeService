package com.cdd.recipeservice.ingredientmodule.targetprice.domain;

import java.util.List;

import org.springframework.data.redis.core.RedisHash;

import com.cdd.recipeservice.ingredientmodule.targetprice.dto.response.TargetPriceInfo;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@RedisHash(timeToLive = 60 * 60 * 24)
public class TargetPriceInfoList {
	@Id
	private String key;
	private List<TargetPriceInfo> targetPriceInfos;

	public static TargetPriceInfoList of(String key, List<TargetPriceInfo> targetPriceInfos) {
		return TargetPriceInfoList.builder()
			.key(key)
			.targetPriceInfos(targetPriceInfos)
			.build();
	}

	public TargetPriceInfo loadMaximum() {
		return targetPriceInfos.stream()
			.max(TargetPriceInfo::compareTo)
			.orElse(new TargetPriceInfo(0, 0L));
	}
}
