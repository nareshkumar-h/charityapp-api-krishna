package com.revature.charityapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.charityapp.exception.ServiceException;
import com.revature.charityapp.model.Transaction;
import com.revature.charityapp.repository.TransactionRepository;
import com.revature.charityapp.util.MessageConstant;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	public Transaction contributeFund(Transaction transaction) throws ServiceException
	{
		Transaction transactionObj;
		transactionObj = transactionRepository.save(transaction);
		if(transactionObj == null)
		{
			throw new ServiceException(MessageConstant.UNABLE_TO_FUND_REQUEST);
		}
		return transactionObj;
	}
	
	public List<Transaction> listFundedDonor() throws ServiceException
	{
		List<Transaction> listFundRequest = transactionRepository.findAll();
		if(listFundRequest == null)
		{
			throw new ServiceException(MessageConstant.UNABLE_TO_LIST_FUND_REQUEST);
		}
		return listFundRequest;
	}
	
}
