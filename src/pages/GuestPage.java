package pages;

import java.util.Scanner;

import builder.UserBuilder;
import builder.UserDirector;
import database.UserRepository;
import helper.Helper;
import main.Main;
import models.User;

public class GuestPage {

	private Scanner scan = new Scanner(System.in);

	private GuestPage() { }
	private static GuestPage shared;
	
	public static GuestPage sharedInstance() {
		if(shared == null) {
			synchronized (GuestPage.class) {
				if(shared == null) {
					shared = new GuestPage();
				}
			}
		}
		
		return shared;
	}
	
	public void loginPage() {
		String phoneNumber = "", password = "";

		Helper.sharedInstance().blankSpace();
		System.out.println("Login");
		System.out.println("=======================");
		System.out.println("Type exit to cancel login");
		
		while((!phoneNumber.equals("exit") && !password.equals("exit")) && Main.loggedInUser == null) {
			System.out.print("Input your registered phone number: ");
			phoneNumber = scan.nextLine();
			
			if(phoneNumber.equals("exit")) {
				break;
			}
			
			System.out.print("Input your password: ");
			password = scan.nextLine();
			for (User user: UserRepository.sharedInstance().getUserList()) {
				if(user.getPhoneNumber().equals(phoneNumber) && user.getPassword().equals(password)) {
					Main.loggedInUser = user;
				}
			}
			
			if(!phoneNumber.equals("exit") && !password.equals("exit") && Main.loggedInUser == null) {
				System.out.println("Invalid credentials.\n");
			}
		}
		
		if(Main.loggedInUser != null) {
			if(Main.loggedInUser.getRole() == 1) {
				AdminPage.sharedInstance().navigateToAdminMainMenu();
			} else {
				CustomerPage.sharedInstance().navigateToCustomerMainMenu();
			}
		}
	}
	
	public void registerPage() {
		String name = "", address = "", password = "", phoneNumber = "";
		
		Helper.sharedInstance().blankSpace();
		System.out.println("Register");
		System.out.println("=======================");
		
		boolean isPhoneNumberExist = false;
		boolean isPhoneNumberValid = true;
		
		do {
			isPhoneNumberExist = false;
			isPhoneNumberValid = true;
			
			System.out.print("Hi there, please input your phone number [Minimum 8 characters]: ");
			phoneNumber = scan.nextLine();
			
			for (User user: UserRepository.sharedInstance().getUserList()) {
				if(user.getPhoneNumber().equals(phoneNumber)) {
					isPhoneNumberExist = true;
					System.out.println("The phone number is already taken, please try the other phone number.");
				}
			}
			
			if(!isPhoneNumberExist) {
				for (int i = 0; i < phoneNumber.length(); i++) {
					if(phoneNumber.charAt(i) < '0' || phoneNumber.charAt(i) > '9') {
						System.out.println("Please input a valid phone number.");
						isPhoneNumberValid = false;
						break;
					}
				}
			}
		} while (phoneNumber.equals("") || phoneNumber.length() < 8 || isPhoneNumberExist || !isPhoneNumberValid);
		
		do {
			System.out.print("Please create a password for your account [Minimum 5 characters]: ");
			password = scan.nextLine();
		} while (password.equals("") || password.length() < 5);

		boolean isNameValid = true;
		do {
			isNameValid = true;
			System.out.print("Please fill your name [Minimum 3 characters]: ");
			name = scan.nextLine();
			for (int i = 0; i < name.length(); i++) {
				if(!(name.charAt(i) >= 'a' && name.charAt(i) <= 'z') && !(name.charAt(i) >= 'A' && name.charAt(i) <= 'Z') && name.charAt(i) != ' ') {
					System.out.println("Please fill your name correctly.");
					isNameValid = false;
					break;
				}
			}
		} while (name.equals("") || name.length() < 3 || !isNameValid);
		
		do {
			System.out.print("Please fill up your address [Minimum 10 characters]: ");
			address = scan.nextLine();
		} while (address.equals("") || address.length() < 10);
		
		UserBuilder builder = new UserBuilder();
		UserDirector director = new UserDirector(builder);
		director.make();
		
		User newUser = builder
						.setRole(0)
						.setPhoneNumber(phoneNumber)
						.setPassword(password)
						.setName(name)
						.setAddress(address)
						.get();
		
		UserRepository.sharedInstance().addUser(newUser);
		
		System.out.println("\nSuccessfully created your account, now please login to your account.");
		System.out.println("Press enter to continue...");
		
		scan.nextLine();
	}

	public void viewMenu() {
		Helper.sharedInstance().blankSpace();
		System.out.println("View Menu");
	}
}
