package com.quickly.devploment.utils;

/**
 * @Author lidengjin
 * @Date 2020/11/19 11:13 上午
 * @Version 1.0
 */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.function.Function;

public class MathUtil {
	/**
	 * 2位小数精度
	 */
	private static int PRECISION_2 = 2;


	/**
	 * 2位小数精度处理，向下取整
	 *
	 * @param value
	 * @return 如果 <code>value</code> 为 null， 返回 0
	 */
	public static BigDecimal roundDown2(BigDecimal value) {
		return roundDown(value, PRECISION_2);
	}

	/**
	 * 四舍五入，保留两位小数
	 *
	 *
	 */
	public static BigDecimal round(BigDecimal value) {
		return Half_UP(value, PRECISION_2);
	}

	/**
	 * 四舍五入，
	 *
	 * @param value
	 * @param precision
	 * @return
	 */
	private static BigDecimal Half_UP(BigDecimal value, int precision) {
		if (value == null) {
			return BigDecimal.ZERO;
		}
		return value.setScale(precision, RoundingMode.HALF_UP);
	}

	private static BigDecimal roundDown(BigDecimal value, int precision) {
		if (value == null) {
			return BigDecimal.ZERO;
		}
		return value.setScale(precision, RoundingMode.DOWN);
	}




	public static <S,R> BigDecimal subtract(S s,R r, Function<S,BigDecimal> sf,Function<R,BigDecimal> rf){
		BigDecimal subtraction = sf.apply(s);
		BigDecimal minuend = rf.apply(r);
		subtraction = Objects.nonNull(subtraction) ? subtraction : BigDecimal.ZERO;
		minuend = Objects.nonNull(minuend)? minuend  : BigDecimal.ZERO;
		return subtraction.subtract(minuend);
	}

	public static BigDecimal subtract(BigDecimal s, BigDecimal r) {
		s = s == null ? BigDecimal.ZERO : s;
		return r == null ? s : s.subtract(r);
	}

	public static BigDecimal add(BigDecimal... p) {
		BigDecimal result = BigDecimal.ZERO;
		for (int i = 0; i < p.length; i++) {
			result = add(result,p[i]);
		}
		return BigDecimal.ZERO;
	}
	public static BigDecimal add(BigDecimal a,BigDecimal b) {
		a = a == null ? BigDecimal.ZERO : a;
		return b == null ? a : a.add(b);
	}

	public static boolean isSame(BigDecimal a, BigDecimal b) {
		if (a == null || b == null ){
			return false;
		}
		return a.compareTo(b) == 0;
	}
}

