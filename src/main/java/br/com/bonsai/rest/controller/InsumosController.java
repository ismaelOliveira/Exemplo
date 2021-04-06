package br.com.bonsai.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/insumo")
public class InsumosController {

	
	@GetMapping("/consultar")
	public String conusltar() {
		return "ok";
	}
}
