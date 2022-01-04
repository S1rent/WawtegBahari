package pages;

import java.util.ArrayList;
import java.util.Scanner;

import database.MenuRepository;
import factories.DrinkFactory;
import factories.FoodFactory;
import helper.Helper;
import main.Main;
import models.menu.Drink;
import models.menu.Food;
import models.menu.Menu;

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
		
		Helper.sharedInstance().printMenuList();
		System.out.println("Input 0 if you wish to cancel");
		ArrayList<Menu> menuList = MenuRepository.sharedInstance().getMenuList();
		int menuIdx = -1;
		do {
			try {
				System.out.printf("Input menu you want to delete [0 - %d]: ", menuList.size());
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
		MenuRepository.sharedInstance().removeMenu(selectedMenu);
		
		System.out.printf("\nSuccessfully delete %s.\n", selectedMenu.getName());
		System.out.print("Press enter to continue...");
		scan.nextLine();
	}

	public void editMenuPage() {
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
				System.out.printf("Input menu you want to edit [0 - %d]: ", menuList.size());
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
		double price = selectedMenu.getPrice();
		String name = selectedMenu.getName(), description = selectedMenu.getDescription(), type = selectedMenu.getType();
		System.out.println("Leave field in a blank if you wish to not edit a field.");
		do {
			System.out.print("Please input the menu name [Minimum 3 characters]: ");
			name = scan.nextLine();
			if(name.equals("")) {
				name = selectedMenu.getName();
				break;
			}
		} while (name.length() < 3);
		
		do {
			System.out.print("Please input the menu description [Minimum 7 characters]: ");
			description = scan.nextLine();
			if(description.equals("")) {
				description = selectedMenu.getDescription();
				break;
			}
		} while (description.length() < 7);
		
		do {
			System.out.print("Please input the menu type [Food | Drinks]: ");
			type = scan.nextLine();
			
			if(type.equals("")) {
				type = selectedMenu.getType();
				break;
			}
		} while (!type.equals("Food") && !type.equals("Drinks"));

		System.out.println("Input 0 if you wish to not edit the price.");
		do {
			try {
				System.out.print("Please input the menu price in IDR [Minimum 500]: ");
				price = scan.nextDouble();
			} catch (Exception e) {
				price = -1;
			}
			scan.nextLine();
			if(price == 0) {
				price = selectedMenu.getPrice();
				break;
			}
		} while (price < 500);
		
		Menu newMenu = null;
		if(selectedMenu instanceof Food) {
			newMenu = (Food) selectedMenu;
		} else {
			newMenu = (Drink) selectedMenu;
		}
		newMenu.setName(name);
		newMenu.setDescription(description);
		newMenu.setType(type);
		newMenu.setMenuPrice(price);
		
		System.out.printf("\nSuccessfully edit %s.\n", selectedMenu.getName());
		MenuRepository.sharedInstance().editMenu(newMenu, selectedMenu.getMenuID());
		System.out.print("Press enter to continue...");
		scan.nextLine();
	}

	public void addMenuPage() {
		String name = "", description = "", type = "";
		double price = -1;
		Helper.sharedInstance().blankSpace();
		do {
			System.out.print("Please input the menu name [Minimum 3 characters]: ");
			name = scan.nextLine();
		} while (name.length() < 3);
		
		do {
			System.out.print("Please input the menu description [Minimum 7 characters]: ");
			description = scan.nextLine();
		} while (description.length() < 7);
		
		do {
			System.out.print("Please input the menu type [Food | Drinks]: ");
			type = scan.nextLine();
		} while (!type.equals("Food") && !type.equals("Drinks"));
		
		do {
			try {
				System.out.print("Please input the menu price in IDR [Minimum 500]: ");
				price = scan.nextDouble();
			} catch (Exception e) {
				price = -1;
			}
			scan.nextLine();
		} while (price < 500);
		
		FoodFactory foodFactory = new FoodFactory();
		DrinkFactory drinkFactory = new DrinkFactory();
		Menu menu = null;
		
		if(type.equals("Food")) {
			menu = foodFactory.make(name, description, type, price);
		} else {
			menu = drinkFactory.make(name, description, type, price);
		}
		
		MenuRepository.sharedInstance().addMenu(menu);
		
		System.out.println("\nSuccessfully added new menu.");
		System.out.print("Press enter to continue...");
		scan.nextLine();
	}
}
