package pages;

import java.util.Scanner;

import helper.Helper;
import main.Main;

public class AdminPage {
	
	private Scanner scan = new Scanner(System.in);

	private AdminPage() { }
	private static AdminPage shared;
	
	public static AdminPage sharedInstance() {
		if(shared == null) {
			synchronized (AdminPage.class) {
				if(shared == null) {
					shared = new AdminPage();
				}
			}
		}
		
		return shared;
	}
	
	public void navigateToAdminMainMenu() {
		int mainMenuIdx = -1;
		
		do {
			Helper.sharedInstance().printAdminMainMenu();
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
					AdminPage.sharedInstance().addMenuPage();
					break;
				case 2:
					AdminPage.sharedInstance().editMenuPage();
					break;
				case 3:
					AdminPage.sharedInstance().deleteMenuPage();
					break;
				case 4:
					AdminPage.sharedInstance().viewTransactionsPage();
				case 5:
					Main.loggedInUser = null;
					break;
			}
		} while(mainMenuIdx != 5);
	}
	
	public void viewTransactionsPage() {
		// TODO Auto-generated method stub
		
	}

	public void deleteMenuPage() {
		// TODO Auto-generated method stub
		
	}

	public void editMenuPage() {
		// TODO Auto-generated method stub
		
	}

	public void addMenuPage() {
		// TODO Auto-generated method stub
		
	}
}
