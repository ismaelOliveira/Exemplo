package br.com.bonsai.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.bonsai.exception.ApiErrors;
import br.com.bonsai.exception.NegocioException;

@RestControllerAdvice
public class ApplicationControllerAdvice {

	@ExceptionHandler(NegocioException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleRegraNegocioException(NegocioException ex) {
		String mensagemErro = ex.getMessage();
		return new ApiErrors(mensagemErro,HttpStatus.BAD_REQUEST.value());
	}
	
}
