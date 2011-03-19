package de.haukeingmar.wicketrepeater.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Book implements HasId {

	@Id
	@GeneratedValue
	private Long id;

	private String author;

	private String title;

	public String getAuthor() {
		return author;
	}

	@Override
	public Long getId() { // Note covariance
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setAuthor(final String author) {
		this.author = author;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

}
