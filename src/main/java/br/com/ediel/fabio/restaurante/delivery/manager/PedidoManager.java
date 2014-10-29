package br.com.ediel.fabio.restaurante.delivery.manager;

import br.com.ediel.fabio.restaurante.delivery.model.Pedido;

public interface PedidoManager {
	void realizarPedido(Pedido pedido) throws ManagerException;
}
