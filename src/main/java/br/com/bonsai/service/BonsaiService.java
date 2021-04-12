package br.com.bonsai.service;

import br.com.bonsai.entidade.Bonsai;

public interface BonsaiService {

	
	Bonsai salvar(Bonsai bonsai);
	Bonsai alterar(Bonsai bonsai);
	Bonsai consultarPorId(Integer id);
	
	Bonsai consultarPorNome(String id);
	void deletar(Integer id);
	
	
}
