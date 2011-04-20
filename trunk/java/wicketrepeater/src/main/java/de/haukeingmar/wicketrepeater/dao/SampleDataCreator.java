package de.haukeingmar.wicketrepeater.dao;

import java.util.Random;

import de.haukeingmar.wicketrepeater.model.Author;
import de.haukeingmar.wicketrepeater.model.Book;

/**
 * Creates the sample data.
 */
public class SampleDataCreator {

	private BookDao bookDao;

	private AuthorDao authorDao;

	private String availableCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private Random rnd = new Random();

	private String createRandomAuthor() {
		return createRandomString(10);
	}

	private String createRandomString(final int length) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < length; i++) {
			result.append(availableCharacters.charAt(rnd.nextInt(availableCharacters.length())));
		}
		return result.toString();
	}

	private String createRandomTitle() {
		return createRandomString(10);
	}

	public void createSampleData(final int count) {
		for (int i = 0; i < count; i++) {
			Author newAuthor = new Author();
			newAuthor.setName(createRandomAuthor());

			for (int j = 0; j < 5; j++) {
				Book newBook = new Book();
				newBook.setTitle(createRandomTitle());
				newAuthor.addBook(newBook);
			}

			authorDao.save(newAuthor);

		}
	}

	public AuthorDao getAuthorDao() {
		return authorDao;
	}

	public BookDao getBookDao() {
		return bookDao;
	}

	public void setAuthorDao(final AuthorDao authorDao) {
		this.authorDao = authorDao;
	}

	public void setBookDao(final BookDao bookDao) {
		this.bookDao = bookDao;
	}

}
