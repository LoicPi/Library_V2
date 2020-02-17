package com.books.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.books.serializer.BookTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="booktypes")
@JsonSerialize(using = BookTypeSerializer.class)
public class BookType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "type")
	@Size( max = 50, min = 3, message = "L'ISBN doit contenir entre 3 et 50 charactères." )
    @NotEmpty( message = "Merci de rentrer un type de livre" )
	private String type;
	
	@OneToMany(mappedBy = "bookType")
	private List<Book> books;
	
	public BookType() {
		
	}

	public BookType(Integer id,
			@Size(max = 50, min = 3, message = "L'ISBN doit contenir entre 3 et 50 charactères.") @NotEmpty(message = "Merci de rentrer un type de livre") String type,
			List<Book> books) {
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

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "BookType [id=" + id + ", type=" + type + "]";
	}
}
