package br.com.jp.esloc.apilost.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import br.com.jp.esloc.apilost.domain.FluxoMensalVendas;
import br.com.jp.esloc.apilost.models.Profile;

public interface ProfileService {

	Profile getProfileData();
	boolean isContaining();
	LocalDate getDataExpiracao(LocalDateTime ultupdate, Integer tempoexpira);
	public boolean isExpired();
	List<FluxoMensalVendas> fluxoMensalVendas();
	List<FluxoMensalVendas> fluxoMensalCreditos();
	BigDecimal getTotalDebitos();
	BigDecimal getTotalDebitosPorUsuario(Integer idUsuario);
	BigDecimal getTotalQuitado();
	BigDecimal getTotalQuitadoPorUsuario(Integer idUsuario);
	BigDecimal getTotalVendido();
	BigDecimal getTotalVendidoPorUsuario(Integer idUsuario);
}
