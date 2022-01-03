package pages;

import java.util.Scanner;

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
					CustomerPage.sharedInstance().orderMenuPage();
					break;
				case 2:
					CustomerPage.sharedInstance().checkOutPage();
					break;
				case 3:
					CustomerPage.sharedInstance().transactionHistoryPage();
					break;
				case 4:
					Main.loggedInUser = null;
					break;
			}
		} while(mainMenuIdx != 4);
	}
	
	public void transactionHistoryPage() {
		// TODO Auto-generated method stub
		
	}

	public void checkOutPage() {
		// TODO Auto-generated method stub
		
	}

	public void orderMenuPage() {
		// TODO Auto-generated method stub
		
	}
}
