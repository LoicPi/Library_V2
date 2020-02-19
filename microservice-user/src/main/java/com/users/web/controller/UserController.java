package com.users.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.users.dao.UserDao;
import com.users.dto.UpdatePasswordUser;
import com.users.dto.UpdateUser;
import com.users.exceptions.CanNotAddUserException;
import com.users.exceptions.PasswordDoesNotMatchException;
import com.users.exceptions.UserNotFoundException;
import com.users.model.Role;
import com.users.model.User;
import com.users.tools.PasswordEncryptor;

@RestController
public class UserController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserDao userDao;
	
//	@Autowired
//	private UserValidator userValidator;
		
	/**
	 * Function to have the list of users
	 * @return list of users
	 */
	@GetMapping("/comptes")
	public List<User> listUsers() {
		
		List<User> users = userDao.findAllOrderByDateRegistration();
		
		if(users.isEmpty()) throw  new UserNotFoundException("Aucun utilisateur ne s'est inscrit");
	
		log.info("Récupération de la liste des utilisateurs");
		
		return users;
	}
	
	/**
	 * Function to add user in database
	 * @param user User who save 
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/compte/add-user")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user, BindingResult result) {
		
		user.setPassword(PasswordEncryptor.hashPassword(user.getPassword()));
		
		user.setRole(Role.Utilisateur);
		
		int cardNumber = 0;
		
		List<User> users = userDao.findAllOrderByDateRegistration();
		
		if(users.size() == 0) {
			cardNumber = 1;
		} else {
			for (User userS : users) {
				if(cardNumber <= userS.getCardNumber()) {
					cardNumber = userS.getCardNumber()+1;
				}
			}
		}
		
		User userMail = userDao.findByEmail(user.getEmail());
		
		if(!(Objects.isNull(userMail))) throw new UserNotFoundException("Le mail est déjà utilisé pour un autre compte.");
		
		user.setCardNumber(cardNumber);
		
		user.setDateRegistration(new Date());
		
		User newUser = userDao.save(user);
		
		if(newUser == null) throw new CanNotAddUserException("UserException01");
		
		log.info("Sauvegarde de l'utilisateur : " + newUser );
		
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	
	/**
	 * Function thats returns a user found with his id 
	 * @param id id of the user
	 * @return user
	 */
	@GetMapping("/compte/{id}/moncompte")
	public User getUser(@PathVariable int id){
		
		Optional<User> user = userDao.findById(id);
		
		if(!user.isPresent()) throw new UserNotFoundException("UserException02");
		
		log.info("Accès au compte utilisateur");
		
		return user.get();
	}
	
	/**
	 * Function to log user
	 * @param emailLog email implements to log
	 * @param passwordLogg password implements to log
	 * @return ResponseEntity
	 */
	@PostMapping("/compte/log-user")
	public ResponseEntity<User> logUser(@RequestParam String email, @RequestParam String password){
		
		User userLogged = userDao.findByEmail(email);
		
		if(Objects.isNull(userLogged)) throw new UserNotFoundException("UserException03");
		
		if(!PasswordEncryptor.checkPassword(password, userLogged.getPassword())) throw new PasswordDoesNotMatchException("UserException04");
		
		log.info("Connexion au compte utilisateur n°" + userLogged.getId());
		
		return new ResponseEntity<User>(userLogged, HttpStatus.OK);
	}
	
	/**
	 * Function to update a user account
	 * @param id id of user who is update
	 * @param updateUser the dto of user
	 * @return ResponseEntity
	 */
	@PutMapping("/compte/{id}/update-user")
	public ResponseEntity<User> updateUser(@PathVariable int id, @Valid @RequestBody UpdateUser updateUser) {
		
		Optional<User> user = userDao.findById(id);
		
		if(!user.isPresent()) throw new UserNotFoundException("UserException02");
		
		User userToUpdate = userDao.findUserById(id);
		
		userToUpdate.setFirstName(updateUser.getUpdateFirstName());
		
		userToUpdate.setLastName(updateUser.getUpdateLastName());
		
		userToUpdate.setEmail(updateUser.getUpdateEmail());
		
		userToUpdate.setPhoneNumber(updateUser.getUpdatePhoneNumber());
		
		final User updatedUser = userDao.save(userToUpdate);
		
		return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
	}
	
	/**
	 * Function to delete a user's account
	 * @param id id of the delete-user
	 */
	@DeleteMapping("/compte/{id}/delete-user")
	public Response deleteUser (@PathVariable int id) {
		
		Optional<User> user = userDao.findById(id);
		
		if(!user.isPresent()) throw new UserNotFoundException("UserException02");
		
	    User userToDelete = user.get();
	    
	    String userD = userToDelete.toString();

	    userDao.delete(userToDelete);
	    
	    log.info("Suppression de l'utilisateur : " + userD);

	    return Response.status(200).entity("User is deleted").build();
	    
	}

	/**
	 * Function to update password user
	 * @param id id of the user who update his password
	 * @param updatePasswordUser the dto of updated password
	 * @return ResponseEntity
	 */
	@PutMapping("/compte/{id}/update-password")
	public ResponseEntity<User> updatePassword(@PathVariable int id, @Valid @RequestBody UpdatePasswordUser updatePasswordUser){
		
		Optional<User> user = userDao.findById(id);
		
		if(!user.isPresent()) throw new UserNotFoundException("UserException02");
		
		User userToUpdate = user.get();
		
		if (updatePasswordUser.getOldPassword() == null || updatePasswordUser.getNewPassword() == null || updatePasswordUser.getConfirmPassword() == null)
			throw new PasswordDoesNotMatchException("UpdatePasswordException01");
		
		if (!(updatePasswordUser.getNewPassword().equals(updatePasswordUser.getConfirmPassword())))
			throw new PasswordDoesNotMatchException("UpdatePasswordException02");
				
		if(!PasswordEncryptor.checkPassword(updatePasswordUser.getOldPassword(), userToUpdate.getPassword())) 
			throw new PasswordDoesNotMatchException("UpdatePasswordException03");
		
		userToUpdate.setPassword(PasswordEncryptor.hashPassword(updatePasswordUser.getNewPassword()));
		
		final User updatedUser = userDao.save(userToUpdate);
		
		return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
	}
	
}
