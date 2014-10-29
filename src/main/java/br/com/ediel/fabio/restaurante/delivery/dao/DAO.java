package br.com.ediel.fabio.restaurante.delivery.dao;

import java.io.Serializable;
import java.util.List;

public interface DAO<T, PK extends Serializable> {
	void save(T obj) throws DAOException;
	void update(T obj) throws DAOException;
	void update(List<T> objs) throws DAOException;
	T find(PK id) throws DAOException;
	void delete(T obj) throws DAOException;
	
	List<T> loadAll() throws DAOException;
	
}
