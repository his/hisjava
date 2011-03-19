package de.haukeingmar.wicketrepeater.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.haukeingmar.wicketrepeater.dataaccess.GenericDataProvider;
import de.haukeingmar.wicketrepeater.model.Book;

public class HomePage extends WebPage {

	private static final long serialVersionUID = 6937466747068718689L;

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

	}

}
