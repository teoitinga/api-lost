package br.com.jp.esloc.apilost.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.jp.esloc.apilost.domain.CompraNoQuitResponseDto;
import br.com.jp.esloc.apilost.domain.CompraPostDto;
import br.com.jp.esloc.apilost.domain.CompraResponseDto;
import br.com.jp.esloc.apilost.domain.UpdatePagamentoDeContaDto;
import br.com.jp.esloc.apilost.exceptions.CompraNotFoundException;
import br.com.jp.esloc.apilost.models.Compra;
import br.com.jp.esloc.apilost.services.CompraService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/compras")
@CrossOrigin(origins = "*")
public class CompraResource {
	@Autowired
	private CompraService comprasService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation("Registra uma nova compra")
	@ApiResponses({ @ApiResponse(code = 201, message = "Compra criada com sucesso."),
			@ApiResponse(code = 404, message = "Não foi possível registrar a compra.") })
	public CompraResponseDto save(@RequestBody CompraPostDto compraDto) {
		CompraResponseDto compra = this.comprasService.save(compraDto);
		return compra;
	}
	@GetMapping("{id}")
	@ApiOperation("Obter detalhes da compra pelo código ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Compra encrontrada."),
			@ApiResponse(code = 404, message = "Compra não encontrada nos registros.") })
	public CompraResponseDto getById(@PathVariable Integer id) {
		return this.comprasService.getCompra(id)
				.map(c -> toResponseDto(c))
				.orElseThrow(()->new CompraNotFoundException("Compra não encontrada nos registros."))
			; 
	}
	@GetMapping("cliente/{id}")
	@ApiOperation("Obter detalhes da compra do cliente informado")
	@ApiResponses({ @ApiResponse(code = 200, message = "Compras encrontradas."),
			@ApiResponse(code = 404, message = "Não há compras para o cliente informado.") })
	public List<CompraResponseDto> getByClienteId(@PathVariable Integer id) {
		return this.comprasService.getCompraPorCliente(id)
				.map(compra -> verificaCompras(compra))
				.orElseThrow(() -> new CompraNotFoundException("Este cliente não possui compras registradas."))
				; 
	}
	private List<CompraResponseDto> verificaCompras(List<CompraResponseDto> compra) throws CompraNotFoundException {
		//Dispara erro caso não haja itens na lista de compra
		if(compra.isEmpty()) {
			throw new CompraNotFoundException("Não foi encontrada compras quitadas para este cliente.");
		}
		return compra;
	}
	private List<CompraNoQuitResponseDto> verificaComprasNoQuitResponseDto(List<CompraNoQuitResponseDto> compra) throws CompraNotFoundException {
		//Dispara erro caso não haja itens na lista de compra
		if(compra.isEmpty()) {
			throw new CompraNotFoundException("Não foi encontrada compras em aberto para este cliente.");
		}
		return compra;
	}

	@PatchMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation("Atualiza os dados da compra com o ID informado")
	@ApiResponses({ @ApiResponse(code = 200, message = "Compras modificada com sucesso."),
			@ApiResponse(code = 404, message = "Não foi encontrada compras com o código informado.") })
	public void updateDataPagamentoConta(@PathVariable Integer id,
									@RequestBody UpdatePagamentoDeContaDto dto) {
		this.comprasService.updatePagamento(id, dto);
	}
	@GetMapping("cliente/noquit/{id}")
	@ApiOperation("Retorna a lista de compras em aberto para o cliente especificado.")
	@ApiResponses({ @ApiResponse(code = 200, message = "Retorna a lista de compras em aberto para o cliente especificado."),
			@ApiResponse(code = 404, message = "Não foi encontrada compras em aberto para este cliente.") })
	public List<CompraNoQuitResponseDto> getByClienteIdNotQuit(@PathVariable Integer id) {
		return this.comprasService.getComprasNaoQuitadasPorCliente(id)
				.map(compra -> verificaComprasNoQuitResponseDto(compra))
				.orElseThrow(()->new CompraNotFoundException())
				; 
	}
	@GetMapping("cliente/quit/{id}")
	@ApiOperation("Retorna a lista de compras já quitadas para o cliente especificado.")
	@ApiResponses({ @ApiResponse(code = 200, message = "Retorna a lista de compras já quitadas para o cliente especificado."),
		@ApiResponse(code = 404, message = "Não foi encontrada compras quitadas para este cliente.") })
	public List<CompraResponseDto> getByClienteIdQuit(@PathVariable Integer id) {
		return this.comprasService.getComprasQuitadasPorCliente(id)
				.map(compra -> verificaCompras(compra))
				.orElseThrow(()->new CompraNotFoundException())
				; 
	}
	@GetMapping("/teen")
	@ApiOperation("Retorna a lista das 10 ultimas compras registradas.")
	@ApiResponses({ @ApiResponse(code = 200, message = "Lista de compras registradas."),
		@ApiResponse(code = 404, message = "Não foi encontrada compras.") })
	public List<CompraResponseDto> getLastCompras() {
		return this.comprasService.getTeenCompras()
				.map(compra -> verificaCompras(compra))
				.orElseThrow(()->new CompraNotFoundException())
				; 
	}
	
	private CompraResponseDto toResponseDto(Compra c) {

		return this.comprasService.toResponseDto(c);
	}
	
	/*
	 * private CompraResponseDto toResponseDto(CompraNoQuitResponseDto c) {
	 * 
	 * return this.comprasService.toResponseDto(c); }
	 */

}
