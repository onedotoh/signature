package io.spheric.signature.exception;

public class InvalidTokenException extends RuntimeException {
	private final String token;

	public InvalidTokenException(String message, String token, Throwable cause) {
		super(message, cause);
		this.token = token;
	}

	public InvalidTokenException(String message, String token) {
		super(message);
		this.token = token;
	}

	public InvalidTokenException(String message) {
		super(message);
		this.token = null;
	}

	public String getToken() {
		return token;
	}
}
