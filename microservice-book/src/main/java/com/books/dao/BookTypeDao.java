package com.books.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.books.model.BookType;

public interface BookTypeDao extends JpaRepository<BookType, Integer> {
	
}
