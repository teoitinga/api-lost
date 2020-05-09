package br.com.jp.esloc.apilost.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.jp.esloc.apilost.domain.CompraDto;
import br.com.jp.esloc.apilost.domain.DetalheCompraDto;
import br.com.jp.esloc.apilost.domain.PersonaDto;
import br.com.jp.esloc.apilost.exceptions.NoItensOnPurchase;
import br.com.jp.esloc.apilost.exceptions.PersonaNotFound;
import br.com.jp.esloc.apilost.models.Compra;
import br.com.jp.esloc.apilost.models.Detalhecompra;
import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.services.CompraService;
import br.com.jp.esloc.apilost.services.DetalheCompraService;
import br.com.jp.esloc.apilost.services.PersonaService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompraBuild {

	@Autowired
	private PersonaService personaService;
	@Autowired
	private CompraService compraService;
	@Autowired
	private DetalheCompraService detalheCompraService;

	private Persona cliente;
	private Persona usuario;
	private Compra compra;
	private Detalhecompra item;
	private List<RuntimeException> errors = new ArrayList<>();

	public void create(Integer idCliente, Integer idUser, CompraDto compra, DetalheCompraDto item) {
		// verifica dados do cliente
		this.cliente = this.personaService.findById(idCliente);
		verifyCliente(this.cliente);
		
		// verifica dados do usuario
		this.usuario = this.personaService.findById(idUser);

		// verifica dados da lista de compra
		verifyItens(item);
	
		ModelMapper modelMapper = new ModelMapper();

		this.item = modelMapper.map(item, Detalhecompra.class);

		// configura dados da compra caso não exista
		Optional<Compra> c = Optional.ofNullable(this.compraService.findById(compra.getId()));
		if (!c.isPresent()) {
			this.compra = new Compra();
			this.compra.setDataCompra(compra.getDataCompra());
			this.compra.setDebAtual(this.cliente.getDebito() + compra.getValorCompra());
			this.compra.setEntregueA(compra.getEntregueA());
			this.compra.setEntreguePor(this.usuario.getId());
			this.compra.setFkCliente(this.cliente);
		}
		this.compra = c.get();

		// calculando o valor total da compra através da lista de itens informado
		/**
		 * double valor = this.itens.stream() .filter(item ->item!=null&&
		 * item.getVltotal()!=null).mapToDouble(Detalhecompra::getVltotal) .sum();
		 **/

		// configurando o valor da compra
		this.compra.setValorCompra(this.compra.getValorCompra() + this.item.getVltotal());

		// atualiza debitos do cliente
		this.cliente.setDebito(this.cliente.getDebito() + this.compra.getValorCompra());
		// atualizando registro de cliente no banco de dados
		this.personaService.save(this.cliente);

		// registra compra
		log.info("Registrando compra {}", this.compra);
		this.compra = this.compraService.save(this.compra);
		// this registra item de compra
		log.info("Registrando item {}", this.item);
		this.detalheCompraService.save(this.item);
		// retorna ok
	}

	private void verifyCliente(Persona cliente2) {
		if (item == null) {
			errors.add(new PersonaNotFound("Cliente não encontrado."));
		}
		
	}

	private void verifyItens(DetalheCompraDto item) {
		// TODO Auto-generated method stub

		if (item == null) {
			errors.add(new NoItensOnPurchase("Não há itens cadastrados neste compra"));
		}
	}
}
