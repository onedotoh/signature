package io.spheric.signature.service;

import io.jsonwebtoken.Claims;

import java.util.HashMap;
import java.util.Map;

public class ClaimsAdapter {
	public static Map<String, String> adapt(Claims claims) {
		Map<String, String> result = new HashMap<>();
		claims.forEach((claim, value) -> result.put(claim, value.toString()));
		return result;
	}
}
