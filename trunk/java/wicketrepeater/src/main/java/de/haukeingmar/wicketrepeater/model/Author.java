package de.haukeingmar.wicketrepeater.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.REMOVE;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
		@NamedQuery(name = "Author.countBooks", query = "select count(*) from Book b where b.author.id=:authorId"),
		@NamedQuery(name = "Author.listBooks", query = "from Book b where b.author.id=:authorId") })
public class Author implements HasId {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@OneToMany(cascade = { ALL, PERSIST, MERGE, REMOVE, REFRESH, DETACH }, mappedBy = "author", orphanRemoval = true)
	private Set<Book> booksWritten = new HashSet<Book>();

	public void addBook(final Book aBook) {
		booksWritten.add(aBook);
		aBook.setAuthor(this);
	}

	public Set<Book> getBooksWritten() {
		return booksWritten;
	}

	@Override
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setBooksWritten(final Set<Book> booksWritten) {
		this.booksWritten = booksWritten;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
