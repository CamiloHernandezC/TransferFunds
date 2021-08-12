package com.visa.transferFunds.interfaces;

import com.visa.transferFunds.dto.TransferDTO;
import com.visa.transferFunds.dto.TransferResultDTO;


public interface AccountManagerInterface {
	
	public TransferResultDTO transferFunds(final TransferDTO transferDTO);
}
