package io.ucb.rafael.bluefood.infrastructure.web.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.ucb.rafael.bluefood.application.service.ClienteService;
import io.ucb.rafael.bluefood.application.service.RestauranteService;
import io.ucb.rafael.bluefood.application.service.ValidationException;
import io.ucb.rafael.bluefood.domain.cliente.Cliente;
import io.ucb.rafael.bluefood.domain.cliente.ClienteRepository;
import io.ucb.rafael.bluefood.domain.pedido.Pedido;
import io.ucb.rafael.bluefood.domain.pedido.PedidoRepository;
import io.ucb.rafael.bluefood.domain.restaurante.CategoriaRestaurante;
import io.ucb.rafael.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import io.ucb.rafael.bluefood.domain.restaurante.ItemCardapio;
import io.ucb.rafael.bluefood.domain.restaurante.ItemCardapioRepository;
import io.ucb.rafael.bluefood.domain.restaurante.Restaurante;
import io.ucb.rafael.bluefood.domain.restaurante.RestauranteRepository;
import io.ucb.rafael.bluefood.domain.restaurante.SearchFilter;
import io.ucb.rafael.bluefood.util.SecurityUtils;

@Controller
@RequestMapping(path = { "/cliente", "/" })
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private ItemCardapioRepository itemCardapioRespository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@GetMapping(path = "/home")
	public String home(Model model) {
		
		List<CategoriaRestaurante> categorias = categoriaRestauranteRepository.findAll(Sort.by("nome"));
		model.addAttribute("categorias", categorias);
		model.addAttribute("searchFilter", new SearchFilter());
		
		List<Pedido>pedidos = pedidoRepository.listPedidosByCliente(SecurityUtils.loggedCliente().getId());
		model.addAttribute("pedidos", pedidos);
		
		return "cliente-home";
	}
	
	@GetMapping("/edit")
	public String edit(Model model) {
		
		Integer clienteId = SecurityUtils.loggedCliente().getId();
		Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();
		model.addAttribute("cliente", cliente);
		ControllerHelper.setEditMode(model, true);
		
		return "cliente-cadastro";
	}
	
	@PostMapping(path = "/save")
	public String save(
			@ModelAttribute("cliente") @Valid Cliente cliente, 
			Errors errors, 
			Model model) {

		if (!errors.hasErrors()) {
			try {
				clienteService.saveCliente(cliente);
				model.addAttribute("msg", "Cliente gravado com sucesso!");
				
			} catch (ValidationException e) {
				errors.rejectValue("email", null, e.getMessage());
			}
		}

		ControllerHelper.setEditMode(model, false);
		return "cliente-cadastro";
	}
	
	@GetMapping(path = "/search")
	public String search(
			@ModelAttribute("searchFilter") SearchFilter filter,
			@RequestParam(value = "cmd", required = false) String command,
			Model model) {
		
		filter.processFilter(command);
		
		List <Restaurante> restaurantes = restauranteService.search(filter);
		model.addAttribute("restaurantes", restaurantes);
		
		ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
		
		model.addAttribute("searchFilter", filter);
		model.addAttribute("cep", SecurityUtils.loggedCliente().getCep());
		
		return "cliente-busca";
	}
	
	@GetMapping(path = "/restaurante")
	public String viewRestaurante(
			@RequestParam("restauranteId") Integer restauranteId,
			@RequestParam(value = "categoria", required = false) String categoria,
			Model model) {
		
		Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow(NoSuchElementException::new);
		model.addAttribute("restaurante", restaurante);
		model.addAttribute("cep", SecurityUtils.loggedCliente().getCep());
		
		List<String> categorias = itemCardapioRespository.findCategorias(restauranteId);
		model.addAttribute("categorias", categorias);
		
		List<ItemCardapio> itensCardapioDestaque;
		List<ItemCardapio> itensCardapioNaoDestaque;
		
		if (categoria == null) {
			itensCardapioDestaque = itemCardapioRespository.findByRestaurante_IdAndDestaqueOrderByNome(restauranteId, true);
			itensCardapioNaoDestaque = itemCardapioRespository.findByRestaurante_IdAndDestaqueOrderByNome(restauranteId, false);
			
		} else {
			itensCardapioDestaque = itemCardapioRespository.findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(restauranteId, true, categoria);
			itensCardapioNaoDestaque = itemCardapioRespository.findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(restauranteId, false, categoria);
		}
		
		model.addAttribute("itensCardapioDestaque", itensCardapioDestaque);
		model.addAttribute("itensCardapioNaoDestaque", itensCardapioNaoDestaque);
		model.addAttribute("categoriaSelecionada", categoria);
		
		return "cliente-restaurante";
	}
	
}