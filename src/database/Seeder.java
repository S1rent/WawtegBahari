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
				.setPhoneNumber("088811112222")
				.setPassword("admin")
				.setName("Admin")
				.setAddress("Wawteg Bahari")
				.get();
		User user = builder
				.setRole(0)
				.setPhoneNumber("087812344321")
				.setPassword("password")
				.setName("Philip Indra Prayitno")
				.setAddress("Jl. Kelapa Gading")
				.get();
		UserRepository.sharedInstance().addUser(admin);
		UserRepository.sharedInstance().addUser(user);
		
		// Initialize menu
		FoodFactory foodFactory = new FoodFactory();
		DrinkFactory drinkFactory = new DrinkFactory();
		
		MenuRepository.sharedInstance().addMenu(foodFactory.make("Martabak", "Martabak dari bangka", "Food", 45000));
		MenuRepository.sharedInstance().addMenu(drinkFactory.make("Green Tea", "Teh hijau dari daun suji", "Drinks", 15000));
		MenuRepository.sharedInstance().addMenu(drinkFactory.make("Sweet Ice Tea", "Teh yang manis, kayak kamu", "Drinks", 5000));
		MenuRepository.sharedInstance().addMenu(foodFactory.make("Bakwan Jagung", "Cemilan uenak dari jagung", "Food", 2000));
		MenuRepository.sharedInstance().addMenu(foodFactory.make("Tempe Goreng", "Tempe digoreng, bonus sambel", "Food", 1500));
		MenuRepository.sharedInstance().addMenu(foodFactory.make("Mie Goreng", "Mie Goreng aja. Bukan MIEnta kepastian", "Food", 8000));
	}

}
