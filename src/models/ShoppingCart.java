package models;

import java.util.ArrayList;
import java.util.UUID;

public class ShoppingCart {
	
	private String cartID, userID, date;
	private ArrayList<ShoppingCartDetail> details;

	public ShoppingCart(String userID, String date, ArrayList<ShoppingCartDetail> details) {
		super();
		this.cartID = UUID.randomUUID().toString();
		this.userID = userID;
		this.date = date;
		this.details = details;
	}

	public String getCartID() {
		return cartID;
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

	public ArrayList<ShoppingCartDetail> getDetails() {
		return details;
	}

	public void setDetails(ArrayList<ShoppingCartDetail> details) {
		this.details = details;
	}
	
	public void addDetails(ShoppingCartDetail detail) {
		this.details.add(detail);
	}
	
}
