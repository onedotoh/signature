package io.spheric.signature.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Date;

@JsonTypeName("registration")
public class RegistrationToken extends AbstractToken {
	public RegistrationToken(String userId, String issuer, Date issueDate, Date expirationDate, String token) {
		super(userId, TokenType.REGISTRATION, issuer, issueDate, expirationDate, token);
	}
}
