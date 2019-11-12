package com.books.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.books.model.Author;
import com.books.model.Book;
import com.books.model.BookType;

public interface BookDao extends JpaRepository<Book, Integer> {
	
	Book findBookByName (String name);
	List<Book> findBookByAuthor(Author author);
	Book findBookById(Integer id);
	List<Book> findBookByType(BookType bookType);
	
}
