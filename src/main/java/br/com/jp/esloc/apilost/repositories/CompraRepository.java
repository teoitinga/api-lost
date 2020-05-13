package br.com.jp.esloc.apilost.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.jp.esloc.apilost.models.Compra;

public interface CompraRepository extends JpaRepository<Compra, Integer> {
	@Query("select c from Compra c where c.fkCliente.id = :fkCliente order by c.dataCompra DESC")
	Page<Compra> findByClienteId(Pageable page, @Param("fkCliente") Integer fkClienteId);
	@Query("select c from Compra c where ((c.fkCliente.id = :fkCliente) AND (c.acertadoEm is null)) order by c.dataCompra DESC")
	Page<Compra> findNoQuitByClienteId(Pageable page, @Param("fkCliente") Integer fkClienteId);
	@Query("select c from Compra c where  ((c.fkCliente.id = :fkCliente) AND (c.acertadoEm is not null)) order by c.dataCompra DESC")
	Page<Compra> findQuitByClienteId(Pageable page, @Param("fkCliente") Integer fkClienteId);
	@Query("select c from Compra c where  (c.entreguePor = :fkUser) order by c.dataCompra DESC")
	Page<Compra> findQuitByUserId(Pageable page, @Param("fkUser") Integer fkUser);
	@Query("Select c from Compra c join fetch c.itens where c.id =:id")
	Optional<Compra> findByIdFetchItens(@Param("id") Integer id);
	@Query("Select c from Compra c join fetch c.itens where c.fkCliente.id =:id")
	Optional<Compra> findCompraByClienteId(@Param("id") Integer id);
}