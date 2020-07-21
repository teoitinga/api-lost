package br.com.jp.esloc.apilost.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.jp.esloc.apilost.models.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer>{
	@Query(value = "SELECT * FROM Perfil p LIMIT 1", nativeQuery = true)
	Optional<Profile> findFirst();
	@Query(value = "SELECT month(data_compra) as mes, year(data_compra) as ano, sum(valor_compra) as valor, count(valor_compra) as qtd FROM lost.compra where valor_compra > 0 group by month(data_compra), year(data_compra) order by ano desc, mes desc LIMIT 13", nativeQuery = true)
	List<Object[]> fluxoMensalDeVendas();
	@Query(value = "SELECT month(data_compra) as mes, year(data_compra) as ano, sum(valor_compra) as valor, count(valor_compra) as qtd FROM lost.compra where valor_compra <= 0 group by month(data_compra), year(data_compra) order by ano desc, mes desc LIMIT 13", nativeQuery = true)
	List<Object[]> fluxoMensalDeCreditos();
	
	@Query(value = "select sum(valor_compra) from lost.compra where valor_compra < 0", nativeQuery = true)
	BigDecimal TotalQuitado();
	@Query(value = "select sum(valor_compra) from lost.compra where valor_compra >= 0", nativeQuery = true)
	BigDecimal TotalVendido();
	@Query(value = "select sum(valor_compra) from lost.compra", nativeQuery = true)
	BigDecimal TotalDebitos();
	
	@Query(value = "select sum(valor_compra) from lost.compra where valor_compra < 0 and lost.compra.entregue_por = :id", nativeQuery = true)
	BigDecimal TotalQuitadoPorUsuario(@Param("id") Integer id);
	@Query(value = "select sum(valor_compra) from lost.compra where valor_compra >= 0 and lost.compra.entregue_por = :id and month(data_compra)=month(now()) and year(data_compra)=year(now())", nativeQuery = true)
	BigDecimal TotalVendidoPorUsuario(@Param("id") Integer id);
	@Query(value = "select sum(valor_compra) from lost.compra where lost.compra.entregue_por = :id", nativeQuery = true)
	BigDecimal TotalDebitosPorUsuario(@Param("id") Integer id);
}
