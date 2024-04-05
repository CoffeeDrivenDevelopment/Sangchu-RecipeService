package com.cdd.recipeservice.global.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.cdd.common.exception.SangChuException;
import com.cdd.recipeservice.recipemodule.comment.dto.cond.PagingCond;

@Aspect
@Component
public class CheckPagingCondAspect {
	private static final int MAX_PAGE_SIZE = 20;

	@Before("@annotation(com.cdd.recipeservice.global.annotation.CheckPagingCond)")
	public void check(JoinPoint point) {
		for (Object arg : point.getArgs()) {
			if (arg instanceof PagingCond cond) {
				validateSize(cond.size());
				return;
			}
		}
	}

	private void validateSize(int size) {
		if (size > MAX_PAGE_SIZE) {
			throw new SangChuException(PagingErrorCode.TOO_MANY_REQUEST_SIZE);
		}
	}
}
