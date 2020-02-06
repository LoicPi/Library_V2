package com.books.web.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.books.dao.BookDao;
import com.books.dao.BookingDao;
import com.books.dao.BorrowingDao;
import com.books.exceptions.BookingNotFoundException;
import com.books.exceptions.CanNotAddBookingException;
import com.books.model.Booking;
import com.books.model.Borrowing;

@RestController
public class BookingController {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BookDao bookDao;
	
	@Autowired
	private BorrowingDao borrowingDao;
	
	@Autowired
	private BookingDao bookingDao;
	
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
		List<Booking> bookingBook = bookingDao.findBookingByBook_Id(idBook);
		
		for (Booking bookingB : bookingBook) {
			if ((bookingB.getIdUser()).equals(booking.getIdUser())) {
				throw new CanNotAddBookingException("L'utilisateur a déjà réservé ce livre.");
			}
		}
		
		//Check that user has not already borrowed this book
		List<Borrowing> borrowingsOfUser = borrowingDao.findByIdUser(idUser);
		
		for (Borrowing borrowingU : borrowingsOfUser) {
			if ((borrowingU.getBookCopy().getBook().getId()).equals(booking.getBook().getId())) {
				throw new CanNotAddBookingException("Ce livre est emprunté par l'utilisateur");
			}
		}
		
		//Checks that if the maximum number of bookings is reached
		if (bookingBook.size() >= maxBooking) {
			throw new CanNotAddBookingException("La capacité de réservation de ce livre est au maximum");
		}
		
		booking.setPosition(bookingBook.size()+1);
				
		Booking newBooking = bookingDao.save(booking);
		
		if (newBooking == null ) throw new CanNotAddBookingException("Impossible d'ajouter la réservation");
		
		return new ResponseEntity<Booking>(newBooking, HttpStatus.OK);		
	}
	
	/**
	 * Function to delete a booking
	 * @param id id of the booking
	 */
	@PostMapping("/reservation/{id}/del-booking")
	public void delBooking (@PathVariable int id){
		
		Optional<Booking> booking = bookingDao.findById(id);
		
		if (!booking.isPresent()) throw new BookingNotFoundException ("La réservation avec l'id : " + id + " n'a pas été retrouvé.");
		
		Booking bookingDel = booking.get();
		
		Integer bookingDelPosition = bookingDel.getPosition();
		
		Integer bookId = bookingDel.getBook().getId();
		
		bookingDao.delete(bookingDel);

		List<Booking> bookingList = bookingDao.findBookingByBook_Id(bookId);
		
		if (bookingList.size() > 0) {
			for (Booking bookingB : bookingList) {
				if (bookingB.getPosition() != 1 && bookingDelPosition < bookingB.getPosition() ) {
					bookingB.setPosition(bookingB.getPosition()-1);
					bookingDao.save(bookingB);
				}
			}
		}
	}
	
}
