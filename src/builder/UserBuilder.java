package builder;

import models.User;

public class UserBuilder {
	
	private User user;

	public UserBuilder() {
		user = new User();
	}
	
	public UserBuilder setRole(int role) {
		this.user.setRole(role);
		return this;
	}
	
	public UserBuilder setName(String name) {
		this.user.setName(name);
		return this;
	}
	
	public UserBuilder setAddress(String address) {
		this.user.setAddress(address);
		return this;
	}
	
	public UserBuilder setPassword(String password) {
		this.user.setPassword(password);
		return this;
	}
	
	public UserBuilder setPhoneNumber(String phoneNumber) {
		this.user.setPhoneNumber(phoneNumber);
		return this;
	}
	
	public User get() {
		User currentUser = this.user;
		this.user = new User();
		return currentUser;
	}
}