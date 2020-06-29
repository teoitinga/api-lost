package br.com.jp.esloc.apilost.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import br.com.jp.esloc.apilost.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jp.esloc.apilost.models.Compra;

public interface CompraService {
	CompraResponseDto save(CompraPostDto compra);

	boolean isContaining();

	Page<Compra> findAll(Pageable page);

	void delete(Compra compra);

	Compra findOne(String id);

	Page<Compra> findByCliente(Pageable page, Integer idCliente);

	Page<Compra> findNoQuitByClienteId(Pageable page, Integer idCliente);

	Page<Compra> findQuitByClienteId(Pageable page, Integer idCliente);

	Optional<Compra> findById(Integer idCompra);

	Compra save(Compra compra);

	Optional<Compra> getCompra(Integer id);

	Optional<List<CompraResponseDto>> getCompraPorCliente(Integer id);

	Optional<List<CompraResponseDto>> toListResponseDto(List<Compra> compras);

	Optional<List<CompraNoQuitResponseDto>> getComprasNaoQuitadasPorCliente(Integer id);

	Optional<List<CompraResponseDto>> getComprasQuitadasPorCliente(Integer id);

	Optional<List<CompraResponseDto>> getTeenCompras();

	void updatePagamento(Integer id, UpdatePagamentoDeContaDto dto);

	void zerarDebitoDoCliente(Integer idCliente);

	void registrarHaverParaCliente(Integer idCliente);

	CompraResponseDto toResponseDto(Compra compra);

	CompraResponseDto toResponseDto(CompraNoQuitResponseDto c);
	List<CompraResponseDto> toComprasListDto(List<CompraResponseDto> compras);
	List<ComprasListDto> toInLineCompraListDto(List<CompraResponseDto> compras);

	void payParcial(@Valid ParcialPayDto payDto);
}
