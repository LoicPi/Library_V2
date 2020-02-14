package com.clientui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clientui.beans.AuthorBean;
import com.clientui.beans.BookBean;
import com.clientui.beans.BookTypeBean;
import com.clientui.beans.BookingBean;
import com.clientui.beans.UserBean;
import com.clientui.exceptions.BorrowingInvalidRenewalException;
import com.clientui.exceptions.CanNotAddBookingException;
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

		if (session.getAttribute("id") != null) {
			UserBean user = UsersProxy.getUser(idSession);
			model.addAttribute("user", user);
		}

		List<BookBean> books = BooksProxy.listBooks();

		model.addAttribute("books", books);

		BookBean bookBean = new BookBean();

		model.addAttribute("bookBean", bookBean);

		return "booksListPage";
	}

	@RequestMapping("/livres/recherche")
	public String searchBook(@ModelAttribute("bookBean") BookBean bookBean, Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();

		Integer idSession = (Integer) session.getAttribute("id");

		if (session.getAttribute("id") != null) {
			UserBean user = UsersProxy.getUser(idSession);
			model.addAttribute("user", user);
		}

		String name = bookBean.getName();

		List<BookBean> books = BooksProxy.searchBooksByName(name);

		model.addAttribute("bookBean", bookBean);

		model.addAttribute("books", books);

		return "booksListPage";
	}

	@GetMapping("/livres/{id}/vuelivre")
	public String bookPage(@PathVariable("id") int id, Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();

		Integer idSession = (Integer) session.getAttribute("id");

		if (session.getAttribute("id") != null) {
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

		if (session.getAttribute("id") != null) {
			UserBean user = UsersProxy.getUser(idSession);
			model.addAttribute("user", user);
		}

		List<AuthorBean> authors = BooksProxy.listAuthors();

		model.addAttribute("authors", authors);

		return "authorsListPage";
	}

	@RequestMapping("/auteurs/{id}/vueauteur")
	public String authorPage(@PathVariable("id") int id, Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();

		Integer idSession = (Integer) session.getAttribute("id");

		if (session.getAttribute("id") != null) {
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

		if (session.getAttribute("id") != null) {
			UserBean user = UsersProxy.getUser(idSession);
			model.addAttribute("user", user);
		}

		List<BookTypeBean> bookTypes = BooksProxy.listBooksTypes();

		model.addAttribute("bookTypes", bookTypes);

		return "bookTypesListPage";
	}

	@RequestMapping("/categories/{id}/vuecategories")
	public String bookTypePage(@PathVariable("id") int id, Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();

		Integer idSession = (Integer) session.getAttribute("id");

		if (session.getAttribute("id") != null) {
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

		try {
			BooksProxy.renewalBorrowing(id);
		} catch( Exception e) {
			e.printStackTrace();
			if (e instanceof BorrowingInvalidRenewalException) {
				String message = e.getMessage();
				model.addAttribute("errorMessage", message);
			}
		}

		return "redirect:/compte/" + idSession + "/moncompte";
	}

	@RequestMapping("/reservations")
	public String bookingsListPage(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();

		Integer idSession = (Integer) session.getAttribute("id");

		if (session.getAttribute("id") != null) {
			UserBean user = UsersProxy.getUser(idSession);
			model.addAttribute("user", user);
		}

		List<BookingBean> bookings = BooksProxy.listBookings();

		model.addAttribute("bookings", bookings);

		return "bookingsListPage";
	}

	@PostMapping("/reservation/{id}/add-booking")
	public String addBooking(@PathVariable("id") int id, HttpServletRequest request, RedirectAttributes redirectAttributes) {

		HttpSession session = request.getSession();

		Integer idSession = (Integer) session.getAttribute("id");
		
		if (session.getAttribute("id") != null) {
			UserBean user = UsersProxy.getUser(idSession);
			try {
				BooksProxy.addBooking(user.getId(), id);
				String acceptMessage = "Votre demande de réservation a bien été pris en compte.";
				redirectAttributes.addFlashAttribute("acceptMessage", acceptMessage);
			} catch (Exception e) {
				e.printStackTrace();
				if (e instanceof CanNotAddBookingException) {
					String message = e.getMessage();
					redirectAttributes.addFlashAttribute("errorMessage", message);
				}
			}
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Merci de vous connecter pour effectuer une réservation.");
		}
		
		return "redirect:/livres/" + id + "/vuelivre";
	}

	@RequestMapping("reservation/{id}/cancel-booking")
	public String cancelBooking(@PathVariable("id") int id, Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();

		Integer idSession = (Integer) session.getAttribute("id");
		
		BooksProxy.cancelBookingByUser(id);
		
		return "redirect:/compte/" + idSession + "/moncompte";
	}
}
