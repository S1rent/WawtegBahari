package observer;

import java.util.ArrayList;

import models.Coupon;

public class NotificationReceiver implements Subject {
	
	private ArrayList<Observer> observers;
	private Coupon coupon;

	public NotificationReceiver() {
		observers = new ArrayList<>();
		coupon = null;
	}

	@Override
	public void subscribe(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void unsubscribe(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifySubscriber() {
		for (Observer observer : observers) {
			observer.generateCoupon(coupon);
		}
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
		notifySubscriber();
	}

}
