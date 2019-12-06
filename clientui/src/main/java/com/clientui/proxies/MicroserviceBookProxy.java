package com.clientui.proxies;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.clientui.beans.AuthorBean;
import com.clientui.beans.BookBean;
import com.clientui.beans.BookCopyBean;
import com.clientui.beans.BookTypeBean;
import com.clientui.beans.BorrowingBean;
import com.clientui.configuration.FeignConfig;

@FeignClient(name = "zuul-server", contextId="booksProxy", configuration=FeignConfig.class)
@RibbonClient(name = "microservice-books")
public interface MicroserviceBookProxy {
	
	@GetMapping("/microservice-books/auteurs")
	List<AuthorBean> listAuthors();
	
	@GetMapping("/microservice-books/auteurs/{id}/vueauteur")
	AuthorBean getAuthor(@PathVariable("id") int id);
	
	@GetMapping( "/microservice-books/livres")
	List<BookBean> listBooks();
	
	@GetMapping("/microservice-books/livres/{id}/vueLivre")
	BookBean getBook(@PathVariable("id") int id);
	
	@PostMapping("/microservice-books/livres/recherche")
	List<BookBean> searchBooksByName(@RequestParam("name") String name);
	
	@GetMapping("/microservice-books/exemplaires")
	List<BookCopyBean> listBooksCopies();
	
	@PostMapping("/microservice-books/exemplaires/add-bookcopy")
	BookCopyBean addBookCopy(@Valid @RequestBody BookCopyBean bookCopyBean);
	
	@GetMapping("/microservice-books/categories")
	List<BookTypeBean> listBooksTypes();
	
	@PostMapping("/microservice-books/categories/add-booktype")
	BookTypeBean addBookType(@Valid @RequestBody BookTypeBean bookTypeBean);
	
	@GetMapping("/microservice-books/categories/{id}/vuecategorie")
	BookTypeBean getBookType(@PathVariable("id") int id);
	
	@GetMapping("/microservice-books/emprunts")
	List<BorrowingBean> listBorrowigs();
	
	@GetMapping("/microservice-books/emprunts/utilisateur/{idUser}")
	List<BorrowingBean> listBorrowingsOfUser(@PathVariable("idUser") int idUser);
	
	@PostMapping("/microservice-books/emprunt/add-borrowing")
	BorrowingBean addBorrowing(@Valid @RequestBody BorrowingBean borrowingBean);
	
	@PostMapping("/microservice-books/emprunt/{id}/renouvellement")
	BorrowingBean renewalBorrowing (@PathVariable("id") int id);
	
	@PostMapping("/microservice-books/emprunt/{id}/returnborrowing")
	BorrowingBean returnBorrowing(@PathVariable("id") int id);	
}
