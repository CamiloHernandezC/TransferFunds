package com.visa.transferFunds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.visa.transferFunds.dto.TransferDTO;
import com.visa.transferFunds.dto.TransferResultDTO;
import com.visa.transferFunds.entities.Account;
import com.visa.transferFunds.enums.Currency;
import com.visa.transferFunds.enums.Taxes;
import com.visa.transferFunds.exceptions.InsufficientFundsException;
import com.visa.transferFunds.exceptions.TransfersPerDayExceededException;
import com.visa.transferFunds.repositories.AccountRepository;
import com.visa.transferFunds.services.AccountManagerImpl;

@SpringBootTest
class AccountManagerImplTest {
	
	@Autowired
	private AccountManagerImpl accountManager;
	
	@MockBean
	private AccountRepository repository;

	
	@Test
	void testTransferFundsWithHighTaxShouldSuccess() {
		final float amount = Taxes.LIMIT_AMOUNT+1;
		final Long originAccountId = 12345600L;
		final Long destinationAccountId = 12345601L;
		final TransferDTO transfer = new TransferDTO(amount, Currency.USD, originAccountId, destinationAccountId, "description");
		Account originAccount = new Account(12345600L, 10000, 3);
		Account destinationAccount = new Account(12345601L, 0, 3);
		when(repository.getById(originAccountId)).thenReturn(originAccount);
		when(repository.getById(destinationAccountId)).thenReturn(destinationAccount);
		final TransferResultDTO result = accountManager.transferFunds(transfer);
		assertEquals(Currency.CAD, result.getCurrency());
		assertEquals(amount*Taxes.HIGH_TAX.taxRate(), result.getTaxCollected());
		assertTrue(amount < result.getAmount());
	}
	
	@Test
	void testTransferFundsWithLowTaxShouldSuccess() {
		final float amount = Taxes.LIMIT_AMOUNT-1;
		final Long originAccountId = 12345600L;
		final Long destinationAccountId = 12345601L;
		final TransferDTO transfer = new TransferDTO(amount, Currency.USD, originAccountId, destinationAccountId, "description");
		Account originAccount = new Account(12345600L, 10000, 3);
		Account destinationAccount = new Account(12345601L, 0, 3);
		when(repository.getById(originAccountId)).thenReturn(originAccount);
		when(repository.getById(destinationAccountId)).thenReturn(destinationAccount);
		final TransferResultDTO result = accountManager.transferFunds(transfer);
		assertEquals(Currency.CAD, result.getCurrency());
		assertEquals(amount*Taxes.LOW_TAX.taxRate(), result.getTaxCollected());
		assertTrue(amount < result.getAmount());
	}
	
	@Test
	void testTransferFundsShouldThrowsInsufficientFundsException() {
		final int amount = 5000;
		final Long originAccountId = 12345600L;
		final Long destinationAccountId = 12345601L;
		final TransferDTO transfer = new TransferDTO(amount, Currency.USD, originAccountId, destinationAccountId, "description");
		Account originAccount = new Account(12345600L, amount-1, 3);
		Account destinationAccount = new Account(12345601L, 0, 3);
		when(repository.getById(originAccountId)).thenReturn(originAccount);
		when(repository.getById(destinationAccountId)).thenReturn(destinationAccount);
		assertThrows(InsufficientFundsException.class,() -> {accountManager.transferFunds(transfer);});
	}
	
	@Test
	void testTransferFundsShouldThrowsTransfersPerDayExceededException() {
		final int amount = 5000;
		final Long originAccountId = 12345600L;
		final Long destinationAccountId = 12345601L;
		final TransferDTO transfer = new TransferDTO(amount, Currency.USD, originAccountId, destinationAccountId, "description");
		Account originAccount = new Account(12345600L, amount*2, 0);
		Account destinationAccount = new Account(12345601L, 0, 3);
		when(repository.getById(originAccountId)).thenReturn(originAccount);
		when(repository.getById(destinationAccountId)).thenReturn(destinationAccount);
		assertThrows(TransfersPerDayExceededException.class,() -> {accountManager.transferFunds(transfer);});
	}
	
	
	@Test
	void testTransferFundsWithUsuportedExchangeShouldFail() {
		final int amount = 5000;
		final Long originAccountId = 12345600L;
		final Long destinationAccountId = 12345601L;
		final TransferDTO transfer = new TransferDTO(amount, Currency.EUR, originAccountId, destinationAccountId, "description");
		Account originAccount = new Account(12345600L, amount*2, 0);
		Account destinationAccount = new Account(12345601L, 0, 3);
		when(repository.getById(originAccountId)).thenReturn(originAccount);
		when(repository.getById(destinationAccountId)).thenReturn(destinationAccount);
		final Throwable exception = assertThrows(IllegalArgumentException.class,() -> {accountManager.transferFunds(transfer);});
		assertEquals("Currency not supported", exception.getMessage());

	}

}
