package io.spheric.signature.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@JsonDeserialize(as = DefaultToken.class)
public interface Token {
	Map<String, String> getClaims();

	@JsonIgnore
	Optional<String> getOwner();

	String getToken();

	@JsonIgnore
	TokenType getType();

	@JsonIgnore
	Optional<String> getIssuer();

	@JsonIgnore
	Date getIssueDate();

	@JsonIgnore
	Optional<Date> getExpirationDate();

	@JsonIgnore
	Optional<String> getTokenId();

	@JsonIgnore
	Optional<Date> getNotBeforeDate();

	@JsonIgnore
	Optional<String> getAudience();

	@JsonIgnore
	Optional<String> getIntention();

	@JsonIgnore
	Optional<String> getData();
}
