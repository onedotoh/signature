package io.spheric.signature.domain.payload;

import io.spheric.signature.domain.TokenType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;

@Data
@Builder
public class TokenRequest {
	@NotBlank
	private final String owner;
	@NotBlank
	private final String audience;
	private Date expiration;
	private Date notBefore;
	@NotNull
	private final TokenType type;
	@NotBlank
	private final String intention;
	private HashMap<String, String> data;
}
