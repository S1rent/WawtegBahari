package models;

import java.util.ArrayList;
import java.util.UUID;

public class Transaction {
	
	private String transactionID, userID, date;
	private ArrayList<TransactionDetail> details;

	public Transaction(String userID, String date, ArrayList<TransactionDetail> details) {
		super();
		this.transactionID = UUID.randomUUID().toString();
		this.userID = userID;
		this.date = date;
		this.details = details;
	}

	public String getTransactionID() {
		return transactionID;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public ArrayList<TransactionDetail> getDetails() {
		return details;
	}
	
	public void setDetails(ArrayList<TransactionDetail> details) {
		this.details = details;
	}
}
