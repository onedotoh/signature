package io.spheric.signature.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;
import java.util.Map;

@JsonDeserialize(as=DefaultToken.class)
public interface Token {
	Map<String, String> getClaims();

	String getUserId();

	String getToken();

	String getType();

	String getIssuer();

	Date getIssueDate();

	Date getExpirationDate();
}
