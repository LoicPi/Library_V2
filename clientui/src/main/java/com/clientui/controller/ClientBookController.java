package com.clientui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clientui.beans.AuthorBean;
import com.clientui.beans.BookBean;
import com.clientui.beans.BookTypeBean;
import com.clientui.beans.UserBean;
import com.clientui.proxies.MicroserviceBookProxy;
import com.clientui.proxies.MicroserviceUserProxy;

@Controller
public class ClientBookController {

	@Autowired
	private MicroserviceBookProxy BooksProxy;
	
	@Autowired
	private MicroserviceUserProxy UsersProxy;
	
	@RequestMapping("/livres")
	public String booksListPage(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		Integer idSession = (Integer) session.getAttribute("id");
		
		if (session.getAttribute("id") != null ) {			
			UserBean user = UsersProxy.getUser(idSession);
			model.addAttribute("user", user);
		}
		
		List<BookBean> books = BooksProxy.listBooks();
		
		model.addAttribute("books", books);
		
		return "booksListPage";
	}
	
	@RequestMapping("/livres/{id}/vuelivre")
	public String bookPage(@PathVariable("id") int id, Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		Integer idSession = (Integer) session.getAttribute("id");
		
		if (session.getAttribute("id") != null ) {			
			UserBean user = UsersProxy.getUser(idSession);
			model.addAttribute("user", user);
		}
		
		BookBean bookBean = BooksProxy.getBook(id);
		
		model.addAttribute("book", bookBean);
		
		return "bookPage";
	}
	
	@RequestMapping("/auteurs")
	public String authorsListPage(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		Integer idSession = (Integer) session.getAttribute("id");
		
		if (session.getAttribute("id") != null ) {			
			UserBean user = UsersProxy.getUser(idSession);
			model.addAttribute("user", user);
		}
		
		List<AuthorBean> authors = BooksProxy.listAuthors();
		
		model.addAttribute("authors",  authors);
		
		return "authorsListPage";
	}
	
	@RequestMapping("/auteurs/{id}/vueauteur")
	public String authorPage(@PathVariable("id") int id, Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		Integer idSession = (Integer) session.getAttribute("id");
		
		if (session.getAttribute("id") != null ) {			
			UserBean user = UsersProxy.getUser(idSession);
			model.addAttribute("user", user);
		}
		
		AuthorBean author = BooksProxy.getAuthor(id);
		
		model.addAttribute("author", author);
		
		return "authorPage";
	}
	
	@RequestMapping("/categories")
	public String bookTypesListPage(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		Integer idSession = (Integer) session.getAttribute("id");
		
		if (session.getAttribute("id") != null ) {			
			UserBean user = UsersProxy.getUser(idSession);
			model.addAttribute("user", user);
		}
		
		List<BookTypeBean> bookTypes  = BooksProxy.listBooksTypes();
		
		model.addAttribute("bookTypes", bookTypes);
		
		return "bookTypesListPage";
	}
	
	@RequestMapping("/categories/{id}/vuecategories")
	public String bookTypePage(@PathVariable("id") int id, Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		Integer idSession = (Integer) session.getAttribute("id");
		
		if (session.getAttribute("id") != null ) {			
			UserBean user = UsersProxy.getUser(idSession);
			model.addAttribute("user", user);
		}
		
		BookTypeBean bookType = BooksProxy.getBookType(id);
		
		model.addAttribute("bookType", bookType);
		
		return "bookTypePage";
	}
	
	@RequestMapping("/emprunt/{id}/renouvellement")
	public String renewalBorrowing(@PathVariable("id") int id, Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		Integer idSession = (Integer) session.getAttribute("id");
		
		BooksProxy.renewalBorrowing(id);
				
		return "redirect:/compte/" + idSession + "/moncompte";
	}
	
	
}
