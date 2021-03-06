package models.menu;

import java.util.UUID;

import models.currencies.IDRCurrency;

public abstract class Menu implements IDRCurrency {

	private String menuID, name, description, type;
	private double menuPrice;
	private boolean isRemoved;
	
	public Menu(String name, String description, String type, double menuPrice) {
		super();
		this.menuID = UUID.randomUUID().toString();
		this.isRemoved = false;
		this.name = name;
		this.description = description;
		this.type = type;
		this.menuPrice = menuPrice;
	}
	
	public String getMenuID() {
		return menuID;
	}
	
	public boolean isRemoved() {
		return isRemoved;
	}

	public void setRemoved(boolean isRemoved) {
		this.isRemoved = isRemoved;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getMenuPrice() {
		return menuPrice;
	}

	public void setMenuPrice(double menuPrice) {
		this.menuPrice = menuPrice;
	}

	@Override
	public double getPrice() {
		return getMenuPrice();
	}

}
