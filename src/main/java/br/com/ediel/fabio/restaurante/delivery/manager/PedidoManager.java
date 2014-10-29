package br.com.ediel.fabio.restaurante.delivery.manager;

import java.util.List;

import br.com.ediel.fabio.restaurante.delivery.model.Pedido;
import br.com.ediel.fabio.restaurante.delivery.model.Reclamacao;

public interface PedidoManager {
	void realizarPedido(Pedido pedido) throws ManagerException;
	void atenderPedido(Long id) throws ManagerException;
	List<Pedido> buscarTodosEmAberto() throws ManagerException;
	void registrarReclamacao(Reclamacao reclamacao,Long idPedido) throws ManagerException;
	Double calcularFretePedido(Long idPedido, Double distanciaKm) throws ManagerException;
	Pedido buscarPorNumero(Long numero) throws ManagerException;
}

