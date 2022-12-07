package io.ucb.rafael.bluefood.domain.pagamento;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.ucb.rafael.bluefood.domain.pedido.Pedido;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "pagamento")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pagamento implements Serializable {

	@Id
	private Integer id;
	
	@NotNull
	@OneToOne
	@MapsId
	private Pedido pedido;
	
	@NotNull
	private LocalDateTime data;
	
	@Column(name = "num_cartao_final")
	@NotNull
	@Size(min = 4, max = 4)
	private String numCartaoFinal;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private BandeiraCartao bandeiraCartao;
	
	public void definirNumeroEBandeira(String numCartao) {
		numCartaoFinal = numCartao.substring(12);
		bandeiraCartao = getBandeira(numCartao);
	}
	
	private BandeiraCartao getBandeira(String numCartao) {
		if (numCartao.startsWith("4")) {
			return BandeiraCartao.VISA;
		}
		
		if (numCartao.startsWith("5")) {
			return BandeiraCartao.MASTER;
		}
		
		if (numCartao.startsWith("301") || numCartao.startsWith("305") || numCartao.startsWith("36") || numCartao.startsWith("38")) {
			return BandeiraCartao.DINERS;
		}
		
		if (numCartao.startsWith("636368") || numCartao.startsWith("636369") || numCartao.startsWith("438935") || numCartao.startsWith("504175") ||
			numCartao.startsWith("451416") || numCartao.startsWith("636297") || numCartao.startsWith("5067") || numCartao.startsWith("4576") || 
			numCartao.startsWith("4011") || numCartao.startsWith("506699")) {
			return BandeiraCartao.ELO;
		}
		
		if (numCartao.startsWith("34") || numCartao.startsWith("37")) {
			return BandeiraCartao.AMEX;
		}
		
		if (numCartao.startsWith("6011") || numCartao.startsWith("622") || numCartao.startsWith("64") || numCartao.startsWith("65")) {
			return BandeiraCartao.DISCOVER;
		}
		
		if (numCartao.startsWith("50")) {
			return BandeiraCartao.AURA;
		}
		
		if (numCartao.startsWith("35")) {
			return BandeiraCartao.JCB;
		}
		
		if (numCartao.startsWith("38") || numCartao.startsWith("60")) {
			return BandeiraCartao.HIPERCARD;
		}
		
		return BandeiraCartao.OUTROS;
	}
}
