package io.spheric.signature.service;

import io.jsonwebtoken.Claims;
import io.spheric.signature.domain.*;
import io.spheric.signature.exception.InvalidTokenException;

public class TokenAdapter {

	public static Token adapt(Claims claims, String token) {
		TokenType type = TokenType.valueOf(claims.get("type", String.class));

		switch (type) {
			case AUTHORIZATION:
				return new AuthorizationToken(
						claims.getSubject(),
						claims.getIssuer(),
						claims.getIssuedAt(),
						claims.getExpiration(),
						claims.get("role", String.class),
						token
				);
			case REGISTRATION:
				return new RegistrationToken(
						claims.getSubject(),
						claims.getIssuer(),
						claims.getIssuedAt(),
						claims.getExpiration(),
						token
				);
			case EMAIL_UPDATE:
				return new EmailUpdateToken(
						claims.getSubject(),
						claims.getIssuer(),
						claims.getIssuedAt(),
						claims.getExpiration(),
						token,
						claims.get("old-email", String.class),
						claims.get("new-email", String.class));
			case PASSWORD_UPDATE:
				return new PasswordUpdateToken(
						claims.getSubject(),
						claims.getIssuer(),
						claims.getIssuedAt(),
						claims.getExpiration(),
						token);
			default:
				throw new InvalidTokenException("Unexpected type: " + type, token);
		}
	}
}
