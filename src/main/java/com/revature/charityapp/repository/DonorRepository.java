package com.revature.charityapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.charityapp.model.Donor;

@Repository
public interface DonorRepository extends JpaRepository<Donor,Integer> {
	/**
	 * Donor login
	 * @Param email and password
	 */
	@Query("SELECT d FROM Donor d WHERE d.email = :email AND d.password = :password")
	Donor findByEmailAndPassword(
			@Param("email") String email,
			@Param("password") String password
			);
	
	/**
	 * List donor
	 * return donor details
	 */
//	@Query("SELECT d From Donor d")
//	List<Donor> findAll();
	
}
