package de.haukeingmar.wicketrepeater.dao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.haukeingmar.wicketrepeater.model.HasId;

public class GenericObjectLoader {

	@PersistenceContext
	private EntityManager entityManager;

	public <T extends HasId> long count(final Class<T> clazz) {
		return count(clazz, "e", null);
	}

	public <T extends HasId> long count(final Class<T> clazz, final String entityName, final String filter) {
		return count(clazz, entityName, filter, null);
	}

	public <T extends HasId> long count(final Class<T> clazz, String entityName, final String filter,
			final Map<String, Object> parameterMap) {
		if (entityName == null) {
			entityName = "e";
		}

		String queryString = "select count(" + entityName + ") from " + clazz.getName() + " " + entityName;

		if (filter != null) {
			queryString = queryString + " where " + filter;
		}
		Query q = entityManager.createQuery(queryString);

		fillParameters(q, parameterMap);

		return (Long) q.getSingleResult();
	}

	private void fillParameters(final Query query, final Map<String, Object> parameterMap) {
		Set<String> parameterNames = new HashSet<String>();
		for (Parameter p : query.getParameters()) {
			parameterNames.add(p.getName());
		}

		if (parameterMap != null) {
			for (String aName : parameterMap.keySet()) {
				if (parameterNames.contains(aName)) {
					query.setParameter(aName, parameterMap.get(aName));
				}
			}
		}
	}

	public <T extends HasId> List<T> getList(final Class<T> clazz, final int first, final int count) {
		return getList(clazz, first, count, "e", null, null);
	}

	public <T extends HasId> List<T> getList(final Class<T> clazz, final int first, final int count,
			final String entityName, final String filter, final String orderClause) {
		return getList(clazz, first, count, entityName, filter, orderClause, null);
	}

	public <T extends HasId> List<T> getList(final Class<T> clazz, final int first, final int count, String entityName,
			final String filter, final String orderClause, final Map<String, Object> parameterMap) {
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

		Query q = entityManager.createQuery(queryString).setFirstResult(first).setMaxResults(count);

		fillParameters(q, parameterMap);

		return q.getResultList();

	}

	public <T extends HasId> T load(final Class<T> clazz, final Serializable key) {
		return entityManager.find(clazz, key);
	}
}
