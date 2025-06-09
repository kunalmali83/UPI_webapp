package com.example.project.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class BankAccount {

	private String accountHolder;
	private String upiId;
	@Id
    private String accountNumber;
    private String balance;
	public BankAccount() {
	
	}
	public BankAccount(String accountHolder, String upiId, String accountNumber, String balance) {
		super();
		this.accountHolder = accountHolder;
		this.upiId = upiId;
		this.accountNumber = accountNumber;
		this.balance = balance;
	}
	public String getAccountHolder() {
		return accountHolder;
	}
	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}
	public String getUpiId() {
		return upiId;
	}
	public void setUpiId(String upiId) {
		this.upiId = upiId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
    
    

	
}
