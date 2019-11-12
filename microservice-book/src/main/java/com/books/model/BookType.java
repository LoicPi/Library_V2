package com.books.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name="booktypes")
public class BookType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "type")
	@Size( max = 50, min = 3, message = "L'ISBN doit contenir entre 3 et 50 charactères." )
    @NotEmpty( message = "Merci de rentrer un type de livre" )
	private String type;
	
	@OneToMany(mappedBy = "booksTypes")
	private Set<Book> books;
	
	public BookType() {
		
	}

	public BookType(Integer id,
			@Size(max = 50, min = 3, message = "L'ISBN doit contenir entre 3 et 50 charactères.") @NotEmpty(message = "Merci de rentrer un type de livre") String type,
			Set<Book> books) {
		super();
		this.id = id;
		this.type = type;
		this.books = books;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "BookType [id=" + id + ", type=" + type + "]";
	}
}
