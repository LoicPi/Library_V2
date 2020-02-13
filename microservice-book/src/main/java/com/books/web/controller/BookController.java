package com.books.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.books.dao.BookDao;
import com.books.exceptions.BookNotFoundException;
import com.books.model.Book;
import com.books.service.BookService;

@RestController
public class BookController {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BookDao bookDao;
	
	@Autowired
	private BookService bookService;
	
	/**
	 * Function to have the list of books
	 * @return list of books
	 */
	@GetMapping( "/livres")
	public List<Book> listBooks() {
		
		List<Book> books = bookDao.findAll();
		
		if( books.isEmpty() ) throw new BookNotFoundException("Aucun livre n'a été retrouvé.");
		
		for (Book book : books) {
			bookService.nearestDeadlineBook(book);
			bookService.numberOfBooking(book);
		}

		log.info("Récupération de la liste des livres");

		return books;
	}
	
	/**
	 * Function to get a book by id
	 * @param id Id of book
	 * @return book
	 */
	@GetMapping("/livres/{id}/vueLivre")
	public Book getBook(@PathVariable int id){
		
		Optional<Book> bookO = bookDao.findById(id);
		
		if(!bookO.isPresent()) throw new BookNotFoundException("Le livre avec l'id : " + id +" n'a pas été retrouvé.");

		Book book = bookO.get();
		
		bookService.nearestDeadlineBook(book);
		bookService.numberOfBooking(book);

		log.info("Accès au livre");
		
		return book;
	}
	
	/**
	 * Function to get a list of books by title
	 * @param title Title of book
	 * @return List of books
	 */
	@PostMapping("/livres/recherche")
	public List<Book> searchBooksByName (String name) {
		
		List<Book> books = bookDao.findByNameContainingIgnoreCaseOrderByName(name);
		
		List<Book> booksS = new ArrayList<Book>();
		
		for (Book book : books) {
			bookService.nearestDeadlineBook(book);
			bookService.numberOfBooking(book);
			booksS.add(book);
		}
		
		log.info("Récupération de la liste de livres recherchée");
		
		return booksS;
	}
	
}
