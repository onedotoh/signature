package io.spheric.signature.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Date;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = AuthorizationToken.class, name = "authorization"),
		@JsonSubTypes.Type(value = PasswordUpdateToken.class, name = "password-update"),
		@JsonSubTypes.Type(value = EmailUpdateToken.class, name = "email-update"),
		@JsonSubTypes.Type(value = RegistrationToken.class, name = "registration")
})
public interface Token {
	String getUserId();

	String getToken();

	TokenType getTokenType();

	String getIssuer();

	Date getIssueDate();

	Date getExpirationDate();
}
