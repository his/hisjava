package de.haukeingmar.wicketrepeater.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import de.haukeingmar.wicketrepeater.model.Book;

@Repository
public class BookDaoImpl implements BookDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void delete(final Book book) {
		entityManager.remove(book);
	}

	@Override
	public Book get(final Long key) {
		return entityManager.find(Book.class, key);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public Book merge(final Book book) {
		Book mergedBook = entityManager.merge(book);
		return mergedBook;
	}

	@Override
	public void save(final Book book) {
		entityManager.persist(book);
	}

	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
