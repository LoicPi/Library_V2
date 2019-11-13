package com.books.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.books.dao.BookCopyDao;
import com.books.exceptions.BookCopyNotFoundException;
import com.books.exceptions.CanNotAddBookCopyException;
import com.books.model.BookCopy;

@RestController
public class BookCopyController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BookCopyDao bookCopyDao;
	
	@GetMapping("/exemplaires")
	public List<BookCopy> listBooksCopies() {
		
		List<BookCopy> booksCopies = bookCopyDao.findAll();
		
		if (booksCopies.isEmpty()) throw new BookCopyNotFoundException("Les exemplaires des livres n'ont pas été retrouvés.");
		
		log.info("Récupération de la liste des exemplaires des livres.");
		
		return booksCopies;
	}

	@PostMapping("exemplaires/add-bookcopy")
	public ResponseEntity<BookCopy> addBookCopy(@Valid @RequestBody BookCopy bookCopy) {
		
		BookCopy newBookCopy = bookCopyDao.save(bookCopy);
		
		if(newBookCopy == null) throw new CanNotAddBookCopyException("Impossible d'ajouter l'exemplaire de ce livre.");
		
		log.info("Sauvegarde de l'exemplaire du livre : " + newBookCopy );
		
		return new ResponseEntity<BookCopy>(bookCopy, HttpStatus.CREATED);
	}
	
	
}
