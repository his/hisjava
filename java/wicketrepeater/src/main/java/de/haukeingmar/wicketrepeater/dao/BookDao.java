package de.haukeingmar.wicketrepeater.dao;

import org.springframework.transaction.annotation.Transactional;

import de.haukeingmar.wicketrepeater.model.Book;

public interface BookDao {

	void delete(Book book);

	Book get(Long key);

	Book merge(Book book);

	@Transactional
	void save(Book book);

}
