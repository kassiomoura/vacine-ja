package com.ufcg.psoft.vacinaja.exceptions;

public class UsuarioNaoAutorizadoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1930365915249441734L;
	
	public UsuarioNaoAutorizadoException(String msg) {
		super(msg);
	}

}
