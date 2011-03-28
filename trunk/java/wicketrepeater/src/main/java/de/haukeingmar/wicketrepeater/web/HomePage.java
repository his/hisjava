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
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.haukeingmar.wicketrepeater.dataaccess.GenericDataProvider;
import de.haukeingmar.wicketrepeater.model.Book;

public class HomePage extends WebPage {

	private static final long serialVersionUID = 6937466747068718689L;

	private String filterString = "ZZ%";

	public HomePage(final PageParameters parameters) {

		final GenericDataProvider bookDataProvider = new GenericDataProvider(Book.class);

		DataView<Book> dataView = new DataView<Book>("table", bookDataProvider) {
			private static final long serialVersionUID = -1697604589968241161L;

			@Override
			protected void populateItem(final Item<Book> item) {
				item.add(new Label("author", item.getModelObject().getAuthor()));
				item.add(new Label("title", item.getModelObject().getTitle()));
			}
		};
		dataView.setItemsPerPage(10);
		add(dataView);

		add(new PagingNavigator("navigator", dataView));

		add(new Link("linkAll") {
			@Override
			public void onClick() {
				bookDataProvider.setFilter(null);
			}
		});

		add(new Link("linkStartingWithA") {
			@Override
			public void onClick() {
				bookDataProvider.setFilter("book.author like 'A%'");
			}
		});

		add(new Link("linkStartingWithR") {
			@Override
			public void onClick() {
				bookDataProvider.setFilter("book.author like 'R%'");
			}
		});

		add(new Link("orderByNothing") {
			@Override
			public void onClick() {
				bookDataProvider.setOrderClause(null);
			}
		});

		add(new Link("orderByAuthor") {
			@Override
			public void onClick() {
				bookDataProvider.setOrderClause("book.author");
			}
		});

		add(new Link("orderByTitle") {
			@Override
			public void onClick() {
				bookDataProvider.setOrderClause("book.title");
			}
		});

		Form formFilter = new Form("formFilter");

		formFilter.add(new TextField<String>("filterString", new PropertyModel<String>(this, "filterString"),
				String.class));
		formFilter.add(new Button("doFilter") {
			@Override
			public void onSubmit() {
				/*
				 * When using user-submitted strings you should validate and
				 * sanitize the input. Not using string concatenation is one
				 * step; this is what this sample should show, so we will not do
				 * any further checks here (in onValidate, where such should be
				 * done).
				 */
				bookDataProvider.setFilter("book.author like :filter");
				bookDataProvider.putParameter("filter", filterString);
			}
		});

		add(formFilter);

	}

	public String getFilterString() {
		return filterString;
	}

	public void setFilterString(final String filterString) {
		this.filterString = filterString;
	}

}
