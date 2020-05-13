package br.com.jp.esloc.apilost.resources;

import org.modelmapper.ModelMapper;
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

import br.com.jp.esloc.apilost.domain.CompraDto;
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
	public CompraResponseDto save(@RequestBody CompraDto compraDto) {
		CompraResponseDto compra = this.comprasService.save(compraDto);
		return compra;//.getId();
	}
	@GetMapping("{id}")
	public CompraResponseDto getById(@PathVariable Integer id) {
		Compra dto = this.comprasService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra não encontrada."));
		ModelMapper modelMapper = new ModelMapper();
		CompraResponseDto Compradto = modelMapper.map(dto, CompraResponseDto.class);
		return Compradto;
	}
}
