package com.visa.transferFunds.controllers;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.visa.transferFunds.dto.TransferDTO;
import com.visa.transferFunds.dto.TransferResultDTO;
import com.visa.transferFunds.interfaces.AccountManagerInterface;

/**
 * 
 * @author Camilo Hernandez
 *
 */
@RestController
public class AccountController {
	
	private final AccountManagerInterface accountManager;
	
	public AccountController(final AccountManagerInterface accountManager) {
		this.accountManager = accountManager;
	}

	@PostMapping("/transferFunds")
	//@valid
	TransferResultDTO transferFunds( @RequestBody final TransferDTO transfer) {
		return accountManager.transferFunds(transfer);
	}
  
}
