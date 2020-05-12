package br.com.jp.esloc.apilost.builders;

import org.springframework.beans.factory.annotation.Autowired;

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


}
