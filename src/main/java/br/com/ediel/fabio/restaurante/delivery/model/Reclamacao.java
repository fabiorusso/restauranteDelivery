package br.com.ediel.fabio.restaurante.delivery.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "reclamacao")
public class Reclamacao implements Serializable {
	
	private static final long serialVersionUID = 8950387592355834696L;

	@Id
	private Long id;
	
	private String descricao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_hora")
	private Date dataHora;
	
	@ManyToOne
	private Pedido pedido;
	
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDataHora() {
		return dataHora;
	}
	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
	
}
