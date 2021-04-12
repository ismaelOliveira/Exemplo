package br.com.bonsai.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(Exception.class)
	public ResponseEntity handleException(Exception e) {
		e.printStackTrace();
		String mensagemErro = e.getMessage();
		return 	new ResponseEntity(new ApiErrors(mensagemErro,HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
	}

}
