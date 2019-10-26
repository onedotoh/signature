package io.spheric.signature.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.spheric.signature.domain.TokenClaim;
import io.spheric.signature.domain.payload.TokenRequest;

import javax.crypto.SecretKey;
import java.util.Date;

class TokenBuilder {

	private TokenBuilder() {
	}

	static Claims buildClaims(TokenRequest request, String issuer) {
		Claims claims = Jwts.claims()
				.setSubject(request.getOwner())
				.setIssuer(issuer)
				.setIssuedAt(new Date())
				.setExpiration(request.getExpiration())
				.setNotBefore(request.getNotBefore())
				.setAudience(request.getAudience());

		claims.put(TokenClaim.TYPE.getClaim(), request.getType());
		claims.put(TokenClaim.DESCRIPTION.getClaim(), request.getIntention());
		claims.put(TokenClaim.DATA.getClaim(), request.getData());

		return claims;
	}


	static String buildToken(Claims claims, SecretKey secretKey) {
		return Jwts.builder()
				.setClaims(claims)
				.signWith(secretKey)
				.compact();
	}
}
