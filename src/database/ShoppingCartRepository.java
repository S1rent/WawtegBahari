package database;

import java.util.ArrayList;

import models.ShoppingCart;

public class ShoppingCartRepository {
	private ArrayList<ShoppingCart> cartList = new ArrayList<ShoppingCart>();

	private ShoppingCartRepository() { }
	private static ShoppingCartRepository shared;
	
	public static ShoppingCartRepository sharedInstance() {
		if(shared == null) {
			synchronized (ShoppingCartRepository.class) {
				if(shared == null) {
					shared = new ShoppingCartRepository();
				}
			}
		}
		
		return shared;
	}
	
	public ArrayList<ShoppingCart> getShoppingCartList() {
		return this.cartList;
	}
	
	public ArrayList<ShoppingCart> getShoppingCartList(String userID) {
		ArrayList<ShoppingCart> userCartList = new ArrayList<ShoppingCart>();
		for (ShoppingCart shoppingCart : cartList) {
			if(shoppingCart.getUserID().equals(userID) && !shoppingCart.isCheckedOut()) {
				userCartList.add(shoppingCart);
			}
		}
		return userCartList;
	}
	
	public void addShoppingCart(ShoppingCart shoppingCart) {
		this.cartList.add(shoppingCart);
	}
	
	public void updateShoppingCart(ShoppingCart newCart, String cartID) {
		for(int i = 0; i < cartList.size(); i++) {
			ShoppingCart currentCart = cartList.get(i);
			if(currentCart.getCartID().equals(cartID)) {
				cartList.set(i, newCart);
			}
		}
	}

}
