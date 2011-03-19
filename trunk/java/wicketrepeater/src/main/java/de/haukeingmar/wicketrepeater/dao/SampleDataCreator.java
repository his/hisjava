package de.haukeingmar.wicketrepeater.dao;

import java.util.Random;

import de.haukeingmar.wicketrepeater.model.Book;

public class SampleDataCreator {

	private BookDao bookDao;

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
			Book newBook = new Book();
			newBook.setAuthor(createRandomAuthor());
			newBook.setTitle(createRandomTitle());
			bookDao.save(newBook);
		}
	}

	public BookDao getBookDao() {
		return bookDao;
	}

	public void setBookDao(final BookDao bookDao) {
		this.bookDao = bookDao;
	}

}
