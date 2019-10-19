package io.spheric.signature.domain;

import java.util.Date;
import java.util.Objects;

public class EmailUpdateToken extends AbstractToken {
	private final String newEmail;
	private final String oldEmail;

	public EmailUpdateToken(String userId, String issuer, Date issueDate, Date expirationDate, String token, String oldEmail, String newEmail) {
		super(userId, TokenType.EMAIL_UPDATE, issuer, issueDate, expirationDate, token);
		this.newEmail = newEmail;
		this.oldEmail = oldEmail;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof EmailUpdateToken)) return false;
		if (!super.equals(o)) return false;
		EmailUpdateToken that = (EmailUpdateToken) o;
		return Objects.equals(newEmail, that.newEmail);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), newEmail);
	}

	public String getNewEmail() {
		return newEmail;
	}

	public String getOldEmail() {
		return oldEmail;
	}
}
