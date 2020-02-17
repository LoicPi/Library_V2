package com.books.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.books.beans.UserBean;
import com.books.dao.BookingDao;
import com.books.dao.BorrowingDao;
import com.books.exceptions.BorrowingInvalidRenewalException;
import com.books.exceptions.BorrowingNotFoundException;
import com.books.exceptions.CanNotAddBorrowingException;
import com.books.model.Book;
import com.books.model.Booking;
import com.books.model.Borrowing;
import com.books.model.State;
import com.books.proxies.MicroserviceUserProxy;
import com.books.service.BookingService;
import com.books.service.MailService;

@RestController
public class BorrowingController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BorrowingDao borrowingDao;
	
	@Autowired
	private BookingDao bookingDao;
	
	@Autowired
	private MicroserviceUserProxy microserviceUserProxy;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private BookingService bookingService;
	
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
		
		Book bookBorrowed = borrowing.getBookCopy().getBook();
		
		Integer borrowingIdUser = borrowing.getIdUser();
		
		List<Booking> bookingBook = bookingDao.findBookingByBookAndStateOrderByDateCreate(bookBorrowed, State.EnAttente);
		
		if (bookingBook.size() > 0) {
			Booking booking = bookingBook.get(0);
			
			Integer bookingIdUser = booking.getIdUser();
			
			if(borrowingIdUser.equals(bookingIdUser)) {
				borrowing.setDateBorrowed(java.sql.Date.valueOf(localDate));
				
				borrowing.setDeadline(java.sql.Date.valueOf(localDate.plusMonths(1)));
				
				borrowing.setState(State.EnCours);
				
				LocalDate ld = LocalDate.of( 2001 , 01 , 01 ) ; 
				
				borrowing.setDateRenewal(java.sql.Date.valueOf(ld));
				
				borrowing.setDateReturn(java.sql.Date.valueOf(ld));
				
				Borrowing newBorrowing = borrowingDao.save(borrowing);
				
				bookingService.endBooking(booking.getId());
				
				if(newBorrowing == null) throw new CanNotAddBorrowingException ("Impossible d'ajouter l'emprunt.");
				
				return new ResponseEntity<Borrowing>(newBorrowing, HttpStatus.OK);
			} else {
				throw new CanNotAddBorrowingException ("Ce livre est reservé par un autre utilisateur.");
			}
		} else {
			borrowing.setDateBorrowed(java.sql.Date.valueOf(localDate));
			
			borrowing.setDeadline(java.sql.Date.valueOf(localDate.plusMonths(1)));
			
			borrowing.setState(State.EnCours);
			
			LocalDate ld = LocalDate.of( 2001 , 01 , 01 ) ; 
			
			borrowing.setDateRenewal(java.sql.Date.valueOf(ld));
			
			borrowing.setDateReturn(java.sql.Date.valueOf(ld));
			
			Borrowing newBorrowing = borrowingDao.save(borrowing);
			
			if(newBorrowing == null) throw new CanNotAddBorrowingException ("Impossible d'ajouter l'emprunt.");
			
			return new ResponseEntity<Borrowing>(newBorrowing, HttpStatus.OK);
		}			
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
			throw new BorrowingInvalidRenewalException ("Borrowing01");
		}
		
		borrowingRenewal.setRenewal(true);
		
		LocalDate localDate = LocalDate.now();
		
		borrowingRenewal.setDateRenewal(java.sql.Date.valueOf(localDate));
		
		Date newDeadline = DateUtils.addMonths(borrowingRenewal.getDeadline(), 1);
		
		borrowingRenewal.setDeadline(newDeadline);
		
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
		
		borrowingReturn.setState(State.Termine);
		
		Book bookReturn = borrowingReturn.getBookCopy().getBook();
		
		List<Booking> bookings = bookingDao.findBookingByBookAndStateOrderByDateCreate(bookReturn, State.EnAttente);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		if (bookings.size() > 0) {
			
			Booking booking = bookings.get(0);
			
			booking.setDateMail(java.sql.Date.valueOf(localDate));
			
			Date deadlineBooking = DateUtils.addDays(booking.getDateMail(), 2);
			
			UserBean user = microserviceUserProxy.getUser(booking.getIdUser());
			
			String mailTo = user.getEmail();
			String mailSubject = "Bibliothèque : Votre réservation est disponible";
			String mailText = "Bonjour " + user.getFirstName() + " " + user.getLastName() + "," +
					"\n\nNous vous informons que la réservation du document ci-dessous est disponible : " +
					"\n\n" + bookReturn.getName() +
					"\n\nVous avez jusqu'au " + dateFormat.format(deadlineBooking) + " pour venir récupérer votre document." +
					"\n\nPassée cette date, le document sera remis en circulation." +
					"\n\nCordialement," +
                    "\n\nL'équipe de la Bibliothèque";
			
			mailService.sendMessage( mailTo, mailSubject, mailText );
			
			booking.setSendMail(true);
			
			bookingDao.save(booking);
			
		}
		
		Borrowing returnBorrowing = borrowingDao.save(borrowingReturn);
		
		return new ResponseEntity<Borrowing>(returnBorrowing, HttpStatus.OK);
	}
	
	@DeleteMapping("emprunt/{id}/delete-borrowing")
	public Response deleteBorrowing(@PathVariable int id) {
		
		Optional<Borrowing> borrowing = borrowingDao.findById(id);
		
		if(!borrowing.isPresent()) throw new BorrowingNotFoundException("Aucun prêt n'a été retrouvé.");
		
		Borrowing borrowingDelete = borrowing.get();
		
		String borrowingD = borrowingDelete.toString();
		
		borrowingDao.delete(borrowingDelete);
		
		log.info("Suppression de l'emprunt : " + borrowingD );
		
		return Response.status(200).entity("Borrowing is deleted").build();
	}
	
}
