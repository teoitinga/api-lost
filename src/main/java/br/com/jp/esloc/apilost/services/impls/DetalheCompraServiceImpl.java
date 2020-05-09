package br.com.jp.esloc.apilost.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.jp.esloc.apilost.exceptions.ItemNotFound;
import br.com.jp.esloc.apilost.models.Detalhecompra;
import br.com.jp.esloc.apilost.repositories.DetalheCompraRepository;
import br.com.jp.esloc.apilost.services.DetalheCompraService;

@Service
public class DetalheCompraServiceImpl implements DetalheCompraService{
	@Autowired
	private DetalheCompraRepository detalheCompraRepository;
	
	@Override
	public Detalhecompra save(Detalhecompra item) {
		return this.detalheCompraRepository.save(item); 
	}

	@Override
	public Detalhecompra findById(Integer idItem) throws ItemNotFound {
		return this.detalheCompraRepository.findById(idItem)
				.orElseThrow(()-> new ItemNotFound("Item de compra não encontrado."));
	}

	@Override
	public boolean isContaining() {
		return this.detalheCompraRepository.findAll().size()>0?true:false;
	}

	@Override
	public Page<Detalhecompra> findAll(Pageable page) {
		return this.detalheCompraRepository.findAll(page);
	}

	@Override
	public void delete(Detalhecompra item) {
		this.detalheCompraRepository.delete(item);
		
	}

}
