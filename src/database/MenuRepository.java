package database;

import java.util.ArrayList;
import models.menu.*;;

public class MenuRepository {

private ArrayList<Menu> menuList = new ArrayList<Menu>();
	
	private MenuRepository() { }
	private static MenuRepository shared;
	
	public static MenuRepository sharedInstance() {
		if(shared == null) {
			synchronized (MenuRepository.class) {
				if(shared == null) {
					shared = new MenuRepository();
				}
			}
		}
		
		return shared;
	}

	public ArrayList<Menu> getMenuList() {
		return menuList;
	}

	public void addMenu(Menu menu) {
		this.menuList.add(menu);
	}

}
