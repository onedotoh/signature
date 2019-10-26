package io.spheric.signature.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Data
public class Token {
	private final Map<TokenClaim, String> claims;
	private final String jwt;

	public Map<TokenClaim, String> getClaims() {
		return claims;
	}

	public String getOwner() {
		return claims.get(TokenClaim.OWNER);
	}

	@JsonIgnore
	public String getIssuer() {
		return claims.get(TokenClaim.ISSUER);
	}

	@JsonIgnore
	public Date getIssueDate() {
		return new Date(Long.parseLong(claims.get(TokenClaim.ISSUE_DATE)) * 1000);
	}

	@JsonIgnore
	public Optional<Date> getExpirationDate() {
		return Optional.of(new Date(Long.parseLong(claims.get(TokenClaim.EXPIRATION)) * 1000));
	}

	@JsonIgnore
	public String getTokenId() {
		return claims.get(TokenClaim.TOKEN_ID);
	}

	@JsonIgnore
	public Optional<Date> getNotBeforeDate() {
		return Optional.of(new Date(Long.parseLong(claims.get(TokenClaim.NOT_BEFORE)) * 1000));
	}

	@JsonIgnore
	public Optional<String> getAudience() {
		return Optional.of(claims.get(TokenClaim.AUDIENCE));
	}

	@JsonIgnore
	public Optional<String> getDescription() {
		return Optional.of(claims.get(TokenClaim.DESCRIPTION));
	}

	@JsonIgnore
	public Optional<String> getData() {
		return Optional.of(claims.get(TokenClaim.DATA));
	}

	public String getJwt() {
		return jwt;
	}

	@JsonIgnore
	public TokenType getType() {
		return TokenType.valueOf(claims.get(TokenClaim.TYPE));
	}

	@Override
	public String toString() {
		return this.getJwt();
	}
}
