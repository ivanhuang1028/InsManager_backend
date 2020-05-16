package com.hl.common.constants;

public interface Response {
	/**
	 * 获取api返回的状态码
	 *
	 * @return 状态码
	 */
	int getCode();

	/**
	 * 获取api返回的结果描述
	 *
	 * @return 结果描述
	 */
	String getMessage();
}
