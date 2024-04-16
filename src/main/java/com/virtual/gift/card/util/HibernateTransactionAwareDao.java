package com.virtual.gift.card.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.virtual.gift.card.domain.Bank;
import com.virtual.gift.card.domain.GiftCard;
import com.virtual.gift.card.domain.Transaction;

public class HibernateTransactionAwareDao {
	private static Logger LOGGER = LoggerFactory.getLogger(HibernateTransactionAwareDao.class);

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				LOGGER.info("Inside configuration");
				Configuration configuration = new Configuration();

				// Hibernate settings equivalent to hibernate.cfg.xml's properties
				Properties settings = new Properties();
				settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				settings.put(Environment.URL,
						"jdbc:mysql://localhost:3306/virtual_gift_card_system?createDatabaseIfNotExist=true&useSSL=false");
				settings.put(Environment.USER, "root");
				settings.put(Environment.PASS, "root");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
				settings.put(Environment.SHOW_SQL, "true");
				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
				settings.put(Environment.HBM2DDL_AUTO, "update");
//				settings.put(Environment.USE_SECOND_LEVEL_CACHE, true);
//				settings.put(Environment.CACHE_REGION_FACTORY,"org.hibernate.cache.ehcache.EhCacheRegionFactory");
//				settings.put(Environment.USE_QUERY_CACHE,"true");

				configuration.setProperties(settings);

				configuration.addAnnotatedClass(Bank.class);
				configuration.addAnnotatedClass(Transaction.class);
				configuration.addAnnotatedClass(GiftCard.class);

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();

				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}
}