package io.ucb.rafael.bluefood.domain.restaurante;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Integer>{
	
	public Restaurante findByEmail(String email);
	
	public List<Restaurante> findByNomeIgnoringCaseContaining(String nome);
	
	public List<Restaurante> findByCategorias_Id(Integer categoriaId);
	
}
