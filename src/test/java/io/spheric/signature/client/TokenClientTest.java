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
			.intention("authorization")
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
		assertEquals(tokenRequest.getOwner(), token.getOwner().get());
		assertEquals(tokenRequest.getType(), token.getType());
	}

	@Test
	public void adaptToken() {
		Token authorizationToken = tokenService.generate(tokenRequest);
		ResponseEntity<Token> response = tokenClient.adaptToken(authorizationToken.getToken());
		assertEquals(authorizationToken, response.getBody());

//		RegistrationToken registrationToken = tokenService.registration(ownerId);
//		response = tokenClient.adaptToken(registrationToken.getToken());
//		assertEquals(registrationToken, response.getBody());
//
//		PasswordUpdateToken passwordUpdateToken = tokenService.passwordUpdate(ownerId);
//		response = tokenClient.adaptToken(passwordUpdateToken.getToken());
//		assertEquals(passwordUpdateToken, response.getBody());
//
//		EmailUpdateToken emailUpdateToken = tokenService.emailUpdate(ownerId, "old@email.spheric", "new@email.spheric");
//		response = tokenClient.adaptToken(emailUpdateToken.getToken());
//		assertEquals(emailUpdateToken, response.getBody());
	}
}