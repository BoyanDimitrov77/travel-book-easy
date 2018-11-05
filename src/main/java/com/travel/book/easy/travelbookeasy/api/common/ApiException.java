package com.travel.book.easy.travelbookeasy.api.common;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	@Setter
	@Getter
	private HttpStatus httpStatus;

	@Setter
	@Getter
	private String message;

	public ApiException(String message) {
		this(message, HttpStatus.BAD_REQUEST);
	}

	public ApiException(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public ApiException(Throwable t) {
		super(t);
		this.message = t.getMessage();
		this.httpStatus = HttpStatus.BAD_REQUEST;
	}
}

