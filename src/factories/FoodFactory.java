package factories;

import models.menu.Food;
import models.menu.Menu;

public class FoodFactory extends MenuFactory {
	@Override
	protected Menu makeMenu(String name, String description, String type, double menuPrice) {
		return new Food(name, description, type, menuPrice);
	}
}
