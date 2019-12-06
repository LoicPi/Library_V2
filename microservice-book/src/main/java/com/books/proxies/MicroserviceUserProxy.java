package com.books.proxies;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.books.beans.UserBean;
import com.books.configuration.FeignConfig;

@FeignClient(name = "zuul-server", contextId="usersProxy", configuration= FeignConfig.class )
@RibbonClient(name = "microservice-users")
public interface MicroserviceUserProxy {

	@GetMapping(value = "/microservice-users/compte/{id}/moncompte")
	UserBean getUser(@PathVariable("id") int id);
}
