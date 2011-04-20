package de.haukeingmar.wicketrepeater.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.haukeingmar.wicketrepeater.dataaccess.GenericDataProvider;
import de.haukeingmar.wicketrepeater.dataaccess.JpqlQueryDataProvider;
import de.haukeingmar.wicketrepeater.dataaccess.NamedQueryDataProvider;
import de.haukeingmar.wicketrepeater.model.Author;
import de.haukeingmar.wicketrepeater.model.Book;

/**
 * Sample for using the three different data providers.
 */
public class DetailsPage extends WebPage {

	private NamedQueryDataProvider<Book> bookNamedQueryDataprovider;

	private JpqlQueryDataProvider<Book> bookJpqlDataprovider;

	public DetailsPage() {

		final IModel<String> authornameModel = new Model<String>();
		add(new Label("selectedAuthorName", authornameModel));

		/*
		 * The GenericDataProvider.
		 */
		GenericDataProvider<Author> authorDataProvider = new GenericDataProvider<Author>(Author.class);

		/*
		 * Presentation.
		 */
		DataView<Author> authorTable = new DataView<Author>("authorlist", authorDataProvider) {
			@Override
			protected void populateItem(final Item<Author> item) {
				item.add(new Label("authorid", "" + item.getModelObject().getId()));
				Link authorselect = new Link("authorselectlink") {
					@Override
					public void onClick() {
						authornameModel.setObject(item.getModelObject().getName());
						bookNamedQueryDataprovider.putParameter("authorId", item.getModelObject().getId());
						bookJpqlDataprovider.putParameter("authorId", item.getModelObject().getId());
					}
				};
				authorselect.add(new Label("authorname", item.getModelObject().getName()));
				item.add(authorselect);

			}
		};
		authorTable.setItemsPerPage(10);
		add(authorTable);
		add(new PagingNavigator("authornavigator", authorTable));

		/*
		 * The NamedQueryDataProvider.
		 */
		bookNamedQueryDataprovider = new NamedQueryDataProvider<Book>(Book.class);
		bookNamedQueryDataprovider.setCountQueryName("Author.countBooks");
		bookNamedQueryDataprovider.setListQueryName("Author.listBooks");
		// Needed, because we don't hide the lists when nothing is selected; but parameters must be set.
		bookNamedQueryDataprovider.putParameter("authorId", -1L);
		add(new BookListPanel("namedquerydetails", bookNamedQueryDataprovider));

		/*
		 * The JpqlQueryDataProvider.
		 */
		bookJpqlDataprovider = new JpqlQueryDataProvider<Book>(Book.class);
		bookJpqlDataprovider.setCountQuery("select count(*) from Book b where b.author.id=:authorId");
		bookJpqlDataprovider.setListQuery("from Book b where b.author.id=:authorId");
		bookJpqlDataprovider.putParameter("authorId", -1L);
		add(new BookListPanel("jpqldetails", bookJpqlDataprovider));

	}

}
