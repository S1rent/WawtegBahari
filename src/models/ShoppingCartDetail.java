package models;

import java.util.UUID;

import models.menu.Menu;

public class ShoppingCartDetail {

	private Menu menu;
	private int quantity;
	private String cartDetailID, cartID;
	
	public ShoppingCartDetail(Menu menu, int quantity, String cartID) {
		super();
		this.menu = menu;
		this.quantity = quantity;
		this.cartDetailID = UUID.randomUUID().toString();
		this.cartID = cartID;
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

	public String getCartDetailID() {
		return cartDetailID;
	}

	public void setCartDetailID(String cartDetailID) {
		this.cartDetailID = cartDetailID;
	}

	public String getCartID() {
		return cartID;
	}

}
