package io.spheric.signature.client;

import io.spheric.signature.AbstractTokenTest;
import io.spheric.signature.domain.Token;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TokenClientTest extends AbstractTokenTest {
	@Autowired
	private TokenClient tokenClient;

	@Test
	public void generate() {
		ResponseEntity<Token> response = tokenClient.generate(authorizationTokenRequest);
		Token token = response.getBody();
		assert token != null;
		assertEquals(authorizationTokenRequest.getOwner(), token.getOwner());
		assertEquals(authorizationTokenRequest.getType(), token.getType());
		assertEquals(authorizationTokenRequest.getAudience(), token.getAudience().orElse(null));
		assertEquals(authorizationTokenRequest.getData(), token.getData().orElse(null));
		assertEquals(new Date((authorizationTokenRequest.getExpiration().getTime() / 1000) * 1000), token.getExpirationDate().orElse(null));
		assertEquals(authorizationTokenRequest.getDescription(), token.getDescription().orElse(null));
		assertEquals(authorizationTokenRequest.getNotBefore(), token.getNotBeforeDate().orElse(null));
		assertEquals(issuer, token.getIssuer());
		assertNotNull(UUID.fromString(token.getTokenId()));
		assertNotNull(token.getJwt());
	}

	@Test
	public void adapt() {
		Token authorizationToken = tokenService.generate(authorizationTokenRequest);
		ResponseEntity<Token> response = tokenClient.adapt(authorizationToken.getJwt());
		assertEquals(authorizationToken, response.getBody());
	}

	@Test
	public void validate() {
		Token token = tokenClient.generate(authorizationTokenRequest).getBody();
		tokenClient.validate(token);
	}

	@Test
	public void resolve() {
		Token token = tokenClient.generate(authorizationTokenRequest).getBody();
		assert token != null;

		Token resolvedToken = tokenClient.resolve("Bearer " + token.getJwt()).getBody();
		assertEquals(token, resolvedToken);
	}
}