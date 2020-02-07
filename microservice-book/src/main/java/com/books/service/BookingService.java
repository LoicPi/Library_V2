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

	public void cancelBookingUser(int id) {
		// Find booking by id
		Optional<Booking> booking = bookingDao.findById(id);

		if (!booking.isPresent())
			throw new BookingNotFoundException("La réservation avec l'id : " + id + " n'a pas été retrouvé.");

		Booking bookingEnd = booking.get();

		bookingEnd.setState(State.AnnuleU);

		// Save the booking in the database
		bookingDao.save(bookingEnd);
	}
	
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
