package br.com.jp.esloc.apilost.services.impls;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import br.com.jp.esloc.apilost.domain.CompraNoQuitResponseDto;
import br.com.jp.esloc.apilost.domain.CompraPostDto;
import br.com.jp.esloc.apilost.domain.CompraResponseDto;
import br.com.jp.esloc.apilost.domain.ItensDto;
import br.com.jp.esloc.apilost.domain.UpdatePagamentoDeContaDto;
import br.com.jp.esloc.apilost.exceptions.CompraNotFound;
import br.com.jp.esloc.apilost.exceptions.PersonaNotFound;
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
public class CompraServiceImpl implements CompraService{
	
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
	public Optional<Compra> findById(Integer idCompra) throws CompraNotFound {
		return this.compraRepository.findById(idCompra);
	}

	@Override
	public boolean isContaining() {
		return this.compraRepository.findAll().size()>0?true:false;
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
		
		Compra compra = new Compra();
		//buscando os dados do cliente
		if(dto.getCliente()==null || dto.getCliente().equals("")) {
			new RegraNegocioException("Código de cliente não informado.");
		}
		Persona cliente = this.personaRepository.findById( dto.getCliente())
			.orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));
		compra.setFkCliente(cliente);

		//buscando os dados do usuario
		Persona vendedor = this.personaRepository.findById(dto.getVendedor())
				.orElseThrow(() -> new RegraNegocioException("Código de vendedor inválido."));
		compra.setEntreguePor(vendedor);
		
		//configurando data da compra
		//Caso não seja informada, informa a data/hora atual
		if(dto.getDataCompra()==null) {
			dto.setDataCompra(LocalDateTime.now());
		}
		compra.setDataCompra(dto.getDataCompra());
		
		//configurando nome da pessoa que recebeu a mercadoria
		compra.setEntregueA(dto.getRecebebida());
		
		
		//Configurando Itens de compra
		List<Detalhecompra> itens = this.converterItens(compra, dto.getItens());
		//calcula e registra o valor da compra
		BigDecimal valorDaCompra = BigDecimal.valueOf(itens.stream().map(item-> 
        item.getVltotal())
				.mapToDouble(BigDecimal::doubleValue).sum());
		compra.setValorCompra(valorDaCompra);
		
		//atualiza débito do cliente
		if(cliente.getDebito()==null || cliente.getDebito().equals(BigDecimal.ZERO)) {
			//o Debito atual é zerado no registro
			cliente.setDebito(BigDecimal.ZERO);
			compra.setDebAtual(cliente.getDebito());
		}else {
			//configurando o valor do debito atual do cliente nesta compra
			//é o valor atual do debito, excluido o valor da compra em processo de registro
			compra.setDebAtual(cliente.getDebito());
			
			//atualiza débito do cliente, incluindo a compra em registro
			cliente.setDebito(cliente.getDebito().add(compra.getValorCompra()));
		}
		compra = this.compraRepository.save(compra);
		itens = this.detalheCompraRepository.saveAll(itens);
		compra.setItens(itens);
		
		//atualizando dados da compra com a lista de itens populada
		//compra = this.compraRepository.findByIdFetchItens(compra.getId());
		
		cliente = this.personaRepository.save(cliente);
		
		//retorna dados da compra
		return toResponseDto(compra);
	}
	public Optional<Compra> getCompra(Integer id) {
		return this.compraRepository.findById(id);
	}
	public CompraResponseDto toResponseDto(Compra compra) {

		return CompraResponseDto.builder()
		.codigo(compra.getId())
		.dataCompra(compra.getDataCompra())
		.recebedor(compra.getEntregueA())
		.vendedor(compra.getEntreguePor().getNome())
		.clienteNome(compra.getFkCliente().getNome())
		.valorCompra(compra.getValorCompra())
		.itens(toDto(compra.getItens())).build();
	}

	private List<ItensDto> toDto(List<Detalhecompra> itens) {

		if(CollectionUtils.isEmpty(itens)) {
			return Collections.emptyList();
		}
		return itens.stream()
				.map(item -> ItensDto.builder()
						.id(item.getId())
						.dsc(item.getDsc())
						.vlunit(item.getVlunit())
						.qtd(item.getQtd())
						.desconto(item.getDesconto())
						.build()).collect(Collectors.toList());
	}

	private List<Detalhecompra> converterItens(Compra compra, List<ItensDto> itens) {
		
		//Dispara erro caso não haja itens na lista de compra
		if(itens.isEmpty()) {
			throw new RegraNegocioException("Não é possível realizar uma compra sem itens.");
		}
		//Configura valores totais baseado QUANTIDADE, VALOR UNITARIO E DESCONTO
		//Ajusta valores caso esteja null
		return itens.stream().map(dto->{
			Detalhecompra itemDeCompra = new Detalhecompra();
			itemDeCompra.setCompra(compra);
			itemDeCompra.setDsc(dto.getDsc());
			//Se o DESCONTO é null atribui 0
			if(dto.getDesconto()==null) {
				dto.setDesconto(BigDecimal.ZERO);
			}
			itemDeCompra.setDesconto(dto.getDesconto());
			
			//Se a QUANTIDADE é null atribui o valor 1
			if(dto.getQtd()==null) {
				dto.setDesconto(BigDecimal.ONE);
			}
			itemDeCompra.setQtd(dto.getQtd());
			
			//Se o VALOR UNITARIOé null atribui o valor 0
			if(dto.getVlunit()==null) {
				dto.setDesconto(BigDecimal.ZERO);
			}
			itemDeCompra.setVlunit(dto.getVlunit());
			
			//Calcula o valor do item com as informaçoes anteriores
			itemDeCompra.setVltotal(dto.getQtd().multiply(dto.getVlunit()).subtract(dto.getDesconto()));
			return itemDeCompra;
		}).collect(Collectors.toList());
	}

	@Override
	public Optional<List<CompraResponseDto>> getCompraPorCliente(Integer id) {
		return toListResponseDto(this.compraRepository
				.findFetchItensByFkClienteIdOrderByDataCompraDesc(id));
	}
	@Override
	public Optional<List<CompraNoQuitResponseDto>> getComprasNaoQuitadasPorCliente(Integer id) {
		return toListResponseQuitDto(this.compraRepository
				.findFetchItensByFkClienteIdAndAcertadoEmNullOrderByDataCompraDesc(id));
	}
	private Optional<List<CompraNoQuitResponseDto>> toListResponseQuitDto(List<Compra> compras) {
		return Optional.of(
				compras.stream().map(compra->CompraNoQuitResponseDto.builder()
				.codigo(compra.getId())
				.dataCompra(compra.getDataCompra())
				.recebedor(compra.getEntregueA())
				.vendedor(compra.getEntreguePor().getNome())
				.clienteNome(compra.getFkCliente().getNome())
				.valorCompra(compra.getValorCompra())
				.itens(this.toDto(compra.getItens()))
				.build()).collect(Collectors.toList()));
	}

	@Override
	public Optional<List<CompraResponseDto>> getComprasQuitadasPorCliente(Integer id) {
		System.out.println("Pesquisando comprar do Cliente: "+ id);
		return toListResponseDto(this.compraRepository
				.findFetchItensByFkClienteIdAndAcertadoEmNotNullOrderByDataCompraDesc(id));
	}

	@Override
	public Optional<List<CompraResponseDto>> toListResponseDto(List<Compra> compras) {
		return Optional.of(
				compras.stream().map(compra->CompraResponseDto.builder()
				.codigo(compra.getId())
				.dataCompra(compra.getDataCompra())
				.dataPagamento(compra.getAcertadoEm())
				.recebedor(compra.getEntregueA())
				.vendedor(compra.getEntreguePor().getNome())
				.clienteNome(compra.getFkCliente().getNome())
				.valorCompra(compra.getValorCompra())
				.itens(this.toDto(compra.getItens()))
				.build()).collect(Collectors.toList()));
	}

	@Override
	public void updatePagamento(Integer id, UpdatePagamentoDeContaDto dto) {
		this.compraRepository.findById(id).map(compra -> {
			compra.setAcertadoEm(LocalDateTime.now());
			return this.compraRepository.save(compra);
		}).orElseThrow(()-> new CompraNotFound());
	}
	@Transactional
	@Override
	public void zerarDebitoDoCliente(Integer idCliente) {
		LocalDateTime dataAtualização = LocalDateTime.now();
		
		Persona cliente = this.personaRepository.findById(idCliente)
				.orElseThrow(()-> new PersonaNotFound("Cliente não encontrado nos registros."));
		this.compraRepository.registrarPagamentoDeComprasEmAberto(dataAtualização, cliente.getId());
		
		cliente.setDebito(BigDecimal.ZERO);
		cliente.setUltAtualizacao(dataAtualização);
		this.personaRepository.save(cliente);
	}

	@Override
	public void registrarHaverParaCliente(Integer idCliente) {
		// TODO Auto-generated method stub
		
	}

}
