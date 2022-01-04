package pages;

import java.util.Scanner;

import database.MenuRepository;
import database.ShoppingCartRepository;
import database.TransactionRepository;
import helper.Helper;
import main.Main;

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
		
		if(ShoppingCartRepository.sharedInstance().getShoppingCartList(Main.loggedInUser.getUserID()).isEmpty()) {
			Helper.sharedInstance().noData();
			return;
		}
	}

	public void orderMenuPage() {
		Helper.sharedInstance().blankSpace();
		if(MenuRepository.sharedInstance().getMenuList().isEmpty()) {
			Helper.sharedInstance().noData();
			return;
		}
	}
}
