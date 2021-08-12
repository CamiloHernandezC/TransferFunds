package com.visa.transferFunds.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.visa.transferFunds.entities.Account;
import com.visa.transferFunds.repositories.AccountRepository;
import com.visa.transferFunds.repositories.TransferResultRepository;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(AccountRepository accountRepository, TransferResultRepository transferResultRepository) {
		return args -> {
			log.info("Preloading Origin account " + accountRepository.save(new Account(12345601L,10000D)));
			log.info("Preloading Destination account" + accountRepository.save(new Account(12345600L,10000D)));
	    };
	}
}
