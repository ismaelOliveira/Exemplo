package br.com.bonsai.exception;

import lombok.Getter;

public class ApiErrors {

	@Getter
	private String error;
	@Getter
	private Integer codigo;

	public ApiErrors(String mensagemErro, Integer codigo) {
		
	}

	public ApiErrors(String mensagemErro, int value) {
		this.error = mensagemErro;
		this.codigo = value;
	}

}
