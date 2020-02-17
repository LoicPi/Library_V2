package com.books.web.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.books.service.BookService;
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
	private BookService bookService;
	
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
		List<Booking> bookingBook = bookingDao.findBookingByBookAndStateOrderByDateCreate(booking.getBook(), State.EnAttente);
		
		for (Booking bookingB : bookingBook) {
			if ((bookingB.getIdUser()).equals(booking.getIdUser())) {
				throw new CanNotAddBookingException("BookingException01");
			}
		}
		
		//Check that user has not already borrowed this book
		List<Borrowing> borrowingsOfUser = borrowingDao.findByIdUserAndStates(idUser, Arrays.asList(State.EnCours, State.Renouvele, State.EnRetard));
		
		for (Borrowing borrowingU : borrowingsOfUser) {
			if ((borrowingU.getBookCopy().getBook().getId()).equals(booking.getBook().getId())) {
				throw new CanNotAddBookingException("BookingException02");
			}
		}
		
		//Checks that if the maximum number of bookings is reached
		if (bookingBook.size() >= maxBooking) {
			throw new CanNotAddBookingException("BookingException03");
		}
		
		LocalDate localDate = LocalDate.now();
		
		LocalDate ld = LocalDate.of( 2001 , 01 , 01 );
		
		booking.setDateCreate(java.sql.Date.valueOf(localDate));
		
		booking.setDateMail(java.sql.Date.valueOf(ld));
		
		booking.setState(State.EnAttente);
				
		Booking newBooking = bookingDao.save(booking);
		
		if (newBooking == null ) throw new CanNotAddBookingException("BookingException04");
		
		return new ResponseEntity<Booking>(newBooking, HttpStatus.OK);		
	}
	
	/**
	 * Function to delete a booking
	 * @param id id of the booking
	 */
	@PostMapping("/reservation/{id}/cancel-booking")
	public ResponseEntity<Booking> cancelBookingByUser (@PathVariable int id){	
		
		Booking booking = bookingService.cancelBookingUser(id);
		
		return new ResponseEntity<Booking>(booking, HttpStatus.OK);
	}
	
	@GetMapping("/reservation/utilisateur/{idUser}")
	public List<BookingDto> listBookingsOfUser(@PathVariable int idUser) {
		
		List<Booking> bookingsofUser = bookingDao.findByIdUserAndStateOrderByDateCreate(idUser, State.EnAttente);
		
		List<BookingDto> bookings = new ArrayList<BookingDto>();
		
		for (Booking booking : bookingsofUser) {
			BookingDto bookingD = new BookingDto();
			
			bookingD.setId(booking.getId());
			bookingD.setIdUser(booking.getIdUser());
			bookingD.setBookName(booking.getBook().getName());
			bookingD.setState(booking.getState().stateName);
			
			List<Booking> bookingList = bookingDao.findBookingByBookAndStateOrderByDateCreate(booking.getBook(), State.EnAttente);
			
			for (int i = 0; i < bookingList.size(); i++) {
				if (bookingList.get(i).getId().equals(bookingD.getId())) {
					bookingD.setPosition(i+1);
					break;
				}
			}
			
			bookService.nearestDeadlineBook(booking.getBook());
			
			bookingD.setNearestDeadline(booking.getBook().getNearestDeadline());
			
			bookings.add(bookingD);
		}
		
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
	
	@DeleteMapping("/reservation/{id}/delete-booking")
	public Response deleteBooking (@PathVariable int id) {
		
		Optional<Booking> booking = bookingDao.findById(id);
		
		if(!booking.isPresent()) throw new BookingNotFoundException("Aucune réservation n'a été retrouvé.");
		
		Booking bookingDelete = booking.get();
		
		String bookingD = bookingDelete.toString();
		
		bookingDao.delete(bookingDelete);
		
		log.info("Suppression de la reservation : " + bookingD );
		
		return Response.status(200).entity("Booking is deleted").build();
	}
	
}
