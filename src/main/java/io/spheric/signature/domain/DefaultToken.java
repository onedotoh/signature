package io.spheric.signature.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.EqualsAndHashCode;

import java.beans.ConstructorProperties;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

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
	public String getOwner() {
		return claims.get(TokenClaim.OWNER);
	}

	@Override
	public Optional<String> getIssuer() {
		return Optional.of(claims.get(TokenClaim.ISSUER));
	}

	@Override
	public Date getIssueDate() {
		return new Date(Long.parseLong(claims.get(TokenClaim.ISSUE_DATE)) * 1000);
	}

	@Override
	public Optional<Date> getExpirationDate() {
		return Optional.of(new Date(Long.parseLong(claims.get(TokenClaim.EXPIRATION)) * 1000));
	}

	@Override
	public Optional<String> getTokenId() {
		return Optional.of(claims.get(TokenClaim.TOKEN_ID));
	}

	@Override
	public Optional<Date> getNotBeforeDate() {
		return Optional.of(new Date(Long.parseLong(claims.get(TokenClaim.NOT_BEFORE)) * 1000));
	}

	@Override
	public Optional<String> getAudience() {
		return Optional.of(claims.get(TokenClaim.AUDIENCE));
	}

	@Override
	public Optional<String> getIntention() {
		return Optional.of(claims.get(TokenClaim.DESCRIPTION));
	}

	@Override
	public Optional<String> getData() {
		return Optional.of(claims.get(TokenClaim.DATA));
	}

	@Override
	public String getToken() {
		return token;
	}

	@Override
	public TokenType getType() {
		return TokenType.valueOf(claims.get(TokenClaim.TYPE));
	}

	@Override
	public String toString() {
		return this.getToken();
	}
}
