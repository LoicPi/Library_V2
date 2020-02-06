package com.books.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.books.model.Booking;

public interface BookingDao extends JpaRepository<Booking, Integer> {

	List<Booking> findBookingByBook_Id(Integer idBook);
	
	List<Booking> findBookingBySendMail(Boolean sendMail);
	
	@Query("Select b from Booking where b.book_id =: bookId and b.position =: position")
	Booking findByBookAndPosition (Integer bookId, Integer position); 
	
}
