package br.com.ediel.fabio.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
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
						PedidoDAOJPA.class, StatusPedido.class, ManagerException.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsResource("test-persistence.xml",
						"META-INF/persistence.xml");
	}

	@PersistenceContext(name = "test")
	private EntityManager em;

	@Test
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

		} catch (DAOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	@UsingDataSet("datasets/xml-test.xml")
	public void testRealizarPedido() {
		ItemDAO dao = new ItemDAOJPA(em);

		Pedido pedido = new Pedido();
		pedido.setNumero(1L);
		pedido.setObservacao("Com batatas");

		try {
			pedido.getItens().add(dao.find(1L));
			pedido.getItens().add(dao.find(2L));

			System.out.println(pedido.calcularTotalPedido());

			PedidoManager pedidoManager = new PedidoManagerImpl(em);
			pedidoManager.realizarPedido(pedido);
		} catch (DAOException e) {
			fail(e.getMessage());
		} catch (ManagerException e) {
			fail(e.getMessage());
		}

	}

}
