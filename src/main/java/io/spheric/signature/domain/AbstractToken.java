package io.spheric.signature.domain;

import java.util.Date;
import java.util.Objects;

public abstract class AbstractToken implements Token {
	private final Date expirationDate;
	private final Date issueDate;
	private final String issuer;
	private final String token;
	private final TokenType tokenType;
	private final String userId;

	public AbstractToken(
			String userId,
			TokenType tokenType,
			String issuer,
			Date issueDate,
			Date expirationDate,
			String token) {
		this.tokenType = tokenType;
		this.expirationDate = expirationDate;
		this.issueDate = issueDate;
		this.issuer = issuer;
		this.userId = userId;
		this.token = token;
	}

	@Override
	public String getUserId() {
		return userId;
	}


	@Override
	public String getIssuer() {
		return issuer;
	}

	@Override
	public Date getIssueDate() {
		return issueDate;
	}

	@Override
	public Date getExpirationDate() {
		return expirationDate;
	}

	@Override
	public TokenType getTokenType() {
		return tokenType;
	}

	@Override
	public String getToken() {
		return token;
	}

	@Override
	public String toString() {
		return this.getToken();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AbstractToken)) return false;
		AbstractToken that = (AbstractToken) o;
		return Objects.equals(expirationDate, that.expirationDate) &&
				Objects.equals(issueDate, that.issueDate) &&
				Objects.equals(issuer, that.issuer) &&
				Objects.equals(token, that.token) &&
				tokenType == that.tokenType &&
				Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(expirationDate, issueDate, issuer, token, tokenType, userId);
	}
}
