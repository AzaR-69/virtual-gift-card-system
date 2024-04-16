package com.virtual.gift.card.service;

import java.sql.Timestamp;

import com.virtual.gift.card.bank.BankDaoImpl;
import com.virtual.gift.card.domain.Bank;
import com.virtual.gift.card.exceptions.InvalidBankAccountException;
import com.virtual.gift.card.exceptions.MinimumBalanceException;

public class BankServiceImpl {
	BankDaoImpl bankDao;

	public BankServiceImpl() {
		bankDao = new BankDaoImpl();
	}

	public boolean createBankAccount(Bank account) throws MinimumBalanceException {
		if (account.getBalance() < 5000)
			throw new MinimumBalanceException("Minimum Balance should be 5000!");
		account.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		return bankDao.createBankAccount(account);
	}

	public boolean depositToAccount(Bank requestObject) throws InvalidBankAccountException {
		Bank existingAccount = fetchAccountById(requestObject.getId());
		existingAccount.setBalance(existingAccount.getBalance()+requestObject.getBalance());
		return updateBankAccount(existingAccount);
	}
	
	public boolean updateBankAccount(Bank requestObject) {
		return bankDao.depositToAccount(requestObject);
	}

	public Bank fetchAccountById(long accountNumber) throws InvalidBankAccountException {
		Bank existingAccount = bankDao.fetchAccountById(accountNumber);
		if (existingAccount == null)
			throw new InvalidBankAccountException("Bank account doesn't exist! Kindly enter valid account details");
		return bankDao.fetchAccountById(accountNumber);
	}

	public void printAccountDetails() {
		bankDao.printAccountDetails();
	}
}
