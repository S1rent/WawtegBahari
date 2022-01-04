package models;

import java.util.UUID;

import models.currencies.IDRCurrency;

public class Coupon implements IDRCurrency {

	private String couponID, couponCode, userID;
	private double discount;
	private boolean isActive;
	
	public Coupon(String couponCode, double discount, String userID) {
		super();
		this.couponID = UUID.randomUUID().toString();
		this.couponCode = couponCode;
		this.discount = discount;
		this.isActive = true;
		this.userID = userID;
	}

	public String getCouponID() {
		return couponID;
	}
	
	public String getCouponCode() {
		return couponCode;
	}
	
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	
	public double getDiscount() {
		return discount;
	}
	
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void deactivate() {
		this.isActive = false;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	@Override
	public double getPrice() {
		return getDiscount();
	}
}
