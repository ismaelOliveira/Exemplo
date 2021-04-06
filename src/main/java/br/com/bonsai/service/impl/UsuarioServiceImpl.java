package br.com.bonsai.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bonsai.entidade.Usuario;
import br.com.bonsai.exception.AutenticacaoException;
import br.com.bonsai.exception.SenhaInvalidaException;
import br.com.bonsai.repository.UsuarioRepository;
import br.com.bonsai.rest.controller.dto.CredenciaisDTO;

/**
 * 
 * @author ismael
 * Classe implemenmta uma interface do spring security para essa parte de usuarios
 */
@Service
public class UsuarioServiceImpl implements UserDetailsService{

	@Autowired
	private PasswordEncoder encode;
	
	@Autowired
	private UsuarioRepository usuarioRepositry;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepositry.findByLogin(username)
			.orElseThrow(()-> new AutenticacaoException("Usuario ou senha invalido."));
		
		String [] roles = usuario.isAdim() ? new String [] {"ADMIN","USER"} : new String [] {"USER"};
		
		
		return User
				.builder()
				.username(usuario.getLogin())
				.password(usuario.getSenha())
				.roles(roles)
				.build();
		
	}

	@Transactional
	public Usuario salvar(Usuario usuario) {
		return usuarioRepositry.save(usuario);
		
	}

	public UserDetails autenticar(Usuario usuario) {
		UserDetails user = loadUserByUsername(usuario.getLogin());
		//compara a senha
		boolean senhaOk  = encode.matches(usuario.getSenha(), user.getPassword());
		if(senhaOk) {
			return user;
		}
		throw new AutenticacaoException("Usuario ou senha invalido.");
	}

}
