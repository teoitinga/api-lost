package br.com.jp.esloc.apilost.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jp.esloc.apilost.exceptions.CompraNotFound;
import br.com.jp.esloc.apilost.models.Compra;

public interface CompraService {
	Compra save(Compra compra);
	Compra findById(Integer idCompra) throws CompraNotFound;
	boolean isContaining();
	Page<Compra> findAll(Pageable page);
	void delete(Compra compra);
}
