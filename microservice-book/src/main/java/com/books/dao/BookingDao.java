package com.books.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.books.model.Book;
import com.books.model.Booking;
import com.books.model.State;

public interface BookingDao extends JpaRepository<Booking, Integer> {
	
	@Query("Select b from Booking b where b.book =:book and b.state =:state order by b.dateCreate")
	List<Booking> findBookingByBookAndStateOrderByDateCreate(Book book, State state);
	
	@Query("Select b from Booking b where b.sendMail =:sendMail and b.state =:state")
	List<Booking> findBookingBySendMailAndState(Boolean sendMail, State state);

	@Query("Select b from Booking b where b.idUser =:idUser and b.state =:state order by b.dateCreate")
	List<Booking> findByIdUserAndStateOrderByDateCreate(int idUser, State state);

	List<Booking> findBookingByBook_IdOrderByDateCreate( Integer idBook); 
	
}
