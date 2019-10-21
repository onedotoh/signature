package io.spheric.signature.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Date;

@JsonTypeName("password-update")
public class PasswordUpdateToken extends AbstractToken {
	public PasswordUpdateToken(String userId, String issuer, Date issueDate, Date expirationDate, String token) {
		super(userId, TokenType.PASSWORD_UPDATE, issuer, issueDate, expirationDate, token);
	}
}
