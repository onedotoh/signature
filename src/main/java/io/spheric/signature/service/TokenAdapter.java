package io.spheric.signature.service;

import io.jsonwebtoken.Claims;
import io.spheric.signature.domain.DefaultToken;
import io.spheric.signature.domain.Token;

public class TokenAdapter {

	public static Token adapt(Claims claims, String token) {
		return new DefaultToken(ClaimsAdapter.adapt(claims), token);
	}
}
