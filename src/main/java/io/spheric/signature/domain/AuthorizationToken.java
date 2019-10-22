package io.spheric.signature.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.EqualsAndHashCode;

import java.beans.ConstructorProperties;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@JsonTypeName("authorization")
public class AuthorizationToken extends AbstractToken {
	@JsonIgnore
	private static final TokenType type = TokenType.AUTHORIZATION;

	@JsonCreator
	@ConstructorProperties({"claims", "token"})
	public AuthorizationToken(Map<TokenClaim, String> claims, String token) {
		super(claims, token);
	}

	@JsonIgnore
	public String getRole() {
		return getClaims().get(TokenClaim.ROLE);
	}

	@Override
	@JsonIgnore
	public TokenType getType() {
		return type;
	}
}
