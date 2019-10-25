package io.spheric.signature.service;

import io.spheric.signature.SignatureApplicationTests;
import io.spheric.signature.domain.*;
import io.spheric.signature.exception.InvalidTokenException;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class TokenServiceTest extends SignatureApplicationTests {
	private final ObjectId userId = ObjectId.get();
	private Token token;
	@Autowired
	private TokenService tokenService;

	@Before
	public void setup() {
		token = tokenService.generate(userId.toString(), TokenType.AUTHORIZATION);
	}

	@Test
	public void getRegistrationToken() {
		tokenService.validate(token, TokenType.AUTHORIZATION);

		assertEquals(userId.toString(), token.getUserId());
	}

	@Test
	public void adaptToken() {
		String registrationToken = this.token.getToken();

		assertEquals(this.token, tokenService.adapt(registrationToken));
	}


	@Test(expected = InvalidTokenException.class)
	public void validateInvalidType() {
		tokenService.validate(token, TokenType.REFRESH);
	}


}