package com.virtual.gift.card.handler;

import java.sql.Timestamp;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.virtual.gift.card.domain.Bank;
import com.virtual.gift.card.domain.GiftCard;
import com.virtual.gift.card.exceptions.IncorrectPinException;
import com.virtual.gift.card.exceptions.InsufficientBalanceException;
import com.virtual.gift.card.exceptions.InvalidBankAccountException;
import com.virtual.gift.card.exceptions.InvalidCardException;
import com.virtual.gift.card.exceptions.InvalidOptionException;
import com.virtual.gift.card.exceptions.MinimumBalanceException;
import com.virtual.gift.card.main.VirtualGiftCardSystem;
import com.virtual.gift.card.request.GiftCardRequest;
import com.virtual.gift.card.service.BankServiceImpl;
import com.virtual.gift.card.service.GiftCardServiceImpl;
import com.virtual.gift.card.util.AppConst;

public class RequestHandler {
	private static Logger LOGGER = LoggerFactory.getLogger(VirtualGiftCardSystem.class);
	private static BankServiceImpl bankServiceImpl;
	private static GiftCardServiceImpl giftCardService;
	private static Scanner ss;

	public RequestHandler() {
		bankServiceImpl = new BankServiceImpl();
		giftCardService = new GiftCardServiceImpl();
		ss = new Scanner(System.in);
	}
	
	public void handleOperations(int option) {
		switch (option) {
		case 1:{
			handleBankOperation(option);
			break;
		}
			
		case 2:{
			handleGiftCardOperation(option);
			break;
		}
			
		case 3:{
			handleTransactionOperation(option);
			break;
		}
			
		}
	}
	
	public void handleBankOperation(int option) {
		LOGGER.info("\nChoose an operation to be performed on Bank accounts");
		LOGGER.info("\n1.Create bank account \n2.Deposit to account \n3.Check account balance \n4.Print account details \n5.Go Back");
		int bankOption=ss.nextInt();
		try {
			switch (bankOption) {
			case 1: {
					LOGGER.info("\nEnter customer name");
					String customerName = ss.next();
					LOGGER.info("\nEnter amount to be deposited");
					long balance = ss.nextLong();
					Bank account = new Bank(customerName, balance, new Timestamp(System.currentTimeMillis()));
					bankServiceImpl.createBankAccount(account);
					bankServiceImpl.printAccountDetails();
					break;
			}
				case 2: {
					LOGGER.info("\nEnter account no:");
					long id = ss.nextLong();
					LOGGER.info("\nAmount to deposit:");
					long amountToDeposit = ss.nextLong();
					Bank requestObject = new Bank(id, amountToDeposit);
					bankServiceImpl.depositToAccount(requestObject);
					break;
				}
				case 3: {
					LOGGER.info("\nEnter account no:");
					long accountNumber = ss.nextLong();
					LOGGER.info("\nBalance for the account: {} is {}", accountNumber,
							bankServiceImpl.fetchAccountById(accountNumber).getBalance());
					break;
				}
				case 4: {
					bankServiceImpl.printAccountDetails();
					break;
				}
				default:
					return;
			}
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
			if(e instanceof MinimumBalanceException || e instanceof InvalidBankAccountException)
				handleBankOperation(bankOption);
		}
	}
	
	
	public void handleGiftCardOperation(int giftCardOption) {
		LOGGER.info("\nChoose an operation to be performed on Gift Cards");
		LOGGER.info("\n1.Create Gift Card \n2.Top Up Gift Card \n3.Block/Unblock gift Card \n4.Close Gift Card \n5.Print Gift Cards \n6.Go Back");
		int option=ss.nextInt();
		try {
			switch(option) {
				case 1:{
					LOGGER.info("\nEnter bank account number to use: ");
					long accountNumber=ss.nextLong();
					Bank bankAccount=bankServiceImpl.fetchAccountById(accountNumber);
					LOGGER.info("\nEnter amount to be credited to gift card: ");
					long amount=ss.nextLong();
					validateSufficientBalance(bankAccount, amount);
					LOGGER.info("\nEnter PIN: ");
					String pin=ss.next();
					LOGGER.info("\nRe-Enter PIN: ");
					String pinToVerify=ss.next();
					giftCardService.createGiftCard(new GiftCard(pin, amount, AppConst.SILVER, true), bankAccount, pinToVerify);
					break;
				}
				case 2:{
					LOGGER.info("\nEnter Top Up amount: ");
					long topUpAmount = ss.nextLong();
					GiftCard userGiftCard=giftCardService.fetchGiftCard(getGiftCardInputs());
					Bank mappedAccount=userGiftCard.getBank();
					validateSufficientBalance(mappedAccount, topUpAmount);
					mappedAccount.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
					mappedAccount.setBalance(mappedAccount.getBalance() - topUpAmount);
					userGiftCard.setBank(mappedAccount);
					userGiftCard.setAmount(userGiftCard.getAmount()+topUpAmount);
					giftCardService.updateCard(userGiftCard, AppConst.TOP_UP);
					break;
				}
				case 3: {
					LOGGER.info("\n1.Block Card \n2.Unblock Card");
					int blockOption=ss.nextInt();
					giftCardService.updateCard(giftCardService.fetchGiftCard(getGiftCardInputs()), blockOption==1 ? AppConst.BLOCK : AppConst.UNBLOCK);
					break;
				}
				case 4:{
					LOGGER.info("\nAre you sure you want to close gift card? \n1.YES \n2.NO");
					int confirmation=ss.nextInt();
					if(confirmation==2)
						break;
					giftCardService.updateCard(giftCardService.fetchGiftCard(getGiftCardInputs()), AppConst.CLOSE);
					break;
				}
				case 5:{
					giftCardService.printGiftCard();
					return;
				}
				default:
					return;
					
			}
			giftCardService.printGiftCard();
			bankServiceImpl.printAccountDetails();
		}
		catch(Exception e) {
			//e.printStackTrace();
			LOGGER.error(e.getMessage());
			if(e instanceof IncorrectPinException || e instanceof InvalidBankAccountException || e instanceof InvalidOptionException || e instanceof InvalidOptionException || e instanceof InvalidCardException || e instanceof InvalidOptionException || e instanceof InsufficientBalanceException ) {
				handleGiftCardOperation(option);
			}
		}
	}
	
	public void handleTransactionOperation(int option) {
		
	}
	
	public GiftCardRequest getGiftCardInputs() {
		LOGGER.info("\nEnter gift card number: ");
		long giftCardNumber=ss.nextLong();
		LOGGER.info("\nEnter PIN: ");
		String pin=ss.next();
		GiftCardRequest request= new GiftCardRequest();
		request.setId(giftCardNumber);
		request.setPin(pin);
		return request;
	}
	
	public void validateSufficientBalance(Bank account,long topUpAmount) throws InsufficientBalanceException {
		LOGGER.info("User bank balance: {}",account);
		
		if(topUpAmount>account.getBalance())
			throw new InsufficientBalanceException("Insufficient balance in account "+account.getBalance()+" : "+topUpAmount);
		
	}
}
