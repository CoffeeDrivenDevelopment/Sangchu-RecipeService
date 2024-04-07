package com.cdd.recipeservice.global.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.cdd.common.exception.CallConstructorException;

public class LocalDateTimeUtils {
	private LocalDateTimeUtils() {
		throw new CallConstructorException();
	}
	public static LocalDateTime today(String... zoneId){
		if(zoneId.length == 0) {
			return LocalDateTime.now(ZoneId.of("Asia/Seoul"));
		}
		return LocalDateTime.now(ZoneId.of(zoneId[0]));
	}
	public static String pattern(LocalDateTime time) {
		return time.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
	}

	public static String timePattern(LocalDateTime time) {
		return time.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));
	}

	public static String nowTimePattern(String... zoneId) {
		return timePattern(today(zoneId));
	}
}
