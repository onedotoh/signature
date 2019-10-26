package io.spheric.signature;

import io.spheric.signature.domain.TokenType;
import io.spheric.signature.domain.payload.TokenRequest;
import io.spheric.signature.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public abstract class AbstractTokenTest extends SignatureApplicationTests {
	private static final String audience = "audience";
	private static final Date expiration = new Date(new Date().getTime() + 360000);
	private static final String owner = "owner-id";
	private static final String data = "some-data";
	protected static final String issuer = "signature.spheric.io";
	protected TokenRequest authorizationTokenRequest;
	protected TokenRequest dataTokenRequest;
	protected TokenRequest refreshTokenRequest;
	protected TokenRequest updateTokenRequest;

	@Autowired
	protected TokenService tokenService;

	@BeforeEach
	public void init() {
		authorizationTokenRequest = TokenRequest.builder()
				.audience(audience)
				.owner(owner)
				.type(TokenType.AUTHORIZATION)
				.expiration(expiration)
				.description("authorization")
				.data(data)
				.build();

		dataTokenRequest = TokenRequest.builder()
				.audience(audience)
				.owner(owner)
				.type(TokenType.DATA)
				.expiration(expiration)
				.description("data")
				.data(data)
				.build();

		refreshTokenRequest = TokenRequest.builder()
				.audience(audience)
				.owner(owner)
				.type(TokenType.REFRESH)
				.expiration(expiration)
				.description("refresh")
				.data(data)
				.build();

		updateTokenRequest = TokenRequest.builder()
				.audience(audience)
				.owner(owner)
				.type(TokenType.UPDATE)
				.expiration(expiration)
				.description("update")
				.data(data)
				.build();
	}
}
