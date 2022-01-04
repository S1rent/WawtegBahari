package database;

import java.util.ArrayList;

import models.ShoppingCart;
import models.Transaction;

public class TransactionRepository {

	private ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

	private TransactionRepository() { }
	private static TransactionRepository shared;
	
	public static TransactionRepository sharedInstance() {
		if(shared == null) {
			synchronized (TransactionRepository.class) {
				if(shared == null) {
					shared = new TransactionRepository();
				}
			}
		}
		
		return shared;
	}
	
	public ArrayList<Transaction> getTransactionList() {
		return this.transactionList;
	}
	
	public ArrayList<Transaction> getTransactionList(String userID) {
		ArrayList<Transaction> userTransactionList = new ArrayList<Transaction>();
		for (Transaction transaction : transactionList) {
			if(transaction.getUserID().equals(userID)) {
				userTransactionList.add(transaction);
			}
		}
		return userTransactionList;
	}
	
	public void addTransaction(Transaction transaction) {
		this.transactionList.add(transaction);
	}

}
