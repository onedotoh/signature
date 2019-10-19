package io.spheric.signature.domain;

import java.util.Date;
import java.util.Objects;

public class AuthorizationToken extends AbstractToken {
	private final String role;

	public AuthorizationToken(String userId, String issuer, Date issueDate, Date expirationDate, String role, String token) {
		super(userId, TokenType.AUTHORIZATION, issuer, issueDate, expirationDate, token);
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AuthorizationToken)) return false;
		if (!super.equals(o)) return false;
		AuthorizationToken that = (AuthorizationToken) o;
		return Objects.equals(role, that.role);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), role);
	}
}
