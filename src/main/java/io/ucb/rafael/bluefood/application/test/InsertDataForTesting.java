package io.ucb.rafael.bluefood.application.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import io.ucb.rafael.bluefood.domain.cliente.Cliente;
import io.ucb.rafael.bluefood.domain.cliente.ClienteRepository;
import io.ucb.rafael.bluefood.domain.restaurante.CategoriaRestaurante;
import io.ucb.rafael.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import io.ucb.rafael.bluefood.domain.restaurante.Restaurante;
import io.ucb.rafael.bluefood.domain.restaurante.RestauranteRepository;
import io.ucb.rafael.bluefood.util.StringUtils;

@Component
public class InsertDataForTesting {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;

	

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		clientes();
		Restaurante[] restaurantes = restaurantes();
	}
	
	private Restaurante[] restaurantes() {
		List<Restaurante> restaurantes = new ArrayList<>();

		CategoriaRestaurante categoriaPizza = categoriaRestauranteRepository.findById(1).orElseThrow(NoSuchElementException::new);
		CategoriaRestaurante categoriaSanduiche = categoriaRestauranteRepository.findById(2).orElseThrow(NoSuchElementException::new);
		CategoriaRestaurante categoriaSobremesa = categoriaRestauranteRepository.findById(5).orElseThrow(NoSuchElementException::new);
		CategoriaRestaurante categoriaJapones = categoriaRestauranteRepository.findById(6).orElseThrow(NoSuchElementException::new);
		
		Restaurante r = new Restaurante();
		r.setNome("Bubger King");
		r.setEmail("contato@bubgerking.com.br");
		r.setSenha(StringUtils.funcEncrypt("r"));
		r.setCnpj("01234567890123");
		r.setTaxaEntrega(BigDecimal.valueOf(3.20));
		r.setTelefone("01234567890");
		r.getCategorias().add(categoriaSanduiche);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0001-logo.png");
		r.setTempoEntregaBase(30);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		r = new Restaurante();
		r.setNome("Mc Naldo's");
		r.setEmail("contato@mcnaldos.com.br");
		r.setSenha(StringUtils.funcEncrypt("r"));
		r.setCnpj("12345678901234");
		r.setTaxaEntrega(BigDecimal.valueOf(4.50));
		r.setTelefone("12345678901");
		r.getCategorias().add(categoriaSanduiche);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0002-logo.png");
		r.setTempoEntregaBase(25);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		r = new Restaurante();
		r.setNome("Sbubby");
		r.setEmail("contato@sbubby.com.br");
		r.setSenha(StringUtils.funcEncrypt("r"));
		r.setCnpj("23456789012345");
		r.setTaxaEntrega(BigDecimal.valueOf(12.20));
		r.setTelefone("23456789012");
		r.getCategorias().add(categoriaSanduiche);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0003-logo.png");
		r.setTempoEntregaBase(38);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		r = new Restaurante();
		r.setNome("Pizza brut");
		r.setEmail("contato@pizzabrut.com.br");
		r.setSenha(StringUtils.funcEncrypt("r"));
		r.setCnpj("34567890123456");
		r.setTaxaEntrega(BigDecimal.valueOf(9.80));
		r.setTelefone("34567890123");
		r.getCategorias().add(categoriaPizza);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0004-logo.png");
		r.setTempoEntregaBase(22);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		r = new Restaurante();
		r.setNome("Wiki Japa");
		r.setEmail("contato@wikijapa.com.br");
		r.setSenha(StringUtils.funcEncrypt("r"));
		r.setCnpj("45678901234567");
		r.setTaxaEntrega(BigDecimal.valueOf(14.90));
		r.setTelefone("45678901234");
		r.getCategorias().add(categoriaJapones);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0005-logo.png");
		r.setTempoEntregaBase(19);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		Restaurante[] array = new Restaurante[restaurantes.size()];
		return restaurantes.toArray(array);
	}
	
	private Cliente[] clientes() {
		List<Cliente> clientes = new ArrayList<>();
		
		Cliente c = new Cliente();
		c.setNome("João Silva");
		c.setEmail("joaosilva@hotmail.com");
		c.setSenha(StringUtils.funcEncrypt("c"));
		c.setCep("01234567");
		c.setCpf("01234567890");
		c.setTelefone("01234567890");
		clientes.add(c);
		clienteRepository.save(c);
		
		c = new Cliente();
		c.setNome("Maria Torres");
		c.setEmail("mariatorres@hotmail.com");
		c.setSenha(StringUtils.funcEncrypt("c"));
		c.setCep("12345678");
		c.setCpf("12345678901");
		c.setTelefone("12345678901");
		clientes.add(c);
		clienteRepository.save(c);
		
		Cliente[] array = new Cliente[clientes.size()];
		return clientes.toArray(array);
	}
}
