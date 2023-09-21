package com.edubook.site.checkout;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class CheckoutInfo {

	private int bookTotal;
	private int paymentTotal;
	private Date deliverDate;
	
	public int getBookTotal() {
		return bookTotal;
	}
	public void setBookTotal(int bookTotal) {
		this.bookTotal = bookTotal;
	}
	public int getPaymentTotal() {
		return paymentTotal;
	}
	public void setPaymentTotal(int paymentTotal) {
		this.paymentTotal = paymentTotal;
	}
	public Date getDeliverDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 3);
		return calendar.getTime();
	}
	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}
	
	/*public String getPaymentTotal4PayPal() {
		DecimalFormat formatter = new DecimalFormat("###,###,###");
		return formatter.format(paymentTotal);
	}*/
	
}
