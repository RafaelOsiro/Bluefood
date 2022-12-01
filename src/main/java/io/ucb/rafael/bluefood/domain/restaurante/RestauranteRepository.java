package io.ucb.rafael.bluefood.domain.restaurante;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Integer>{
	
	public Restaurante findByEmail(String email);
	
}
