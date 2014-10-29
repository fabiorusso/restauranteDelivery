package br.com.ediel.fabio.restaurante.delivery.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1347391541668378311L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long numero;

	@Enumerated(EnumType.STRING)
	private StatusPedido status;

	@Column(name = "valor_total")
	private Double valorTotal;

	private String observacao;

	@ManyToMany
	@JoinTable(name = "itens_pedido", joinColumns = { @JoinColumn(name = "fk_id_pedido") }, inverseJoinColumns = { @JoinColumn(name = "fk_id_item") })
	private Set<Item> itens;

	@OneToMany(mappedBy = "pedido")
	private Set<Reclamacao> reclamacoes;

	public Pedido() {
		itens=new HashSet<>();
		status=StatusPedido.ABERTO;
	}
	
	public Set<Reclamacao> getReclamacoes() {
		return reclamacoes;
	}

	public void setReclamacoes(Set<Reclamacao> reclamacoes) {
		this.reclamacoes = reclamacoes;
	}

	public Set<Item> getItens() {
		if(itens==null)
			itens=new HashSet<>();
		
		return itens;
	}

	public void setItens(Set<Item> itens) {
		this.itens = itens;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	//Calcular total
	public Double calcularTotalPedido() {
		valorTotal=0.0;
		for(Item i: getItens()) {
			valorTotal+=i.getValor();
		}
		
		return valorTotal;
	}

}
