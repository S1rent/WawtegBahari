package models;

import java.util.UUID;

import models.menu.Menu;

public class TransactionDetail {

	private Menu menu;
	private int quantity;
	private String transactionDetailID, transactionID;
	
	public TransactionDetail(Menu menu, int quantity, String transactionID) {
		super();
		this.menu = menu;
		this.quantity = quantity;
		this.transactionDetailID = UUID.randomUUID().toString();
		this.transactionID = transactionID;
	}

	public Menu getMenu() {
		return menu;
	}
	
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getTransactionDetailID() {
		return transactionDetailID;
	}
	
	public String getTransactionID() {
		return transactionID;
	}
	
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
}
