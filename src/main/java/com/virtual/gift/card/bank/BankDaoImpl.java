package com.virtual.gift.card.bank;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.virtual.gift.card.domain.Bank;
import com.virtual.gift.card.util.HibernateTransactionAwareDao;

import jakarta.transaction.Transactional;

@Transactional
public class BankDaoImpl extends HibernateTransactionAwareDao implements BankDao {

	private static Logger LOGGER = LoggerFactory.getLogger(BankDaoImpl.class);

	public BankDaoImpl() {
		//Initialising session factory
		getSessionFactory();
	}

	@Override
	public boolean createBankAccount(Bank account) {
		try (Session session = getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();
			session.persist(account);
			transaction.commit();

		} catch (Exception e) {
			LOGGER.error("Exception occurred: ", e);
		}
		return false;
	}

	@Override
	public boolean depositToAccount(Bank account) {
		try (Session session = getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();
			account.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			session.merge(account);
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error("Exception occurred: ", e);
		}
		return false;
	}

	@Override
	public void printAccountDetails() {
		try (Session session = getSessionFactory().openSession()) {
			Query<Bank> query = session.createQuery("from Bank", Bank.class);
			Optional<List<Bank>> accountDetails = Optional.of(query.getResultList());
			if (accountDetails.get() != null && !accountDetails.get().isEmpty()) {
				LOGGER.info(String.format("%-20s %-20s %-20s %-30s %-30s", "Account ID","Customer name","Balance","Created Time","Last Update time"));
				for (Bank bank : accountDetails.get()) {
					LOGGER.info(String.format("%-20s %-20s %-20s %-30s %-30s", bank.getId(),bank.getCustomerName(),bank.getBalance(),bank.getCreatedTime(),bank.getLastUpdateTime()));
				}
			}

		} catch (Exception e) {
			LOGGER.error("Exception occurred: ", e);
		}

	}

	@Override
	public Bank fetchAccountById(long id) {
		try (Session session = getSessionFactory().openSession()) {
			Query<Bank> query = session.createQuery("from Bank where id=:id", Bank.class);
			query.setParameter("id", id);
			Optional<Bank> accountDetails = query.uniqueResultOptional();
			if (accountDetails.isPresent()) {
				return accountDetails.get();
			} 
		} catch (Exception e) {
			LOGGER.error("Exception occurred: ", e);
		}
		return null;
	}

}
