package br.com.bonsai.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.bonsai.exception.ApiErrors;
import br.com.bonsai.exception.AutenticacaoException;
import br.com.bonsai.exception.NegocioException;
import br.com.bonsai.exception.SenhaInvalidaException;

@RestControllerAdvice
public class ApplicationControllerAdvice {

	@ExceptionHandler(NegocioException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleRegraNegocioException(NegocioException ex) {
		String mensagemErro = ex.getMessage();
		return new ApiErrors(mensagemErro,HttpStatus.BAD_REQUEST.value());
	}
	
	
	@ExceptionHandler(AutenticacaoException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ApiErrors handleLoginException(AutenticacaoException ex) {
		String msgErro = ex.getMessage();
		return new ApiErrors(msgErro,HttpStatus.UNAUTHORIZED.value());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleRegraNegocioException(MethodArgumentNotValidException ex) {
		
		
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		 fieldErrors.forEach(e -> {
		        String mensagem = e.getField() ;
		 });

		String mensagemErro = ex.getMessage();
		return new ApiErrors(mensagemErro,HttpStatus.BAD_REQUEST.value());
	}
	
}
