package com.assignment.job.recruitor.myrecruitor.exception;


public class RecordNotFoundException extends RuntimeException {

	public RecordNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public RecordNotFoundException(String arg0) {
		super(arg0);
	}

	public RecordNotFoundException(Throwable arg0) {
		super(arg0);
	}

	public RecordNotFoundException() {
		super();
	}

	public RecordNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	
}
