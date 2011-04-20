package de.haukeingmar.wicketrepeater.web;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;

import de.haukeingmar.wicketrepeater.model.Book;

/**
 * Presents a list of books. No, really.
 */
public class BookListPanel extends Panel {

	public BookListPanel(final String id, final IDataProvider<Book> dataProvider) {
		super(id);

		DataView<Book> bookTable = new DataView<Book>("booktable", dataProvider) {

			@Override
			protected void populateItem(final Item<Book> item) {

				item.add(new Label("bookid", "" + item.getModelObject().getId()));
				item.add(new Label("bookname", item.getModelObject().getTitle()));

			}
		};
		bookTable.setItemsPerPage(3);
		add(bookTable);

		add(new PagingNavigator("bookdetailsnavigator", bookTable));
	}

}
