package com.revature.charityapp.dto;

import lombok.Data;

@Data
public class RaiseFundDTO {
	
	private Integer adminId;
	private Double amount;
	private String expiryDate;
	private String description;
	private String requestType;
	
}
