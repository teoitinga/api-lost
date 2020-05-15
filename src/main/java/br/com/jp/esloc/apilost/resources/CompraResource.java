package br.com.jp.esloc.apilost.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.jp.esloc.apilost.domain.CompraPostDto;
import br.com.jp.esloc.apilost.domain.CompraResponseDto;
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
	public CompraResponseDto save(@RequestBody CompraPostDto compraDto) {
		CompraResponseDto compra = this.comprasService.save(compraDto);
		return compra;//.getId();
	}
	@GetMapping("{id}")
	public CompraResponseDto getById(@PathVariable Integer id) {
		return this.comprasService.getCompra(id)
				.map(c -> toResponseDto(c))
				.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Não há registros desta compra."))
				; 
	}
	@GetMapping("cliente/{id}")
	public List<CompraResponseDto> getByClienteId(@PathVariable Integer id) {
		return this.comprasService.getCompraPorCliente(id)
				.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Não há registros de compras para este cliente."))
				; 
	}
	private CompraResponseDto toResponseDto(Compra c) {

		return this.comprasService.toResponseDto(c);
	}
	
	/*
	 * private List<CompraResponseDto> toListrResponseDto(List<Compra> compras) {
	 * 
	 * return this.comprasService.toListResponseDto(compras); }
	 */
}
