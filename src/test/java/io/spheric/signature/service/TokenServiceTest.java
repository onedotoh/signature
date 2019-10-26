package io.spheric.signature.service;

import io.spheric.signature.SignatureApplicationTests;
import io.spheric.signature.domain.Token;
import io.spheric.signature.domain.TokenType;
import io.spheric.signature.domain.payload.TokenRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TokenServiceTest extends SignatureApplicationTests {
	TokenRequest tokenRequest = TokenRequest.builder()
			.audience("audience")
			.owner("owner-id")
			.type(TokenType.AUTHORIZATION)
			.expiration(new Date(new Date().getTime() + 360000))
			.intention("authorization")
			.build();

	private Token token;
	@Autowired
	private TokenService tokenService;

	@Before
	public void setup() {
		token = tokenService.generate(tokenRequest);
	}

	@Test
	public void getRegistrationToken() {
		tokenService.validate(token);

		assertEquals(tokenRequest.getOwner(), token.getOwner());
	}

	@Test
	public void adaptToken() {
		String registrationToken = this.token.getJwt();

		assertEquals(this.token, tokenService.adapt(registrationToken));
	}


}