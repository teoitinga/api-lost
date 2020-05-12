package br.com.jp.esloc.apilost.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.jp.esloc.apilost.domain.CompraDto;
import br.com.jp.esloc.apilost.models.Compra;
import br.com.jp.esloc.apilost.repositories.PersonaRepository;
import br.com.jp.esloc.apilost.services.CompraService;

@RestController
@RequestMapping("/api/v1/compras")
public class CompraResource {
	@Autowired
	private CompraService comprasService;
	@Autowired
	private PersonaRepository personaRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Integer save(@RequestBody CompraDto compraDto) {
		Compra compra = this.comprasService.save(compraDto);
		return compra.getId();
	}
}
