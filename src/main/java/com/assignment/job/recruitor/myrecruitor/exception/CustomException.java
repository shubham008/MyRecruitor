package com.assignment.job.recruitor.myrecruitor.exception;

public class CustomException extends RuntimeException {

	public CustomException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CustomException(String arg0) {
		super(arg0);
	}

	public CustomException(Throwable arg0) {
		super(arg0);
	}

	public CustomException() {
		super();
	}

	public CustomException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
