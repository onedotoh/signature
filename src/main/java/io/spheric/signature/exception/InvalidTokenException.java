package io.spheric.signature.exception;

public class InvalidTokenException extends RuntimeException {
	private final String jwt;

	public InvalidTokenException(String message, String jwt, Throwable cause) {
		super(message, cause);
		this.jwt = jwt;
	}

	public InvalidTokenException(String message, String jwt) {
		super(message);
		this.jwt = jwt;
	}

	public InvalidTokenException(String message) {
		super(message);
		this.jwt = null;
	}

	public String getToken() {
		return jwt;
	}
}
