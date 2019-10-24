package io.spheric.signature.controller;

import io.spheric.signature.client.TokenClient;
import io.spheric.signature.domain.Token;
import io.spheric.signature.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController implements TokenClient {

	private final TokenService tokenService;

	public TokenController(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	public ResponseEntity<String> generate(@PathVariable(name = "ownerId") String ownerId, @PathVariable(name = "type") String type) {
		Token authorizationToken = this.tokenService.generate(ownerId, type);
		return ResponseEntity.ok(authorizationToken.getToken());
	}

	public ResponseEntity<Token> adaptToken(String token) {
		Token adaptedToken = this.tokenService.adapt(token);
		return ResponseEntity.status(HttpStatus.OK).body(adaptedToken);
	}
}
