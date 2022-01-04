package database;

import java.util.ArrayList;

import models.Coupon;

public class CouponRepository {

private ArrayList<Coupon> couponList = new ArrayList<Coupon>();
	
	private CouponRepository() { }
	private static CouponRepository shared;
	
	public static CouponRepository sharedInstance() {
		if(shared == null) {
			synchronized (CouponRepository.class) {
				if(shared == null) {
					shared = new CouponRepository();
				}
			}
		}
		
		return shared;
	}
	
	public ArrayList<Coupon> getCouponList() {
		ArrayList<Coupon> activeCoupons = new ArrayList<>();
		for(int i = 0; i < couponList.size(); i++) {
			if(couponList.get(i).isActive()) {
				activeCoupons.add(couponList.get(i));
			}
		}
		return activeCoupons;
	}
	
	public ArrayList<Coupon> getCouponList(String userID) {
		ArrayList<Coupon> activeCoupons = new ArrayList<>();
		for(int i = 0; i < couponList.size(); i++) {
			if(couponList.get(i).isActive() && couponList.get(i).getUserID().equals(userID)) {
				activeCoupons.add(couponList.get(i));
			}
		}
		
		return activeCoupons;
	}
	
	public Coupon getCouponByCode(String couponCode) {
		for (Coupon coupon : couponList) {
			if(coupon.getCouponCode().equals(couponCode)) {
				return coupon;
			}
		}
		return null;
	}
	
	public void addCoupon(Coupon coupon) {
		this.couponList.add(coupon);
	}
	
	public void deactivateCoupon(String couponID) {
		for(int i = 0; i < couponList.size(); i++) {
			if(couponList.get(i).getCouponID().equals(couponID)) {
				Coupon newCoupon = couponList.get(i);
				newCoupon.deactivate();
				couponList.set(i, newCoupon);
			}
		}
	}

}
