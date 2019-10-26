package io.spheric.signature.service;

import io.jsonwebtoken.Claims;
import io.spheric.signature.configuration.TokenConfiguration;
import io.spheric.signature.domain.Token;
import io.spheric.signature.domain.TokenType;
import io.spheric.signature.domain.payload.TokenRequest;
import io.spheric.signature.exception.InvalidTokenException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

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
		String jwt = TokenBuilder.buildToken(claims, secretKey);

		return TokenAdapter.adapt(jwt, claims);
	}

	public void validate(Token token) {
		TokenValidator.validate(token, secretKey, issuer);
	}

	public Token adapt(String jwt) {
		return TokenAdapter.adapt(jwt, secretKey);
	}

	public Token resolve(String authorizationHeader) {
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			Token token = this.adapt(authorizationHeader.substring(7));
			validate(token);
			if (!TokenType.AUTHORIZATION.equals(token.getType())) {
				throw new InvalidTokenException("Type is not authorization", token.getJwt());
			}
			return adapt(token.getJwt());
		}
		return null;
	}

}
