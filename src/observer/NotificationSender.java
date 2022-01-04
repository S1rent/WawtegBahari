package observer;

import database.CouponRepository;
import models.Coupon;

public class NotificationSender implements Observer {
	
	private Coupon coupon;

	public NotificationSender() {
		coupon = null;
	}
	
	@Override
	public void generateCoupon(Coupon coupon) {
		this.coupon = coupon;
		generateCouponInDB();
	}
	
	public void generateCouponInDB() {
		CouponRepository.sharedInstance().addCoupon(this.coupon);
	}
}
