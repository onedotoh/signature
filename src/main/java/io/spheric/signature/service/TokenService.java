package io.spheric.signature.service;

import io.jsonwebtoken.Claims;
import io.spheric.signature.configuration.TokenConfiguration;
import io.spheric.signature.domain.Token;
import io.spheric.signature.domain.TokenType;
import io.spheric.signature.domain.payload.TokenRequest;
import io.spheric.signature.exception.InvalidTokenException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

@Service
public class TokenService {

	private final String issuer;
	private final SecretKey secretKey;

	public TokenService(TokenConfiguration tokenConfiguration) {
		this.issuer = tokenConfiguration.getIssuer();
		this.secretKey = tokenConfiguration.getSecret();
	}

	public Token generate(TokenRequest request) {
		Claims claims = TokenBuilder.buildClaims(request, issuer);
		String token = TokenBuilder.buildToken(claims, secretKey);

		return TokenAdapter.adapt(token, claims);
	}

	public void validate(Token token) {
		TokenValidator.validate(token, secretKey, issuer);
	}

	public Token adapt(String token) {
		return TokenAdapter.adapt(token, secretKey);
	}

	public Token resolve(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			Token token = this.adapt(bearerToken.substring(7));
			validate(token);
			if (!TokenType.AUTHORIZATION.equals(token.getType())) {
				throw new InvalidTokenException("Type is not authorization", token.getToken());
			}
			return adapt(token.getToken());
		}
		return null;
	}

}
