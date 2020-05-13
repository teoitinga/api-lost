package br.com.jp.esloc.apilost.services.impls;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jp.esloc.apilost.domain.CompraDto;
import br.com.jp.esloc.apilost.domain.CompraResponseDto;
import br.com.jp.esloc.apilost.domain.ItensDto;
import br.com.jp.esloc.apilost.exceptions.CompraNotFound;
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
	public CompraResponseDto save(CompraDto dto) {
		Compra compra = new Compra();
		Integer idCliente = dto.getFkCliente();
		//buscando os dados do cliente
		Persona cliente = this.personaRepository.findById(idCliente)
			.orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));
		//buscando os dados do usuario
		Persona vendedor = this.personaRepository.findById(dto.getEntreguePor())
				.orElseThrow(() -> new RegraNegocioException("Código de vendedor inválido."));
		compra.setEntreguePor(vendedor);
				
		compra.setDataCompra(dto.getDataCompra());
		compra.setEntregueA(dto.getEntregueA());
		
		List<Detalhecompra> itens = this.converterItens(compra, dto.getItens());
		//calcula e registra o valor da compra
		BigDecimal valorDaCompra = BigDecimal.valueOf(itens.stream().map(item-> 
        item.getVltotal())
				.mapToDouble(BigDecimal::doubleValue).sum());
		
		compra.setValorCompra(valorDaCompra);
		compra = this.compraRepository.save(compra);
		itens = this.detalheCompraRepository.saveAll(itens);
		compra.setItens(itens);
		ModelMapper modelMapper = new ModelMapper();
		CompraResponseDto Compradto = modelMapper.map(compra, CompraResponseDto.class);
		return Compradto;
	}
	private List<Detalhecompra> converterItens(Compra compra, List<ItensDto> itens) {
		if(itens.isEmpty()) {
			throw new RegraNegocioException("Não é possível realizar uma compra sem itens.");
		}
		
		return itens.stream().map(dto->{
			Detalhecompra itemDeCompra = new Detalhecompra();
			itemDeCompra.setCompra(compra);
			itemDeCompra.setDsc(dto.getDsc());
			if(dto.getDesconto()==null) {
				dto.setDesconto(BigDecimal.ZERO);
			}
			itemDeCompra.setDesconto(dto.getDesconto());
			
			if(dto.getQtd()==null) {
				dto.setDesconto(BigDecimal.ONE);
			}
			itemDeCompra.setQtd(dto.getQtd());
			
			if(dto.getVlunit()==null) {
				dto.setDesconto(BigDecimal.ZERO);
			}
			itemDeCompra.setVlunit(dto.getVlunit());
			
			itemDeCompra.setVltotal(dto.getQtd().multiply(dto.getVlunit()).subtract(dto.getDesconto()));
			return itemDeCompra;
		}).collect(Collectors.toList());
	}

}
