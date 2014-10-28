package br.com.ediel.fabio.restaurante.delivery.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;


public class AbstractDAO<T, PK extends Serializable> implements DAO<T, PK> {
	//private static final Logger logger = Logger.getLogger(AbstractDAO.class);
	protected final Class<T> clazz;
	protected final EntityManager entityManager;

	public AbstractDAO(final EntityManager entityManager, Class<T> clazz) {
		this.entityManager = entityManager;
		this.clazz = clazz;
	}

	@Override
	public void save(T obj) throws DAOException {
		try {
			setCreatedAt(obj);
			setUpdatedAtToObject(obj);
			entityManager.persist(obj);
			entityManager.flush();
		} catch (PersistenceException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void update(T obj) throws DAOException {
		try {
			setUpdatedAtToObject(obj);
			entityManager.merge(obj);
			entityManager.flush();
		} catch (PersistenceException e) {
			throw new DAOException(e);
		}

	}

	@Override
	public void update(List<T> objs) throws DAOException {
		try {
			for (T obj : objs)
				update(obj);

		} catch (PersistenceException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public T find(PK id) throws DAOException {
		try {
			return entityManager.find(clazz, id);
		} catch (PersistenceException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void delete(T obj) throws DAOException {
		try {
			entityManager.remove(obj);
		} catch (PersistenceException e) {
			throw new DAOException(e);
		}
	}

	
	
	protected Expression<?> getExpression(String field, Path<?> entity) {

		String[] fullPath = field.split("\\.");

		Expression<?> expression = entity;

		for (String path : fullPath) {
			expression = ((Path<?>) expression).get(path);
		}

		return expression;
	}

	protected void setUpdatedAtToObject(T obj) {
		try {
			if (obj.getClass().getMethod("setUpdatedAt",
					new Class[] { Date.class }) != null) {
				obj.getClass()
						.getMethod("setUpdatedAt", new Class[] { Date.class })
						.invoke(obj, new Object[] { new Date() });
			}
		} catch (SecurityException e1) {
		} catch (NoSuchMethodException e1) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
	}

	protected void setCreatedAt(T obj) {
		try {
			if (obj.getClass().getMethod("setCreatedAt",
					new Class[] { Date.class }) != null) {
				obj.getClass()
						.getMethod("setCreatedAt", new Class[] { Date.class })
						.invoke(obj, new Object[] { new Date() });
			}
		} catch (SecurityException e1) {
		} catch (NoSuchMethodException e1) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
	}

	
}
