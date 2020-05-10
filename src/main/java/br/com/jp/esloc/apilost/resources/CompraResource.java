package br.com.jp.esloc.apilost.resources;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jp.esloc.apilost.domain.CompraDto;
import br.com.jp.esloc.apilost.models.Compra;
import br.com.jp.esloc.apilost.responce.Response;
import br.com.jp.esloc.apilost.services.CompraService;
import br.com.jp.esloc.apilost.services.DetalheCompraService;
import br.com.jp.esloc.apilost.services.PersonaService;

@RestController
@RequestMapping("/api/v1/compras")
public class CompraResource {

	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private CompraService compraService;
	
	@Autowired
	private DetalheCompraService detalheCompraService;
	
	@GetMapping()
	public ResponseEntity<Response<Page<CompraDto>>> findAll(@RequestParam(value="page", defaultValue = "0") Integer page,
			@RequestParam(value="size", defaultValue = "5") Integer size) {
		
		Response<Page<CompraDto>> response = new Response<Page<CompraDto>>();
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<Compra> compras = this.compraService.findAll(PageRequest.of(page, size));

		Page<CompraDto> itemDto = new PageImpl(compras.stream().map(CompraDto::create).collect(Collectors.toList()));

		ModelMapper modelMapper = new ModelMapper();
		
		Page<CompraDto> dto = new PageImpl(compras.stream().map(p -> modelMapper
				.map(p, CompraDto.class)).collect(Collectors.toList()));
		
		response.setData(dto);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<Response<CompraDto>> findById(@PathVariable("id") Integer id){
		
		Response<CompraDto> response = new Response<CompraDto>();
		
		Compra compra = this.compraService.findById(id);
		
		ModelMapper modelMapper = new ModelMapper();
		
		CompraDto compraDto = modelMapper.map(compra, CompraDto.class);
		
		response.setData(compraDto);
		
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping()
	public ResponseEntity<Response<CompraDto>> create(@RequestBody CompraDto compra){
		
		Response<CompraDto> response = new Response<CompraDto>();
		
		ModelMapper modelMapper = new ModelMapper();
		Compra novaCompra = modelMapper.map(compra, Compra.class);
		
		novaCompra = this.compraService.save(novaCompra);
		
		CompraDto dto = modelMapper.map(novaCompra, CompraDto.class);
		response.setData(dto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	@GetMapping("/{idCliente}")
	public ResponseEntity<Response<CompraDto>> findByCliente(@PathVariable("id") Integer id,
			@PathVariable("idCliente") Integer idCliente){
		
		Response<CompraDto> response = new Response<CompraDto>();
		
		Compra item = this.compraService.findById(id);
		
		ModelMapper modelMapper = new ModelMapper();
		
		CompraDto itemDto = modelMapper.map(item, CompraDto.class);
		
		response.setData(itemDto);
		
		return ResponseEntity.ok().body(response);
	}

}
