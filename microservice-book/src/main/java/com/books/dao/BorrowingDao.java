package com.books.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.books.model.BookCopy;
import com.books.model.Borrowing;

public interface BorrowingDao extends JpaRepository<Borrowing, Integer> {
	
	List<Borrowing> findByIdUser(Integer idUser);
	List<Borrowing> findByBookCopy(BookCopy bookCopy);
	
}
