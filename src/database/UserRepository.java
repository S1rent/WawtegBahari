package database;

import java.util.ArrayList;

import models.User;

public class UserRepository {
	private ArrayList<User> userList = new ArrayList<User>();
	
	private UserRepository() { }
	private static UserRepository shared;
	
	public static UserRepository sharedInstance() {
		if(shared == null) {
			synchronized (UserRepository.class) {
				if(shared == null) {
					shared = new UserRepository();
				}
			}
		}
		
		return shared;
	}

	public ArrayList<User> getUserList() {
		return userList;
	}
	
	public User find(String userID) {
		for (User user : userList) {
			if(user.getUserID().equals(userID)) {
				return user;
			}
		}
		return null;
	}

	public void addUser(User user) {
		this.userList.add(user);
	}
}
