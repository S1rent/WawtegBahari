package models;

import java.util.ArrayList;
import java.util.UUID;

import database.CouponRepository;

public class Transaction {
	
	private String transactionID, userID, date, paymentMethod;
	private Coupon usedCoupon;
	private ArrayList<TransactionDetail> details;

	public Transaction(String userID, String date, ArrayList<TransactionDetail> details, String paymentMethod, Coupon usedCoupon) {
		super();
		this.transactionID = UUID.randomUUID().toString();
		this.userID = userID;
		this.date = date;
		this.details = details;
		this.usedCoupon = usedCoupon;
		this.paymentMethod = paymentMethod;
		
		if(usedCoupon != null) {
			CouponRepository.sharedInstance().deactivateCoupon(usedCoupon.getCouponID());
		}
	}

	public String getTransactionID() {
		return transactionID;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public ArrayList<TransactionDetail> getDetails() {
		return details;
	}
	
	public void setDetails(ArrayList<TransactionDetail> details) {
		this.details = details;
	}
	
	public void addDetails(TransactionDetail detail) {
		this.details.add(detail);
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Coupon getUsedCoupon() {
		return usedCoupon;
	}

	public void setUsedCoupon(Coupon usedCoupon) {
		this.usedCoupon = usedCoupon;
	}
}
