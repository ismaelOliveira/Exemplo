package br.com.bonsai.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.bonsai.service.impl.UsuarioServiceImpl;

/**
 * 
 * @author ismael
 *filtro que vai interceptar uma requisição e colocar o usuario dentro do contexto do spring securiy
 */
public class JwtFilter extends OncePerRequestFilter {

	private JwtService jwtService;
	private UsuarioServiceImpl usuarioService;
	
	public  JwtFilter(JwtService serviceJwt, UsuarioServiceImpl serviceUsuario) {
		this.jwtService = serviceJwt;
		this.usuarioService = serviceUsuario;
	}
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		String authorization = request.getHeader("Authorization");
		
		if(authorization != null && authorization.startsWith("Bearer")) {
			String token  = authorization.split(" ")[1];
			boolean isValid = jwtService.tokenValido(token);
			if(isValid) {
				String loginUsuario = jwtService.obterLoginUsuario(token);
				UserDetails usuario = usuarioService.loadUserByUsername(loginUsuario);
				// indicar que se trata de uma autenticação de uma aplicação web
				UsernamePasswordAuthenticationToken user = new 
						UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
				user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				//injeta o usuario dentro do contexto do spring security
				SecurityContextHolder.getContext().setAuthentication(user);
			}
		}
		
		filterChain.doFilter(request, response);
		
		
		
	}

}
