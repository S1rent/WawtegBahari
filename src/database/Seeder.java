package database;

import builder.UserBuilder;
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
				.setPhoneNumber("087511112222")
				.setPassword("admin")
				.setName("Admin")
				.setAddress("Wawteg Bahari")
				.get();
		UserRepository.sharedInstance().addUser(admin);
	}

}
