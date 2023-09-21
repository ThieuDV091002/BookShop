package com.edubook.site.checkout;

import java.util.List;

import org.springframework.stereotype.Service;

import com.edubook.common.entity.ShoppingCart;

@Service
public class CheckoutService {

	public CheckoutInfo prepareCheckout(List<ShoppingCart> cartItems) {
		CheckoutInfo checkoutInfo = new CheckoutInfo();
		int bookTotal = calculateBookTotal(cartItems);
		int paymentTotal = bookTotal;
		checkoutInfo.setBookTotal(bookTotal);
		checkoutInfo.setPaymentTotal(paymentTotal);
		return checkoutInfo;
	}
	
	private int calculateBookTotal(List<ShoppingCart> cartItems) {
		int total = 0;
		for(ShoppingCart item : cartItems) {
			total += item.getSubTotal();
		}
		return total;
	}

}
