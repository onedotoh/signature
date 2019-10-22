package io.spheric.signature.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.EqualsAndHashCode;

import java.util.Map;

@JsonTypeName("email-update")
@EqualsAndHashCode(callSuper = true)
public class EmailUpdateToken extends AbstractToken {
	private static final TokenType type = TokenType.EMAIL_UPDATE;

	public EmailUpdateToken(Map<TokenClaim, String> claims, String token) {
		super(claims, token);
	}

	public String getNewEmail() {
		return getClaims().get(TokenClaim.NEW_EMAIL);
	}

	public String getOldEmail() {
		return getClaims().get(TokenClaim.OLD_EMAIL);
	}

	@Override
	public TokenType getType() {
		return type;
	}
}
