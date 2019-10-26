package io.spheric.signature.service;

import io.spheric.signature.AbstractTokenTest;
import io.spheric.signature.domain.Token;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TokenServiceTest extends AbstractTokenTest {

	@Test
	public void generate() {
		Date currentTime = new Date((new Date().getTime() / 1000) * 1000);
		authorizationTokenRequest.setNotBefore(new Date(700000));
		Token token = tokenService.generate(authorizationTokenRequest);

		assertEquals(authorizationTokenRequest.getOwner(), token.getOwner());
		assertEquals(authorizationTokenRequest.getType(), token.getType());
		assertEquals(authorizationTokenRequest.getAudience(), token.getAudience().get());
		assertEquals(authorizationTokenRequest.getData(), token.getData().get());
		assertEquals(new Date((authorizationTokenRequest.getExpiration().getTime() / 1000 ) * 1000), token.getExpirationDate().get());
		assertEquals(authorizationTokenRequest.getDescription(), token.getDescription().get());
		assertEquals(authorizationTokenRequest.getNotBefore(), token.getNotBeforeDate().get());
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
		try {
			tokenService.validate(token);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void resolve() {
		Token token = tokenService.generate(authorizationTokenRequest);
		HttpServletRequest request = mock(HttpServletRequest.class);

		when(request.getHeader("Authorization")).thenReturn("Bearer " + token.getJwt());

		try {
			Token resolvedToken = tokenService.resolve(request);
			assertEquals(token, resolvedToken);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}


}