package factories;

import models.menu.Drink;
import models.menu.Menu;

public class DrinkFactory extends MenuFactory {
	@Override
	protected Menu makeMenu(String name, String description, String type, double menuPrice) {
		return new Drink(name, description, type, menuPrice);
	}
}
