package br.com.ediel.fabio.restaurante.delivery.dao;

import javax.persistence.EntityManager;

import br.com.ediel.fabio.restaurante.delivery.model.Reclamacao;

public class ReclamacaoDAOJPA extends AbstractDAO<Reclamacao, Long> implements
		ReclamacaoDAO {

	public ReclamacaoDAOJPA(EntityManager em) {
		this(em, Reclamacao.class);
	}

	private ReclamacaoDAOJPA(EntityManager entityManager,
			Class<Reclamacao> clazz) {
		super(entityManager, clazz);
	}

}
