package br.com.bonsai.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.bonsai.entidade.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
	
	public boolean tokenValido(String token) {
		try {
			
			Claims claims = this.obterClaims(token);
			Date dataExpiracao = claims.getExpiration();
			//convertendo data para localDateTIme
			LocalDateTime dateTime =  dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			return !LocalDateTime.now().isAfter(dateTime);
			
		} catch (Exception e) {
			return false;
		}
	}
	
	
	public String obterLoginUsuario(String token)  throws ExpiredJwtException{
		return (String) this.obterClaims(token).getSubject();
	}
	
	
	
}
