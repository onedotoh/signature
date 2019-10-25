package io.spheric.signature.client;

import io.spheric.signature.SignatureApplicationTests;
import io.spheric.signature.domain.*;
import io.spheric.signature.service.TokenService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

public class TokenClientTest extends SignatureApplicationTests {
	@Autowired
	private TokenService tokenService;

	@Autowired
	private TokenClient tokenClient;

	@Test
	public void generateAuthorizationToken() {
		String ownerId = "owner-id";
		Token authorizationToken = tokenService.generate(ownerId, TokenType.AUTHORIZATION);
		ResponseEntity<String> response = tokenClient.generate(ownerId, TokenType.AUTHORIZATION);
		assertEquals(authorizationToken.getToken(), response.getBody());
	}

	@Test
	public void adaptToken() {
		String ownerId = "owner-id";
		Token authorizationToken = tokenService.generate(ownerId, TokenType.AUTHORIZATION);
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