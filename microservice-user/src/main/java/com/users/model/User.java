package com.users.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table( name = "users")
@JsonSerialize(using= UserSerializer.class)
public class User {
	
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
	
    @Column( name = "email", unique = true )
    @Email( message = "Merci de rentrer une adresse mail valide." )
    @NotEmpty( message = "Merci de rentrer une adresse email" )
    private String email;
    
    @Column( name = "password" )
    @Size( min = 8, message = "Le mot de passe doit contenir au minimum 8 charactères." )
    @NotEmpty( message = "Merci de rentrer un mot de passe" )
    private String password;
    
    @Transient
    private String passwordControl;
    
    @Column( name = "card_number")
    @NotEmpty(message = "Merci de renseigner votre numéro de carte d'adhérent.")
    private Integer cardNumber;
    
    @Column(name = "phone_number")
    @Size( max = 9, min = 10, message = "Merci de renseigner un numéro de téléphone valide.")
    private Integer phoneNumber;
    
    @Column(name = "date_registration")
    @Temporal( TemporalType.DATE )
    @DateTimeFormat( iso = ISO.DATE )
    private Date dateRegistration;
    
    public User() {
    	
    }

	public User(Integer id,
			@Size(max = 50, min = 3, message = "Le nom doit contenir entre 3 et 50 charactères.") @NotEmpty(message = "Merci de rentrer un prénom") String lastName,
			@Size(max = 50, min = 3, message = "Le prénom doit contenir entre 3 et 50 charactères.") @NotEmpty(message = "Merci de rentrer un nom") String firstName,
			@Email(message = "Merci de rentrer une adresse mail valide.") @NotEmpty(message = "Merci de rentrer une adresse email") String email,
			@Size(min = 8, message = "Le mot de passe doit contenir au minimum 8 charactères.") @NotEmpty(message = "Merci de rentrer un mot de passe") String password,
			String passwordControl,
			@NotEmpty(message = "Merci de renseigner votre numéro de carte d'adhérent.") Integer cardNumber,
			@Size(max = 10, min = 10, message = "Merci de renseigner un numéro de téléphone valide.") Integer phoneNumber,
			Date dateRegistration) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.password = password;
		this.passwordControl = passwordControl;
		this.cardNumber = cardNumber;
		this.phoneNumber = phoneNumber;
		this.dateRegistration = dateRegistration;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordControl() {
		return passwordControl;
	}

	public void setPasswordControl(String passwordControl) {
		this.passwordControl = passwordControl;
	}

	public Integer getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(Integer cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Integer getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getDateRegistration() {
		return dateRegistration;
	}

	public void setDateRegistration(Date dateRegistration) {
		this.dateRegistration = dateRegistration;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", email=" + email
				+ ", password=" + password + ", passwordControl=" + passwordControl + ", cardNumber=" + cardNumber
				+ ", phoneNumber=" + phoneNumber + "]";
	}

    
}
