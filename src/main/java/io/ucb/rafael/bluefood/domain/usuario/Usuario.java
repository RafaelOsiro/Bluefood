package io.ucb.rafael.bluefood.domain.usuario;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.ucb.rafael.bluefood.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@MappedSuperclass
public class Usuario implements Serializable {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "O NOME não pode ser vazio")
	@Size(max = 80, message = "O NOME é muito grande")
	@NotNull(message = "O NOME não pode ser nulo")
	private String nome;
	
	@NotBlank(message = "O E-MAIL não pode ser vazio")
	@Size(max = 60, message = "O E-MAIL é muito grande")
	@Email(message = "O E-MAIL é invalido")
	@NotNull(message = "O E-MAIL não pode ser nulo")
	private String email;
	
	@NotBlank(message = "A SENHA não pode ser vazia")
	@Size(max = 60, message = "A SENHA é muito grande")
	@NotNull(message = "A SENHA não pode ser nula")
	private String senha;
	
	@NotBlank(message = "O TELEFONE não pode ser vazio")
	@Pattern(regexp = "[0-9]{10,11}", message = "O TELEFONE possui formato invalido")
	@Column(length = 11)
	@NotNull(message = "O TELEFONE não pode ser nulo")
	private String telefone;
	
	public void funcEncryptPassword() {
		this.senha = StringUtils.funcEncrypt(this.senha);
	}
}
