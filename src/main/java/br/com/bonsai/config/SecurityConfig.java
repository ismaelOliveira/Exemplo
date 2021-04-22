package br.com.bonsai.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.bonsai.security.jwt.JwtFilter;
import br.com.bonsai.security.jwt.JwtService;
import br.com.bonsai.service.impl.UsuarioServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Autowired
	private JwtService jwtService;
	
	// vai criptografar e descriptografar a senha dos usuario , o BCryptPasswordEncoder e do proprio spring
	@Bean
	public PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public OncePerRequestFilter jwtFilter() {
		return new JwtFilter(jwtService, usuarioService);
	}
	
	
	
	/**
	 * configurar a autenticação 
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioService)
			.passwordEncoder(passwordencoder());
	}
	
	/**
	 * configurar a autorização, quem tem acesso ao que.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/api/bonsai/**")
					.hasAnyRole("USER","ADMIN")
				.antMatchers("/api/insumo/**")
					.hasAnyRole("ADMIN")
				.antMatchers(HttpMethod.POST,"/api/usuarios/**")
					.permitAll()
				.anyRequest().authenticated()
			.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// indica qua não tem sessão para a api com isso toda requisição tem que ser enviado o token
			.and()
				.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);


		
	}
	
	

}
