package com.clientui.proxies;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.clientui.beans.UpdatePasswordBean;
import com.clientui.beans.UserBean;
import com.clientui.beans.UserUpdateBean;
import com.clientui.configuration.FeignConfig;

@FeignClient(name = "zuul-server", contextId="usersProxy", configuration= FeignConfig.class )
public interface MicroserviceUserProxy {
	
	@GetMapping(value = "/microservice-users/Comptes")
	List<UserBean> listUsers();
	
	@PostMapping(value = "/microservice-users/Compte/add-user")
	UserBean addUser(@Valid @RequestBody UserBean user);
	
	@GetMapping(value = "/microservice-users/Compte/{id}/MonCompte")
	UserBean getUser(@PathVariable("id") int id);
	
	@PostMapping(value = "/microservice-users/Compte/log-user")
	UserBean logUser(@RequestParam ("email") String emailLog, @RequestParam ("password") String passwordLog);

	@PutMapping(value = "/microservice-users/Compte/{id}/update-user")
	UserBean updateUser(@PathVariable("id") int id, @Valid @RequestBody UserUpdateBean updateUser);
	
	@DeleteMapping(value = "/microservice-users/Compte/{id}/delete-user")
	UserBean deleteUser(@PathVariable("id") int id);
	
	@PutMapping(value = "/microservice-users/Compte/{id}/update-password")
	UserBean updatePassword(@PathVariable("id") int id, @Valid @RequestBody UpdatePasswordBean updatePassword);
}
