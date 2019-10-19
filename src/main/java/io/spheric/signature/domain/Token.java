package io.spheric.signature.domain;

import java.util.Date;

public interface Token {
	String getUserId();

	String get();

	TokenType getType();

	String getIssuer();

	Date getIssueDate();

	Date getExpirationDate();
}
