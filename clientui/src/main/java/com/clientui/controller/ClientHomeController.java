package com.clientui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clientui.beans.UserBean;
import com.clientui.proxies.MicroserviceUserProxy;

@Controller
public class ClientHomeController {

	@Autowired
	private MicroserviceUserProxy UsersProxy;
	
	@RequestMapping("/Accueil")
	public String homePage(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		Integer idSession = (Integer) session.getAttribute("id");
		
		if (session.getAttribute("id") != null ) {			
			UserBean user = UsersProxy.getUser(idSession);
			model.addAttribute("user", user);
		}
		return "homePage";
	}
}
