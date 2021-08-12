package com.visa.transferFunds.services;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.visa.transferFunds.consumingrest.dto.FixerExchange;
import com.visa.transferFunds.dto.TransferDTO;
import com.visa.transferFunds.dto.TransferResultDTO;
import com.visa.transferFunds.entities.Account;
import com.visa.transferFunds.entities.TransferResult;
import com.visa.transferFunds.enums.Currency;
import com.visa.transferFunds.enums.Taxes;
import com.visa.transferFunds.exceptions.InsufficientFundsException;
import com.visa.transferFunds.exceptions.TransfersPerDayExceededException;
import com.visa.transferFunds.interfaces.AccountManagerInterface;
import com.visa.transferFunds.repositories.AccountRepository;
import com.visa.transferFunds.repositories.TransferResultRepository;

@Service
public class AccountManagerImpl implements AccountManagerInterface{

	private final AccountRepository accountRepository;
	private final TransferResultRepository transferResultRepository;
	
	private static final Logger log = LoggerFactory.getLogger(AccountManagerImpl.class);
	
	public AccountManagerImpl(AccountRepository repository, TransferResultRepository transferResultRepository) {
		this.accountRepository = repository;
		this.transferResultRepository = transferResultRepository;
	}

	@Transactional
	@Override
	public TransferResultDTO transferFunds(final TransferDTO transferDTO) {
		checkInputs(transferDTO);
		final double tax =  calculateTax(transferDTO.getAmount());
		final Account originAccount = accountRepository.getById(transferDTO.getOriginAccount());
		checkFunds(originAccount.getBalance(), transferDTO.getAmount(), tax);
		checkTransfersPerDay(originAccount.getRemainingOperations());
		final double totalAmount = tax+transferDTO.getAmount();
		decreaseBalance(originAccount,totalAmount, true);
		final Account destinationAccount = accountRepository.getById(transferDTO.getDestinationAccount());
		increaseBalance(destinationAccount, transferDTO.getAmount(), false);
		final FixerExchange fixerExchange = transformCurrency();
		TransferResult transferResult;
		if(fixerExchange.isSuccess()) {
			final double usdTocad = transferDTO.getAmount() * fixerExchange.getRates().valueCAD()/fixerExchange.getRates().valueUSD();
			transferResult = new TransferResult(tax, Currency.CAD, usdTocad);
		}else {
			transferResult = new TransferResult(tax, transferDTO.getCurrency(), transferDTO.getAmount());
		}
		final ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(transferResultRepository.save(transferResult), TransferResultDTO.class);
	}
	
	private FixerExchange transformCurrency(){
		final RestTemplate restTemplate = new RestTemplate();
		final String url = "http://data.fixer.io/api/latest?access_key={accessKey}";
		try {
			final FixerExchange fixerExchange = restTemplate.getForObject(url,FixerExchange.class,"c98e3d0a0394112b73e3d849a62e189f");
			if(!fixerExchange.isSuccess()) {
				log.error("fixer API not working");
			}
			return fixerExchange;
		}catch (RestClientException e) {
			log.error("fixer API not working");
			return new FixerExchange();
		}
	}

	private void increaseBalance(Account account, float amount, final boolean countTransaction) {
		account.setBalance(account.getBalance()+amount);
		accountRepository.save(account, countTransaction);
	}

	private void decreaseBalance(final Account account,final double amount, final boolean countTransaction) {
		account.setBalance(account.getBalance()-amount);
		accountRepository.save(account, countTransaction);
	}

	private void checkTransfersPerDay(int remainingOperations) {
		if(remainingOperations <= 0) {
			throw new TransfersPerDayExceededException();
		}
	}

	private double calculateTax(final double amount) {
		if(amount > Taxes.LIMIT_AMOUNT) {
			return Taxes.HIGH_TAX.taxRate() * amount;
		}else {
			return Taxes.LOW_TAX.taxRate() * amount;
		}
	}

	private void checkFunds(final double balance, final double amount, double tax) {
		if(balance < amount + tax) {
			throw new InsufficientFundsException();
		}
	}

	private void checkInputs(TransferDTO transferDTO) {
		if(!Currency.USD.equals(transferDTO.getCurrency())){
			throw new IllegalArgumentException("Currency not supported");
		}
	}

}
