package br.com.ediel.fabio.restaurante.delivery.manager;

import javax.persistence.EntityManager;

import br.com.ediel.fabio.restaurante.delivery.dao.DAOException;
import br.com.ediel.fabio.restaurante.delivery.dao.PedidoDAO;
import br.com.ediel.fabio.restaurante.delivery.dao.PedidoDAOJPA;
import br.com.ediel.fabio.restaurante.delivery.model.Pedido;

public class PedidoManagerImpl implements PedidoManager {
	
	private final PedidoDAO dao;
	
	public PedidoManagerImpl(EntityManager em) {
			dao = new PedidoDAOJPA(em);
	}
	
	@Override
	public void realizarPedido(Pedido pedido) throws ManagerException {
		
		try {
			dao.save(pedido);
		} catch (DAOException e) {
			throw new ManagerException(e);
		}		
	}
	
}
