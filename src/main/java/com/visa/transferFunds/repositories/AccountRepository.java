package com.visa.transferFunds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.visa.transferFunds.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

	default void save(final Account account,final boolean countTransaction) {
		if(countTransaction) {
			account.setRemainingOperations(account.getRemainingOperations()-1);
		}
		save(account);
	}

}
