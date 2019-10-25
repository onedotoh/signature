package io.spheric.signature.client;

import io.spheric.signature.configuration.ApiEndpoints;
import io.spheric.signature.domain.Token;
import io.spheric.signature.domain.payload.TokenRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${server.url}", value = "signature-token-client")
public interface TokenClient {
	@PostMapping(value = ApiEndpoints.AUTHORIZATION)
	ResponseEntity<Token> generate(@RequestBody TokenRequest request);

	@GetMapping(value = ApiEndpoints.ADAPT)
	ResponseEntity<Token> adaptToken(@RequestParam(name = "token") String token);
}
