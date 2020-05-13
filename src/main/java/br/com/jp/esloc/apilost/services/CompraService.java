package br.com.jp.esloc.apilost.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jp.esloc.apilost.domain.CompraDto;
import br.com.jp.esloc.apilost.domain.CompraResponseDto;
import br.com.jp.esloc.apilost.models.Compra;

public interface CompraService {
	CompraResponseDto save(CompraDto compra);
	boolean isContaining();
	Page<Compra> findAll(Pageable page);
	void delete(Compra compra);
	Compra findOne(String id);
	Page<Compra> findByCliente(Pageable page, Integer idCliente);
	Page<Compra> findNoQuitByClienteId(Pageable page, Integer idCliente);
	Page<Compra> findQuitByClienteId(Pageable page, Integer idCliente);
	Optional<Compra> findById(Integer idCompra);
	Compra save(Compra compra);
}
