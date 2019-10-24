package io.spheric.signature.domain;

public class TokenClaim {
	public static final String ISSUER = "iss";
	public static final String OWNER_ID = "sub";
	public static final String TOKEN_ID = "jti";
	public static final String EXPIRATION = "exp";
	public static final String ISSUE_DATE = "iat";
	public static final String NOT_BEFORE = "nfb";
	public static final String AUDIENCE = "aud";
	public static final String TYPE = "type";

	private TokenClaim() {
	}
}
