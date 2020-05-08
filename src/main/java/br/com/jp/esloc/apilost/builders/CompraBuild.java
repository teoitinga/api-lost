package br.com.jp.esloc.apilost.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.jp.esloc.apilost.domain.CompraDto;
import br.com.jp.esloc.apilost.domain.DetalheCompraDto;
import br.com.jp.esloc.apilost.domain.PersonaDto;
import br.com.jp.esloc.apilost.exceptions.NoItensOnPurchase;
import br.com.jp.esloc.apilost.models.Compra;
import br.com.jp.esloc.apilost.models.Detalhecompra;
import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.services.CompraService;
import br.com.jp.esloc.apilost.services.PersonaService;

public class CompraBuild {
	@Autowired
	private PersonaService personaService;
	@Autowired
	private CompraService compraService;
	
	private Persona cliente;
	private Persona usuario;
	private Compra compra;
	private Detalhecompra item;
	private List<Detalhecompra> itens;
	private List<RuntimeException> errors = new ArrayList<>();
	
	public void create(PersonaDto cliente, PersonaDto user, CompraDto compra, List<DetalheCompraDto> itens) {
		//verifica dados do cliente
		this.cliente = this.personaService.findById(cliente.getId());
		//verifica dados do usuario
		this.usuario = this.personaService.findById(user.getId());
		
		//verifica dados da lista de compra
		verifyItens(itens);

		//configura dados da compra caso não exista
		Optional<Compra> c = Optional.ofNullable(this.compraService.findById(compra.getId()));
		if(!c.isPresent()) {
			this.compra = new Compra();
			this.compra.setDataCompra(compra.getDataCompra());
			this.compra.setDebAtual(this.cliente.getDebito() + compra.getValorCompra());
			this.compra.setEntregueA(compra.getEntregueA());
			this.compra.setEntreguePor(this.usuario.getId());
			this.compra.setFkCliente(this.cliente);
		}
		this.compra = c.get();
		
		//calculando o valor total da compra através da lista de itens informado
		double valor = this.itens.stream()
				.filter(item ->item!=null&& 
				item.getVltotal()!=null).mapToDouble(Detalhecompra::getVltotal)
				.sum();
		
		//configurando o valor da compra
		this.compra.setValorCompra(valor);
		
		//atualiza debitos do cliente
		this.cliente.setDebito(this.cliente.getDebito() + this.compra.getValorCompra());
		//atualizando registro de cliente no banco de dados
		this.personaService.save(this.cliente);
		
		//registra compra
		this.compra = this.compraService.save(this.compra);
		
		//retorna ok
	}

	private void verifyItens(List<DetalheCompraDto> itens) {
		// TODO Auto-generated method stub
		if(itens.isEmpty()) {
			errors.add(new NoItensOnPurchase("Não há itens cadastrados neste compra"));
		}
		if(itens==null) {
			errors.add(new NoItensOnPurchase("Não há itens cadastrados neste compra"));
		}
	}
}
