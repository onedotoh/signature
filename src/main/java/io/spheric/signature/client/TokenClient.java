package io.spheric.signature.client;

import io.spheric.signature.configuration.ApiEndpoints;
import io.spheric.signature.domain.Token;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${server.url}", value = "signature-token-client")
public interface TokenClient {
	@GetMapping(value = ApiEndpoints.AUTHORIZATION)
	ResponseEntity<String> generateAuthorizationToken(@RequestParam(name = "ownerId") String ownerId, @RequestParam(name = "role") String role);

	@GetMapping(value = ApiEndpoints.ADAPT)
	ResponseEntity<Token> adaptToken(@RequestParam(name = "token") String token);
}
