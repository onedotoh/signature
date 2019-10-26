package io.spheric.signature.client;

import io.spheric.signature.SignatureApplicationTests;
import io.spheric.signature.domain.Token;
import io.spheric.signature.domain.TokenType;
import io.spheric.signature.domain.payload.TokenRequest;
import io.spheric.signature.service.TokenService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TokenClientTest extends SignatureApplicationTests {
	private TokenRequest tokenRequest = TokenRequest.builder()
			.audience("audience")
			.owner("owner-id")
			.type(TokenType.AUTHORIZATION)
			.expiration(new Date(new Date().getTime() + 360000))
			.description("authorization")
			.build();
	;
	@Autowired
	private TokenService tokenService;

	@Autowired
	private TokenClient tokenClient;

	@Test
	public void generateToken() {
		ResponseEntity<Token> response = tokenClient.generate(tokenRequest);
		Token token = response.getBody();
		assert token != null;
		assertEquals(tokenRequest.getOwner(), token.getOwner());
		assertEquals(tokenRequest.getType(), token.getType());
	}

	@Test
	public void adaptToken() {
		Token authorizationToken = tokenService.generate(tokenRequest);
		ResponseEntity<Token> response = tokenClient.adaptToken(authorizationToken.getJwt());
		assertEquals(authorizationToken, response.getBody());
	}
}