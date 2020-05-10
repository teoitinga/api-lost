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

import br.com.jp.esloc.apilost.builders.CompraBuild;
import br.com.jp.esloc.apilost.domain.CompraDto;
import br.com.jp.esloc.apilost.domain.DetalheCompraDto;
import br.com.jp.esloc.apilost.models.Compra;
import br.com.jp.esloc.apilost.models.Detalhecompra;
import br.com.jp.esloc.apilost.responce.Response;
import br.com.jp.esloc.apilost.services.CompraService;
import br.com.jp.esloc.apilost.services.DetalheCompraService;
import br.com.jp.esloc.apilost.services.PersonaService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/compras/itens")
public class DetalheCompraResource {
	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private CompraService compraService;
	
	@Autowired
	private DetalheCompraService detalheCompraService;
	
	@GetMapping()
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
	
	@PostMapping()
	public ResponseEntity<Response<DetalheCompraDto>> createItem(@RequestBody DetalheCompraDto item){
		
		Response<DetalheCompraDto> response = new Response<DetalheCompraDto>();
		
		ModelMapper modelMapper = new ModelMapper();
		
		Detalhecompra novoItem = modelMapper.map(item, Detalhecompra.class);
		
		novoItem = this.detalheCompraService.save(novoItem);
		
		DetalheCompraDto dto = modelMapper.map(novoItem, DetalheCompraDto.class);
		response.setData(dto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response<DetalheCompraDto>> findById(@PathVariable("id") Integer id){
		
		Response<DetalheCompraDto> response = new Response<DetalheCompraDto>();
		
		Detalhecompra item = this.detalheCompraService.findById(id);
		
		ModelMapper modelMapper = new ModelMapper();
		
		DetalheCompraDto itemDto = modelMapper.map(item, DetalheCompraDto.class);
		
		response.setData(itemDto);
		
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/{idCliente}/{idCompra}")
	public ResponseEntity<Response<CompraDto>> findItemById(@PathVariable("idCliente") Integer idCliente, 
			@PathVariable("idCompra") Integer idCompra,
			@RequestBody DetalheCompraDto item){
		
		ModelMapper modelMapper = new ModelMapper();
		
		Response<CompraDto> response = new Response<CompraDto>();
		//Configurando compra
		Compra cmp = this.compraService.findById(idCompra);
		
		Compra novaCompra = this.compraService.save(modelMapper.map(cmp, Compra.class));
		
		CompraDto novaCompraDto = modelMapper.map(novaCompra, CompraDto.class);
		
		CompraBuild build = new CompraBuild();
		log.info("verificando cliente {}", idCliente);
		log.info("verificando nova compra {}", novaCompraDto);
		log.info("verificando item {}", item);
		log.info("verificando cliente {}", idCliente);
		build.create(idCliente, idCliente, novaCompraDto, item);
		
		response.setData(novaCompraDto);
		
		return ResponseEntity.ok().body(response);
	}
}
