package io.ucb.rafael.bluefood.infrastructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.ucb.rafael.bluefood.application.ClienteService;
import io.ucb.rafael.bluefood.domain.cliente.Cliente;

@Controller
@RequestMapping(path = "/public")
public class PublicController {
	
	@Autowired
	private ClienteService clienteService;

	@GetMapping("/cliente/new")
	public String funcNewCliente(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "cliente-cadastro";
	}
	
	@PostMapping(path = "/cliente/save")
	public String funcSaveCliente(@ModelAttribute("cliente") Cliente cliente) {
		clienteService.funcSaveCliente(cliente);
		return "cliente-cadastro";
	}
}
