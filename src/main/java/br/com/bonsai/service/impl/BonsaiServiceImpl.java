package br.com.bonsai.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bonsai.config.MensagemSource;
import br.com.bonsai.entidade.Bonsai;
import br.com.bonsai.exception.NegocioException;
import br.com.bonsai.repository.BonsaiRepository;
import br.com.bonsai.service.BonsaiService;

@Service
public class BonsaiServiceImpl implements BonsaiService {

	@Autowired
	private MensagemSource mensage;
	
	@Autowired
	private  BonsaiRepository bonsaiRepositorio;
	
	@Override
	public Bonsai salvar(Bonsai bonsai) {
		return bonsaiRepositorio.save(bonsai);
	}

	@Override
	public Bonsai consultarPorId(Integer id) {
		// TODO Auto-generated method stub
		return bonsaiRepositorio.findById(id).orElseThrow(() -> new NegocioException(mensage.messageSource().getMessage("bonsai-nao-encontrado", null, null)));
	}

	
	
}
