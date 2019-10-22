package io.spheric.signature.domain;

public enum TokenClaim {
	ISSUER("iss"),
	OWNER_ID("sub"),
	TOKEN_ID("jti"),
	EXPIRATION("exp"),
	ISSUE_DATE("iat"),
	NOT_BEFORE("nfb"),
	AUDIENCE("aud"),
	ROLE("role"),
	NEW_EMAIL("new-email"),
	OLD_EMAIL("old-email"),
	TYPE("type");

	private final String value;

	TokenClaim(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static TokenClaim fromString(String value) {
		for (TokenClaim claim : TokenClaim.values()) {
			if (claim.value.equalsIgnoreCase(value)) {
				return claim;
			}
		}
		//TODO: handle?
		return null;
	}
}
