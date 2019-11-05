package com.users.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.users.dao.UserDao;
import com.users.exceptions.CanNotAddUserException;
import com.users.exceptions.UserNotFoundException;
import com.users.model.User;
import com.users.tools.PasswordEncryptor;
import com.users.validator.UserValidator;

@RestController
public class UserController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserValidator userValidator;
		
	/**
	 * List of users
	 * @return a list of users
	 */
	
	@GetMapping("/Comptes")
	public List<User> listUsers() {
		
		List<User> users = userDao.findAll();
		
		if(users.isEmpty()) throw  new UserNotFoundException("Aucun utilisateur ne s'est inscrit");
	
		log.info("Récupération de la liste des utilisateurs");
		
		return users;
	}
	
	@PostMapping(value = "/Compte/add-user")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user, BindingResult result) {
		
		user.setPassword(PasswordEncryptor.hashPassword(user.getPassword()));		
		
		User newUser = userDao.save(user);
		
		if(newUser == null) throw new CanNotAddUserException("Impossible d'ajouter cet utilisateur");
		
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	
	

}
