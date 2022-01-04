package database;

import builder.UserBuilder;
import factories.DrinkFactory;
import factories.FoodFactory;
import models.User;

public class Seeder {

	private Seeder() { }
	private static Seeder shared;
	
	public static Seeder sharedInstance() {
		if(shared == null) {
			synchronized (Seeder.class) {
				if(shared == null) {
					shared = new Seeder();
				}
			}
		}
		
		return shared;
	}
	
	public void initializeDummyData() {
		// Initialize admin account
		UserBuilder builder = new UserBuilder();
		User admin = builder
				.setRole(1)
				.setPhoneNumber("2")
				.setPassword("2")
				.setName("Admin")
				.setAddress("Wawteg Bahari")
				.get();
		User user = builder
				.setRole(0)
				.setPhoneNumber("1")
				.setPassword("1")
				.setName("1")
				.setAddress("Wawteg Bahari")
				.get();
		UserRepository.sharedInstance().addUser(admin);
		UserRepository.sharedInstance().addUser(user);
		
		// Initialize menu
		FoodFactory foodFactory = new FoodFactory();
		DrinkFactory drinkFactory = new DrinkFactory();
		
		MenuRepository.sharedInstance().addMenu(foodFactory.make("Martabak", "Martabak dari bangka", "Food", 45000));
		MenuRepository.sharedInstance().addMenu(drinkFactory.make("Green Tea", "Teh hijau dari daun suji", "Drinks", 15000));
	}

}
