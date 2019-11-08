package com.clientui.controller;

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

import com.clientui.beans.UpdatePasswordBean;
import com.clientui.beans.UserBean;
import com.clientui.beans.UserUpdateBean;
import com.clientui.exceptions.CanNotAddUserException;
import com.clientui.exceptions.PasswordDoesNotMatchException;
import com.clientui.exceptions.UserNotFoundException;
import com.clientui.proxies.MicroserviceUserProxy;

@Controller
public class ClientUserController {

	@Autowired
	private MicroserviceUserProxy UsersProxy;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/compte/Inscription")
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
			return "redirect:/Compte/" + userAdd.getId() + "/MonCompte";
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
			return "redirect:/Compte/" + idSession + "/MonCompte";
		} else {
			UserBean user = new UserBean();
			model.addAttribute("user", user);
			return "loginPage";
		}
	}
	
	@RequestMapping("/compte/log-user")
	public String logUser(@Valid @ModelAttribute("user") UserBean user, Model model, HttpServletRequest request ) {
		
		HttpSession session = request.getSession();
		
		try {
			UserBean userLogged = UsersProxy.logUser(user.getEmail(), user.getPassword());
			session.setAttribute("id", userLogged.getId());
			return "redirect:/Compte/" + userLogged.getId() + "/MonCompte";
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof PasswordDoesNotMatchException) {
				model.addAttribute("errorMessage", "Le mot de passe ou l'adresse mail ne correspondent pas.");
			}
			return "loginPage";
		}
	}
	
	@RequestMapping("/compte/{id}/maj")
	public String updateUserPage (@PathVariable("id") int id, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();

		Integer idSession = (Integer) session.getAttribute("id");
		
		if (session.getAttribute("id") == null || id != idSession ) {
			return "redirect:/Accueil";
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
			return "redirect:/Accueil";
		} else {
			try {
				UserBean user = UsersProxy.updateUser(id, updateUser);
				return "redirect:/Compte/" + user.getId() + "/MonCompte";
			} catch (Exception e) {
				e.printStackTrace();
				if (e instanceof UserNotFoundException) {
					model.addAttribute("errorMessage", "L'utilisateur n'a pas été retrouvé.");
				}
				return "updateUserPage";
			}	
		}
	}
	
	@RequestMapping("/compte/{id}/mon_compte")
	public String accountPage (@PathVariable("id") int id, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		Integer idSession = (Integer) session.getAttribute("id");
		
		if (session.getAttribute("id") == null || id != idSession ) {
			return "redirect:/Accueil";
		} else {
			UserBean user = UsersProxy.getUser(id);
			model.addAttribute("user", user);
			return "userAccountPage";
		}	
	}
	
	@RequestMapping("/compte/{id}/deconnexion")
	public String logOutUser (@PathVariable ("id") int id, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		Integer idSession = (Integer) session.getAttribute("id");
		
		if (session.getAttribute("id") == null || id != idSession ) {
			return "redirect:/Accueil";
		} else {
			session.invalidate();
			return "redirect:/Compte/Connexion";
		}
	}
	
	@RequestMapping("compte/{id}/maj_mdp")
	public String updatePasswordPage (@PathVariable("id") int id, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		Integer idSession = (Integer) session.getAttribute("id");
		
		if (session.getAttribute("id") == null || id != idSession) {
			return "redirect:/Accueil";
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
			return "redirect:/Accueil";
		} else {
			try {
				UserBean user = UsersProxy.updatePassword(id, updatePassword);
				return "redirect:/Compte/" + user.getId() + "/MonCompte";
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

