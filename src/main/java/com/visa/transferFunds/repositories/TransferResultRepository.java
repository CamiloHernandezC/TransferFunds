package com.visa.transferFunds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.visa.transferFunds.entities.TransferResult;

public interface TransferResultRepository extends JpaRepository<TransferResult, String>{

}
