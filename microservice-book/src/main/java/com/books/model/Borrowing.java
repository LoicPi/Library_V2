package com.books.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="borrowings")
@JsonSerialize(using= BorrowingSerializer.class)
public class Borrowing {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name="date_borrowed")
    @Temporal( TemporalType.DATE )
    @DateTimeFormat( iso = ISO.DATE )
	private Date dateBorrowed;
	
	@Column(name="deadline")
    @Temporal( TemporalType.DATE )
    @DateTimeFormat( iso = ISO.DATE )
	private Date deadline;
	
	@Column(name="renewal")
	private Boolean renewal = false;
	
	@Column(name="dateRenewal")
    @Temporal( TemporalType.DATE )
    @DateTimeFormat( iso = ISO.DATE )
	private Date dateRenewal;
	
	@Column(name="dateReturn")
    @Temporal( TemporalType.DATE )
    @DateTimeFormat( iso = ISO.DATE )
	private Date dateReturn;
	
	@Column(name="idUser")
	private Integer idUser;
	
	@Column(name="State")
	@Enumerated(EnumType.STRING)
	private State state;
	
	@ManyToOne
	@JoinColumn(name = "bookCopy_id")
	private BookCopy bookCopy;

	public Borrowing() {
		
	}

	public Borrowing(Integer id, Date dateBorrowed, Date deadline, Boolean renewal, Date dateRenewal, Date dateReturn,
			Integer idUser, State state, BookCopy bookCopy) {
		super();
		this.id = id;
		this.dateBorrowed = dateBorrowed;
		this.deadline = deadline;
		this.renewal = renewal;
		this.dateRenewal = dateRenewal;
		this.dateReturn = dateReturn;
		this.idUser = idUser;
		this.state = state;
		this.bookCopy = bookCopy;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateBorrowed() {
		return dateBorrowed;
	}

	public void setDateBorrowed(Date dateBorrowed) {
		this.dateBorrowed = dateBorrowed;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Boolean getRenewal() {
		return renewal;
	}

	public void setRenewal(Boolean renewal) {
		this.renewal = renewal;
	}

	public Date getDateRenewal() {
		return dateRenewal;
	}

	public void setDateRenewal(Date dateRenewal) {
		this.dateRenewal = dateRenewal;
	}

	public Date getDateReturn() {
		return dateReturn;
	}

	public void setDateReturn(Date dateReturn) {
		this.dateReturn = dateReturn;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public BookCopy getBookCopy() {
		return bookCopy;
	}

	public void setBookCopy(BookCopy bookCopy) {
		this.bookCopy = bookCopy;
	}

	@Override
	public String toString() {
		return "Borrowing [id=" + id + ", dateBorrowed=" + dateBorrowed + ", deadline=" + deadline + ", renewal="
				+ renewal + ", dateRenewal=" + dateRenewal + ", dateReturn=" + dateReturn + ", idUser=" + idUser
				+ ", state=" + state + "]";
	}
}
