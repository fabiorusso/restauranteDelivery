package br.com.ediel.fabio.restaurante.delivery.dao;

import javax.persistence.EntityManager;

import br.com.ediel.fabio.restaurante.delivery.model.Pedido;

public class PedidoDAOJPA extends AbstractDAO<Pedido, Long> implements
		PedidoDAO {
	
	public PedidoDAOJPA(EntityManager entityManager) {
		this(entityManager, Pedido.class);
	}
	
	private PedidoDAOJPA(EntityManager entityManager, Class<Pedido> clazz) {
		super(entityManager, clazz);
	}

}
