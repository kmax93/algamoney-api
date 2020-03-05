package com.algamoney.algamoneyapi.usuario.exception;

public class UsuarioNaoEcontradoException extends Exception {

	private static final long serialVersionUID = 4753233753408519249L;
	
	public UsuarioNaoEcontradoException(String mensagem) {
		super(mensagem);
	}

}