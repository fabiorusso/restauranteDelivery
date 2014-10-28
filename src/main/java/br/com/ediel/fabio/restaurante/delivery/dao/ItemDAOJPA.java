package br.com.ediel.fabio.restaurante.delivery.dao;

import javax.persistence.EntityManager;

import br.com.ediel.fabio.restaurante.delivery.model.Item;

public class ItemDAOJPA extends AbstractDAO<Item, Long> implements ItemDAO {

	public ItemDAOJPA(EntityManager entityManager) {
		this(entityManager,Item.class);
	}
	
	private ItemDAOJPA(EntityManager entityManager, Class<Item> clazz) {
		super(entityManager, clazz);
	}

}
