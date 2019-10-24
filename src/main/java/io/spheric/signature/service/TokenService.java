package io.spheric.signature.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.spheric.signature.configuration.TokenConfiguration;
import io.spheric.signature.exception.InvalidTokenException;
import io.spheric.signature.domain.*;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class TokenService {

	private final String issuer;
	private final SecretKey secretKey;

	public TokenService(TokenConfiguration tokenConfiguration) {
		this.issuer = tokenConfiguration.getIssuer();
		this.secretKey = tokenConfiguration.getSecret();
	}

	public Token generate(String userId, String type) {
		Claims claims = buildClaims(userId, type);
		String token = buildToken(claims);

		return TokenAdapter.adapt(claims, token);
	}

	public void validate(Token token, String expectedType) {
		if (!expectedType.equals(token.getType())) {
			throw new InvalidTokenException(String.format("Type is not [%s]", expectedType), token.getToken());
		}

		if (!issuer.equals(token.getIssuer())) {
			throw new InvalidTokenException(String.format("Issuer is not [%s]", issuer), token.getToken());
		}

		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token.getToken());
		} catch (JwtException | IllegalArgumentException exception) {
			throw new InvalidTokenException("Expired or invalid JWT token", token.getToken(), exception);
		}
	}

	public Token adapt(String token) {
		Claims claims;

		try {
			claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		} catch (JwtException | IllegalArgumentException exception) {
			throw new InvalidTokenException("Expired or invalid JWT token", token, exception);
		}

		if (!this.issuer.equals(claims.getIssuer())) {
			throw new InvalidTokenException(String.format("Issuer is not [%s]", issuer), token);
		}

		return TokenAdapter.adapt(claims, token);
	}

	private Claims buildClaims(String userId, String tokenType) {
		Date now = new Date();
//		Date expirationDate = new Date(now.getTime() + tokenType.getDuration());

		Claims claims = Jwts.claims()
				.setSubject(userId)
				.setIssuer(this.issuer)
				.setIssuedAt(now);
//				.setExpiration(expirationDate);
		claims.put("type", tokenType);

		return claims;
	}

	public Token resolve(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			Token token = this.adapt(bearerToken.substring(7));
			if (!"authorization".equals(token.getType())) {
				throw new InvalidTokenException("Type is not authorization", token.getToken());
			}
			return token;
		}
		return null;
	}

	private String buildToken(Claims claims) {
		return Jwts.builder()
				.setClaims(claims)
				.signWith(secretKey)
				.compact();
	}

}
