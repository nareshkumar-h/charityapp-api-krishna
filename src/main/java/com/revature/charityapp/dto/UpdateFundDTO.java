package com.revature.charityapp.dto;

import lombok.Data;

@Data
public class UpdateFundDTO {
	private Integer id;
	private String requestType;
	private String description;
	private Double amount;
	private String expireDate;
}
