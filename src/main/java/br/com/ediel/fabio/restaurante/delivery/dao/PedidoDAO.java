package br.com.ediel.fabio.restaurante.delivery.dao;

import java.util.List;

import br.com.ediel.fabio.restaurante.delivery.model.Pedido;
import br.com.ediel.fabio.restaurante.delivery.model.StatusPedido;

public interface PedidoDAO extends DAO<Pedido, Long> {
	List<Pedido> buscarPorStatus(StatusPedido status) throws DAOException;
}
