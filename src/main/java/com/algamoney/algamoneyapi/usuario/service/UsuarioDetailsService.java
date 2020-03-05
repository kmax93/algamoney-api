package com.algamoney.algamoneyapi.usuario.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.algamoney.algamoneyapi.usuario.exception.UsuarioNaoEcontradoException;
import com.algamoney.algamoneyapi.usuario.model.Usuario;

@Service
public class UsuarioDetailsService implements UserDetailsService {

	private UsuarioService usuarioService;
	
	public UsuarioDetailsService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		try {
			
			Usuario usuario = usuarioService.findByLogin(login);
			
			return new User(login, usuario.getSenha(), getPermissoes(usuario));
			
		} catch (UsuarioNaoEcontradoException e) {
			throw new UsernameNotFoundException(login, e);
		}

	}

	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		usuario.getPermissoes().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getNome().toUpperCase())));
		return authorities;
	}

}