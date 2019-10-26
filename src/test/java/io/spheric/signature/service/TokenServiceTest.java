package io.spheric.signature.service;

import io.spheric.signature.AbstractTokenTest;
import io.spheric.signature.domain.Token;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TokenServiceTest extends AbstractTokenTest {

	@Test
	public void generate() {
		Date currentTime = new Date((new Date().getTime() / 1000) * 1000);
		authorizationTokenRequest.setNotBefore(new Date(700000));
		Token token = tokenService.generate(authorizationTokenRequest);

		assertEquals(authorizationTokenRequest.getOwner(), token.getOwner());
		assertEquals(authorizationTokenRequest.getType(), token.getType());
		assertEquals(authorizationTokenRequest.getAudience(), token.getAudience().orElse(null));
		assertEquals(authorizationTokenRequest.getData(), token.getData().orElse(null));
		assertEquals(new Date((authorizationTokenRequest.getExpiration().getTime() / 1000) * 1000), token.getExpirationDate().orElse(null));
		assertEquals(authorizationTokenRequest.getDescription(), token.getDescription().orElse(null));
		assertEquals(authorizationTokenRequest.getNotBefore(), token.getNotBeforeDate().orElse(null));
		assertEquals(issuer, token.getIssuer());
		assertEquals(currentTime, token.getIssueDate());
		assertNotNull(UUID.fromString(token.getTokenId()));
		assertNotNull(token.getJwt());
	}

	@Test
	public void adapt() {
		Token token = tokenService.generate(authorizationTokenRequest);
		assertEquals(token, tokenService.adapt(token.getJwt()));
	}

	@Test
	public void validate() {
		Token token = tokenService.generate(authorizationTokenRequest);
		tokenService.validate(token);
	}

	@Test
	public void resolve() {
		Token token = tokenService.generate(authorizationTokenRequest);
		Token resolvedToken = tokenService.resolve("Bearer " + token.getJwt());
		assertEquals(token, resolvedToken);
	}


}