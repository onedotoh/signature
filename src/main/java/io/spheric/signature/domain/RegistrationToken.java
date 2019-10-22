package io.spheric.signature.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.EqualsAndHashCode;

import java.util.Map;

@JsonTypeName("registration")
@EqualsAndHashCode(callSuper = true)
public class RegistrationToken extends AbstractToken {

	private static final TokenType type = TokenType.REGISTRATION;

	public RegistrationToken(Map<TokenClaim, String> claims, String token) {
		super(claims, token);
	}

	@Override
	public TokenType getType() {
		return type;
	}
}
