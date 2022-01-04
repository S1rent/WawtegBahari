package helper;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import adapters.USDAdapter;
import database.CouponRepository;
import database.MenuRepository;
import database.ShoppingCartRepository;
import database.UserRepository;
import models.Coupon;
import models.ShoppingCartDetail;
import models.Transaction;
import models.TransactionDetail;
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
		System.out.println("========================================================================================================");
		System.out.println("|No  |Menu Name           |Description                             |Type   |Price [IDR]  |Price [USD]  |");
		System.out.println("========================================================================================================");
		ArrayList<Menu> menuList = MenuRepository.sharedInstance().getMenuList();
		for(int i = 0; i < menuList.size(); i++) {
			Menu menu = menuList.get(i);
			USDAdapter usdAdapter = new USDAdapter(menu);
			System.out.printf("|%-4s|%-20s|%-40s|%-7s|%-13.2f|%-13.2f|\n", i+1, menu.getName(), menu.getDescription(), menu.getType(), menu.getPrice(),usdAdapter.getPrice());
		}
		System.out.println("========================================================================================================");
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
	
	public void printTotalPayment(String userID) {
		double idrTotal = 0, usdTotal = 0;
		ArrayList<ShoppingCartDetail> userCarts = ShoppingCartRepository.sharedInstance().getShoppingCartList(userID).get(0).getDetails();
		for(int i = 0; i < userCarts.size(); i++) {
			ShoppingCartDetail cart = userCarts.get(i);
			Menu menu = userCarts.get(i).getMenu();
			idrTotal += menu.getPrice() * cart.getQuantity();
			USDAdapter usdAdapter = new USDAdapter(userCarts.get(i).getMenu());
			usdTotal += cart.getQuantity() * usdAdapter.getPrice();
		}
		System.out.printf("|Total Payment [in IDR]: %-46.2f|\n", (idrTotal < 0 ) ? 0 : idrTotal);
		System.out.printf("|Total Payment [in USD]: %-46.2f|\n", (usdTotal < 0) ? 0 : usdTotal);
		System.out.println("========================================================================");
	}
	
	public void printTotalPaymentUsingCoupon(String userID, Coupon coupon) {
		double idrTotal = 0, usdTotal = 0;
		ArrayList<ShoppingCartDetail> userCarts = ShoppingCartRepository.sharedInstance().getShoppingCartList(userID).get(0).getDetails();
		for(int i = 0; i < userCarts.size(); i++) {
			ShoppingCartDetail cart = userCarts.get(i);
			Menu menu = userCarts.get(i).getMenu();
			idrTotal += menu.getPrice() * cart.getQuantity();
			USDAdapter usdAdapter = new USDAdapter(userCarts.get(i).getMenu());
			usdTotal += cart.getQuantity() * usdAdapter.getPrice();
		}
		
		USDAdapter discountUSDAdapter = new USDAdapter(coupon);
		System.out.println("========================================================================");
		idrTotal = idrTotal - coupon.getPrice();
		usdTotal = usdTotal - discountUSDAdapter.getPrice();
		System.out.printf("|Total Payment After Using Coupon [in IDR]: %-27.2f|\n", (idrTotal < 0 ) ? 0 : idrTotal);
		System.out.printf("|Total Payment After Using Coupon [in USD]: %-27.2f|\n", (usdTotal < 0) ? 0 : usdTotal);
		System.out.println("========================================================================");
	}
	
	public void printTransactions(ArrayList<Transaction> transactionList, boolean isAdmin) {
		for(int i = 0; i < transactionList.size(); i++) {
			double idrTotal = 0, usdTotal = 0;
			Transaction currentTransaction = transactionList.get(i);
			System.out.print("Transaction at "+currentTransaction.getDate());
			if(isAdmin) {
				System.out.printf(" , by user with phone number %s", UserRepository.sharedInstance().find(currentTransaction.getUserID()).getPhoneNumber());
			}
			System.out.println("\n======================================================================");
			System.out.println("|TransactionID                        |Payment Method  |Coupon Used  |");
			System.out.println("======================================================================");
			System.out.printf("|%-37s|%-16s|%-13s|\n", 
					currentTransaction.getTransactionID(), 
					currentTransaction.getPaymentMethod(), 
					(currentTransaction.getUsedCoupon() != null) ? currentTransaction.getUsedCoupon().getCouponCode() : '-');
			System.out.println("======================================================================");
			System.out.println("|No  |Menu Name         |Quantity  |Subtotal [IDR]  |Subtotal [USD]  |");
			System.out.println("======================================================================");
			
			for(int j = 0; j < currentTransaction.getDetails().size(); j++) {
				TransactionDetail detail = currentTransaction.getDetails().get(j);
				Menu menu = detail.getMenu();
				double subTotal = menu.getPrice() * detail.getQuantity();
				idrTotal += menu.getPrice() * detail.getQuantity();
				USDAdapter usdAdapter = new USDAdapter(detail.getMenu());
				usdTotal += detail.getQuantity() * usdAdapter.getPrice();
				System.out.printf("|%-4s|%-18s|%-10s|%-16.2f|%-16.2f|\n", i+1, menu.getName(), detail.getQuantity(), subTotal, detail.getQuantity() * usdAdapter.getPrice());
			}
			System.out.println("======================================================================");
			idrTotal = (currentTransaction.getUsedCoupon() != null) ? idrTotal - currentTransaction.getUsedCoupon().getDiscount() : idrTotal;
			USDAdapter discountUSDAdapter = new USDAdapter(currentTransaction.getUsedCoupon());
			usdTotal = (currentTransaction.getUsedCoupon() != null) ? usdTotal - discountUSDAdapter.getPrice() : usdTotal;
					
			System.out.printf("|Total Payment [in IDR]: %-44.2f|\n", (idrTotal < 0) ? 0 : idrTotal);
			System.out.printf("|Total Payment [in USD]: %-44.2f|\n", (usdTotal < 0) ? 0 : usdTotal);
			System.out.println("======================================================================\n");
		}
	}
	
	public double calculateTotalPayment(String userID) {
		double idrTotal = 0;
		ArrayList<ShoppingCartDetail> userCarts = ShoppingCartRepository.sharedInstance().getShoppingCartList(userID).get(0).getDetails();
		for(int i = 0; i < userCarts.size(); i++) {
			ShoppingCartDetail cart = userCarts.get(i);
			Menu menu = userCarts.get(i).getMenu();
			idrTotal += menu.getPrice() * cart.getQuantity();
		}
		return idrTotal;
	}
	
	public void printUserAvailableCoupon(String userID) {
		ArrayList<Coupon> couponList = CouponRepository.sharedInstance().getCouponList(userID);
		
		if(couponList.isEmpty()) {
			System.out.println("You currently don't have any coupon.");
			System.out.println("Do any transactions, and well give you some :D");
		} else {
			System.out.println("Available Coupon for You");
			System.out.println("===============================================================");
			System.out.println("|No  |Coupon Code         |Discount [IDR]   |Discount [USD]   |");
			System.out.println("===============================================================");
			
			for(int i = 0; i < couponList.size(); i++) {
				Coupon coupon = couponList.get(i);
				
				USDAdapter usdAdapter = new USDAdapter(coupon);
				System.out.printf("|%-4s|%-20s|%-17.2f|%-17.2f|\n", i+1, coupon.getCouponCode(), coupon.getPrice(), usdAdapter.getPrice());
			}
			System.out.println("===============================================================");
		}
	}
	
	public boolean validateCoupon(String userID, String couponCode) {
		ArrayList<Coupon> userCoupons = CouponRepository.sharedInstance().getCouponList(userID);
		
		for(int i = 0; i < userCoupons.size(); i++) {
			Coupon coupon = userCoupons.get(i);
			if(coupon.getUserID().equals(userID) && coupon.getCouponCode().equals(couponCode)) {
				return true;
			}
		}
		
		return false;
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
	
	public String generateRandomCoupon() {
		String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String couponCode = "";
		
		Random rand = new Random();
		
		while(couponCode.length() != 10) {
			int idx = rand.nextInt(characterSet.length());
			couponCode += characterSet.charAt(idx);
		}
        return couponCode;
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
