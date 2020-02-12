package com.clientui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clientui.beans.BookingBean;
import com.clientui.beans.BorrowingBean;
import com.clientui.beans.UpdatePasswordBean;
import com.clientui.beans.UserBean;
import com.clientui.beans.UserLogBean;
import com.clientui.beans.UserUpdateBean;
import com.clientui.exceptions.CanNotAddUserException;
import com.clientui.exceptions.PasswordDoesNotMatchException;
import com.clientui.exceptions.UserNotFoundException;
import com.clientui.proxies.MicroserviceBookProxy;
import com.clientui.proxies.MicroserviceUserProxy;

@Controller
public class ClientUserController {

	@Autowired
	private MicroserviceUserProxy UsersProxy;
	
	@Autowired
	private MicroserviceBookProxy BooksProxy;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/compte/inscription")
	public String registrationPage (Model model) {
		
		UserBean user = new UserBean();
		
		model.addAttribute("user", user);
		
		return "userRegistrationPage";
	}
	
	@RequestMapping("/compte/add-user")
	public String addUser (@Valid @ModelAttribute("user") UserBean user, Model model, HttpServletRequest request) {		
		HttpSession session = request.getSession();
		
		try {
			UserBean userAdd = UsersProxy.addUser(user);
			session.setAttribute("id", userAdd.getId());
			return "redirect:/compte/" + userAdd.getId() + "/moncompte";
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof CanNotAddUserException) {
				model.addAttribute("errorMessage", "La création du compte n'a pas pu s'effectuer.");
			}
			return "userRegistrationPage";
		}
	}
	
	@RequestMapping("/compte/connexion")
	public String loginPage(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		Integer idSession = (Integer) session.getAttribute("id");
		
		if (session.getAttribute("id") != null ) {
			return "redirect:/compte/" + idSession + "/moncompte";
		} else {
			UserLogBean user = new UserLogBean();
			model.addAttribute("user", user);
			return "loginPage";
		}
	}
	
	@RequestMapping("/compte/log-user")
	public String logUser(@Valid @ModelAttribute("user") UserLogBean user, Model model, HttpServletRequest request ) {
		
		HttpSession session = request.getSession();
		
		try {
			UserBean userLogged = UsersProxy.logUser(user.getEmail(), user.getPassword());
			session.setAttribute("id", userLogged.getId());
			return "redirect:/compte/" + userLogged.getId() + "/moncompte";
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof PasswordDoesNotMatchException) {
				model.addAttribute("errorMessage", e.getMessage());
			}
			if (e instanceof UserNotFoundException) {
				model.addAttribute("errorMessage", e.getMessage());
			}
			return "loginPage";
		}
	}
	
	@RequestMapping("/compte/{id}/maj")
	public String updateUserPage (@PathVariable("id") int id, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();

		Integer idSession = (Integer) session.getAttribute("id");
		
		if (session.getAttribute("id") == null || id != idSession ) {
			return "redirect:/accueil";
		} else {
			UserUpdateBean userUpdate = new UserUpdateBean();
			
			UserBean user = UsersProxy.getUser(id);
			
			userUpdate.setId(id);
			userUpdate.setUpdateLastName(user.getLastName());
			userUpdate.setUpdateFirstName(user.getFirstName());
			userUpdate.setUpdateEmail(user.getEmail());
			userUpdate.setUpdatePhoneNumber(user.getPhoneNumber());
			
			model.addAttribute("user", userUpdate);
			
			return "updateUserPage";
		}
	}
	
	@RequestMapping("/compte/{id}/update-user")
	public String updateUser (@PathVariable("id") int id, @Valid @ModelAttribute("user") UserUpdateBean updateUser, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		Integer idSession = (Integer) session.getAttribute("id");
		
		if (session.getAttribute("id") == null || id != idSession ) {
			return "redirect:/accueil";
		} else {
//			try {
				UserBean user = UsersProxy.updateUser(id, updateUser);
				return "redirect:/compte/" + user.getId() + "/moncompte";
//			} catch (Exception e) {
//				e.printStackTrace();
//				if (e instanceof UserNotFoundException) {
//					model.addAttribute("errorMessage", "L'utilisateur n'a pas été retrouvé.");
//				}
//				return "updateUserPage";
//			}	
		}
	}
	
	@RequestMapping("/compte/{id}/moncompte")
	public String accountPage (@PathVariable("id") int id, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		Integer idSession = (Integer) session.getAttribute("id");
		
		if (session.getAttribute("id") == null || id != idSession ) {
			return "redirect:/accueil";
		} else {
			UserBean user = UsersProxy.getUser(id);
			List<BorrowingBean> borrowings = BooksProxy.listBorrowingsOfUser(id);
			List<BookingBean> bookings = BooksProxy.listBookingsOfUser(id);
			model.addAttribute("user", user);
			model.addAttribute("borrowings", borrowings);
			model.addAttribute("bookings", bookings);
			return "userAccountPage";
		}	
	}
	
	@RequestMapping("/compte/{id}/deconnexion")
	public String logOutUser (@PathVariable ("id") int id, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		Integer idSession = (Integer) session.getAttribute("id");
		
		if (session.getAttribute("id") == null || id != idSession ) {
			return "redirect:/accueil";
		} else {
			session.invalidate();
			return "redirect:/compte/connexion";
		}
	}
	
	@RequestMapping("compte/{id}/majmdp")
	public String updatePasswordPage (@PathVariable("id") int id, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		Integer idSession = (Integer) session.getAttribute("id");
		
		if (session.getAttribute("id") == null || id != idSession) {
			return "redirect:/accueil";
		} else {
			UserBean user = UsersProxy.getUser(id);
			model.addAttribute("user", user);
			UpdatePasswordBean updatePassword = new UpdatePasswordBean();
			updatePassword.setId(user.getId());
			model.addAttribute("user", updatePassword);
			return "userUpdatePasswordPage";		
		}
	}
	
	@RequestMapping("compte/{id}/update-password")
	public String updatePassword (@PathVariable("id") int id, @Valid @ModelAttribute("updatePassword") UpdatePasswordBean updatePassword, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		Integer idSession = (Integer) session.getAttribute("id");
		
		if (session.getAttribute("id") == null || id != idSession ) {
			return "redirect:/accueil";
		} else {
			try {
				UserBean user = UsersProxy.updatePassword(id, updatePassword);
				return "redirect:/compte/" + user.getId() + "/moncompte";
			} catch (Exception e) {
				e.printStackTrace();
				if (e instanceof UserNotFoundException) {
					model.addAttribute("errorMessage", "L'utilisateur n'a pas été retrouvé.");
				}
				return "userUpdatePasswordPage";
			}
		}
	}

}

