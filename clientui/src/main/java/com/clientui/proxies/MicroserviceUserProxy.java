package com.clientui.proxies;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
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
@RibbonClient(name = "microservice-users")
public interface MicroserviceUserProxy {
	
	@GetMapping(value = "/microservice-users/comptes")
	List<UserBean> listUsers();
	
	@PostMapping(value = "/microservice-users/compte/add-user")
	UserBean addUser(@Valid @RequestBody UserBean user);
	
	@GetMapping(value = "/microservice-users/compte/{id}/moncompte")
	UserBean getUser(@PathVariable("id") int id);
	
	@PostMapping(value = "/microservice-users/compte/log-user")
	UserBean logUser(@RequestParam ("email") String email, @RequestParam ("password") String password);

	@PutMapping(value = "/microservice-users/compte/{id}/update-user")
	UserBean updateUser(@PathVariable("id") int id, @Valid @RequestBody UserUpdateBean updateUser);
	
	@DeleteMapping(value = "/microservice-users/compte/{id}/delete-user")
	UserBean deleteUser(@PathVariable("id") int id);
	
	@PutMapping(value = "/microservice-users/compte/{id}/update-password")
	UserBean updatePassword(@PathVariable("id") int id, @Valid @RequestBody UpdatePasswordBean updatePassword);
}
