package com.books.web.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.books.dao.AuthorDao;
import com.books.exceptions.AuthorNotFoundException;
import com.books.model.Author;

@RestController
public class AuthorController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AuthorDao authorDao;
	
	/**
	 * Function to get a list of authors
	 * @return list of authors
	 */
	@GetMapping("/auteurs")
	public List<Author> listAuthors() {
		
		List<Author> authors = authorDao.findAll();
		
		if( authors.isEmpty() ) throw new AuthorNotFoundException("Aucun auteur n'a été retrouvé.");
		
		log.info("Récupération de la liste des auteurs");
	
		return authors;
	}
	
	/**
	 * Function to see a view of author
	 * @param id Id of the author
	 * @return author
	 */
	@GetMapping("/auteurs/{id}/vueauteur")
	public Author getAuthor(@PathVariable int id){
		
		Optional<Author> author = authorDao.findById(id);
		
		if(!author.isPresent()) throw new AuthorNotFoundException("L'auteur avec l'id : " + id +" n'a pas été retrouvé.");
		
		log.info("Accès à l'auteur");
		
		return author.get();
	}
	
}
