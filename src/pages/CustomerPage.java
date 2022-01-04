package pages;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import database.MenuRepository;
import database.ShoppingCartRepository;
import database.TransactionRepository;
import helper.Helper;
import main.Main;
import models.ShoppingCart;
import models.ShoppingCartDetail;
import models.menu.Drink;
import models.menu.Food;
import models.menu.Menu;

public class CustomerPage {

	private Scanner scan = new Scanner(System.in);
	
	private CustomerPage() { }
	private static CustomerPage shared;
	
	public static CustomerPage sharedInstance() {
		if(shared == null) {
			synchronized (CustomerPage.class) {
				if(shared == null) {
					shared = new CustomerPage();
				}
			}
		}
		
		return shared;
	}
	
	public void navigateToCustomerMainMenu() {
		int mainMenuIdx = -1;
		
		do {
			Helper.sharedInstance().printCustomerMainMenu(Main.loggedInUser);
			do {
				System.out.print(">> ");
				try {
					mainMenuIdx = scan.nextInt();
				} catch (Exception e) {
					mainMenuIdx = -1;
				}
				scan.nextLine();
			} while(mainMenuIdx < 1 || mainMenuIdx > 4);
			
			Helper.sharedInstance().blankSpace();
			
			switch(mainMenuIdx) {
				case 1:
					orderMenuPage();
					break;
				case 2:
					shoppingCartPage();
					break;
				case 3:
					transactionHistoryPage();
					break;
				case 4:
					Main.loggedInUser = null;
					break;
			}
		} while(mainMenuIdx != 4);
	}
	
	public void transactionHistoryPage() {
		Helper.sharedInstance().blankSpace();
		
		if(TransactionRepository.sharedInstance().getTransactionList(Main.loggedInUser.getUserID()).isEmpty()) {
			Helper.sharedInstance().noData();
			return;
		}
	}

	public void shoppingCartPage() {
		Helper.sharedInstance().blankSpace();
		
		int menuIdx = -1;
		do {
			ArrayList<ShoppingCart> userShoppingCarts = ShoppingCartRepository.sharedInstance().getShoppingCartList(Main.loggedInUser.getUserID());
			if(userShoppingCarts.isEmpty() || userShoppingCarts.get(0).getDetails().isEmpty()) {
				Helper.sharedInstance().noData();
				return;
			}
			Helper.sharedInstance().printShoppingCartList(Main.loggedInUser.getUserID());
			Helper.sharedInstance().printShoppingCartPage();
			do {
				System.out.print(">> ");
				try {
					menuIdx = scan.nextInt();
				} catch (Exception e) {
					menuIdx = -1;
				}
				scan.nextLine();
			} while (menuIdx < 1 || menuIdx > 3);
			Helper.sharedInstance().blankSpace();
			
			switch(menuIdx) {
				case 1:
					removeCartItemPage();
					break;
				case 2:
					checkoutPage();
					break;
				case 3:
					break;
			}
		} while (menuIdx != 2 && menuIdx != 3);
	}

	public void orderMenuPage() {
		Helper.sharedInstance().blankSpace();
		if(MenuRepository.sharedInstance().getMenuList().isEmpty()) {
			Helper.sharedInstance().noData();
			return;
		}
		
		Helper.sharedInstance().printMenuList();
		System.out.println("Input 0 if you wish to cancel");
		ArrayList<Menu> menuList = MenuRepository.sharedInstance().getMenuList();
		int menuIdx = -1;
		do {
			try {
				System.out.printf("Input menu you want to order [0 - %d]: ", menuList.size());
				menuIdx = scan.nextInt();
			} catch (Exception e) {
				menuIdx = -1;
			}
			scan.nextLine();
		} while(menuIdx < 0 || menuIdx > menuList.size());
		
		if(menuIdx == 0) {
			return;
		}

		Menu selectedMenu = menuList.get(menuIdx-1);
		
		int quantity = -1;
		
		do {
			System.out.print("Please input order quantity [Minimum 1]: ");
			try {
				quantity = scan.nextInt();
			} catch (Exception e) {
				quantity = -1;
			}
			scan.nextLine();
		} while (quantity < 1);
		
		// Cek dulu user ini masih punya active shopping cart ato nggak
		// kalo gaada, bikin active shopping cart baru
		// kalo ada, pakai yang sudah ada
		
		if(ShoppingCartRepository.sharedInstance().getShoppingCartList(Main.loggedInUser.getUserID()).isEmpty()) {
			// Bikin baru
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date date = new Date();  
		    String transactionDate = formatter.format(date);
		    
			ShoppingCart userCart = new ShoppingCart(Main.loggedInUser.getUserID(), transactionDate, new ArrayList<ShoppingCartDetail>());
			ShoppingCartDetail cartDetail = new ShoppingCartDetail(selectedMenu, quantity, userCart.getCartID());
			userCart.addDetails(cartDetail);
			
			ShoppingCartRepository.sharedInstance().addShoppingCart(userCart);
		} else {
			ShoppingCart userCart = ShoppingCartRepository.sharedInstance().getShoppingCartList(Main.loggedInUser.getUserID()).get(0);
			
			// Cek dulu dia udh pernah order item ini belom
			// kalo belom, bikin cart detail baru
			// kalo udah, modif aja cart detail yang lama
			boolean hasOrderedSameProduct = false;
			for (ShoppingCartDetail dbCartDetail : userCart.getDetails()) {
				if(dbCartDetail.getMenu().getMenuID().equals(selectedMenu.getMenuID())) {
					hasOrderedSameProduct = true;
					break;
				}
			}
			
			if(hasOrderedSameProduct) {
				ShoppingCartDetail newCartDetail = null;
				int detailIndex = -1;
				
				for(int i = 0; i < userCart.getDetails().size(); i++) {
					if(userCart.getDetails().get(i).getMenu().getMenuID().equals(selectedMenu.getMenuID())) {
						newCartDetail = userCart.getDetails().get(i);
						detailIndex = i;
						break;
					}
				}
				newCartDetail.setQuantity(newCartDetail.getQuantity() + quantity);
				
				userCart.getDetails().set(detailIndex, newCartDetail);
			} else {
				ShoppingCartDetail cartDetail = new ShoppingCartDetail(selectedMenu, quantity, userCart.getCartID());
				userCart.addDetails(cartDetail);
			}
			
			ShoppingCartRepository.sharedInstance().updateShoppingCart(userCart, userCart.getCartID());
		}
		
		System.out.printf("\nSuccessfully ordered %s.\n", selectedMenu.getName());
		System.out.print("Press enter to continue...");
		scan.nextLine();
	}
	
	public void removeCartItemPage() {
		Helper.sharedInstance().blankSpace();
		ArrayList<ShoppingCart> userShoppingCarts = ShoppingCartRepository.sharedInstance().getShoppingCartList(Main.loggedInUser.getUserID());
		if(userShoppingCarts.isEmpty() || userShoppingCarts.get(0).getDetails().isEmpty()) {
			Helper.sharedInstance().noData();
			return;
		}
		ShoppingCart activeShoppingCart = userShoppingCarts.get(0);
		Helper.sharedInstance().printShoppingCartList(Main.loggedInUser.getUserID());
		System.out.println("Input 0 if you wish to cancel");
		
		int idx = -1;
		do {
			try {
				System.out.printf("Input menu you want to remove from your shopping cart [0 - %d]: ", activeShoppingCart.getDetails().size());
				idx = scan.nextInt();
			} catch (Exception e) {
				idx = -1;
			}
			scan.nextLine();
		} while(idx < 0 || idx > activeShoppingCart.getDetails().size());
		
		if(idx == 0) {
			Helper.sharedInstance().blankSpace();
			return;
		}
		
		ShoppingCartDetail selectedMenu = activeShoppingCart.getDetails().get(idx-1);
		
		ArrayList<ShoppingCartDetail> newDetails = new ArrayList<ShoppingCartDetail>();
		for(int i = 0; i < activeShoppingCart.getDetails().size(); i++) {
			if(!activeShoppingCart.getDetails().get(i).getMenu().getMenuID().equals(selectedMenu.getMenu().getMenuID())) {
				newDetails.add(activeShoppingCart.getDetails().get(i));
			}
		}
		activeShoppingCart.setDetails(newDetails);
		ShoppingCartRepository.sharedInstance().updateShoppingCart(activeShoppingCart, activeShoppingCart.getCartID());

		System.out.printf("\nSuccessfully removed %s.\n", selectedMenu.getMenu().getName());
		System.out.print("Press enter to continue...");
		scan.nextLine();
		Helper.sharedInstance().blankSpace();
	}
	
	public void checkoutPage() {
		
	}
}
