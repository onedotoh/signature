package io.spheric.signature.domain.payload;

import io.spheric.signature.domain.TokenType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenRequest {
	@NotBlank
	private String owner;
	@NotBlank
	private String audience;
	@Future
	private Date expiration;
	@Future
	private Date notBefore;
	@NotNull
	private TokenType type;
	@NotBlank
	private String intention;
	private Map<String, String> data;

	private TokenRequest() {

	}
}
