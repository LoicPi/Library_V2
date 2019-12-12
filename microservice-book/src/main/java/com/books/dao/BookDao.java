package com.books.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.books.model.Book;

public interface BookDao extends JpaRepository<Book, Integer> {
	
	List<Book> findByNameContainingIgnoreCase(@Param("name") String name); 
	
	@Query("From Book b order by b.name")
	List<Book> findAll();
	
	Book findBookById(Integer id);
	
	
}
