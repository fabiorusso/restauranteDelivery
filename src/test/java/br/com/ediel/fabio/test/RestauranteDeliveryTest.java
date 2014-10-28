package br.com.ediel.fabio.test;

import static org.junit.Assert.assertEquals;

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

import br.com.ediel.fabio.restaurante.delivery.model.Item;
import br.com.ediel.fabio.restaurante.delivery.model.Pedido;
import br.com.ediel.fabio.restaurante.delivery.model.Reclamacao;

@RunWith(Arquillian.class)
public class RestauranteDeliveryTest {

	@Deployment
	public static JavaArchive createDeployArchive() {
		return ShrinkWrap
				.create(JavaArchive.class)
				.addClasses(Item.class, Pedido.class, Reclamacao.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsResource("test-persistence.xml",
						"META-INF/persistence.xml");
	}
	
	@PersistenceContext(name="test")
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

}
