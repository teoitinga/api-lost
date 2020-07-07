package br.com.jp.esloc.apilost.services.impls;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jp.esloc.apilost.domain.FluxoMensalVendas;
import br.com.jp.esloc.apilost.exceptions.PerfilException;
import br.com.jp.esloc.apilost.models.Profile;
import br.com.jp.esloc.apilost.repositories.ProfileRepository;
import br.com.jp.esloc.apilost.services.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {
	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public boolean isContaining() {
		return this.profileRepository.findAll().size() > 0 ? true : false;
	}

	public LocalDate getDataExpiracao(LocalDateTime atualizacao, Integer tempoMeses) {
		LocalDate expira = atualizacao.plusMonths(tempoMeses).toLocalDate();

		return expira;
	}

	@Override
	public Profile getProfileData() {
		return this.profileRepository.findFirst()
				.orElseThrow(() -> new PerfilException("Sistema sem licença para utilização."));

	}

	@Override
	public List<FluxoMensalVendas> fluxoMensalVendas() {

		return this.profileRepository.fluxoMensalDeVendas().stream().map(fluxo -> toFluxoMensal(fluxo))
				.collect(Collectors.toList());

	}
	@Override
	public List<FluxoMensalVendas> fluxoMensalCreditos() {
		
		return this.profileRepository.fluxoMensalDeCreditos().stream().map(fluxo -> toFluxoMensal(fluxo))
				.collect(Collectors.toList());
		
	}

	@Override
	public boolean isExpired() {
		Profile perfil = this.getProfileData();
		LocalDate expiraEm = this.getDataExpiracao(perfil.getUltupdate(), perfil.getTempoexpira());
		if (expiraEm.isAfter(LocalDate.now())) {
			return false;
		}
		return true;
	}

	public FluxoMensalVendas toFluxoMensal(Object[] object) {
		FluxoMensalVendas fluxo = new FluxoMensalVendas();
		try {
			fluxo.setAno(Integer.parseInt(String.valueOf(object[1])));
			fluxo.setMes(Integer.parseInt(String.valueOf(object[0])));
			fluxo.setValor(BigDecimal.valueOf(Double.parseDouble(String.valueOf(object[2]))).abs().setScale(2, BigDecimal.ROUND_HALF_EVEN));
			fluxo.setQuantidade(Integer.parseInt(String.valueOf(object[3])));
		} catch (Exception e) {

		}

		return fluxo;
	}

	@Override
	public BigDecimal getTotalDebitos() {
		return this.profileRepository.TotalDebitos();
	}

	@Override
	public BigDecimal getTotalDebitosPorUsuario(Integer idUsuario) {
		return this.profileRepository.TotalDebitosPorUsuario(idUsuario);

	}

	@Override
	public BigDecimal getTotalQuitado() {
		return this.profileRepository.TotalQuitado().abs();

	}

	@Override
	public BigDecimal getTotalQuitadoPorUsuario(Integer idUsuario) {
		return this.profileRepository.TotalQuitadoPorUsuario(idUsuario);
	}

	@Override
	public BigDecimal getTotalVendido() {
		return this.profileRepository.TotalVendido();
	}

	@Override
	public BigDecimal getTotalVendidoPorUsuario(Integer idUsuario) {
		return this.profileRepository.TotalVendidoPorUsuario(idUsuario);
	}
}
