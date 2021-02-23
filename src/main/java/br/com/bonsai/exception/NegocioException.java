package br.com.bonsai.exception;

public class NegocioException  extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8748705790009076346L;
	
	
	public NegocioException(String menssage) {
		super(menssage);
	}

}
