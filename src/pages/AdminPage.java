package pages;

import java.util.Scanner;

import database.MenuRepository;
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
			} while(mainMenuIdx < 1 || mainMenuIdx > 5);
			
			Helper.sharedInstance().blankSpace();
			
			switch(mainMenuIdx) {
				case 1:
					addMenuPage();
					break;
				case 2:
					editMenuPage();
					break;
				case 3:
					deleteMenuPage();
					break;
				case 4:
					viewTransactionsPage();
					break;
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
		Helper.sharedInstance().blankSpace();
		if(MenuRepository.sharedInstance().getMenuList().isEmpty()) {
			Helper.sharedInstance().noData();
			return;
		}
	}

	public void editMenuPage() {
		Helper.sharedInstance().blankSpace();
		if(MenuRepository.sharedInstance().getMenuList().isEmpty()) {
			Helper.sharedInstance().noData();
			return;
		}
	}

	public void addMenuPage() {
		Helper.sharedInstance().blankSpace();
	}
}
