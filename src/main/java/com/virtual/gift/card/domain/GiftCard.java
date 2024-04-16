package com.virtual.gift.card.domain;

import java.sql.Timestamp;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="gift_card")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class GiftCard {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="card_id",updatable=false,nullable=false)
	private long id;

	@Column(name="pin")
	private String pin;
	
	@Column(name="amount")
	private long amount;
	
	@Column(name="card_type")
	private String cardType;
	
	@Column(name="is_blocked")
	private boolean blocked;

	@Column(name="active")
	private boolean active;
	
	@Column(name="created_time")
	private Timestamp createdTime;
	
	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;
	
	@ManyToOne(targetEntity=Bank.class,fetch= FetchType.EAGER,cascade = CascadeType.MERGE)
	@JoinColumn(name="bank_id")
	private Bank bank;

	public GiftCard() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GiftCard(long id, String pin, long amount, String cardType, boolean blocked, boolean active,
			Timestamp createdTime, Timestamp lastUpdateTime, Bank bank) {
		super();
		this.id = id;
		this.pin = pin;
		this.amount = amount;
		this.cardType = cardType;
		this.blocked = blocked;
		this.active = active;
		this.createdTime = createdTime;
		this.lastUpdateTime = lastUpdateTime;
		this.bank = bank;
	}

	public GiftCard(String pin, long amount, String cardType, boolean active) {
		super();
		this.pin = pin;
		this.amount = amount;
		this.cardType = cardType;
		this.active = active;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	@Override
	public String toString() {
		return "GiftCard [id=" + id + ", pin=" + pin + ", amount=" + amount + ", cardType=" + cardType + ", blocked="
				+ blocked + ", active=" + active + ", createdTime=" + createdTime + ", lastUpdateTime=" + lastUpdateTime
				+ "]";
	}

	

}
