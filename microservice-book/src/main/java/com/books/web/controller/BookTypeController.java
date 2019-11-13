package com.books.web.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.books.dao.BookTypeDao;
import com.books.exceptions.BookTypeNotFoundException;
import com.books.exceptions.CanNotAddBookTypeException;
import com.books.model.BookType;

@RestController
public class BookTypeController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BookTypeDao bookTypeDao;
	
	/**
	 * Function to get a list of book type
	 * @return a list of book type
	 */
	@GetMapping("/typedelivre")
	public List<BookType> listBookType() {
		
		List<BookType> booksTypes = bookTypeDao.findAll();
		
		if (booksTypes.isEmpty()) throw new BookTypeNotFoundException ("Les types de livre n'ont pas été retrouvés.");
		
		log.info("Récupération de la liste des types de livres");
		
		return booksTypes;
	}
	
	/**
	 * Function to add a booktype in db
	 * @param bookType BookType save in db
	 * @return ResponseEntity
	 */
	@PostMapping("/typedelivre/add-booktype")
	public ResponseEntity<BookType> addBookType(@Valid @RequestBody BookType bookType) {
		
		BookType newBookType = bookTypeDao.save(bookType);
		
		if (newBookType == null) throw new CanNotAddBookTypeException("Impossible d'ajouter le type de livre.");
		
		log.info("Sauvegarde du type de livre : " + newBookType);
		
		return new ResponseEntity<BookType>(bookType, HttpStatus.CREATED);
	}
	
	/**
	 * Function to get a BookType
	 * @param id Id of the BookType
	 * @return bookType
	 */
	@GetMapping("/typedelivre/{id}")
	public BookType getBookType(@PathVariable int id) {
		
		Optional<BookType> bookType = bookTypeDao.findById(id);
		
		if (!bookType.isPresent()) throw new BookTypeNotFoundException("Le type de livre avec l'id : " + id + " n'a pas été retrouvé.");
		
		log.info("Accès au type de livre.");
		
		return bookType.get();		
	}

}
