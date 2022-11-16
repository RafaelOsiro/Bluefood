package io.ucb.rafael.bluefood.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ucb.rafael.bluefood.domain.cliente.Cliente;
import io.ucb.rafael.bluefood.domain.cliente.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public void funcSaveCliente(Cliente cliente) throws ValidationException {
		
		if(!funcValidateEmail(cliente.getEmail(), cliente.getId())) {
			throw new ValidationException("O e-mail está duplicado");
		}
		
		clienteRepository.save(cliente);
	}
	
	private boolean funcValidateEmail(String email, Integer id) {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		
		// Verificar se o cliente existe no banco de dados
		if (cliente != null) {
			
			// Verificar se o ID do cliente é nulo
			if (id == null) {
				return false;
			}
			
			// Compara se o ID buscado do banco de dados é igual do email querendo ser registrado
			if(!cliente.getId().equals(id)) {
				return false;
			}
		}
		return true;
	}
}
