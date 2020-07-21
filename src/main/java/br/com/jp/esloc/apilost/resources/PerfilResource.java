package br.com.jp.esloc.apilost.resources;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jp.esloc.apilost.domain.FluxoMensalVendas;
import br.com.jp.esloc.apilost.domain.PainelDto;
import br.com.jp.esloc.apilost.domain.PerfilDto;
import br.com.jp.esloc.apilost.models.Profile;
import br.com.jp.esloc.apilost.services.ProfileService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/perfil")
@CrossOrigin(origins = "*")
public class PerfilResource {
	@Autowired
	private ProfileService profileService;
	
	@GetMapping
	@ApiOperation("Obter detalhes da sitema")
	@ApiResponses({ @ApiResponse(code = 200, message = "Sistema sem registro."),
			@ApiResponse(code = 404, message = "Não há licença para utilização deste sistema.") })
	public PerfilDto getProfile() {
		
		Profile perfil = this.profileService.getProfileData();
		PerfilDto dto = PerfilDto.builder()
				.estabelecimento(perfil.getEstabelecimento())
				.endereco(perfil.getEnderecoest())
				.cidade(perfil.getCidadeest())
				.telefone(perfil.getFoneest())
				.proprietario(perfil.getResponsavelest())
				.dataExpiracao(this.profileService.getDataExpiracao(perfil.getUltupdate(),perfil.getTempoexpira()))
				.valorMensal(perfil.getValormensal())
				.msgHead(perfil.getMsg1est())
				.msgBody(perfil.getMsg2est())
				.desenvolvedor(perfil.getDesenv())
				.contatoDesenvolvedor(perfil.getContatodesv())
				.emailSistema(perfil.getEmail())
				.driverBackup(perfil.getDriverbck())
				.build();
	
		return dto;

	}
	@GetMapping("/fluxoMensalVendas")
	@ApiOperation("Obtem o fluxo mensal de vendas")
	@ApiResponses({ @ApiResponse(code = 200, message = "Fluxo mensal."),
		@ApiResponse(code = 404, message = "Não há vendas registradas.") })
	public List<FluxoMensalVendas> getFluxoMensalVendas() {
		return this.profileService.fluxoMensalVendas();
		
	}
	@GetMapping("/fluxoMensalCreditos")
	@ApiOperation("Obtem o fluxo mensal de creditos")
	@ApiResponses({ @ApiResponse(code = 200, message = "Fluxo mensal."),
		@ApiResponse(code = 404, message = "Não há creditos registrados.") })
	public List<FluxoMensalVendas> getFluxoMensalCreditos() {
		return this.profileService.fluxoMensalCreditos();
		
	}
	@GetMapping("/painel/{idUsuario}")
	@ApiOperation("Obtem os dados de vendas")
	@ApiResponses({ @ApiResponse(code = 200, message = "Painel de vendas."),
		@ApiResponse(code = 404, message = "Não há registros.") })
	public PainelDto getPainel(@PathVariable Integer idUsuario) {
		//Integer idUsuario = 1;
		log.info("Buscando vendas de ID: " + idUsuario);
		return PainelDto.builder()
				.dataAtual(LocalDate.now())
				.totalDebitos(this.profileService.getTotalDebitos())
				.totalDebitosPorUsuario(this.profileService.getTotalDebitosPorUsuario(idUsuario))
				.totalQuitado(this.profileService.getTotalQuitado())
				.totalQuitadoPorUsuario(this.profileService.getTotalQuitadoPorUsuario(idUsuario))
				.totalVendido(this.profileService.getTotalVendido())
				.totalVendidoPorUsuario(this.profileService.getTotalVendidoPorUsuario(idUsuario))
				.build();
		
	}

}
