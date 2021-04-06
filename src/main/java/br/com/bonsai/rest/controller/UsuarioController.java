package br.com.bonsai.rest.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.bonsai.entidade.Usuario;
import br.com.bonsai.exception.NegocioException;
import br.com.bonsai.exception.SenhaInvalidaException;
import br.com.bonsai.rest.controller.dto.CredenciaisDTO;
import br.com.bonsai.rest.controller.dto.TokenDTO;
import br.com.bonsai.security.jwt.JwtService;
import br.com.bonsai.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
	
	private final UsuarioServiceImpl usuarioServiceImpl;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public  Usuario salvar(@RequestBody @Valid Usuario usuario) {
		String senhaCript = passwordEncoder.encode(usuario.getSenha());
		usuario.setSenha(senhaCript);
		return usuarioServiceImpl.salvar(usuario);
	}
	
	@PostMapping("/autenticar")
	public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais) {
		
			Usuario usuario  = Usuario.builder()
					.login(credenciais.getLogin())
					.senha(credenciais.getSenha())
					.build();
			
			UserDetails usuarioAutenticado = usuarioServiceImpl.autenticar(usuario);
			String token =jwtService.gerarToken(usuario);
			
			return new TokenDTO(usuarioAutenticado.getUsername(),token);
		
	}

}
