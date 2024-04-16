package com.virtual.gift.card.bank;

import com.virtual.gift.card.domain.Bank;

public interface BankDao {
	boolean createBankAccount(Bank account);

	boolean depositToAccount(Bank account);
	
	Bank fetchAccountById(long id);
	
	void printAccountDetails();

}
