package com.virtual.gift.card.service;

import java.sql.Timestamp;
import java.util.Optional;

import com.virtual.gift.card.domain.Bank;
import com.virtual.gift.card.domain.GiftCard;
import com.virtual.gift.card.exceptions.IncorrectPinException;
import com.virtual.gift.card.exceptions.InvalidCardException;
import com.virtual.gift.card.exceptions.InvalidOptionException;
import com.virtual.gift.card.giftcard.GiftCardDaoImpl;
import com.virtual.gift.card.request.GiftCardRequest;
import com.virtual.gift.card.util.AppConst;

import jakarta.transaction.Transactional;

public class GiftCardServiceImpl {

	GiftCardDaoImpl giftCardService;

	public GiftCardServiceImpl() {
		giftCardService = new GiftCardDaoImpl();
	}

	public void createGiftCard(GiftCard card, Bank account, String pinToVerify) throws IncorrectPinException {
		if (card.getPin().equals(pinToVerify)) {
			card.setCreatedTime(new Timestamp(System.currentTimeMillis()));
			card.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			account.setBalance(account.getBalance()-card.getAmount());
			giftCardService.createGiftCard(card, account);
		} else {
			throw new IncorrectPinException("PIN doesnt match, kindly enter again");
		}

	}

	public void updateCard(GiftCard card , String option) throws InvalidOptionException, InvalidCardException {
		checkIfCardIsInactive(card);
		switch (option) {
			case AppConst.BLOCK: {
				if (card.isBlocked())
					throw new InvalidOptionException("Card is already in blocked state!!");
				card.setBlocked(true);
				break;
			}
			case AppConst.UNBLOCK: {
				if (!card.isBlocked())
					throw new InvalidOptionException("Card is already in unblocked state!!");
				card.setBlocked(false);
				break;
			}
			case AppConst.CLOSE: {
				Bank account=card.getBank();
				account.setBalance(account.getBalance()+card.getAmount());
				card.setBank(account);
				card.setActive(false);
				break;
			}
//			case AppConst.TOP_UP: {
//				if (!card.isActive())
//					throw new InvalidCardException("Cannot proceed with topup! Card is inactive!!");
//				break;
//			}
		}
		giftCardService.updateCard(card);

	}

	@Transactional
	public GiftCard fetchGiftCard(GiftCardRequest card) throws InvalidCardException {
		Optional<GiftCard> giftCard = giftCardService.fetchGiftCard(card);
		if (!giftCard.isPresent()) {
			throw new InvalidCardException("ID and PIN doesn't match");
		}
		return giftCard.get();
	}

	public void printGiftCard() {
		giftCardService.printGiftCards();
	}
	public void checkIfCardIsInactive(GiftCard card) throws InvalidCardException {
		if(!card.isActive())
			throw new InvalidCardException("Cannot proceed as the card is inactive!!");
	}

}
