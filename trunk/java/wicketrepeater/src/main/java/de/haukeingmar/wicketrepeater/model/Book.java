package de.haukeingmar.wicketrepeater.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.REMOVE;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Book implements HasId {

	@Id
	@GeneratedValue
	private Long id;

	private String title;

	@ManyToOne(cascade = { ALL, PERSIST, MERGE, REMOVE, REFRESH, DETACH })
	@JoinColumn(name = "author_id", referencedColumnName = "id")
	private Author author;

	public Author getAuthor() {
		return author;
	}

	@Override
	public Long getId() { // Note covariance
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setAuthor(final Author author) {
		this.author = author;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

}
