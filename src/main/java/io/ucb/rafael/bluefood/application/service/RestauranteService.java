package io.ucb.rafael.bluefood.application.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ucb.rafael.bluefood.domain.cliente.Cliente;
import io.ucb.rafael.bluefood.domain.cliente.ClienteRepository;
import io.ucb.rafael.bluefood.domain.restaurante.Restaurante;
import io.ucb.rafael.bluefood.domain.restaurante.RestauranteComparator;
import io.ucb.rafael.bluefood.domain.restaurante.RestauranteRepository;
import io.ucb.rafael.bluefood.domain.restaurante.SearchFilter;
import io.ucb.rafael.bluefood.domain.restaurante.SearchFilter.SearchType;
import io.ucb.rafael.bluefood.util.SecurityUtils;

@Service
public class RestauranteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private ImageService imageService;
	
	@Transactional
	public void saveRestaurante(Restaurante restaurante) throws ValidationException {
		
		if(!funcValidateEmail(restaurante.getEmail(), restaurante.getId())) {
			throw new ValidationException("O e-mail está duplicado");
		}
		
		if (restaurante.getId() != null) {
			Restaurante restauranteDB = restauranteRepository.findById(restaurante.getId()).orElseThrow();
			restaurante.setSenha(restauranteDB.getSenha());
			
		// Gravação e criptografando a senha quando for usuário novo
		} else {
			restaurante.encryptPassword();
			restaurante = restauranteRepository.save(restaurante);
			restaurante.setLogotipoFileName();
			imageService.uploadLogtipo(restaurante.getLogotipoFile(), restaurante.getLogotipo());
		}
	}
	
	private boolean funcValidateEmail(String email, Integer id) {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if (cliente != null) {
			return false;
		}
		
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
	
	public List<Restaurante> search(SearchFilter filter) {
		
		List<Restaurante> restaurantes;
		
		if (filter.getSearchType() == SearchType.Texto) {
			restaurantes = restauranteRepository.findByNomeIgnoringCaseContaining(filter.getTexto());
		} else if (filter.getSearchType() == SearchType.Categoria) {
			restaurantes = restauranteRepository.findByCategorias_Id(filter.getCategoriaId());
		} else {
			throw new IllegalStateException("O tipo de busca " + filter.getSearchType() + " não é suportado");
		}
		
		Iterator<Restaurante> it = restaurantes.iterator();
		
		while (it.hasNext()) {
			Restaurante restaurante = it.next();
			double taxaEntrega = restaurante.getTaxaEntrega().doubleValue();
			
			if (filter.isEntregaGratis() && taxaEntrega > 0
					|| !filter.isEntregaGratis() && taxaEntrega == 0) {
				it.remove();
			} 
		}
		
		RestauranteComparator comparator = new RestauranteComparator(filter, SecurityUtils.loggedCliente().getCep());
		restaurantes.sort(comparator);
		
		return restaurantes;
	}
}
