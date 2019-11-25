package com.users.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.users.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

	User findByEmail(String email);
	User findUserById(int id);
	
}
