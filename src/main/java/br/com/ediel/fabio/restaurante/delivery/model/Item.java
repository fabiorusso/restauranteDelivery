package br.com.ediel.fabio.restaurante.delivery.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item implements Serializable {
	
	private static final long serialVersionUID = -7282723636767440050L;

	@Id
	private Long codigo;
	
	private String descricao;
	
	private Double valor;
	
	@ManyToMany(mappedBy = "itens")
	private Set<Pedido> pedidos;
	
	public Set<Pedido> getPedidos() {
		return pedidos;
	}
	public void setPedidos(Set<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	@Override
	public String toString() {
		return getCodigo() + " - " + getDescricao() + " - " + getValor();
	}
	
}
