package br.com.bonsai.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.bonsai.auditoria.EntidadeComRevisao;
import br.com.bonsai.entidade.Bonsai;
import br.com.bonsai.repository.GenericRevisionRepository;
import br.com.bonsai.service.BonsaiService;

@RestController
@RequestMapping("/api/bonsai")
public class BonsaiController {

	@Autowired
	private BonsaiService service;
	
	@Autowired
    private GenericRevisionRepository genericRevisionRepository;
	
	@GetMapping("/versao")
	public String versao() {
		return "v1.0.0";
	}
	
	@PostMapping()
	@ResponseStatus( code = HttpStatus.CREATED)
	public Bonsai salvar(@RequestBody  Bonsai bonsai) {
		return service.salvar(bonsai); 
	}
	
	@GetMapping("{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Bonsai consultarPorId(@PathVariable Integer id) {		
		return service.consultarPorId(id);
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void deletarPorId(@PathVariable Integer id) {		
		 service.deletar(id);
	}
	
	@PutMapping
	@ResponseStatus(code = HttpStatus.OK)
	public Bonsai alterar(@RequestBody  Bonsai bonsai) {		
		return service.alterar(bonsai);
	}
	
	@GetMapping("/revisoes/{nome}")
    public ResponseEntity<List<EntidadeComRevisao<Bonsai>>> getRevisions(@PathVariable String nome) {
		Bonsai bonsai = service.consultarPorNome(nome);
        List<EntidadeComRevisao<Bonsai>> revisoes = genericRevisionRepository.listaRevisoes(bonsai.getId(),Bonsai.class);
        if(revisoes != null)
            return new ResponseEntity<List<EntidadeComRevisao<Bonsai>>>(revisoes, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

	
}
