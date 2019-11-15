package com.books.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.books.model.Book;
import com.books.model.BookType;

public interface BookDao extends JpaRepository<Book, Integer> {
	
	List<Book> findBooksByName (String name);
	Book findBookById(Integer id);
	
}
