package com.books.web.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.books.dao.BookDao;
import com.books.dao.BookingDao;
import com.books.dao.BorrowingDao;
import com.books.dto.BookingDto;
import com.books.exceptions.BookingNotFoundException;
import com.books.exceptions.CanNotAddBookingException;
import com.books.model.Booking;
import com.books.model.Borrowing;
import com.books.model.State;
import com.books.service.BookingService;

@RestController
public class BookingController {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BookDao bookDao;
	
	@Autowired
	private BorrowingDao borrowingDao;
	
	@Autowired
	private BookingDao bookingDao;
	
	@Autowired
	private BookingService bookingService;
	
	/**
	 * Function which adds a booking following user request
	 * @param idUser the id of the user
	 * @param idBook the id of the book
	 * @return response entity
	 */
	@PostMapping("/reservation/add-booking")
	public ResponseEntity<Booking> addBooking (@RequestParam Integer idUser, @RequestParam Integer idBook) {
		
		//Create a new object reservation
		Booking booking = new Booking();
		
		//Setting the object reservation
		booking.setBook(bookDao.findBookById(idBook));
		
		booking.setIdUser(idUser);
		
		//Setting the maximum number of reservations
		Integer maxBooking = (booking.getBook().getBooksCopies().size())*2;
		
		//Check that user has not already booked this book
		List<Booking> bookingBook = bookingDao.findBookingByBook_IdOrderByDateCreate(idBook);
		
		for (Booking bookingB : bookingBook) {
			if ((bookingB.getIdUser()).equals(booking.getIdUser())) {
				throw new CanNotAddBookingException("L'utilisateur a déjà réservé ce livre.");
			}
		}
		
		//Check that user has not already borrowed this book
		List<Borrowing> borrowingsOfUser = borrowingDao.findByIdUserAndStates(idUser, Arrays.asList(State.EnCours, State.Renouvele, State.EnRetard));
		
		for (Borrowing borrowingU : borrowingsOfUser) {
			if ((borrowingU.getBookCopy().getBook().getId()).equals(booking.getBook().getId())) {
				throw new CanNotAddBookingException("Ce livre est emprunté par l'utilisateur");
			}
		}
		
		//Checks that if the maximum number of bookings is reached
		if (bookingBook.size() >= maxBooking) {
			throw new CanNotAddBookingException("La capacité de réservation de ce livre est au maximum");
		}
		
		LocalDate localDate = LocalDate.now();
		
		LocalDate ld = LocalDate.of( 2001 , 01 , 01 );
		
		booking.setCreateBooking(java.sql.Date.valueOf(localDate));
		
		booking.setDateMail(java.sql.Date.valueOf(ld));
		
		booking.setState(State.EnAttente);
				
		Booking newBooking = bookingDao.save(booking);
		
		if (newBooking == null ) throw new CanNotAddBookingException("Impossible d'ajouter la réservation");
		
		return new ResponseEntity<Booking>(newBooking, HttpStatus.OK);		
	}
	
	/**
	 * Function to delete a booking
	 * @param id id of the booking
	 */
	@PostMapping("/reservation/{id}/cancel-booking")
	public void cancelBookingByUser (@PathVariable int id){	
		bookingService.cancelBookingUser(id);
	}
	
	@GetMapping("/reservation/utilisateur/{idUser}")
	public List<BookingDto> listBookingsOfUser(@PathVariable int idUser) {
		
		List<Booking> bookingsofUser = bookingDao.findByIdUser(idUser);
		
		List<BookingDto> bookings = new ArrayList<BookingDto>();
		
		for (Booking booking : bookingsofUser) {
			BookingDto bookingD = new BookingDto();
			
			bookingD.setId(booking.getId());
			bookingD.setIdUser(booking.getIdUser());
			bookingD.setBookName(booking.getBook().getName());
			
			List<Booking> bookingList = bookingDao.findBookingByBook_IdOrderByDateCreate(booking.getBook().getId());
			
			for (int i = 0; i < bookingList.size(); i++) {
				if (bookingList.get(i).getId().equals(bookingD.getId())) {
					bookingD.setPosition(i+1);
					break;
				}
			}
			
			
			
			bookings.add(bookingD);
		}
		
		if(bookings.isEmpty()) throw new BookingNotFoundException("Les emprunts de livres pour l'utilisateur : " + idUser + " n'ont pas été retrouvés.");
		
		log.info("Récupération de la liste des réservations d'un utilisateur");
		
		return bookings;
	}
	
	@GetMapping("/reservations")
	public List<Booking> listBookings() {
		
		List<Booking> bookings = bookingDao.findAll();
		
		if(bookings.isEmpty()) throw new BookingNotFoundException("Aucune réservation n'a été retrouvé.");
		
		log.info("Récupération de la liste des réservations.");
		
		return bookings;		
	}
	
}
