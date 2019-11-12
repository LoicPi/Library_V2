package com.books.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.books.model.BookCopy;

public interface BookCopyDao extends JpaRepository<BookCopy, Integer> {

}
