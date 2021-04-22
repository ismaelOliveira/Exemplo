package br.com.bonsai.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import br.com.bonsai.entidade.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
/**
 * 
 * @author ismael
 * Classe que faz a codificação e decodificação do token
 */
@Service
public class JwtService {

	@Value("${security.jwt.expiracao}")
	private String expiracao;
	
	@Value("${security.jwt.chave}")
	private String chaveAssinatura;
	
	@Value("${security.jwt.expiracao.refresh}")
	private String refreshExpirationDateInMs;
	
	public String gerarToken(Usuario usuario) {
		
		long expiracaoLong = Long.valueOf(expiracao);
		LocalDateTime  dataHoraExpiracao = LocalDateTime.now().plusMinutes(expiracaoLong);
		//criado data do tipo Date pois o jwt so aceita esse tipo para a expiração
		Date dateExpiracao = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());
		//podemos utilizar o .setClaims() para adiconar mais informações como email, roles...  no token quee gerado 
		return Jwts
			.builder()
			.setSubject(usuario.getLogin())
			.setExpiration(dateExpiracao)
			.signWith(SignatureAlgorithm.HS512, chaveAssinatura)
			.compact();
				
	}
	
	private Claims obterClaims(String token) throws ExpiredJwtException {
		
		return Jwts.parser()
				.setSigningKey(chaveAssinatura)
				.parseClaimsJws(token)
				.getBody();
				
	}
	
	public boolean tokenValido(String token) throws BadCredentialsException, ExpiredJwtException {
		
			try {
				Jws<Claims> claims = Jwts.parser().setSigningKey(chaveAssinatura).parseClaimsJws(token);
				return true;
			} catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
				throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
			} catch (ExpiredJwtException ex) {
				throw ex;
			}
			
	}
	
	
	public String obterLoginUsuario(String token)  throws ExpiredJwtException{
		return (String) this.obterClaims(token).getSubject();
	}
	
	public String doGenerateRefreshToken(Map<String, Object> claims, String subject) {
		long expiracaoLong = Long.valueOf(refreshExpirationDateInMs);
		LocalDateTime  dataHoraExpiracao = LocalDateTime.now().plusMinutes(expiracaoLong);
		//criado data do tipo Date pois o jwt so aceita esse tipo para a expiração
		Date dateExpiracao = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(dateExpiracao)
				.signWith(SignatureAlgorithm.HS512, chaveAssinatura).compact();

	}
	
	
	
}
