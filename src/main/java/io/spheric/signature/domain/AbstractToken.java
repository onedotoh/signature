package io.spheric.signature.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public abstract class AbstractToken implements Token {
	private final Map<TokenClaim, String> claims;
	private final String token;

	@Override
	@JsonIgnore
	public String getUserId() {
		return claims.get(TokenClaim.OWNER_ID);
	}

	@Override
	@JsonIgnore
	public String getIssuer() {
		return claims.get(TokenClaim.ISSUER);
	}

	@Override
	@JsonIgnore
	public Date getIssueDate() {
		return new Date(Long.getLong(claims.get(TokenClaim.ISSUE_DATE)));
	}

	@Override
	@JsonIgnore
	public Date getExpirationDate() {
		return new Date(Long.getLong(claims.get(TokenClaim.EXPIRATION)));
	}

	@Override
	public String getToken() {
		return token;
	}

	@Override
	public String toString() {
		return this.getToken();
	}
}
