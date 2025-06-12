package com.example.project.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Transaction {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "sender_upi")
	    private String senderUpi;

	    @Column(name = "receiver_upi")
	    private String receiverUpi;
	    private Double amount;
	    private LocalDateTime timestamp;
	    private String message;
	    
	    
		public Transaction() {
		}
		public Transaction(Long id, String senderUpi, String receiverUpi, Double amount, LocalDateTime timestamp,
				String message) {
			super();
			this.id = id;
			this.senderUpi = senderUpi;
			this.receiverUpi = receiverUpi;
			this.amount = amount;
			this.timestamp = timestamp;
			this.message = message;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getSenderUpi() {
			return senderUpi;
		}
		public void setSenderUpi(String senderUpi) {
			this.senderUpi = senderUpi;
		}
		public String getReceiverUpi() {
			return receiverUpi;
		}
		public void setReceiverUpi(String receiverUpi) {
			this.receiverUpi = receiverUpi;
		}
		public Double getAmount() {
			return amount;
		}
		public void setAmount(Double amount) {
			this.amount = amount;
		}
		public LocalDateTime getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	    
	    
}
