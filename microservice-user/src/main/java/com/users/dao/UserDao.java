package com.users.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.users.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

	@Query("From User u order by u.dateRegistration")
	List<User> findAllOrderByDateRegistration();
	
	User findByEmail(String email);
	User findUserById(int id);
	
}
