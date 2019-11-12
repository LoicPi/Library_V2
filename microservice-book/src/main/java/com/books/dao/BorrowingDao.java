package com.books.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.books.model.Borrowing;

public interface BorrowingDao extends JpaRepository<Borrowing, Integer> {

}
