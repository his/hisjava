package de.haukeingmar.wicketrepeater.dataaccess;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.haukeingmar.wicketrepeater.dao.GenericObjectLoader;
import de.haukeingmar.wicketrepeater.model.HasId;

/**
 * Provides a convenient way to create a data provider for repeatable views for our domain objects (i.e. objects that
 * implement the {@link HasId} interface and that are loaded via Spring managed JPA).
 * 
 * This data provider allows you to define the list by object class, order and filter clauses.
 * 
 * @param <T>
 *            The class of the objects this data provider provides
 */
public class GenericDataProvider<T extends HasId> implements IDataProvider<T> {

	private static final long serialVersionUID = -2095943194330230409L;

	@SpringBean
	private GenericObjectLoader genericObjectLoader;

	private String filter;

	private String entityName;

	private String orderClause;

	private Class<T> clazz;

	private Map<String, Object> queryParameters = new HashMap<String, Object>();

	/**
	 * Creates a generic data provider. The entity name by which you can access properties within the order and filter
	 * clauses will be set to the simple name of the class in lowercase.
	 * 
	 * @param clazz
	 *            The class of the objects this data provider provides
	 */
	public GenericDataProvider(final Class<T> clazz) {
		this(clazz, clazz.getSimpleName().toLowerCase());
	}

	/**
	 * Creates a generic data provider. The entity name lets you access object properties in the order and filter
	 * clauses.
	 * 
	 * @param clazz
	 *            The class of the objects this data provider provides
	 * @param entityName
	 *            The name by which you can access object properties within the order and filter clause
	 */
	public GenericDataProvider(final Class<T> clazz, final String entityName) {
		Injector.get().inject(this);
		this.clazz = clazz;
		this.entityName = entityName;
	}

	/**
	 * Clears the parameter map.
	 */
	public void clearParameters() {
		queryParameters.clear();
	}

	@Override
	public void detach() {
	}

	/**
	 * Returns the entity name by which you can access object properties in the order and filter clause.
	 * 
	 * @return The entity name in use
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * Returns the filter in use. Is a JPQL term.
	 * 
	 * @return The filter in use
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * Returns the order clause in use. Is a JPQL term.
	 * 
	 * @return The order clause in use
	 */
	public String getOrderClause() {
		return orderClause;
	}

	/**
	 * Returns the parameter map.
	 * 
	 * @return The current instance of the parameter map
	 */
	public Map<String, Object> getParameterMap() {
		return queryParameters;
	}

	@Override
	public Iterator<T> iterator(final int first, final int count) {
		return genericObjectLoader.getList(clazz, first, count, entityName, filter, orderClause, queryParameters)
				.iterator();
	}

	@Override
	public IModel<T> model(final T object) {
		return new DomainObjectLoadableDetachableModel<T>(clazz, object);
	}

	/**
	 * Adds a parameter to the parameter map or replaces it.
	 * 
	 * The only validity checks made here are checks for null; both name and value must not be null.
	 * 
	 * @param name
	 *            The parameter name used in the filter or order JPQL fragment
	 * @param value
	 *            The value to be set
	 * @throws IllegalArgumentException
	 *             When either name or value is nulls
	 */
	public void putParameter(final String name, final Object value) {
		if (name == null) {
			throw new IllegalArgumentException("name must not be null");
		}
		if (value == null) {
			throw new IllegalArgumentException("value must not be null");
		}

		queryParameters.put(name, value);
	}

	/**
	 * Removes the parameter designated by name from the query parameter map.
	 * 
	 * @param name
	 *            The parameter to remove
	 */
	public void removeParameter(final String name) {
		queryParameters.remove(name);
	}

	/**
	 * Sets the entity name by which you can access object properties in the order and filter clauses. Should be a legal
	 * JPQL identifier. Defaults to the classes simple name in lowercase.
	 * 
	 * @param entityName
	 *            The entity name in use
	 */
	public void setEntityName(final String entityName) {
		this.entityName = entityName;
	}

	/**
	 * The filter clause in JPQL. Will be used as the JPQL 'where' clause. You don't need to supply the 'where'.
	 * 
	 * @param filter
	 *            The filter clause
	 */
	public void setFilter(final String filter) {
		this.filter = filter;
	}

	/**
	 * The order clause in JPQL. Will be used as the JPQL 'order by' clause. You don't need to supply the 'order by'.
	 * 
	 * @param orderClause
	 *            The order clause
	 */
	public void setOrderClause(final String orderClause) {
		this.orderClause = orderClause;
	}

	@Override
	public int size() {
		// XXX IDataProvider is restricted to int.
		// Maybe do "something" here? Return Integer.MAX_VALUE? Throw a runtime
		// exception? Submit a patch to Wicket?
		return (int) genericObjectLoader.count(clazz, entityName, filter, queryParameters);
	}

}
