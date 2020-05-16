package com.hl.common.exception;

import com.hl.common.constants.Response;
import lombok.Getter;
import lombok.Setter;
import java.text.MessageFormat;

@Setter
@Getter
public class CustomException extends RuntimeException {
	private static final long serialVersionUID = -3508374497810473007L;
	/**
	 * 默认异常代码
	 */
	private int code;

	public CustomException(int code, String message) {
		super(message);
		this.code = code;
	}

	public CustomException(Response rc, Object... messageArgs) {
		super(MessageFormat.format(rc.getMessage(), messageArgs));
		this.code = rc.getCode();
	}

	public CustomException(Exception e) {
		super(e);
	}
}
