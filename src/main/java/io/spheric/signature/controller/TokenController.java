package io.spheric.signature.controller;

import io.spheric.signature.client.TokenClient;
import io.spheric.signature.domain.Token;
import io.spheric.signature.domain.payload.TokenRequest;
import io.spheric.signature.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TokenController implements TokenClient {

	private final TokenService tokenService;

	public TokenController(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	public ResponseEntity<Token> generate(@RequestBody @Valid TokenRequest request) {
		Token authorizationToken = this.tokenService.generate(request);
		return ResponseEntity.ok(authorizationToken);
	}

	public ResponseEntity<Token> adapt(String jwt) {
		Token adaptedToken = this.tokenService.adapt(jwt);
		return ResponseEntity.status(HttpStatus.OK).body(adaptedToken);
	}

	public ResponseEntity<Token> resolve(String authorizationHeader) {
		Token token = tokenService.resolve(authorizationHeader);
		return ResponseEntity.ok(token);
	}

	public ResponseEntity<Void> validate(Token token) {
		tokenService.validate(token);
		return ResponseEntity.ok().build();
	}
}
