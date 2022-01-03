package helper;

import java.util.Scanner;

import models.User;

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
