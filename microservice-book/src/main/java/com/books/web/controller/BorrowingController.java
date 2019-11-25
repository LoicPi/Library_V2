package com.books.web.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.books.dao.BorrowingDao;
import com.books.exceptions.BorrowingInvalidRenewalException;
import com.books.exceptions.BorrowingNotFoundException;
import com.books.exceptions.CanNotAddBorrowingException;
import com.books.model.Borrowing;
import com.books.model.State;

@RestController
public class BorrowingController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BorrowingDao borrowingDao;
	
	/**
	 * Function to get a list of borrowing
	 * @return a list of borrowing
	 */
	@GetMapping("/emprunts")
	public List<Borrowing> listBorrowings() {
		
		List<Borrowing> borrowings = borrowingDao.findAll();
		
		if(borrowings.isEmpty()) throw new BorrowingNotFoundException("Les emprunts de livres n'ont pas été retrouvés.");
		
		log.info("Récupération de la liste des emprunts des livres");
		
		return borrowings;
	}
	
	/**
	 * Function to get the user's list of borrowing
	 * @param idUser id of user who wants have is borrowing
	 * @return list of borrowing
	 */
	@GetMapping("/emprunts/utilisateur/{idUser}")
	public List<Borrowing> listBorrowingsOfUser(@PathVariable int idUser) {
		
		List<Borrowing> borrowingsOfUser = borrowingDao.findByIdUser(idUser);
		
		if(borrowingsOfUser.isEmpty()) throw new BorrowingNotFoundException("Les emprunts de livres pour l'utilisateur : " + idUser + " n'ont pas été retrouvés.");
		
		log.info("Récupération de la liste des emprunts d'un utilisateur");
		
		return borrowingsOfUser;
	}
	
	/**
	 * Function to add borrowing
	 * @param borrowing The object borrowing to add 
	 * @return response entity
	 */
	@PostMapping("/emprunt/add-borrowing")
	public ResponseEntity<Borrowing> addBorrowing (@Valid @RequestBody Borrowing borrowing) {
		
		LocalDate localDate = LocalDate.now();
		
		borrowing.setDateBorrowed(java.sql.Date.valueOf(localDate));
		
		borrowing.setDeadline(java.sql.Date.valueOf(localDate.plusMonths(1)));
		
		borrowing.setState(State.EnCours);
		
		Borrowing newBorrowing = borrowingDao.save(borrowing);
		
		if(newBorrowing == null) throw new CanNotAddBorrowingException ("Impossible d'ajouter l'emprunt.");
		
		return new ResponseEntity<Borrowing>(newBorrowing, HttpStatus.OK);		
	}
	
	/**
	 * Function to renewal borrowing
	 * @param id the id of borrowing
	 * @return response entity
	 */
	@PostMapping("/emprunt/{id}/renouvellement")
	public ResponseEntity<Borrowing> renewalBorrowing (@PathVariable int id)  {
		
		Optional<Borrowing> borrowing = borrowingDao.findById(id);
		
		if(!borrowing.isPresent()) throw new BorrowingNotFoundException("L'emprunt avec l'id : " + id + " n'a pas été retrouvé.");
		
		Borrowing borrowingRenewal = borrowing.get();
		
		if(borrowingRenewal.getRenewal() == true) {
			throw new BorrowingInvalidRenewalException ("L'emprunt avec l'id : " + borrowingRenewal.getId() + " a déjà été renouvelé.");
		}
		
		borrowingRenewal.setRenewal(true);
		
		LocalDate localDate = LocalDate.now();
		
		borrowingRenewal.setDateRenewal(java.sql.Date.valueOf(localDate));
		
		LocalDate newDeadline = borrowingRenewal.getDeadline().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		borrowingRenewal.setDeadline(java.sql.Date.valueOf(newDeadline.plusMonths(1)));
		
		borrowingRenewal.setState(State.Renouvele);
		
		Borrowing borrowingRenew = borrowingDao.save(borrowingRenewal);
		
		return new ResponseEntity<Borrowing>(borrowingRenew, HttpStatus.OK);
	}
	
	/**
	 * Function to close a borrowing
	 * @param id id the borrowing
	 * @return response entity
	 */
	@PostMapping("/emprunt/{id}/returnborrowing")
	public ResponseEntity<Borrowing> returnBorrowing(@PathVariable int id) {
		
		Optional<Borrowing> borrowing = borrowingDao.findById(id);
		
		if(!borrowing.isPresent()) throw new BorrowingNotFoundException("L'emprunt avec l'id : " + id + " n'a pas été retrouvé.");
		
		Borrowing borrowingReturn = borrowing.get();
		
		LocalDate localDate = LocalDate.now();
		
		borrowingReturn.setDateReturn(java.sql.Date.valueOf(localDate));
		
		Borrowing returnBorrowing = borrowingDao.save(borrowingReturn);
		
		return new ResponseEntity<Borrowing>(returnBorrowing, HttpStatus.OK);
	}
}
