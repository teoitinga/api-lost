package br.com.jp.esloc.apilost.services.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jp.esloc.apilost.domain.CompraDto;
import br.com.jp.esloc.apilost.domain.ItensDto;
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
	public Compra findById(Integer idCompra) throws CompraNotFound {
		log.info("Pesquisando compra com ID: {}",idCompra);
		return this.compraRepository.findById(idCompra).orElseThrow(()-> new PersonaNotFound("Registro de compra não encontrada."));
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
	public Compra save(CompraDto dto) {
		Compra compra = new Compra();
		Integer idCliente = dto.getFkCliente();
		//buscando os dados do cliente
		Persona cliente = this.personaRepository.findById(idCliente)
			.orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));
		compra.setFkCliente(cliente);
				
		compra.setDataCompra(dto.getDataCompra());
		compra.setEntregueA(dto.getEntregueA());
		compra.setEntreguePor(dto.getEntreguePor());
		compra.setValorCompra(dto.getValorCompra());
		
		List<Detalhecompra> itens = this.converterItens(compra, dto.getItens());
		
		compra = this.compraRepository.save(compra);
		itens = this.detalheCompraRepository.saveAll(itens);
		compra.setItens(itens);
		
		return compra;
	}
	private List<Detalhecompra> converterItens(Compra compra, List<ItensDto> itens) {
		if(itens.isEmpty()) {
			throw new RegraNegocioException("Não é possível realizar uma compra sem itens.");
		}
		return itens.stream().map(dto->{
			Detalhecompra itemDeCompra = new Detalhecompra();
			itemDeCompra.setCompra(compra);
			itemDeCompra.setDsc(dto.getDsc());
			itemDeCompra.setDesconto(dto.getDesconto());
			itemDeCompra.setQtd(dto.getQtd());
			itemDeCompra.setVlunit(dto.getVlunit());
			itemDeCompra.setVltotal(dto.getVltotal());
			return itemDeCompra;
		}).collect(Collectors.toList());
	}

}
