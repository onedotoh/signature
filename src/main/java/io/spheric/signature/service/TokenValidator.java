package io.spheric.signature.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.spheric.signature.domain.Token;
import io.spheric.signature.domain.TokenClaim;
import io.spheric.signature.exception.InvalidTokenException;

import javax.crypto.SecretKey;

class TokenValidator {

	private TokenValidator() {
	}

	static void validate(Token token, SecretKey secretKey, String issuer) {
		try {
			Jwts.parser()
					.requireIssuer(issuer)
					.requireSubject(token.getOwner())
					.requireIssuedAt(token.getIssueDate())
					.require(TokenClaim.TYPE.getClaim(), token.getType().name())
					.setSigningKey(secretKey).parseClaimsJws(token.getJwt());
		} catch (JwtException | IllegalArgumentException exception) {
			throw new InvalidTokenException("Expired or invalid JWT token", token.getJwt(), exception);
		}
	}
}
