package de.haukeingmar.wicketrepeater.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.haukeingmar.wicketrepeater.model.Book;

@Repository
public class BookDao {

	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Transactional
	public void save(final Book book) {
		entityManager.persist(book);
	}

	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
