package jp.co.ot.shiritori.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends Exception {

	private String userMessage;
	
	public UnprocessableEntityException(String message) {
		super(message);
	}
	
	public UnprocessableEntityException(String message, String userMessage) {
		super(message);
		this.userMessage = userMessage;		
	}
	
	public String getUserMessage() {
		return userMessage;
	}
	
}
