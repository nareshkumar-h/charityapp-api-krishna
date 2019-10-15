package com.revature.charityapp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.charityapp.dao.FundDAO;
import com.revature.charityapp.exception.ServiceException;
import com.revature.charityapp.model.FundRequest;
import com.revature.charityapp.repository.FundRepository;
import com.revature.charityapp.util.MessageConstant;

@Service
public class FundService {
	
	@Autowired
	private FundRepository fundRepositioryObj;
	@Autowired
	private FundDAO fundDAO;
	@Transactional
	public int updateDonor(FundRequest fundRequest) throws ServiceException
	{
		int rows = 0;
		String requestType = fundRequest.getRequestType();
		String description = fundRequest.getDescription();
		Double amount = fundRequest.getAmount();
		LocalDate expiryDate = fundRequest.getExpireDate();
		Integer id = fundRequest.getId();
		
		rows = fundRepositioryObj.updateDonor(requestType, description, amount, expiryDate, id);
		if(rows == 0)
		{
			throw new ServiceException(MessageConstant.UNABLE_TO_UPDATE_FUND_REQUEST);
		}
		return rows;
	}
	@Transactional
	public FundRequest raiseFund(FundRequest fundRequest) throws ServiceException
	{
		FundRequest fundRequestObj;
		fundRequestObj = fundRepositioryObj.save(fundRequest);
		if(fundRequestObj == null)
		{
			throw new ServiceException(MessageConstant.UNABLE_TO_FUND_REQUEST);
		}
		return fundRequestObj;
	}
	@Transactional
	public List<FundRequest> listFundRequest() throws ServiceException
	{
		List<FundRequest> listFund = null;
		try {
			listFund = fundDAO.findByAll();
		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage());
		}
		return listFund;
	}
	
}
