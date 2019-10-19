package io.spheric.signature.configuration;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class TokenConfiguration {
	@Value("${token.issuer:signature.spheric.io}")
	private String issuer;
	@Value("${token.secret}")
	private String secret;

	public String getIssuer() {
		return issuer;
	}

	public SecretKey getSecret() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}

}
