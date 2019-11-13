package com.clientui.proxies;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.clientui.beans.AuthorBean;
import com.clientui.beans.BookBean;
import com.clientui.beans.BookCopyBean;
import com.clientui.beans.BookTypeBean;

@FeignClient(name = "zuul-server")
public interface MicroserviceBookProxy {
	
	@GetMapping("/microservice-books/auteurs")
	List<AuthorBean> listAuthors();
	
	@PostMapping("/microservice-books/auteurs/add-authors")
	AuthorBean addAuthor(@Valid @RequestBody AuthorBean authorBean);
	
	@GetMapping("/microservice-books/auteurs/{id}/vueauteur")
	AuthorBean getAuthor(@PathVariable int id);
	
	@GetMapping( "/microservice-books/livres")
	List<BookBean> listBooks();
	
	@PostMapping("/microservice-books/livres/add-book")
	BookBean addBook(@Valid @RequestBody BookBean bookBean);
	
	@GetMapping("/microservice-books/livres/{id}/vueLivre")
	BookBean getBook(@PathVariable int id);
	
	@GetMapping("/microservice-books/exemplaires")
	List<BookCopyBean> listBooksCopies();
	
	@PostMapping("/microservice-books/exemplaires/add-bookcopy")
	BookCopyBean addBookCopy(@Valid @RequestBody BookCopyBean bookCopyBean);
	
	@GetMapping("/microservice-books/categories")
	List<BookTypeBean> listBooksTypes();
	
	@PostMapping("/microservice-books/categories/add-booktype")
	BookTypeBean addBookType(@Valid @RequestBody BookTypeBean bookTypeBean);
	
	@GetMapping("/microservice-books/categories/{id}/vuecategorie")
	BookTypeBean getBookType(@PathVariable int id);
}
