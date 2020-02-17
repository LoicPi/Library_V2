package com.books.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.dao.BookingDao;
import com.books.exceptions.BookingNotFoundException;
import com.books.model.Booking;
import com.books.model.State;

@Service
public class BookingService {

	@Autowired
	public BookingDao bookingDao;

	/**
	 * Function to end a booking object
	 * @param id the id of the booking
	 */
	public void endBooking(int id) {
		// Find booking by id
		Optional<Booking> booking = bookingDao.findById(id);

		if (!booking.isPresent())
			throw new BookingNotFoundException("La réservation avec l'id : " + id + " n'a pas été retrouvé.");

		Booking bookingEnd = booking.get();

		bookingEnd.setState(State.Finie);

		// Save the booking in the database
		bookingDao.save(bookingEnd);
	}

	/**
	 * Function to cancel a booking on the user's demand
	 * @param id if of the booking
	 */
	public Booking cancelBookingUser(int id) {
		// Find booking by id
		Optional<Booking> booking = bookingDao.findById(id);

		if (!booking.isPresent())
			throw new BookingNotFoundException("La réservation avec l'id : " + id + " n'a pas été retrouvé.");

		Booking bookingEnd = booking.get();

		bookingEnd.setState(State.AnnuleU);

		// Save the booking in the database
		bookingDao.save(bookingEnd);
		
		return bookingEnd;
	}
	
	/**
	 * Function to cancel a booking when the batch verify if booking is be past
	 * @param id id of the booking
	 */
	public void cancelBookingMail(int id) {
		// Find booking by id
		Optional<Booking> booking = bookingDao.findById(id);

		if (!booking.isPresent())
			throw new BookingNotFoundException("La réservation avec l'id : " + id + " n'a pas été retrouvé.");

		Booking bookingEnd = booking.get();

		bookingEnd.setState(State.AnnuleM);

		// Save the booking in the database
		bookingDao.save(bookingEnd);
	}
}
