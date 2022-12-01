package io.ucb.rafael.bluefood.infrastructure.web.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.ucb.rafael.bluefood.application.service.ClienteService;
import io.ucb.rafael.bluefood.application.service.RestauranteService;
import io.ucb.rafael.bluefood.application.service.ValidationException;
import io.ucb.rafael.bluefood.domain.cliente.Cliente;
import io.ucb.rafael.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import io.ucb.rafael.bluefood.domain.restaurante.Restaurante;

@Controller
@RequestMapping(path = "/public")
public class PublicController {
	
	@Autowired
	private ClienteService clienteService;

	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;

	@GetMapping("/cliente/new")
	public String newCliente(Model model) {
		model.addAttribute("cliente", new Cliente());
		ControllerHelper.funcSetEditMode(model, false);
		return "cliente-cadastro";
	}
	
	@PostMapping(path = "/cliente/save")
	public String saveCliente(
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
	
	@GetMapping("/restaurante/new")
	public String funcNewRestaurante(Model model) {
		model.addAttribute("restaurante", new Restaurante());
		ControllerHelper.funcSetEditMode(model, false);
		
		ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
		
		return "restaurante-cadastro";
	}
	
	@PostMapping(path = "/restaurante/save")
	public String funcSaveRestaurante(@ModelAttribute("restaurante") @Valid Restaurante restaurante,
			Errors errors,
			Model model) {
		
		if (!errors.hasErrors()) {
			
			try {
				restauranteService.funcSaveRestaurante(restaurante);
				model.addAttribute("msg", "Restaurante gravado com sucsso");
			} catch (ValidationException e) {
				errors.rejectValue("email", null, e.getMessage());
			}
		}
		
		ControllerHelper.funcSetEditMode(model, false);
		ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
		return "restaurante-cadastro";
	}
}
