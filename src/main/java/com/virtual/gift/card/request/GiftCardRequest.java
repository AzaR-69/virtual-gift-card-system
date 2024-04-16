package com.virtual.gift.card.request;

public class GiftCardRequest {
	private long id;
	private String pin;
	private long amount;

	public GiftCardRequest() {
		super();
	}

	public GiftCardRequest(long id, String pin, long amount) {
		super();
		this.id = id;
		this.pin = pin;
		this.amount = amount;
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

	@Override
	public String toString() {
		return "GiftCardRequest [id=" + id + ", pin=" + pin + ", amount=" + amount + "]";
	}

}
