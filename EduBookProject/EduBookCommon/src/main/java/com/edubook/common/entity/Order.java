package com.edubook.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "DonHang")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer IDorder;
	@Column(length = 64, nullable = false)
	private String hoten;
	@Column(length = 12, nullable = false)
	private String sodienthoai;
	@Column(length = 256, nullable = false)
	private String diachi;
	@Column(nullable = true, length = 2048)
	private String lydohuy;

	private Date orderTime;
	private int subtotal;
	private int total;
	private Date deliverDate;
	
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	@ManyToOne
	@JoinColumn(name="IDtaikhoan")
	private Account account;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private Set<OrderDetail> orderDetails = new HashSet<>();
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	@OrderBy("updatedTime ASC")
	private List<OrderTrack> orderTracks = new ArrayList<>();
	
	@Transient private boolean customerCanRequest;
	@Transient private boolean requestedByCustomer;
	
	public Integer getIDorder() {
		return IDorder;
	}
	public void setIDorder(Integer iDorder) {
		IDorder = iDorder;
	}
	public String getHoten() {
		return hoten;
	}
	public void setHoten(String hoten) {
		this.hoten = hoten;
	}
	public String getSodienthoai() {
		return sodienthoai;
	}
	public void setSodienthoai(String sodienthoai) {
		this.sodienthoai = sodienthoai;
	}
	public String getDiachi() {
		return diachi;
	}
	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}
	public String getLydohuy() {
		return lydohuy;
	}
	public void setLydohuy(String lydohuy) {
		this.lydohuy = lydohuy;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public int getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(int subtotal) {
		this.subtotal = subtotal;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public Date getDeliverDate() {
		return deliverDate;
	}
	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Set<OrderDetail> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	public void copyAddressFromAccount() {
		setHoten(account.getHoten());
		setSodienthoai(account.getSodienthoai());
		setDiachi(account.getDiachi());
	}
	
	public void copyAddressFromCustomer() {
		setHoten(account.getHoten());
		setSodienthoai(account.getSodienthoai());
		setDiachi(account.getDiachi());
	}
	
	public void copyShippingAddress(Address address) {
		setHoten(address.getHoten());
		setSodienthoai(address.getSodienthoai());
		setDiachi(address.getDiachi());
	}
	
	public List<OrderTrack> getOrderTracks() {
		return orderTracks;
	}
	
	public void setOrderTracks(List<OrderTrack> orderTracks) {
		this.orderTracks = orderTracks;
	}

	public boolean hasStatus(OrderStatus status) {
		for(OrderTrack oTrack : orderTracks) {
			if(oTrack.getStatus().equals(status))
				return true;
		}
		return false;
	}
	
	@Transient
	public boolean isPicked() {
		return hasStatus(OrderStatus.PICKED);
	}
	
	@Transient
	public boolean isNew() {
		return hasStatus(OrderStatus.NEW);
	}
	
	@Transient
	public boolean isCancelled() {
		return hasStatus(OrderStatus.CANCELLED);
	}
	
	@Transient
	public boolean isDelivered() {
		return hasStatus(OrderStatus.DELIVERED);
	}
	
	@Transient
	public boolean isPackaged() {
		return hasStatus(OrderStatus.PACKAGED);
	}
	
	@Transient
	public boolean isPaid() {
		return hasStatus(OrderStatus.PAID);
	}
	
	@Transient
	public boolean isProcessing() {
		return hasStatus(OrderStatus.PROCESSING);
	}
	
	@Transient
	public boolean isRefunded() {
		return hasStatus(OrderStatus.REFUNDED);
	}
	
	@Transient
	public boolean isReturned() {
		return hasStatus(OrderStatus.RETURNED);
	}
	
	@Transient
	public boolean isShipping() {
		return hasStatus(OrderStatus.SHIPPING);
	}
	
	@Transient
	public boolean isReturnRequested() {
		return hasStatus(OrderStatus.RETURN_REQUESTED);
	}
	
	@Transient
	public String getBookNames() {
		String bookNames = "";
		
		bookNames = "<ul>";
		
		for (OrderDetail detail : orderDetails) {
			bookNames += "<li>" + detail.getBook().getTensach() + "</li>";			
		}
		
		bookNames += "</ul>";
		
		return bookNames;
	}
	public boolean isCustomerCanRequest() {
		return customerCanRequest;
	}
	public void setCustomerCanRequest(boolean customerCanRequest) {
		this.customerCanRequest = customerCanRequest;
	}
	public boolean isRequestedByCustomer() {
		return requestedByCustomer;
	}
	public void setRequestedByCustomer(boolean requestedByCustomer) {
		this.requestedByCustomer = requestedByCustomer;
	}
	
}
