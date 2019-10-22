package io.spheric.signature.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.EqualsAndHashCode;

import java.util.Map;

@JsonTypeName("password-update")
@EqualsAndHashCode(callSuper = true)
public class PasswordUpdateToken extends AbstractToken {
	private static final TokenType type = TokenType.PASSWORD_UPDATE;

	public PasswordUpdateToken(Map<TokenClaim, String> claims, String token) {
		super(claims, token);
	}

	@Override
	public TokenType getType() {
		return type;
	}
}
