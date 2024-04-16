package com.virtual.gift.card.giftcard;

import java.util.Optional;

import com.virtual.gift.card.domain.Bank;
import com.virtual.gift.card.domain.GiftCard;
import com.virtual.gift.card.request.GiftCardRequest;

public interface GiftCardDao {
	
	Optional<GiftCard> fetchGiftCard(GiftCardRequest card);
	
	void createGiftCard(GiftCard card,Bank account);
	
	void updateCard(GiftCard card);
	
	void printGiftCards();
}
