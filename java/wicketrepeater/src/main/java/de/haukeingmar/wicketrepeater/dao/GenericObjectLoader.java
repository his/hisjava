package de.haukeingmar.wicketrepeater.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.haukeingmar.wicketrepeater.model.HasId;

public class GenericObjectLoader {

	@PersistenceContext
	private EntityManager entityManager;

	public <T extends HasId> long count(final Class<T> clazz) {
		return count(clazz, "e", null);
	}

	public <T extends HasId> long count(final Class<T> clazz, String entityName, final String filter) {
		if (entityName == null) {
			entityName = "e";
		}

		String queryString = "select count(" + entityName + ") from " + clazz.getName() + " " + entityName;

		if (filter != null) {
			queryString = queryString + " where " + filter;
		}
		Query q = entityManager.createQuery(queryString);
		return (Long) q.getSingleResult();
	}

	public <T extends HasId> List<T> getList(final Class<T> clazz, final int first, final int count) {
		return getList(clazz, first, count, "e", null, null);
	}

	public <T extends HasId> List<T> getList(final Class<T> clazz, final int first, final int count, String entityName,
			final String filter, final String orderClause) {
		if (entityName == null) {
			entityName = "e";
		}

		String queryString = "from " + clazz.getName() + " " + entityName;

		if (filter != null) {
			queryString = queryString + " where " + filter;
		}

		if (orderClause != null) {
			queryString = queryString + " order by " + orderClause;
		}

		return entityManager.createQuery(queryString).setFirstResult(first).setMaxResults(count).getResultList();

	}

	public <T extends HasId> T load(final Class<T> clazz, final Serializable key) {
		return entityManager.find(clazz, key);
	}
}
