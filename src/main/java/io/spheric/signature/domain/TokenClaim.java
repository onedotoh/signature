package io.spheric.signature.domain;

public enum TokenClaim {
	ISSUER("iss"),
	OWNER("sub"),
	TOKEN_ID("jti"),
	EXPIRATION("exp"),
	ISSUE_DATE("iat"),
	NOT_BEFORE("nbf"),
	AUDIENCE("aud"),
	TYPE("type"),
	DESCRIPTION("desc"),
	DATA("data");

	private final String claim;

	TokenClaim(String claim) {
		this.claim = claim;
	}

	public String getClaim() {
		return claim;
	}

	public static TokenClaim fromString(String claim) {
		for (TokenClaim tokenClaim : TokenClaim.values()) {
			if (tokenClaim.claim.equalsIgnoreCase(claim)) {
				return tokenClaim;
			}
		}

		throw new IllegalArgumentException(claim + " is not a defined token claim");
	}
}
