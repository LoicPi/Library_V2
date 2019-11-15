package com.books.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.books.model.Author;

public interface AuthorDao extends JpaRepository<Author, Integer> {
	
	Author findByLastName (String lastname);

}
