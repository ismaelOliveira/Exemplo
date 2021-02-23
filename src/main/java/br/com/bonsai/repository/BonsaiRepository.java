package br.com.bonsai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bonsai.entidade.Bonsai;

public interface BonsaiRepository extends JpaRepository<Bonsai, Integer> {

}
