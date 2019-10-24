package io.spheric.signature.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;

import java.beans.ConstructorProperties;
import java.util.Date;
import java.util.Map;

@EqualsAndHashCode
public class DefaultToken implements Token {
	private final Map<String, String> claims;
	private final String token;

	@ConstructorProperties({"claims", "token"})

	@JsonCreator
	public DefaultToken(Map<String, String> claims, String token) {
		this.claims = claims;
		this.token = token;
	}

	@Override
	public Map<String, String> getClaims() {
		return claims;
	}

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
	public String getType() {
		return claims.get(TokenClaim.TYPE);
	}

	@Override
	public String toString() {
		return this.getToken();
	}
}
