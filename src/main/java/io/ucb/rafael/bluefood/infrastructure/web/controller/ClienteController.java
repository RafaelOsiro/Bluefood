package io.ucb.rafael.bluefood.infrastructure.web.controller;

import java.util.List;

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

import io.ucb.rafael.bluefood.application.service.ClienteService;
import io.ucb.rafael.bluefood.application.service.ValidationException;
import io.ucb.rafael.bluefood.domain.cliente.Cliente;
import io.ucb.rafael.bluefood.domain.cliente.ClienteRepository;
import io.ucb.rafael.bluefood.domain.restaurante.CategoriaRestaurante;
import io.ucb.rafael.bluefood.domain.restaurante.CategoriaRestauranteRepository;
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
	
	@GetMapping(path = "/home")
	public String home(Model model) {
		
		List<CategoriaRestaurante> categorias = categoriaRestauranteRepository.findAll(Sort.by("nome"));
		model.addAttribute(categorias);
		
		return "cliente-home";
	}
	
	@GetMapping("/edit")
	public String edit(Model model) {
		
		Integer clienteId = SecurityUtils.loggedCliente().getId();
		Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();
		model.addAttribute("cliente", cliente);
		ControllerHelper.funcSetEditMode(model, true);
		
		return "cliente-cadastro";
	}
	
	@PostMapping(path = "/save")
	public String save(
			@ModelAttribute("cliente") @Valid Cliente cliente, 
			Errors errors, 
			Model model) {

		if (!errors.hasErrors()) {
			try {
				clienteService.funcSaveCliente(cliente);
				model.addAttribute("msg", "Cliente gravado com sucesso!");
				
			} catch (ValidationException e) {
				errors.rejectValue("email", null, e.getMessage());
			}
		}

		ControllerHelper.funcSetEditMode(model, false);
		return "cliente-cadastro";
	}
	
}