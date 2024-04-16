package com.virtual.gift.card.domain;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.Cacheable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="bank")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class Bank {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="bank_id",updatable=false,nullable=false)
	private long id;
	
	@Column(name="customer_name")
	private String customerName;
	
	@Column(name="balance")
	private long balance;
	
	@Column(name="created_time")
	private Timestamp createdTime;
	
	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;
	
	@OneToMany(mappedBy = "bank",cascade = CascadeType.ALL,fetch= FetchType.EAGER)
	private List<GiftCard> giftCards;
	
	public Bank() {
	}
	
	public Bank(long id, long balance) {
		super();
		this.id = id;
		this.balance = balance;
	}
	public Bank(String customerName, long balance, Timestamp createdTime) {
		super();
		this.customerName = customerName;
		this.balance = balance;
		this.createdTime = createdTime;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public List<GiftCard> getGiftCards() {
		return giftCards;
	}

	public void setGiftCards(List<GiftCard> giftCards) {
		this.giftCards = giftCards;
	}

	@Override
	public String toString() {
		return "Bank [id=" + id + ", customerName=" + customerName + ", balance=" + balance + ", createdTime="
				+ createdTime + ", lastUpdateTime=" + lastUpdateTime + "]";
	}

}
