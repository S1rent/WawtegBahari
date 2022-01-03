package builder;

public class UserDirector {
	private UserBuilder builder;
	public UserDirector(UserBuilder builder) {
		this.builder = builder;
	}
	
	public void make() {
		this.builder
			.setRole(0)
			.setName("")
			.setAddress("")
			.setPhoneNumber("")
			.setPassword("");
	}
}
