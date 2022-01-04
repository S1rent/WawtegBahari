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
		ArrayList<Menu> availableMenu = new ArrayList<Menu>();
		for (Menu menu : menuList) {
			if(!menu.isRemoved()) {
				availableMenu.add(menu);
			}
		}
		return availableMenu;
	}

	public void addMenu(Menu menu) {
		this.menuList.add(menu);
	}
	
	public void removeMenu(Menu targetRemove) {
		for(int i = 0; i < menuList.size(); i++) {
			Menu menu = menuList.get(i);
			if(menu.getMenuID().equals(targetRemove.getMenuID())) {
				menuList.get(i).setRemoved(true);
			}
		}
	}
}
