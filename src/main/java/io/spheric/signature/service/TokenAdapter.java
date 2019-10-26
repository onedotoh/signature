package io.spheric.signature.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.spheric.signature.domain.Token;
import io.spheric.signature.domain.TokenClaim;
import io.spheric.signature.exception.InvalidTokenException;

import javax.crypto.SecretKey;
import java.util.EnumMap;
import java.util.Map;

class TokenAdapter {

	private TokenAdapter() {
	}

	static Token adapt(String jwt, Claims claims) {
		return new Token(adapt(claims), jwt);
	}

	static Token adapt(String jwt, SecretKey secretKey) {
		try {
			Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
			return adapt(jwt, claims);
		} catch (JwtException | IllegalArgumentException exception) {
			throw new InvalidTokenException("Expired or invalid JWT", jwt, exception);
		}
	}

	private static Map<TokenClaim, String> adapt(Claims claims) {
		Map<TokenClaim, String> result = new EnumMap<>(TokenClaim.class);
		claims.forEach((claim, value) -> {
			TokenClaim tokenClaim = TokenClaim.fromString(claim);
			result.put(tokenClaim, value.toString());
		});
		return result;
	}
}
