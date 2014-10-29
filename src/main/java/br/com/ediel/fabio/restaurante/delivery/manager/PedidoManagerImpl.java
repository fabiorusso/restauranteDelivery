package br.com.ediel.fabio.restaurante.delivery.manager;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ediel.fabio.restaurante.delivery.dao.DAOException;
import br.com.ediel.fabio.restaurante.delivery.dao.PedidoDAO;
import br.com.ediel.fabio.restaurante.delivery.dao.PedidoDAOJPA;
import br.com.ediel.fabio.restaurante.delivery.dao.ReclamacaoDAO;
import br.com.ediel.fabio.restaurante.delivery.dao.ReclamacaoDAOJPA;
import br.com.ediel.fabio.restaurante.delivery.model.Pedido;
import br.com.ediel.fabio.restaurante.delivery.model.Reclamacao;
import br.com.ediel.fabio.restaurante.delivery.model.StatusPedido;

public class PedidoManagerImpl implements PedidoManager {

	private final PedidoDAO dao;
	private final ReclamacaoDAO reclamacaoDAO;

	public PedidoManagerImpl(EntityManager em) {
		dao = new PedidoDAOJPA(em);
		reclamacaoDAO = new ReclamacaoDAOJPA(em);
	}

	@Override
	public void realizarPedido(Pedido pedido) throws ManagerException {

		try {
			dao.save(pedido);
		} catch (DAOException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<Pedido> buscarTodosEmAberto() throws ManagerException {
		try {
			return dao.buscarPorStatus(StatusPedido.ABERTO);
		} catch (DAOException e) {
			throw new ManagerException(e);
		}

	}

	@Override
	public Pedido buscarPorNumero(Long numero) throws ManagerException {
		try {
			return dao.find(numero);
		} catch (DAOException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public void atenderPedido(Long id) throws ManagerException {
		try {
			Pedido pedido = dao.find(id);
			pedido.setStatus(StatusPedido.ENTREGUE);
			dao.save(pedido);
		} catch (DAOException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public void registrarReclamacao(Reclamacao reclamacao, Long idPedido)
			throws ManagerException {
		try {
			reclamacaoDAO.save(reclamacao);
			Pedido pedido = dao.find(idPedido);
			pedido.getReclamacoes().add(reclamacao);

			dao.update(pedido);
		} catch (DAOException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public Double calcularFretePedido(Long idPedido, Double distanciaKm)
			throws ManagerException {
		try {
			Double distBaseKm = 10.0;
			Double valorBase10Km = 2.30;

			Double frete = (distanciaKm % distBaseKm) * valorBase10Km;

			Pedido pedido = dao.find(idPedido);

			pedido.setFrete(frete);

			dao.update(pedido);
			return frete;
		} catch (DAOException e) {
			throw new ManagerException(e);
		}
	}

}
