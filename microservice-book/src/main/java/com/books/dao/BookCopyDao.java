package com.books.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.books.model.BookCopy;

public interface BookCopyDao extends JpaRepository<BookCopy, Integer> {

	List<BookCopy> findBookCopyByBook_Id(Integer idBook);
}
