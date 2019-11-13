package com.books.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table (name="bookcopies")
public class BookCopy {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name="isbn")
	@Size( max = 17, min = 17, message = "L'ISBN doit contenir entre 17 charactères sous la forme XXX-X-XXXX-XXXX-X." )
    @NotEmpty( message = "Merci de rentrer un prénom" )
	private String isbn;
	
	@ManyToOne
	@JoinColumn(name="book_id")
	private Book book;
	
	@OneToMany(mappedBy = "bookCopy")
	private Set<Borrowing> borrowings;
	
	public BookCopy() {
		
	}

	public BookCopy(Integer id,
			@Size(max = 17, min = 17, message = "L'ISBN doit contenir entre 17 charactères sous la forme XXX-X-XXXX-XXXX-X.") @NotEmpty(message = "Merci de rentrer un prénom") String isbn,
			Book book, Set<Borrowing> borrowings) {
		super();
		this.id = id;
		this.isbn = isbn;
		this.book = book;
		this.borrowings = borrowings;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Set<Borrowing> getBorrowings() {
		return borrowings;
	}

	public void setBorrowings(Set<Borrowing> borrowings) {
		this.borrowings = borrowings;
	}

	@Override
	public String toString() {
		return "BookCopy [id=" + id + ", isbn=" + isbn + "]";
	}
}
