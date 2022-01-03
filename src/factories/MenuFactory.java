package factories;

import models.menu.Menu;

public abstract class MenuFactory {
	public Menu make(String name, String description, String type, double menuPrice) {
		return makeMenu(name, description, type, menuPrice);
	}
	
	protected abstract Menu makeMenu(String name, String description, String type, double menuPrice);
}