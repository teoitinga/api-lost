package br.com.jp.esloc.apilost.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jp.esloc.apilost.exceptions.ItemNotFoundException;
import br.com.jp.esloc.apilost.models.Detalhecompra;

public interface DetalheCompraService {
	Detalhecompra save(Detalhecompra item);
	boolean isContaining();
	Page<Detalhecompra> findAll(Pageable page);
	void delete(Detalhecompra item);
	Detalhecompra findById(Integer idItem) throws ItemNotFoundException;
}
