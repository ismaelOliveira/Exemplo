package br.com.bonsai.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.bonsai.entidade.Bonsai;
import br.com.bonsai.service.BonsaiService;

@RestController
@RequestMapping("/api/bonsai")
public class BonsaiController {

	@Autowired
	private BonsaiService service;
	
	@GetMapping("/versaook")
	public String versao() {
		return "v1.0.0";
	}
	
	@PostMapping
	@ResponseStatus( code = HttpStatus.CREATED)
	public Bonsai salvar(@RequestBody  Bonsai bonsai) {
		return service.salvar(bonsai); 
	}
	
	@GetMapping("{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Bonsai consultarPorId(@PathVariable Integer id) {
		
		return service.consultarPorId(id);
		
		
		
	}
	
}
