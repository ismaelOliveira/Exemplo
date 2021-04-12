package br.com.bonsai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bonsai.entidade.Bonsai;

public interface BonsaiRepository extends JpaRepository<Bonsai, Integer> {
	
	public List<Bonsai> findByNome(String nome);

}
