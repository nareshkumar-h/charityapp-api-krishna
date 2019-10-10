package com.revature.charityapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.revature.charity.service.AdminService;
import com.revature.charity.service.AdminServiceImpl;
import com.revature.charity.service.DonorService;
import com.revature.charity.service.DonorServiceImpl;
import com.revature.charity.service.FundRequestService;
import com.revature.charity.service.FundRequestServiceImpl;
import com.revature.charity.service.TransactionService;
import com.revature.charity.service.TransactionServiceImpl;

@Configuration
public class BeanConfig {
	
	@Bean
	public DonorService donarService() {
		return new DonorServiceImpl();
	}
	
	@Bean
	public AdminService adminService() {
		return new AdminServiceImpl();
	}
	
	@Bean
	public TransactionService transactionService() {
		return new TransactionServiceImpl();
	}
	
	@Bean
	public FundRequestService fundRequestService() {
		return new FundRequestServiceImpl();
	}
}
