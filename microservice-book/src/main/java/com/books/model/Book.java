package com.books.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table( name ="books" )
@JsonSerialize(using= BookSerializer.class)
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column( name = "name" )
	@Size( max = 100, min = 3, message = "Le nom du livre doit contenir entre 3 et 50 charactères." )
    @NotEmpty( message = "Merci de rentrer un nom de livre" )
    private String name;
	
    @Column( name = "description")
    @Size( max = 1000, message = "La description ne doit pas dépasser 1000 charactères.")
    private String description;
    
    @Column( name = "image" )
    private String imagePath;
    
    @ManyToOne
    @JoinColumn(name="bookType_id")
	private BookType bookType;
    
    @OneToMany(mappedBy = "book")
    private Set<BookCopy> booksCopies;
    
    @ManyToMany(mappedBy = "books")
    private Set<Author> authors;
    
    @OneToMany(mappedBy = "book")
    private Set<Booking> bookings;
    
    public Book() {
    	
    }

	public Book(Integer id,
			@Size(max = 100, min = 3, message = "Le nom du livre doit contenir entre 3 et 50 charactères.") @NotEmpty(message = "Merci de rentrer un nom de livre") String name,
			@Size(max = 1000, message = "La description ne doit pas dépasser 1000 charactères.") String description,
			String imagePath) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.imagePath = imagePath;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public BookType getBookType() {
		return bookType;
	}

	public void setBookType(BookType bookType) {
		this.bookType = bookType;
	}

	public Set<BookCopy> getBooksCopies() {
		return booksCopies;
	}

	public void setBooksCopies(Set<BookCopy> booksCopies) {
		this.booksCopies = booksCopies;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public Set<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", description=" + description + ", imagePath=" + imagePath + "]";
	}
}
