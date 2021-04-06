package br.com.bonsai.entidade;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bonsai")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bonsai {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nome")
	@NotEmpty(message = "{campo-nome-nao-vazio}")
	private String nome;
	
	@Column(name = "idade")
	@NotNull
	private Integer idade;
	
	@Column(name = "estilo")
	@NotNull
	private String estilo;
	
	@Column(name = "data_cadastro")
	private LocalDate dataCadastro;
	
	@PrePersist()
	public void prePersiste() {
		dataCadastro = LocalDate.now();
	}
	
}
