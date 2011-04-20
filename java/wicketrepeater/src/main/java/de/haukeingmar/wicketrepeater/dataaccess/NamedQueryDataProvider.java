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
 * This data provider allows you to define the list by named queries.
 * 
 * @param <T>
 *            The class of the objects this data provider provides
 */
public class NamedQueryDataProvider<T extends HasId> implements IDataProvider<T> {

	private static final long serialVersionUID = -2095943194330230409L;

	@SpringBean
	private GenericObjectLoader genericObjectLoader;

	private String listQueryName;

	private String countQueryName;

	private Map<String, Object> queryParameters = new HashMap<String, Object>();

	private Class<T> clazz;

	/**
	 * Creates a generic data provider. The entity name by which you can access properties within the order and filter
	 * clauses will be set to the simple name of the class in lowercase.
	 * 
	 * @param clazz
	 *            The class of the objects this data provider provides
	 */
	public NamedQueryDataProvider(final Class<T> clazz) {
		Injector.get().inject(this);
		this.clazz = clazz;
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

	public Class<T> getClazz() {
		return clazz;
	}

	public String getCountQueryName() {
		return countQueryName;
	}

	public String getListQueryName() {
		return listQueryName;
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
	public Iterator<? extends T> iterator(final int first, final int count) {
		return genericObjectLoader.getListWithNamedQuery(clazz, listQueryName, first, count, queryParameters)
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

	public void setClazz(final Class<T> clazz) {
		this.clazz = clazz;
	}

	public void setCountQueryName(final String countQueryName) {
		this.countQueryName = countQueryName;
	}

	public void setListQueryName(final String listQueryName) {
		this.listQueryName = listQueryName;
	}

	@Override
	public int size() {
		return (int) genericObjectLoader.countWithNamedQuery(clazz, countQueryName, queryParameters);
	}

}
