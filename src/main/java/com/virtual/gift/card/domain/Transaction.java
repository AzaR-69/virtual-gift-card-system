package com.virtual.gift.card.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="transaction_id")
	private long id;
	
	@Column(name="transaction_amount")
	private long transactionAmount;
	
	@ManyToOne
	@JoinColumn(name="card_id")
	private GiftCard giftCard;

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(long transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public GiftCard getGiftCard() {
		return giftCard;
	}

	public void setGiftCard(GiftCard giftCard) {
		this.giftCard = giftCard;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", transactionAmount=" + transactionAmount + ", giftCard=" + giftCard + "]";
	}

	
}
