package com.revature.charityapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.charityapp.model.FundRequest;

@Repository
public interface FundRepository extends JpaRepository<FundRequest, Integer>{
	/**
	 *Update fund request 
	 **/
	
	@Modifying
	
	@Query("UPDATE FundRequest f SET"
			+ " f.requestType = :requestType,"
			+ " f.description = :description,"
			+ " f.amount = :amount,"
			+ " f.expireDate = :expiryDate WHERE f.id = :id")
	public int updateDonor(
			@Param("requestType") String requestType,
			@Param("description") String description,
			@Param("amount") Double amount,
			@Param("expiryDate") LocalDate expiryDate,
			@Param("id") Integer id
			);
	
	
	@Query(" FROM FundRequest f WHERE f.expireDate >= :currentDate")
	public List<FundRequest> sortByExpiryDate(@Param("currentDate") LocalDate expireDate);
	
	
}
