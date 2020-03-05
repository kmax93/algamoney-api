package com.algamoney.algamoneyapi.usuario.service;

import org.springframework.stereotype.Service;

import com.algamoney.algamoneyapi.usuario.exception.UsuarioNaoEcontradoException;
import com.algamoney.algamoneyapi.usuario.model.Usuario;
import com.algamoney.algamoneyapi.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	public Usuario findByEmail(String email) throws UsuarioNaoEcontradoException {
		Usuario usuario = usuarioRepository.findByEmail(email)
							.orElseThrow( () -> new UsuarioNaoEcontradoException("Usuario com email: " + email + " não encontrado."));
		
		return usuario;
	}
	
	public Usuario findByLogin(String login) throws UsuarioNaoEcontradoException {
		Usuario usuario = usuarioRepository.findByLogin(login)
							.orElseThrow( () -> new UsuarioNaoEcontradoException("Usuario com login: " + login + " não encontrado."));
		
		return usuario;
	}

}