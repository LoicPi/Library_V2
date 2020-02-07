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
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="bookings")
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@NotNull
	@Column(name = "idUser")
	private Integer idUser;
	
	@Column(name = "dateMail")
	@Temporal( TemporalType.DATE )
    @DateTimeFormat( iso = ISO.DATE )
	private Date createBooking;
	
	@Column(name = "sendMail")
	private Boolean sendMail = false;
	
	@Column(name = "dateMail")
	@Temporal( TemporalType.DATE )
    @DateTimeFormat( iso = ISO.DATE )
	private Date dateMail;
	
	@Column(name="State")
	@Enumerated(EnumType.STRING)
	private State state;
	
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

	public Booking() {
		
	}

	public Booking(Integer id, @NotNull Integer idUser, Date createBooking, Boolean sendMail, Date dateMail,
			State state) {
		super();
		this.id = id;
		this.idUser = idUser;
		this.createBooking = createBooking;
		this.sendMail = sendMail;
		this.dateMail = dateMail;
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public Date getCreateBooking() {
		return createBooking;
	}

	public void setCreateBooking(Date createBooking) {
		this.createBooking = createBooking;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Boolean getSendMail() {
		return sendMail;
	}

	public void setSendMail(Boolean sendMail) {
		this.sendMail = sendMail;
	}

	public Date getDateMail() {
		return dateMail;
	}

	public void setDateMail(Date dateMail) {
		this.dateMail = dateMail;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Booking [id=" + id + ", idUser=" + idUser + ", createBooking=" + createBooking + ", sendMail="
				+ sendMail + ", dateMail=" + dateMail + ", state=" + state + "]";
	}
	
}
