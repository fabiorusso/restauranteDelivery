package br.com.ediel.fabio.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.ApplyScriptAfter;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.ediel.fabio.restaurante.delivery.dao.AbstractDAO;
import br.com.ediel.fabio.restaurante.delivery.dao.DAO;
import br.com.ediel.fabio.restaurante.delivery.dao.DAOException;
import br.com.ediel.fabio.restaurante.delivery.dao.ItemDAO;
import br.com.ediel.fabio.restaurante.delivery.dao.ItemDAOJPA;
import br.com.ediel.fabio.restaurante.delivery.dao.PedidoDAO;
import br.com.ediel.fabio.restaurante.delivery.dao.PedidoDAOJPA;
import br.com.ediel.fabio.restaurante.delivery.dao.ReclamacaoDAO;
import br.com.ediel.fabio.restaurante.delivery.dao.ReclamacaoDAOJPA;
import br.com.ediel.fabio.restaurante.delivery.manager.ManagerException;
import br.com.ediel.fabio.restaurante.delivery.manager.PedidoManager;
import br.com.ediel.fabio.restaurante.delivery.manager.PedidoManagerImpl;
import br.com.ediel.fabio.restaurante.delivery.model.Item;
import br.com.ediel.fabio.restaurante.delivery.model.Pedido;
import br.com.ediel.fabio.restaurante.delivery.model.Reclamacao;
import br.com.ediel.fabio.restaurante.delivery.model.StatusPedido;

@RunWith(Arquillian.class)
public class RestauranteDeliveryTest {

	@Deployment
	public static JavaArchive createDeployArchive() {
		return ShrinkWrap
				.create(JavaArchive.class)
				.addClasses(Item.class, Pedido.class, Reclamacao.class,
						AbstractDAO.class, ItemDAO.class, ItemDAOJPA.class,
						DAO.class, DAOException.class, PedidoManager.class,
						PedidoManagerImpl.class, PedidoDAO.class,
						PedidoDAOJPA.class, StatusPedido.class,
						ManagerException.class, ReclamacaoDAO.class,
						ReclamacaoDAOJPA.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsResource("test-persistence.xml",
						"META-INF/persistence.xml");
	}

	@PersistenceContext(name = "test")
	private EntityManager em;

	@Test
	@InSequence(1)
	@UsingDataSet("datasets/xml-test.xml")
	public void test_db_unit() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Item> query = builder.createQuery(Item.class);
		Root<Item> item = query.from(Item.class);
		query.select(item);
		List<Item> result = em.createQuery(query).getResultList();
		result.stream().forEach(i -> System.out.println(i));
		assertEquals(5, result.size());
	}

	@Test
	@InSequence(2)
	@UsingDataSet("datasets/xml-test.xml")
	public void testItemDao() {
		ItemDAO dao = new ItemDAOJPA(em);
		Item item = new Item();
		item.setCodigo(6L);
		item.setDescricao("mais um teste");
		item.setValor(6.00);

		try {
			dao.save(item);

			List<Item> items = dao.loadAll();

			for (Item i : items) {
				System.out.println(i);
			}
			
			assertEquals(6, items.size());

		} catch (DAOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	@InSequence(3)
	@UsingDataSet("datasets/xml-test.xml")
	@Cleanup(phase = TestExecutionPhase.NONE, strategy = CleanupStrategy.DEFAULT)
	public void testRealizarPedido() {
		ItemDAO dao = new ItemDAOJPA(em);

		Pedido pedido = new Pedido();

		pedido.setNumero(1L);
		pedido.setObservacao("Com batatas");

		try {
			pedido.getItens().add(dao.find(1L));
			pedido.getItens().add(dao.find(2L));

			System.out.println(pedido.calcularTotalPedido());
			pedido.getItens().forEach(item -> System.out.println(item));

			PedidoManager pedidoManager = new PedidoManagerImpl(em);
			pedidoManager.realizarPedido(pedido);
			
			Pedido p = pedidoManager.buscarPorNumero(1L);
			
			Optional<Double> somaItens= p.getItens().parallelStream().map(Item::getValor).reduce((a,b) -> a+b);
			
			assertEquals(p.getValorTotal(), somaItens.get());

		} catch (DAOException e) {
			fail(e.getMessage());
		} catch (ManagerException e) {
			fail(e.getMessage());
		}

	}

	@Test
	@InSequence(4)
	@ApplyScriptAfter("datasets/cleanup.sql")
	public void atenderPedido() {
		PedidoManager pedidoManager = new PedidoManagerImpl(em);

		try {
			pedidoManager.atenderPedido(1L);
			Pedido pedido = pedidoManager.buscarPorNumero(1L);
			assertEquals(StatusPedido.ENTREGUE, pedido.getStatus());
			pedido.getItens().forEach(item -> System.out.println(item));

		} catch (ManagerException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@InSequence(5)
	@UsingDataSet("datasets/pedidos-dataset.xml")
	@ApplyScriptAfter("datasets/cleanup.sql")
	public void testCalculoFrete() {
		PedidoManager pedidoManager = new PedidoManagerImpl(em);
		
		try {
			System.out.println(pedidoManager.calcularFretePedido(1L, 50.0));
		} catch (ManagerException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@InSequence(6)
	@UsingDataSet("datasets/pedidos-dataset.xml")
	@ApplyScriptAfter("datasets/cleanup.sql")
	public void testRealizarReclamacao() {
		
		try {
			PedidoManager manager = new PedidoManagerImpl(em);
			
			Reclamacao reclamacao = new Reclamacao();
			
			reclamacao.setId(1L);
			reclamacao.setDescricao("Entrega demorada.");
			
			manager.registrarReclamacao(reclamacao, 1L);
			
			Pedido pedido = manager.buscarPorNumero(1L);
			
			pedido.getReclamacoes().forEach(r -> System.out.println(r));
			
			assertEquals(1, pedido.getReclamacoes().size());
			
		} catch (ManagerException e) {
			fail(e.getMessage());
		}

	}
	
	@Test
	@InSequence(7)
	@UsingDataSet("datasets/pedidos-dataset.xml")
	@ApplyScriptAfter("datasets/cleanup.sql")
	public void testBuscarPedidosPendentes() {
		
		try {
			PedidoManager manager = new PedidoManagerImpl(em);
			
			List<Pedido> pedidosEmAberto = manager.buscarTodosEmAberto();
			
			pedidosEmAberto.forEach(pedido -> System.out.println(pedido));
			
			assertEquals(1, pedidosEmAberto.size());

		} catch (ManagerException e) {
			fail(e.getMessage());
		}

	}

}
