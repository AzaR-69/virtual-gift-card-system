package com.virtual.gift.card.giftcard;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.virtual.gift.card.domain.Bank;
import com.virtual.gift.card.domain.GiftCard;
import com.virtual.gift.card.request.GiftCardRequest;
import com.virtual.gift.card.util.HibernateTransactionAwareDao;

public class GiftCardDaoImpl extends HibernateTransactionAwareDao implements GiftCardDao {

	private static Logger LOGGER = LoggerFactory.getLogger(GiftCardDaoImpl.class);

	public GiftCardDaoImpl() {
		//Initialising session factory
		getSessionFactory();
	}

	@Override
	public void createGiftCard(GiftCard card,Bank account) {
		try(Session session= getSessionFactory().openSession()){
			Transaction transaction = session.beginTransaction();
			account.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			card.setBank(account);
			session.merge(card);
			transaction.commit();
		}catch(Exception e) {
			LOGGER.error("Exception occurred: ", e);
		}
		
	}

	@Override
	public Optional<GiftCard> fetchGiftCard(GiftCardRequest card) {
		Optional<GiftCard> giftCard= Optional.empty();
		try(Session session= getSessionFactory().openSession()){
			Query<GiftCard> query=session.createQuery("SELECT g from GiftCard g where g.id=:id and g.pin=:pin",GiftCard.class);
			query.setParameter("id", card.getId());
			query.setParameter("pin", card.getPin());
			giftCard=query.uniqueResultOptional();
		}catch(Exception e) {
			LOGGER.error("Exception occurred: ", e);
		}
		return giftCard;
	}

	@Override
	public void updateCard(GiftCard card){
		try(Session session= getSessionFactory().openSession()){
			Transaction transaction = session.beginTransaction();
			card.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			session.merge(card);
			transaction.commit();
		}catch(Exception e) {
			LOGGER.error("Exception occurred: ", e);
		}
	}

	@Override
	public void printGiftCards() {
		try(Session session= getSessionFactory().openSession()){
			Query<GiftCard> query=session.createQuery("from GiftCard",GiftCard.class);
			List<GiftCard> giftCard= query.getResultList();
			LOGGER.info(String.format("%-20s %-20s %-20s %-20s %-20s %-30s %-30s", "ID","Amount","Card Type","Is Blocked","Active","Created Time","Last Updated Time"));
			giftCard.forEach(s -> {
				LOGGER.info(String.format("%-20s %-20s %-20s %-20s %-20s %-30s %-30s", s.getId(),s.getAmount(),s.getCardType(),s.isBlocked(),s.isActive(),s.getCreatedTime(),s.getLastUpdateTime()));
			});
		}catch(Exception e) {
			LOGGER.error("Exception occurred: ", e);
		}
		
	}

}
