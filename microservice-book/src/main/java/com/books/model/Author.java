package com.books.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name="authors")
public class Author {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column( name = "last_name" )
    @Size( max = 50, min = 3, message = "Le nom doit contenir entre 3 et 50 charactères." )
    @NotEmpty( message = "Merci de rentrer un prénom" )
    private String lastName;

    @Column( name = "first_name" )
    @Size( max = 50, min = 3, message = "Le prénom doit contenir entre 3 et 50 charactères." )
    @NotEmpty( message = "Merci de rentrer un nom" )
    private String firstName;
    
    @Column( name = "description")
    @Size( max = 1000, message = "La description ne doit pas dépasser 1000 charactères.")
    private String description;
    
    @Column( name = "image" )
    private String image;
    
    @ManyToMany
    @JoinTable( name = "author_book", joinColumns = @JoinColumn(name = "author_id"), inverseJoinColumns = @JoinColumn (name = "book_id"))
    private Set<Book> books;
    
    public Author () {
    	
    }

	public Author(Integer id,
			@Size(max = 50, min = 3, message = "Le nom doit contenir entre 3 et 50 charactères.") @NotEmpty(message = "Merci de rentrer un prénom") String lastName,
			@Size(max = 50, min = 3, message = "Le prénom doit contenir entre 3 et 50 charactères.") @NotEmpty(message = "Merci de rentrer un nom") String firstName,
			@Size(max = 1000, message = "La description ne doit pas dépasser 1000 charactères.") String description,
			String image, Set<Book> books) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.description = description;
		this.image = image;
		this.books = books;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", description="
				+ description + ", image=" + image + "]";
	}
}
