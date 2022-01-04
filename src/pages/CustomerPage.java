package pages;

import java.util.Date;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import database.CouponRepository;
import database.MenuRepository;
import database.ShoppingCartRepository;
import database.TransactionRepository;
import helper.Helper;
import main.Main;
import models.Coupon;
import models.ShoppingCart;
import models.ShoppingCartDetail;
import models.Transaction;
import models.TransactionDetail;
import models.menu.Menu;

public class CustomerPage {

	private Scanner scan = new Scanner(System.in);

	private CustomerPage() {
	}

	private static CustomerPage shared;

	public static CustomerPage sharedInstance() {
		if (shared == null) {
			synchronized (CustomerPage.class) {
				if (shared == null) {
					shared = new CustomerPage();
				}
			}
		}

		return shared;
	}

	public void navigateToCustomerMainMenu() {
		int mainMenuIdx = -1;

		do {
			Helper.sharedInstance().printCustomerMainMenu(Main.loggedInUser);
			do {
				System.out.print(">> ");
				try {
					mainMenuIdx = scan.nextInt();
				} catch (Exception e) {
					mainMenuIdx = -1;
				}
				scan.nextLine();
			} while (mainMenuIdx < 1 || mainMenuIdx > 4);

			Helper.sharedInstance().blankSpace();

			switch (mainMenuIdx) {
			case 1:
				orderMenuPage();
				break;
			case 2:
				shoppingCartPage();
				break;
			case 3:
				transactionHistoryPage();
				break;
			case 4:
				Main.loggedInUser = null;
				break;
			}
		} while (mainMenuIdx != 4);
	}

	public void transactionHistoryPage() {
		Helper.sharedInstance().blankSpace();

		if (TransactionRepository.sharedInstance().getTransactionList(Main.loggedInUser.getUserID()).isEmpty()) {
			Helper.sharedInstance().noData();
			return;
		}
		
		System.out.println("Your Transaction History\n");
		Helper.sharedInstance().printTransactions(TransactionRepository.sharedInstance().getTransactionList(Main.loggedInUser.getUserID()), false);
		
		System.out.print("\nPress enter to continue...");
		scan.nextLine();
		
		Helper.sharedInstance().blankSpace();
	}

	public void shoppingCartPage() {
		Helper.sharedInstance().blankSpace();

		int menuIdx = -1;
		do {
			ArrayList<ShoppingCart> userShoppingCarts = ShoppingCartRepository.sharedInstance()
					.getShoppingCartList(Main.loggedInUser.getUserID());
			if (userShoppingCarts.isEmpty() || userShoppingCarts.get(0).getDetails().isEmpty()) {
				Helper.sharedInstance().noData();
				return;
			}
			Helper.sharedInstance().printShoppingCartList(Main.loggedInUser.getUserID());
			Helper.sharedInstance().printShoppingCartPage();
			do {
				System.out.print(">> ");
				try {
					menuIdx = scan.nextInt();
				} catch (Exception e) {
					menuIdx = -1;
				}
				scan.nextLine();
			} while (menuIdx < 1 || menuIdx > 3);
			Helper.sharedInstance().blankSpace();

			switch (menuIdx) {
			case 1:
				removeCartItemPage();
				break;
			case 2:
				checkoutPage();
				break;
			case 3:
				break;
			}
		} while (menuIdx != 3);
	}

	public void orderMenuPage() {
		Helper.sharedInstance().blankSpace();
		if (MenuRepository.sharedInstance().getMenuList().isEmpty()) {
			Helper.sharedInstance().noData();
			return;
		}

		Helper.sharedInstance().printMenuList();
		System.out.println("Input 0 if you wish to cancel");
		ArrayList<Menu> menuList = MenuRepository.sharedInstance().getMenuList();
		int menuIdx = -1;
		do {
			try {
				System.out.printf("Input menu you want to order [0 - %d]: ", menuList.size());
				menuIdx = scan.nextInt();
			} catch (Exception e) {
				menuIdx = -1;
			}
			scan.nextLine();
		} while (menuIdx < 0 || menuIdx > menuList.size());

		if (menuIdx == 0) {
			return;
		}

		Menu selectedMenu = menuList.get(menuIdx - 1);

		int quantity = -1;

		do {
			System.out.print("Please input order quantity [Minimum 1]: ");
			try {
				quantity = scan.nextInt();
			} catch (Exception e) {
				quantity = -1;
			}
			scan.nextLine();
		} while (quantity < 1);

		// Cek dulu user ini masih punya active shopping cart ato nggak
		// kalo gaada, bikin active shopping cart baru
		// kalo ada, pakai yang sudah ada

		if (ShoppingCartRepository.sharedInstance().getShoppingCartList(Main.loggedInUser.getUserID()).isEmpty()) {
			// Bikin baru
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			String transactionDate = formatter.format(date);

			ShoppingCart userCart = new ShoppingCart(Main.loggedInUser.getUserID(), transactionDate,
					new ArrayList<ShoppingCartDetail>());
			ShoppingCartDetail cartDetail = new ShoppingCartDetail(selectedMenu, quantity, userCart.getCartID());
			userCart.addDetails(cartDetail);

			ShoppingCartRepository.sharedInstance().addShoppingCart(userCart);
		} else {
			ShoppingCart userCart = ShoppingCartRepository.sharedInstance()
					.getShoppingCartList(Main.loggedInUser.getUserID()).get(0);

			// Cek dulu dia udh pernah order item ini belom
			// kalo belom, bikin cart detail baru
			// kalo udah, modif aja cart detail yang lama
			boolean hasOrderedSameProduct = false;
			for (ShoppingCartDetail dbCartDetail : userCart.getDetails()) {
				if (dbCartDetail.getMenu().getMenuID().equals(selectedMenu.getMenuID())) {
					hasOrderedSameProduct = true;
					break;
				}
			}

			if (hasOrderedSameProduct) {
				ShoppingCartDetail newCartDetail = null;
				int detailIndex = -1;

				for (int i = 0; i < userCart.getDetails().size(); i++) {
					if (userCart.getDetails().get(i).getMenu().getMenuID().equals(selectedMenu.getMenuID())) {
						newCartDetail = userCart.getDetails().get(i);
						detailIndex = i;
						break;
					}
				}
				newCartDetail.setQuantity(newCartDetail.getQuantity() + quantity);

				userCart.getDetails().set(detailIndex, newCartDetail);
			} else {
				ShoppingCartDetail cartDetail = new ShoppingCartDetail(selectedMenu, quantity, userCart.getCartID());
				userCart.addDetails(cartDetail);
			}

			ShoppingCartRepository.sharedInstance().updateShoppingCart(userCart, userCart.getCartID());
		}

		System.out.printf("\nSuccessfully ordered %s.\n", selectedMenu.getName());
		System.out.print("Press enter to continue...");
		scan.nextLine();
	}

	public void removeCartItemPage() {
		Helper.sharedInstance().blankSpace();
		ArrayList<ShoppingCart> userShoppingCarts = ShoppingCartRepository.sharedInstance()
				.getShoppingCartList(Main.loggedInUser.getUserID());
		if (userShoppingCarts.isEmpty() || userShoppingCarts.get(0).getDetails().isEmpty()) {
			Helper.sharedInstance().noData();
			return;
		}
		ShoppingCart activeShoppingCart = userShoppingCarts.get(0);
		Helper.sharedInstance().printShoppingCartList(Main.loggedInUser.getUserID());
		System.out.println("Input 0 if you wish to cancel");

		int idx = -1;
		do {
			try {
				System.out.printf("Input menu you want to remove from your shopping cart [0 - %d]: ",
						activeShoppingCart.getDetails().size());
				idx = scan.nextInt();
			} catch (Exception e) {
				idx = -1;
			}
			scan.nextLine();
		} while (idx < 0 || idx > activeShoppingCart.getDetails().size());

		if (idx == 0) {
			Helper.sharedInstance().blankSpace();
			return;
		}

		ShoppingCartDetail selectedMenu = activeShoppingCart.getDetails().get(idx - 1);

		ArrayList<ShoppingCartDetail> newDetails = new ArrayList<ShoppingCartDetail>();
		for (int i = 0; i < activeShoppingCart.getDetails().size(); i++) {
			if (!activeShoppingCart.getDetails().get(i).getMenu().getMenuID()
					.equals(selectedMenu.getMenu().getMenuID())) {
				newDetails.add(activeShoppingCart.getDetails().get(i));
			}
		}
		activeShoppingCart.setDetails(newDetails);
		ShoppingCartRepository.sharedInstance().updateShoppingCart(activeShoppingCart, activeShoppingCart.getCartID());

		System.out.printf("\nSuccessfully removed %s.\n", selectedMenu.getMenu().getName());
		System.out.print("Press enter to continue...");
		scan.nextLine();
		Helper.sharedInstance().blankSpace();
	}

	public void checkoutPage() {
		String paymentMethod = "", couponCode = "";
		
		Helper.sharedInstance().blankSpace();
		Helper.sharedInstance().printShoppingCartList(Main.loggedInUser.getUserID());
		Helper.sharedInstance().printTotalPayment(Main.loggedInUser.getUserID());
		Helper.sharedInstance().printUserAvailableCoupon(Main.loggedInUser.getUserID());
		
		System.out.println("\nType cancel if you wish to cancel transaction");
		do {
			System.out.print("Select Payment Method [Gopay | OVO | Paypal]: ");
			paymentMethod = scan.nextLine();
			if(paymentMethod.equalsIgnoreCase("cancel")) {
				Helper.sharedInstance().blankSpace();
				return;
			}
		} while (!paymentMethod.equals("Gopay") && !paymentMethod.equals("OVO") && !paymentMethod.equals("Paypal"));
		
		if(!CouponRepository.sharedInstance().getCouponList(Main.loggedInUser.getUserID()).isEmpty()) {
			System.out.println("\nLeave the field empty if you didn't wish to use a coupon");
			do {
				System.out.print("Input Coupon Code: ");
				couponCode = scan.nextLine();
				if(couponCode.equalsIgnoreCase("")) {
					break;
				}
				if(!Helper.sharedInstance().validateCoupon(Main.loggedInUser.getUserID(), couponCode)) {
					System.out.println("Coupon code is invalid");
				}
			} while (!Helper.sharedInstance().validateCoupon(Main.loggedInUser.getUserID(), couponCode));
		}
		
		Helper.sharedInstance().blankSpace();
		
		if(Helper.sharedInstance().validateCoupon(Main.loggedInUser.getUserID(), couponCode)) {
			Coupon usedCoupon = CouponRepository.sharedInstance().getCouponByCode(couponCode);
			Helper.sharedInstance().printTotalPaymentUsingCoupon(Main.loggedInUser.getUserID(), usedCoupon);
		}
		
		System.out.println("Processing your payment");
		System.out.print("[");
		 for(int i = 0; i < 15; i++){   
		    try {
		    	Thread.sleep(150);
		    } catch(InterruptedException e){ 
		    	System.out.println(e);
		    }    
			System.out.printf("==");
		 }    
		System.out.printf("]\n");
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String transactionDate = formatter.format(date);
		Transaction transaction = new Transaction(Main.loggedInUser.getUserID(), transactionDate, new ArrayList<TransactionDetail>(), 
													paymentMethod, CouponRepository.sharedInstance().getCouponByCode(couponCode));
		
		ShoppingCart activeShoppingCart = ShoppingCartRepository.sharedInstance().getShoppingCartList(Main.loggedInUser.getUserID()).get(0);
		Random rand = new Random();
		double discount = (rand.nextInt(10) + 1) * Helper.sharedInstance().calculateTotalPayment(Main.loggedInUser.getUserID()) / 100;
		Coupon coupon = new Coupon(Helper.sharedInstance().generateRandomCoupon(), discount, Main.loggedInUser.getUserID());
		Main.notifReceiver.setCoupon(coupon);
//		CouponRepository.sharedInstance().addCoupon(coupon);
		
		for (ShoppingCartDetail cartDetail : activeShoppingCart.getDetails()) {
			TransactionDetail transactionDetail = new TransactionDetail(cartDetail.getMenu(), cartDetail.getQuantity(), transaction.getTransactionID());
			transaction.addDetails(transactionDetail);
		}
		activeShoppingCart.setCheckedOut(true);
		ShoppingCartRepository.sharedInstance().updateShoppingCart(activeShoppingCart, activeShoppingCart.getCartID());
		TransactionRepository.sharedInstance().addTransaction(transaction);
		
		System.out.println("\n\n\nSuccessfully purchased your items");
		System.out.println("Please wait for our courier to deliver your foods to your registered address :D\n");
		System.out.print("Press enter to continue...");
		scan.nextLine();
		
		Helper.sharedInstance().blankSpace();
	}
}
