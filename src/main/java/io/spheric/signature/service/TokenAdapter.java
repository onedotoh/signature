package io.spheric.signature.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.spheric.signature.domain.DefaultToken;
import io.spheric.signature.domain.Token;
import io.spheric.signature.exception.InvalidTokenException;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

class TokenAdapter {

	private TokenAdapter() {
	}

	static Token adapt(String token, Claims claims) {
		return new DefaultToken(adapt(claims), token);
	}

	static Token adapt(String token, SecretKey secretKey) {
		try {
			Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
			return adapt(token, claims);
		} catch (JwtException | IllegalArgumentException exception) {
			throw new InvalidTokenException("Expired or invalid JWT token", token, exception);
		}
	}

	private static Map<String, String> adapt(Claims claims) {
		Map<String, String> result = new HashMap<>();
		claims.forEach((claim, value) -> result.put(claim, value.toString()));
		return result;
	}
}
