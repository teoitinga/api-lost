package br.com.jp.esloc.apilost.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.jp.esloc.apilost.exceptions.CompraNotFound;
import br.com.jp.esloc.apilost.exceptions.PersonaNotFound;
import br.com.jp.esloc.apilost.models.Compra;
import br.com.jp.esloc.apilost.repositories.CompraRepository;
import br.com.jp.esloc.apilost.repositories.DetalheCompraRepository;
import br.com.jp.esloc.apilost.services.CompraService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompraServiceImpl implements CompraService{
	
	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private DetalheCompraRepository detalhecompraRepository;
	
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

}
