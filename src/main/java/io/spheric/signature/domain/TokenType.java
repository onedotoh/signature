package io.spheric.signature.domain;

public enum TokenType {
	AUTHORIZATION(360000), // 6 minutes
	REGISTRATION(86400000), // 24 hours
	EMAIL_UPDATE(86400000), // 24 hours
	PASSWORD_UPDATE(3600000); // 1 hour

	private final long duration;

	TokenType(long duration) {
		this.duration = duration;
	}

	public long getDuration() {
		return duration;
	}
}
