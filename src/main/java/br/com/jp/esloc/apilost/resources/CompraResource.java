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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

		Page<CompraDto> itemDto = new PageImpl(compras.stream().map(c-> new CompraDto(c)).collect(Collectors.toList()));

		ModelMapper modelMapper = new ModelMapper();

		Page<CompraDto> dto = new PageImpl(compras.stream().map(p -> new CompraDto(p)).collect(Collectors.toList()));
		response.setData(dto);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<Response<CompraDto>> findById(@PathVariable("id") Integer id){
		
		Response<CompraDto> response = new Response<CompraDto>();
		
		Compra compra = this.compraService.findById(id);
		
		//ModelMapper modelMapper = new ModelMapper();
		
		CompraDto compraDto = new CompraDto(compra);// modelMapper.map(compra, CompraDto.class);
		
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
	@PutMapping("/{id}")
	public ResponseEntity<Response<CompraDto>> update(@PathVariable("id") Integer id, @RequestBody CompraDto compraDto){
		
		Response<CompraDto> response = new Response<CompraDto>();
		
		ModelMapper modelMapper = new ModelMapper();

		//obtem registro do usuario informado pelo id
		Compra cmp = this.compraService.findById(id);		
		cmp = modelMapper.map(compraDto, Compra.class);
		
		cmp = this.compraService.save(cmp);
		CompraDto dto = modelMapper.map(cmp, CompraDto.class);
		response.setData(dto);
		
		if(cmp==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Response<CompraDto>> delete(@PathVariable("id") Integer id){
		Response<CompraDto> response = new Response<CompraDto>();
		ModelMapper modelMapper = new ModelMapper();
		
		//obtem registro do usuario informado pelo id
		Compra cmp = this.compraService.findById(id);
		
		CompraDto dto = modelMapper.map(cmp, CompraDto.class);
		//deletando usuario especificado
		this.compraService.delete(cmp);
		
		response.setData(dto);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);	
	}
	@GetMapping("/cliente/noquit/{idCliente}")
	public ResponseEntity<Response<Page<CompraDto>>> findNoQuitByClienteId(@RequestParam(value="page", defaultValue = "0") Integer page,
			@RequestParam(value="size", defaultValue = "5") Integer size,
			@PathVariable("idCliente") Integer idCliente) {
		
		Response<Page<CompraDto>> response = new Response<Page<CompraDto>>();
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<Compra> compras = this.compraService.findNoQuitByClienteId(PageRequest.of(page, size), idCliente);

		Page<CompraDto> itemDto = new PageImpl(compras.stream().map(c-> new CompraDto(c)).collect(Collectors.toList()));

		ModelMapper modelMapper = new ModelMapper();

		Page<CompraDto> dto = new PageImpl(compras.stream().map(p -> new CompraDto(p)).collect(Collectors.toList()));
		response.setData(dto);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	@GetMapping("/cliente/quit/{idCliente}")
	public ResponseEntity<Response<Page<CompraDto>>> findQuitByClienteId(@RequestParam(value="page", defaultValue = "0") Integer page,
			@RequestParam(value="size", defaultValue = "5") Integer size,
			@PathVariable("idCliente") Integer idCliente) {
		
		Response<Page<CompraDto>> response = new Response<Page<CompraDto>>();
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<Compra> compras = this.compraService.findQuitByClienteId(PageRequest.of(page, size), idCliente);
		
		Page<CompraDto> itemDto = new PageImpl(compras.stream().map(c-> new CompraDto(c)).collect(Collectors.toList()));
		
		ModelMapper modelMapper = new ModelMapper();
		
		Page<CompraDto> dto = new PageImpl(compras.stream().map(p -> new CompraDto(p)).collect(Collectors.toList()));
		response.setData(dto);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	@GetMapping("/cliente/{idCliente}")
	public ResponseEntity<Response<Page<CompraDto>>> findAllByCliente(@RequestParam(value="page", defaultValue = "0") Integer page,
			@RequestParam(value="size", defaultValue = "5") Integer size,
			@PathVariable("idCliente") Integer idCliente) {
		
		Response<Page<CompraDto>> response = new Response<Page<CompraDto>>();
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<Compra> compras = this.compraService.findByCliente(PageRequest.of(page, size), idCliente);
		
		Page<CompraDto> itemDto = new PageImpl(compras.stream().map(c-> new CompraDto(c)).collect(Collectors.toList()));
		
		ModelMapper modelMapper = new ModelMapper();
		
		Page<CompraDto> dto = new PageImpl(compras.stream().map(p -> new CompraDto(p)).collect(Collectors.toList()));
		response.setData(dto);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
}
