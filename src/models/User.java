package models;

import java.util.UUID;

public class User {
	
	// role = 0 -> customer
	// role = 1 -> admin
	private int role;
	private String userID, name, address, password, phoneNumber;
	
	public User() {
		this.userID = UUID.randomUUID().toString();
	}

	public String getUserID() {
		return userID;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
