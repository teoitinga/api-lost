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
import br.com.jp.esloc.apilost.domain.DetalheCompraDto;
import br.com.jp.esloc.apilost.domain.PersonaPostDto;
import br.com.jp.esloc.apilost.models.Compra;
import br.com.jp.esloc.apilost.models.Detalhecompra;
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
	@GetMapping("/itens")
	public ResponseEntity<Response<Page<DetalheCompraDto>>> findItens(@RequestParam(value="page", defaultValue = "0") Integer page,
			@RequestParam(value="size", defaultValue = "5") Integer size) {
		
		Response<Page<DetalheCompraDto>> response = new Response<Page<DetalheCompraDto>>();
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<Detalhecompra> compras = this.detalheCompraService.findAll(PageRequest.of(page, size));
		
		Page<DetalheCompraDto> itemDto = new PageImpl(compras.stream().map(DetalheCompraDto::create).collect(Collectors.toList()));
		
		ModelMapper modelMapper = new ModelMapper();
		
		Page<DetalheCompraDto> dto = new PageImpl(compras.stream().map(p -> modelMapper
				.map(p, DetalheCompraDto.class)).collect(Collectors.toList()));
		
		response.setData(dto);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	@GetMapping("/itens/{id}")
	public ResponseEntity<Response<DetalheCompraDto>> findItemById(@PathVariable("id") Integer id){
		
		Response<DetalheCompraDto> response = new Response<DetalheCompraDto>();
		
		Detalhecompra item = this.detalheCompraService.findById(id);
		
		ModelMapper modelMapper = new ModelMapper();
		
		DetalheCompraDto itemDto = modelMapper.map(item, DetalheCompraDto.class);
		
		response.setData(itemDto);
		
		return ResponseEntity.ok().body(response);
	}
	@PostMapping("/itens")
	public ResponseEntity<Response<DetalheCompraDto>> findItemById(@PathVariable("idCompra") Integer id, 
			@RequestBody PersonaPostDto persona){
		
		Response<DetalheCompraDto> response = new Response<DetalheCompraDto>();
		
		Detalhecompra item = this.detalheCompraService.findById(id);
		
		ModelMapper modelMapper = new ModelMapper();
		
		DetalheCompraDto itemDto = modelMapper.map(item, DetalheCompraDto.class);
		
		response.setData(itemDto);
		
		return ResponseEntity.ok().body(response);
	}
}
