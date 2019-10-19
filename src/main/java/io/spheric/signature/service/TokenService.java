package io.spheric.signature.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.spheric.signature.configuration.TokenConfiguration;
import io.spheric.signature.exception.InvalidTokenException;
import io.spheric.signature.domain.*;
import io.spheric.signature.domain.TokenType;
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

	public AuthorizationToken authorization(String userId, String role) {
		Claims claims = getClaims(userId, TokenType.AUTHORIZATION);
		claims.put("role", role);
		String token = build(claims);

		return new AuthorizationToken(userId, issuer, claims.getIssuedAt(), claims.getExpiration(), role, token);
	}

	public RegistrationToken registration(String userId) {
		Claims claims = getClaims(userId, TokenType.REGISTRATION);
		String token = build(claims);

		return new RegistrationToken(userId, issuer, claims.getIssuedAt(), claims.getExpiration(), token);
	}

	public EmailUpdateToken emailUpdate(String userId, String oldEmail, String newEmail) {
		Claims claims = getClaims(userId, TokenType.EMAIL_UPDATE);
		claims.put("old-email", oldEmail);
		claims.put("new-email", newEmail);
		String token = build(claims);

		return new EmailUpdateToken(userId, issuer, claims.getIssuedAt(), claims.getExpiration(), token, oldEmail, newEmail);
	}

	public PasswordUpdateToken passwordUpdate(String userId) {
		Claims claims = getClaims(userId, TokenType.PASSWORD_UPDATE);
		String token = build(claims);

		return new PasswordUpdateToken(userId, issuer, claims.getIssuedAt(), claims.getExpiration(), token);
	}

	public void validate(Token token, TokenType expectedType) {
		if (!expectedType.equals(token.getType())) {
			throw new InvalidTokenException(String.format("Type is not [%s]", expectedType), token.get());
		}

		if (!issuer.equals(token.getIssuer())) {
			throw new InvalidTokenException(String.format("Issuer is not [%s]", issuer), token.get());
		}

		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token.get());
		} catch (JwtException | IllegalArgumentException exception) {
			throw new InvalidTokenException("Expired or invalid JWT token", token.get(), exception);
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

		TokenType type = TokenType.valueOf(claims.get("type", String.class));

		switch (type) {
			case AUTHORIZATION:
				return new AuthorizationToken(claims.getSubject(), issuer,
						claims.getIssuedAt(), claims.getExpiration(), claims.get("role", String.class), token);
			case REGISTRATION:
				return new RegistrationToken(claims.getSubject(), issuer,
						claims.getIssuedAt(), claims.getExpiration(), token);
			case EMAIL_UPDATE:
				return new EmailUpdateToken(claims.getSubject(), issuer,
						claims.getIssuedAt(), claims.getExpiration(), token, claims.get("old-email", String.class), claims.get("new-email", String.class));
			case PASSWORD_UPDATE:
				return new PasswordUpdateToken(claims.getSubject(), issuer,
						claims.getIssuedAt(), claims.getExpiration(), token);
			default:
				throw new InvalidTokenException("Unexpected type: " + type, token);
		}
	}

	private Claims getClaims(String userId, TokenType tokenType) {
		Date now = new Date();
		Date expirationDate = new Date(now.getTime() + tokenType.getDuration());

		Claims claims = Jwts.claims()
				.setSubject(userId)
				.setIssuer(this.issuer)
				.setIssuedAt(now)
				.setExpiration(expirationDate);
		claims.put("type", tokenType);

		return claims;
	}

	public AuthorizationToken resolve(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			Token token = this.adapt(bearerToken.substring(7));

			if (!TokenType.AUTHORIZATION.equals(token.getType())) {
				throw new InvalidTokenException(String.format("Type is not [%s]", TokenType.AUTHORIZATION), token.get());
			}

			return (AuthorizationToken) token;
		}

		return null;
	}

	private String build(Claims claims) {
		return Jwts.builder()
				.setClaims(claims)
				.signWith(secretKey)
				.compact();
	}

}
