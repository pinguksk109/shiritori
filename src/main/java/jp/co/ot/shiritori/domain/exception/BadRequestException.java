package jp.co.ot.shiritori.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {
	
	private String userMessage;
	
	public BadRequestException(String message) {
		super(message);
	}
	
	public BadRequestException(String message, String userMessage) {
		super(message);
		this.userMessage = userMessage;
	}
	
	public String getUserMessage() {
		return userMessage;
	}

}
