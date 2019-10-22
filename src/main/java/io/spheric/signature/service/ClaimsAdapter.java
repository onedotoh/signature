package io.spheric.signature.service;

import io.jsonwebtoken.Claims;
import io.spheric.signature.domain.TokenClaim;

import java.util.EnumMap;
import java.util.Map;

public class ClaimsAdapter {
	public static Map<TokenClaim, String> adapt(Claims claims) {
		Map<TokenClaim, String> result = new EnumMap<>(TokenClaim.class);
		claims.forEach((key, value) -> {
			for (TokenClaim tokenClaim : TokenClaim.values()) {
				Object claim = claims.get(tokenClaim.getValue());
				if(claim != null) {
					result.put(tokenClaim, claim.toString());
				}
			}
		});

		return result;
	}
}
