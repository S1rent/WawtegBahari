package database;

import java.util.ArrayList;

import models.ShoppingCart;
import models.ShoppingCartDetail;
import models.menu.*;;

public class MenuRepository {

private ArrayList<Menu> menuList = new ArrayList<Menu>();
	
	private MenuRepository() { }
	private static MenuRepository shared;
	
	public static MenuRepository sharedInstance() {
		if(shared == null) {
			synchronized (MenuRepository.class) {
				if(shared == null) {
					shared = new MenuRepository();
				}
			}
		}
		
		return shared;
	}

	public ArrayList<Menu> getMenuList() {
		ArrayList<Menu> availableMenu = new ArrayList<Menu>();
		for (Menu menu : menuList) {
			if(!menu.isRemoved()) {
				availableMenu.add(menu);
			}
		}
		return availableMenu;
	}
	
	public ArrayList<Menu> getAllMenuList() {
		return menuList;
	}

	public void addMenu(Menu menu) {
		this.menuList.add(menu);
	}
	
	public void editMenu(Menu newMenu, String menuID) {
		for(int i = 0; i < menuList.size(); i++) {
			Menu menu = menuList.get(i);
			if(menu.getMenuID().equals(menuID)) {
				menuList.set(i, newMenu);
			}
		}
	}
	
	public void removeMenu(Menu targetRemove) {
		for(int i = 0; i < menuList.size(); i++) {
			Menu menu = menuList.get(i);
			if(menu.getMenuID().equals(targetRemove.getMenuID())) {
				menuList.get(i).setRemoved(true);
				
				// Jangan lupa delete juga menu ini di shopping cart punya user
				for (ShoppingCart shoppingCart: ShoppingCartRepository.sharedInstance().getShoppingCartList()) {
					shoppingCart.deleteMenuFromDetails(menu.getMenuID());
					ShoppingCartRepository.sharedInstance().updateShoppingCart(shoppingCart, shoppingCart.getCartID());
				}
			}
		}
	}
}
