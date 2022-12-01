package io.ucb.rafael.bluefood.domain.cliente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.ucb.rafael.bluefood.domain.usuario.Usuario;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "cliente")
public class Cliente extends Usuario {

	@NotBlank(message = "O CPF não pode ser vazio")
	@Pattern(regexp = "[0-9]{11}", message = "O CPF possui formato inválido")
	@Column(length = 11, nullable = false)
	@NotNull(message = "O CPF não pode ser nulo")
	private String cpf;
	
	@NotBlank
	@Pattern(regexp = "[0-9]{8}", message = "O CEP possui formato invalido")
	@Column(length = 8, nullable = false)
	@NotNull(message = "O CEP não pode ser nulo")
	private String cep;
}
