package jp.co.ot.shiritori.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends Exception{
	
	public ConflictException(String message) {
		super(message);
	}
	
}
