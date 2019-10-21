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
	private final String newEmail = "new@email.spheric";
	private final String oldEmail = "old@email.spheric";

	private final String role = "MEMBER";
	private final ObjectId userId = ObjectId.get();
	private AuthorizationToken authorizationToken;
	private EmailUpdateToken emailUpdateToken;
	private PasswordUpdateToken passwordUpdateToken;
	private RegistrationToken registrationToken;
	@Autowired
	private TokenService tokenService;

	@Before
	public void setup() {
		authorizationToken = tokenService.authorization(userId.toString(), role);
		registrationToken = tokenService.registration(userId.toString());
		passwordUpdateToken = tokenService.passwordUpdate(userId.toString());
		emailUpdateToken = tokenService.emailUpdate(userId.toString(), oldEmail, newEmail);
	}

	@Test
	public void getAuthorizationToken() {
		tokenService.validate(authorizationToken, TokenType.AUTHORIZATION);

		assertEquals(userId.toString(), authorizationToken.getUserId());
		assertEquals(role, authorizationToken.getRole());
	}

	@Test
	public void getRegistrationToken() {
		tokenService.validate(registrationToken, TokenType.REGISTRATION);

		assertEquals(userId.toString(), registrationToken.getUserId());
	}

	@Test
	public void getPasswordUpdateToken() {
		tokenService.validate(passwordUpdateToken, TokenType.PASSWORD_UPDATE);

		assertEquals(userId.toString(), passwordUpdateToken.getUserId());
	}

	@Test
	public void getEmailUpdateToken() {
		tokenService.validate(emailUpdateToken, TokenType.EMAIL_UPDATE);

		assertEquals(userId.toString(), emailUpdateToken.getUserId());
		assertEquals(newEmail, emailUpdateToken.getNewEmail());
	}

	@Test
	public void adaptToken() {
		String authorizationToken = this.authorizationToken.getToken();
		String registrationToken = this.registrationToken.getToken();
		String passwordUpdateToken = this.passwordUpdateToken.getToken();
		String emailUpdateToken = this.emailUpdateToken.getToken();

		assertEquals(this.authorizationToken, tokenService.adapt(authorizationToken));
		assertEquals(this.registrationToken, tokenService.adapt(registrationToken));
		assertEquals(this.passwordUpdateToken, tokenService.adapt(passwordUpdateToken));
		assertEquals(this.emailUpdateToken, tokenService.adapt(emailUpdateToken));
	}


	@Test(expected = InvalidTokenException.class)
	public void validateInvalidType() {
		tokenService.validate(registrationToken, TokenType.AUTHORIZATION);
	}


}