package io.spheric.signature.service;

import io.jsonwebtoken.Claims;
import io.spheric.signature.domain.*;
import io.spheric.signature.exception.InvalidTokenException;

public class TokenAdapter {

	public static Token adapt(Claims claims, String token) {
		TokenType type = TokenType.valueOf(claims.get("type", String.class));

		switch (type) {
			case AUTHORIZATION:
				return new AuthorizationToken(ClaimsAdapter.adapt(claims), token);
			case REGISTRATION:
				return new RegistrationToken(ClaimsAdapter.adapt(claims), token);
			case EMAIL_UPDATE:
				return new EmailUpdateToken(ClaimsAdapter.adapt(claims), token);
			case PASSWORD_UPDATE:
				return new PasswordUpdateToken(ClaimsAdapter.adapt(claims), token);
			default:
				throw new InvalidTokenException("Unexpected type: " + type, token);
		}
	}
}
