package de.haukeingmar.wicketrepeater.dataaccess;

import java.io.Serializable;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.haukeingmar.wicketrepeater.dao.GenericObjectLoader;
import de.haukeingmar.wicketrepeater.model.HasId;

public class DomainObjectLoadableDetachableModel<T extends HasId> extends LoadableDetachableModel<T> {

	private static final long serialVersionUID = 5415650199676165220L;

	@SpringBean
	private GenericObjectLoader genericObjectLoader;

	private Serializable key;

	private Class<T> clazz;

	public DomainObjectLoadableDetachableModel(final Class<T> clazz, final T model) {
		super(model);

		Injector.get().inject(this);

		if (model != null) {
			key = model.getId();
		}
		this.clazz = clazz;
	}

	@Override
	protected T load() {
		return genericObjectLoader.load(clazz, key);
	}

}
