package de.haukeingmar.wicketrepeater.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.haukeingmar.wicketrepeater.dataaccess.GenericDataProvider;
import de.haukeingmar.wicketrepeater.dataaccess.JpqlQueryDataProvider;
import de.haukeingmar.wicketrepeater.dataaccess.NamedQueryDataProvider;
import de.haukeingmar.wicketrepeater.model.Author;

/**
 * A simple example for the {@link GenericDataProvider}. See {@link DetailsPage} for the more sophisticated
 * {@link JpqlQueryDataProvider} and {@link NamedQueryDataProvider}.
 */
public class HomePage extends WebPage {

	private static final long serialVersionUID = 6937466747068718689L;

	public HomePage(final PageParameters parameters) {

		final GenericDataProvider<Author> authorDataProvider = new GenericDataProvider<Author>(Author.class);

		DataView<Author> dataView = new DataView<Author>("table", authorDataProvider) {
			private static final long serialVersionUID = -1697604589968241161L;

			@Override
			protected void populateItem(final Item<Author> item) {
				item.add(new Label("id", "" + item.getModelObject().getId()));
				item.add(new Label("name", item.getModelObject().getName()));
			}
		};
		dataView.setItemsPerPage(10);
		add(dataView);

		add(new PagingNavigator("navigator", dataView));

		add(new Link("linkAll") {
			@Override
			public void onClick() {
				authorDataProvider.setFilter(null);
			}
		});

		add(new Link("linkStartingWithA") {
			@Override
			public void onClick() {
				authorDataProvider.setFilter("author.name like 'A%'");
			}
		});

		add(new Link("linkStartingWithR") {
			@Override
			public void onClick() {
				authorDataProvider.setFilter("author.name like 'R%'");
			}
		});

		add(new Link("orderByAuthor") {
			@Override
			public void onClick() {
				authorDataProvider.setOrderClause("author.name ASC");
			}
		});

		add(new Link("orderById") {
			@Override
			public void onClick() {
				authorDataProvider.setOrderClause("author.id ASC");
			}
		});

		Form formFilter = new Form("formFilter");

		final IModel<String> filterStringModel = new Model<String>("ZZ%");
		formFilter.add(new TextField<String>("filterString", filterStringModel, String.class));
		formFilter.add(new Button("doFilter") {
			@Override
			public void onSubmit() {
				/*
				 * When using user-submitted strings you should validate and sanitize the input. Not using string
				 * concatenation is one step; this is what this sample should show, so we will not do any further checks
				 * here (in onValidate, where such should be done).
				 */
				authorDataProvider.setFilter("author.name like :filter");
				authorDataProvider.putParameter("filter", filterStringModel.getObject());
			}
		});

		add(formFilter);

	}

}
