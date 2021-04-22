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

import br.com.bonsai.exception.NegocioException;
import br.com.bonsai.service.impl.UsuarioServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;

/**
 * 
 * @author ismael filtro que vai interceptar uma requisição e colocar o usuario
 *         dentro do contexto do spring securiy
 */
public class JwtFilter extends OncePerRequestFilter {

	private JwtService jwtService;
	private UsuarioServiceImpl usuarioService;

	public JwtFilter(JwtService serviceJwt, UsuarioServiceImpl serviceUsuario) {
		this.jwtService = serviceJwt;
		this.usuarioService = serviceUsuario;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorization = request.getHeader("Authorization");

		if (authorization != null && authorization.startsWith("Bearer")) {

			try {
				String token = authorization.split(" ")[1];
				jwtService.tokenValido(token);

				String loginUsuario = jwtService.obterLoginUsuario(token);
				UserDetails usuario = usuarioService.loadUserByUsername(loginUsuario);
				// indicar que se trata de uma autenticação de uma aplicação web
				UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(usuario, null,
						usuario.getAuthorities());
				user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// injeta o usuario dentro do contexto do spring security
				SecurityContextHolder.getContext().setAuthentication(user);

			} catch (ExpiredJwtException ex) {
				String isRefreshToken = request.getHeader("isRefreshToken");
				String requestURL = request.getRequestURL().toString();
				// allow for Refresh Token creation if following conditions are true.
				if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshtoken")) {
					allowForRefreshToken(ex, request);
				} else {
					request.setAttribute("exception", ex);
				}

			}

		}

		filterChain.doFilter(request, response);
	}

	private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

		// create a UsernamePasswordAuthenticationToken with null values.
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				null, null, null);
		// After setting the Authentication in the context, we specify
		// that the current user is authenticated. So it passes the
		// Spring Security Configurations successfully.
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		// Set the claims so that in controller we will be using it to create
		// new JWT
		request.setAttribute("claims", ex.getClaims());

	}
	
	

}
