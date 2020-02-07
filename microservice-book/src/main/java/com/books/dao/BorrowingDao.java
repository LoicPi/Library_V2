package com.books.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.books.model.BookCopy;
import com.books.model.Borrowing;
import com.books.model.State;

public interface BorrowingDao extends JpaRepository<Borrowing, Integer> {
	
	@Query("Select b from Borrowing b where b.idUser =:idUser order by dateBorrowed")
	List<Borrowing> findByIdUser(Integer idUser);
	
	List<Borrowing> findByBookCopy(BookCopy bookCopy);
	
	@Query("Select b from Borrowing b where b.deadline < :today and b.state in (:states)")
	List<Borrowing> findByStates(Date today, List<State> states);
	
	@Query("Select b from Borrowing b where b.state =:state ")
	List<Borrowing> findByStateName(State state);
	
	@Query("Select b from Borrowing b where b.idUser =:idUser and b.state in (:states)")
	List<Borrowing> findByIdUserAndStates(Integer idUser, List<State> states);
}
