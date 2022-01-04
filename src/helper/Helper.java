package helper;

import java.util.ArrayList;
import java.util.Scanner;

import adapters.USDAdapter;
import database.MenuRepository;
import database.ShoppingCartRepository;
import models.ShoppingCartDetail;
import models.User;
import models.menu.Menu;

public class Helper {
	
	private Scanner scan = new Scanner(System.in);

	private Helper() { }
	private static Helper shared;
	
	public static Helper sharedInstance() {
		if(shared == null) {
			synchronized (Helper.class) {
				if(shared == null) {
					shared = new Helper();
				}
			}
		}
		
		return shared;
	}
	
	public void printMenuList() {
		System.out.println("Menu");
		System.out.println("==============================================================================================");
		System.out.println("|No  |Menu Name           |Description                   |Type   |Price [IDR]  |Price [USD]  |");
		System.out.println("==============================================================================================");
		ArrayList<Menu> menuList = MenuRepository.sharedInstance().getMenuList();
		for(int i = 0; i < menuList.size(); i++) {
			Menu menu = menuList.get(i);
			USDAdapter usdAdapter = new USDAdapter(menu);
			System.out.printf("|%-4s|%-20s|%-30s|%-7s|%-13.2f|%-13.2f|\n", i+1, menu.getName(), menu.getDescription(), menu.getType(), menu.getPrice(),usdAdapter.getPrice());
		}
		System.out.println("==============================================================================================");
	}
	
	public void printShoppingCartList(String userID) {
		System.out.println("Your Shopping Cart");
		System.out.println("========================================================================");
		System.out.println("|No  |Menu Name           |Quantity  |Subtotal [IDR]  |Subtotal [USD]  |");
		System.out.println("========================================================================");
		ArrayList<ShoppingCartDetail> userCarts = ShoppingCartRepository.sharedInstance().getShoppingCartList(userID).get(0).getDetails();
		
		for(int i = 0; i < userCarts.size(); i++) {
			ShoppingCartDetail cart = userCarts.get(i);
			Menu menu = userCarts.get(i).getMenu();
			double subTotal = menu.getPrice() * cart.getQuantity();
			
			USDAdapter usdAdapter = new USDAdapter(userCarts.get(i).getMenu());
			System.out.printf("|%-4s|%-20s|%-10s|%-16.2f|%-16.2f|\n", i+1, menu.getName(), cart.getQuantity(), subTotal, cart.getQuantity() * usdAdapter.getPrice());
		}
		System.out.println("========================================================================");
	}
	
	public void printUserAvailableCoupon(String userID) {
		System.out.println("Available Coupon for You");
		System.out.println("=========================================");
		System.out.println("|No  |Coupon           |Total Discount  |");
		System.out.println("=========================================");
	}
	
	public void printShoppingCartPage() {
		System.out.println("1. Remove Item");
		System.out.println("2. Checkout");
		System.out.println("3. Exit");
	}
	
	public void printCustomerMainMenu(User user) {
		blankSpace();
		System.out.println("Wawteg Bahari.");
		System.out.println("Hi " + user.getName() + ", what would you like to eat today?");
		System.out.println("=======================");
		System.out.println("1. Order Menu");
		System.out.println("2. Shopping Cart");
		System.out.println("3. Transactions History");
		System.out.println("4. Logout");
	}
	
	public void printAdminMainMenu() {
		blankSpace();
		System.out.println("Wawteg Bahari.");
		System.out.println("Hi Admin, have a good day.");
		System.out.println("=======================");
		System.out.println("1. Add Menu");
		System.out.println("2. Edit Menu");
		System.out.println("3. Delete Menu");
		System.out.println("4. View All Transactions");
		System.out.println("5. Logout");
	}
	
	public void printFrontPage() {
		blankSpace();
		System.out.println("Wawteg Bahari.");
		System.out.println("=======================");
		System.out.println("1. View Menu");
		System.out.println("2. Login");
		System.out.println("3. Register");
		System.out.println("4. Exit");
	}

	public void noData() {
		System.out.println("No Data...");
		System.out.print("Press enter to continue...");
		scan.nextLine();
	}
	
	public void blankSpace() {
		for(int i = 0; i < 30; i++) { 
			System.out.println(""); 
		}
	}
}
