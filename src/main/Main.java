package main;

import java.util.Scanner;
import database.Seeder;
import helper.Helper;
import models.User;
import pages.GuestPage;

public class Main {

	private Scanner scan = new Scanner(System.in);
	public static User loggedInUser = null;
	
	public Main() {
		Seeder.sharedInstance().initializeDummyData();
		
		int idx = -1;
		
		do {
			Helper.sharedInstance().printFrontPage();
			do {
				System.out.print(">> ");
				try {
					idx = scan.nextInt();
				} catch (Exception e) {
					idx = -1;
				}
				scan.nextLine();
			} while(idx < 1 || idx > 4);
			
			Helper.sharedInstance().blankSpace();
			
			switch(idx) {
				case 1:
					GuestPage.sharedInstance().viewMenu();
					break;
				case 2:
					GuestPage.sharedInstance().loginPage();
					break;
				case 3:
					GuestPage.sharedInstance().registerPage();
					break;
				case 4:
					System.out.println("We hope to see you again!");
					break;
			}
		} while(idx != 4);
	}

	public static void main(String[] args) {
		new Main();
	}
}
