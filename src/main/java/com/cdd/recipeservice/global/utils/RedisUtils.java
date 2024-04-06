package com.cdd.recipeservice.global.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;

import com.cdd.common.exception.SangChuException;
import com.cdd.common.exception.ServerErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisUtils {

	/**
	 * @throws JsonProcessingException
	 */
	public static <T> T get(RedisTemplate<byte[], byte[]> redisTemplate,
		ObjectMapper objectMapper,
		Class<T> classType,
		Object... keys) {
		String key = makeKey(keys);
		byte[] redisValue = redisTemplate.opsForValue().get(key.getBytes(StandardCharsets.UTF_8));
		if (ObjectUtils.isEmpty(redisValue)) {
			throw new SangChuException(ServerErrorCode.BAD_REQUEST);
		}
		return readValue(objectMapper, redisValue, classType);
	}

	private static <T> T readValue(ObjectMapper objectMapper,
		byte[] redisValue,
		Class<T> classType) {
		try {
			return objectMapper.readValue(redisValue, classType);
		} catch (IOException ex) {
			throw new SangChuException(ServerErrorCode.BAD_REQUEST);
		}
	}

	public static <T> void put(RedisTemplate<byte[], byte[]> redisTemplate,
		ObjectMapper objectMapper,
		T value,
		Object... keys) {
		try {
			redisTemplate.opsForValue()
				.set(makeKey(keys).getBytes(StandardCharsets.UTF_8),
					objectMapper.writeValueAsString(value)
						.getBytes(StandardCharsets.UTF_8));
		} catch (JsonProcessingException ex) {
			throw new SangChuException(ServerErrorCode.BAD_REQUEST);
		}
	}

	private static String makeKey(Object... keys) {
		StringBuilder sb = new StringBuilder();
		for (Object key : keys) {
			sb.append(key.toString());
		}
		return sb.toString();
	}
}
