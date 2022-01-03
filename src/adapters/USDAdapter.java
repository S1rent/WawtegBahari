package adapters;

import models.currencies.IDRCurrency;
import models.currencies.USDCurrency;

public class USDAdapter implements USDCurrency {

	private IDRCurrency idrCurrency;
	
	public USDAdapter(IDRCurrency idrCurrency) {
		super();
		this.idrCurrency = idrCurrency;
	}

	@Override
	public double getPrice() {
		return this.idrCurrency.getPrice() / 14000;
	}

}
