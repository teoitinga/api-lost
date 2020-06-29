package br.com.jp.esloc.apilost.services.impls;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import br.com.jp.esloc.apilost.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import br.com.jp.esloc.apilost.exceptions.CompraNotFoundException;
import br.com.jp.esloc.apilost.exceptions.PersonaNotFoundException;
import br.com.jp.esloc.apilost.exceptions.RegraNegocioException;
import br.com.jp.esloc.apilost.models.Compra;
import br.com.jp.esloc.apilost.models.Detalhecompra;
import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.repositories.CompraRepository;
import br.com.jp.esloc.apilost.repositories.DetalheCompraRepository;
import br.com.jp.esloc.apilost.repositories.PersonaRepository;
import br.com.jp.esloc.apilost.services.CompraService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompraServiceImpl implements CompraService {

	@Autowired
	private CompraRepository compraRepository;

	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private DetalheCompraRepository detalheCompraRepository;

	@Override
	public Compra save(Compra compra) {
		return this.compraRepository.save(compra);
	}

	@Override
	public Optional<Compra> findById(Integer idCompra) throws CompraNotFoundException {
		return this.compraRepository.findById(idCompra);
	}

	@Override
	public boolean isContaining() {
		return this.compraRepository.findAll().size() > 0 ? true : false;
	}

	@Override
	public Page<Compra> findAll(Pageable page) {
		return this.compraRepository.findAll(page);
	}

	@Override
	public void delete(Compra compra) {
		this.compraRepository.delete(compra);

	}

	@Override
	public Compra findOne(String id) {
		return this.compraRepository.findById(Integer.parseInt(id)).get();
	}

	@Override
	public Page<Compra> findByCliente(Pageable page, Integer idCliente) {
		return this.compraRepository.findByClienteId(page, idCliente);
	}

	@Override
	public Page<Compra> findNoQuitByClienteId(Pageable page, Integer idCliente) {
		return this.compraRepository.findNoQuitByClienteId(page, idCliente);
	}

	@Override
	public Page<Compra> findQuitByClienteId(Pageable page, Integer idCliente) {
		return this.compraRepository.findQuitByClienteId(page, idCliente);
	}

	@Override
	@Transactional
	public CompraResponseDto save(CompraPostDto dto) {
		log.info("compra{}", dto);
		Compra compra = new Compra();
		// buscando os dados do cliente
		if (dto.getCliente() == null || dto.getCliente().equals("")) {
			new RegraNegocioException("Código de cliente não informado.");
		}
		Persona cliente = this.personaRepository.findById(dto.getCliente())
				.orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));
		compra.setFkCliente(cliente);

		// buscando os dados do usuario
		Persona vendedor = this.personaRepository.findById(dto.getVendedor())
				.orElseThrow(() -> new RegraNegocioException("Código de vendedor inválido."));
		compra.setEntreguePor(vendedor);

		// configurando data da compra
		// Caso não seja informada, informa a data/hora atual
		if (dto.getDataCompra() == null) {
			dto.setDataCompra(LocalDateTime.now());
		}
		compra.setDataCompra(dto.getDataCompra());

		// configurando nome da pessoa que recebeu a mercadoria
		compra.setEntregueA(dto.getRecebebida());

		// Configurando Itens de compra
		List<Detalhecompra> itens = this.converterItens(compra, dto.getItens());
		// calcula e registra o valor da compra
		BigDecimal valorDaCompra = BigDecimal
				.valueOf(itens.stream().map(item -> item.getVltotal()).mapToDouble(BigDecimal::doubleValue).sum());
		compra.setValorCompra(valorDaCompra);

		log.info("Registrando compra para o cliente" + cliente.getNome());

		// atualiza débito do cliente
		if (cliente.getDebito() == null || cliente.getDebito().equals(BigDecimal.ZERO)) {
			// o Debito atual é zerado no registro
			cliente.setDebito(BigDecimal.ZERO);
			compra.setDebAtual(cliente.getDebito());
		}
		// configurando o valor do debito atual do cliente nesta compra
		// é o valor atual do debito, excluido o valor da compra em processo de registro
		compra.setDebAtual(cliente.getDebito());
		// atualiza débito do cliente, incluindo a compra em registro
		cliente.setDebito(cliente.getDebito().add(compra.getValorCompra()));
		this.personaRepository.save(cliente);
		compra = this.compraRepository.save(compra);
		itens = this.detalheCompraRepository.saveAll(itens);
		compra.setItens(itens);

		// atualizando dados da compra com a lista de itens populada
		// compra = this.compraRepository.findByIdFetchItens(compra.getId());

		cliente = this.personaRepository.save(cliente);

		// retorna dados da compra
		return toResponseDto(compra);
	}

	public Optional<Compra> getCompra(Integer id) {
		return this.compraRepository.findById(id);
	}

	public CompraResponseDto toResponseDto(Compra compra) {

		return CompraResponseDto.builder().codigo(compra.getId()).dataCompra(compra.getDataCompra())
				.recebedor(compra.getEntregueA()).vendedor(compra.getEntreguePor().getNome())
				.clienteNome(compra.getFkCliente().getNome()).valorCompra(compra.getValorCompra())
				.itens(toDto(compra.getItens())).build();
	}

	public CompraResponseDto toResponseDto(CompraNoQuitResponseDto compra) {

		return CompraResponseDto.builder().codigo(compra.getCodigo()).dataCompra(compra.getDataCompra())
				.recebedor(compra.getRecebedor()).vendedor(compra.getVendedor()).clienteNome(compra.getClienteNome())
				.valorCompra(compra.getValorCompra()).itens(toResponseDto(compra.getItens())).build();
	}

	private List<ItensDto> toResponseDto(List<ItensDto> itens) {
		if (CollectionUtils.isEmpty(itens)) {
			return Collections.emptyList();
		}
		return itens.stream()
				.map(item -> ItensDto.builder().id(item.getId()).description(item.getDescription())
						.unitvalue(item.getUnitvalue()).qtd(item.getQtd()).desconto(item.getDesconto()).build())
				.collect(Collectors.toList());
	}

//    private Integer codigo;
//    private LocalDateTime dataCompra;
//    private LocalDateTime dataPagamento;
//    private String recebedor;
//    private String vendedor;
//    private String clienteNome;
//    private BigDecimal valorCompra;
//    private List<ItensDto> itens;
//    private Integer ItemId;
//    private String itemDescription;
//    private BigDecimal ItemUnitvalue;
//    private BigDecimal ItemQtd;
//    private BigDecimal ItemDesconto;
	private List<ComprasListDto> toCompraListDtoForItens(CompraResponseDto compraActive) {
		if (CollectionUtils.isEmpty(compraActive.getItens())) {
			return null;
		}
		// variaveis comuns
		CompraResponseDto compras = compraActive;//this.toResponseDto(compraActive);

		Integer codigo = compras.getCodigo();
		LocalDateTime dataCompra = compras.getDataCompra();
		LocalDateTime dataPagamento = compras.getDataPagamento();
		String recebedor = compras.getRecebedor();
		String vendedor = compras.getVendedor();
		String clienteNome = compras.getClienteNome();
		BigDecimal valorCompra = compras.getValorCompra();
		return compras.getItens().stream()
				.map(cmp -> ComprasListDto.builder().codigo(codigo).dataCompra(dataCompra).dataPagamento(dataPagamento)
						.recebedor(recebedor).vendedor(vendedor).clienteNome(clienteNome).valorCompra(valorCompra)
						.ItemId(cmp.getId()).itemDescription(cmp.getDescription()).ItemUnitvalue(cmp.getUnitvalue())
						.ItemQtd(cmp.getQtd()).ItemDesconto(cmp.getDesconto()).build())
				.collect(Collectors.toList());

	}

	private List<ItensDto> toDto(List<Detalhecompra> itens) {

		if (CollectionUtils.isEmpty(itens)) {
			return Collections.emptyList();
		}
		return itens
				.stream().map(item -> ItensDto.builder().id(item.getId()).description(item.getDsc())
						.unitvalue(item.getVlunit()).qtd(item.getQtd()).desconto(item.getDesconto()).build())
				.collect(Collectors.toList());
	}

	private List<Detalhecompra> converterItens(Compra compra, List<ItensDto> itens) {

		// Dispara erro caso não haja itens na lista de compra
		if (itens.isEmpty()) {
			throw new RegraNegocioException("Não é possível realizar uma compra sem itens.");
		}
		// Configura valores totais baseado QUANTIDADE, VALOR UNITARIO E DESCONTO
		// Ajusta valores caso esteja null
		return itens.stream().map(dto -> {
			Detalhecompra itemDeCompra = new Detalhecompra();
			itemDeCompra.setCompra(compra);
			itemDeCompra.setDsc(dto.getDescription());
			// Se o DESCONTO é null atribui 0
			if (dto.getDesconto() == null) {
				dto.setDesconto(BigDecimal.ZERO);
			}
			itemDeCompra.setDesconto(dto.getDesconto());

			// Se a QUANTIDADE é null atribui o valor 1
			if (dto.getQtd() == null) {
				dto.setDesconto(BigDecimal.ONE);
			}
			itemDeCompra.setQtd(dto.getQtd());

			// Se o VALOR UNITARIOé null atribui o valor 0
			if (dto.getUnitvalue() == null) {
				dto.setDesconto(BigDecimal.ZERO);
			}
			itemDeCompra.setVlunit(dto.getUnitvalue());

			// Calcula o valor do item com as informaçoes anteriores
			itemDeCompra.setVltotal(dto.getQtd().multiply(dto.getUnitvalue()).subtract(dto.getDesconto()));
			return itemDeCompra;
		}).collect(Collectors.toList());
	}

	@Override
	public Optional<List<CompraResponseDto>> getCompraPorCliente(Integer id) {
		return toListResponseDto(this.compraRepository.findFetchItensByFkClienteIdOrderByDataCompraDesc(id));
	}

	@Override
	public Optional<List<CompraNoQuitResponseDto>> getComprasNaoQuitadasPorCliente(Integer id) {
		return toListResponseQuitDto(
				this.compraRepository.findFetchItensByFkClienteIdAndAcertadoEmNullOrderByDataCompraDesc(id));
	}

	private Optional<List<CompraNoQuitResponseDto>> toListResponseQuitDto(List<Compra> compras) {
		return Optional.of(compras.stream()
				.map(compra -> CompraNoQuitResponseDto.builder().codigo(compra.getId())
						.dataCompra(compra.getDataCompra()).recebedor(compra.getEntregueA())
						.vendedor(compra.getEntreguePor().getNome()).clienteNome(compra.getFkCliente().getNome())
						.valorCompra(compra.getValorCompra()).itens(this.toDto(compra.getItens())).build())
				.collect(Collectors.toList()));
	}

	@Override
	public Optional<List<CompraResponseDto>> getComprasQuitadasPorCliente(Integer id) {

		return toListResponseDto(
				this.compraRepository.findFetchItensByFkClienteIdAndAcertadoEmNotNullOrderByDataCompraDesc(id));
	}

	@Override
	public Optional<List<CompraResponseDto>> toListResponseDto(List<Compra> compras) {
		return Optional.of(compras.stream()
				.map(compra -> CompraResponseDto.builder().codigo(compra.getId()).dataCompra(compra.getDataCompra())
						.dataPagamento(compra.getAcertadoEm()).recebedor(compra.getEntregueA())
						.vendedor(compra.getEntreguePor() == null ? "" : compra.getEntreguePor().getNome())
						.clienteNome(compra.getFkCliente().getNome()).valorCompra(compra.getValorCompra())
						.itens(this.toDto(compra.getItens())).build())
				.collect(Collectors.toList()));
	}

	@Override
	public void updatePagamento(Integer id, UpdatePagamentoDeContaDto dto) {
		this.compraRepository.findById(id).map(compra -> {
			compra.setAcertadoEm(LocalDateTime.now());
			return this.compraRepository.save(compra);
		}).orElseThrow(() -> new CompraNotFoundException());
	}

	@Transactional
	@Override
	public void zerarDebitoDoCliente(Integer idCliente) {
		LocalDateTime dataAtualização = LocalDateTime.now();

		Persona cliente = this.personaRepository.findById(idCliente)
				.orElseThrow(() -> new PersonaNotFoundException("Cliente não encontrado nos registros."));
		this.compraRepository.registrarPagamentoDeComprasEmAberto(dataAtualização, cliente.getId());

		cliente.setDebito(BigDecimal.ZERO);
		cliente.setUltAtualizacao(dataAtualização);
		this.personaRepository.save(cliente);
	}

	@Override
	public void registrarHaverParaCliente(Integer idCliente) {
		throw new CompraNotFoundException();
	}

	@Override
	public Optional<List<CompraResponseDto>> getTeenCompras() {
		return toListResponseDto(this.compraRepository.findOrderByDataCompraDescTop10());
	}

	public List<Compra> getTeenTreeCompras() {
		return (this.compraRepository.findOrderByDataCompraDescTop10());
	}

	private Optional<List<ComprasListDto>> toListFullDto(List<Compra> compras){
		List<ComprasListDto> lista = new ArrayList<>();
		
		return Optional.of(lista);	
	}

	@Override
	public List<CompraResponseDto> toComprasListDto(List<CompraResponseDto> compras) {
		// TODO Auto-generated method stub
		return compras;
	}
	@Override
	public List<ComprasListDto> toInLineCompraListDto(List<CompraResponseDto> compras){
		List<ComprasListDto> lista = new ArrayList<>();
		for (CompraResponseDto c : compras ) {
            lista.addAll(this.toCompraListDtoForItens(c));
        }
		return lista;
		//return this.toCompraListDtoForItens(compras.get(0));
	}
// List<ComprasListDto> toCompraListDtoForItens(Compra compraActive)

	@Override
	@Transactional
	public void payParcial(@Valid ParcialPayDto payDto) {
		Detalhecompra item = new Detalhecompra();
		Compra compra = new Compra();
		BigDecimal debitoAtual;

		LocalDateTime dataAtualização = LocalDateTime.now();

		Persona cliente = this.personaRepository.findById(Integer.parseInt(payDto.getCliente()))
				.orElseThrow(() -> new PersonaNotFoundException("Cliente não encontrado nos registros."));
		
		// buscando os dados do usuario
		Persona vendedor = this.personaRepository.findById(Integer.parseInt(payDto.getVendedor()))
				.orElseThrow(() -> new RegraNegocioException("Código de vendedor inválido."));
		compra.setEntreguePor(vendedor);
		
		debitoAtual = cliente.getDebito();
		//metodo atualiza data de pagamento para todas as compras em aberto do cliente. Não se aplica neste caso
		//this.compraRepository.registrarPagamentoDeComprasEmAberto(dataAtualização, cliente.getId());
		BigDecimal valorDoPagamento = payDto.getValor();
		cliente.setDebito(cliente.getDebito().subtract(valorDoPagamento));
		cliente.setUltAtualizacao(dataAtualização);
		this.personaRepository.save(cliente);
		
		compra.setFkCliente(cliente);
		compra.setDebAtual(debitoAtual);
		compra.setDataCompra(payDto.getData());
		compra.setEntreguePor(vendedor);
		
		
		Detalhecompra pg = new Detalhecompra();
		pg.setDsc(payDto.getDescricao());
		pg.setCompra(compra);
		pg.setDesconto(payDto.getValor());
		pg.setQtd(BigDecimal.ONE);
		pg.setVlunit(BigDecimal.ZERO);
		
		pg = this.detalheCompraRepository.save(pg);
		compra.setItens(Arrays.asList(pg));

		// atualizando dados da compra com a lista de itens populada
		// compra = this.compraRepository.findByIdFetchItens(compra.getId());

		cliente = this.personaRepository.save(cliente);

	}
}
