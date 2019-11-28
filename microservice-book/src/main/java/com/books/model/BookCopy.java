package com.books.model;

import java.util.List;

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
	
	@Column(name="ean")
	@Size( max = 13, min = 13, message = "L'EAN doit contenir 13 charactères.")
    @NotEmpty( message = "Merci de rentrer le numéro EAN" )
	private String ean;
	
	@ManyToOne
	@JoinColumn(name="book_id")
	private Book book;
	
	@OneToMany(mappedBy = "bookCopy")
	private List<Borrowing> borrowings;
	
	public BookCopy() {
		
	}
	
	public Boolean isBorrowed() {
		Boolean borrowed = false;
		for (Borrowing borrowing : borrowings) {
			if (!(borrowing.getState().equals(State.Termine))) {
				borrowed = true;
				break;
			} 
		}
		return borrowed;
	}
	
	public BookCopy(Integer id,
			String ean,
			Book book, List<Borrowing> borrowings) {
		super();
		this.id = id;
		this.ean = ean;
		this.book = book;
		this.borrowings = borrowings;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public List<Borrowing> getBorrowings() {
		return borrowings;
	}

	public void setBorrowings(List<Borrowing> borrowings) {
		this.borrowings = borrowings;
	}

	@Override
	public String toString() {
		return "BookCopy [id=" + id + ", ean=" + ean + "]";
	}
}
