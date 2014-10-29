package br.com.ediel.fabio.restaurante.delivery.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.ediel.fabio.restaurante.delivery.model.Pedido;
import br.com.ediel.fabio.restaurante.delivery.model.StatusPedido;

public class PedidoDAOJPA extends AbstractDAO<Pedido, Long> implements
		PedidoDAO {
	
	public PedidoDAOJPA(EntityManager entityManager) {
		this(entityManager, Pedido.class);
	}
	
	@Override
	public List<Pedido> buscarPorStatus(StatusPedido status)
			throws DAOException {
		final String hql = "select pedido from Pedido as pedido where pedido.status=?1";
		
		Query query = entityManager.createQuery(hql);
		query.setParameter(1, status);
		
		return query.getResultList();
	}

	private PedidoDAOJPA(EntityManager entityManager, Class<Pedido> clazz) {
		super(entityManager, clazz);
	}

}
