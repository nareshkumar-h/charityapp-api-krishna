package com.revature.charityapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.charityapp.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>{
	/**
	 *Admin Login
	 *@Param {email and password}
	 *return: success{id,name,email}
	 *return: failure{Invalid login} 
	**/
	@Query("SELECT a FROM Admin a WHERE a.email = :email AND a.password = :password")
	Admin findByEmailAndPassword(
			@Param("email") String email,
			@Param("password") String password
			);
}
