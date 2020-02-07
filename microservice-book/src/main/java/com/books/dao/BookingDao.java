package com.books.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.books.model.Booking;

public interface BookingDao extends JpaRepository<Booking, Integer> {

	List<Booking> findBookingByBook_IdOrderByDateCreate(Integer idBook);
	
	List<Booking> findBookingBySendMail(Boolean sendMail);

	List<Booking> findByIdUser(int idUser); 
	
}
