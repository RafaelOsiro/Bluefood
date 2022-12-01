package io.ucb.rafael.bluefood.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ucb.rafael.bluefood.domain.cliente.Cliente;
import io.ucb.rafael.bluefood.domain.cliente.ClienteRepository;
import io.ucb.rafael.bluefood.domain.restaurante.Restaurante;
import io.ucb.rafael.bluefood.domain.restaurante.RestauranteRepository;

@Service
public class RestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	public void funcSaveRestaurante(Restaurante restaurante) throws ValidationException {
		
		if(!funcValidateEmail(restaurante.getEmail(), restaurante.getId())) {
			throw new ValidationException("O e-mail está duplicado");
		}
		
		
		
		restauranteRepository.save(restaurante);
	}
	
	private boolean funcValidateEmail(String email, Integer id) {
		
		Restaurante restaurante = restauranteRepository.findByEmail(email);
		
		// Verificar se o cliente existe no banco de dados
		if (restaurante != null) {
			
			// Verificar se o ID do cliente é nulo
			if (id == null) {
				return false;
			}
			
			// Compara se o ID buscado do banco de dados é igual do email querendo ser registrado
			if(!restaurante.getId().equals(id)) {
				return false;
			}
		}
		return true;
	}
}
