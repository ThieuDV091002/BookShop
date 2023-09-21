package com.edubook.common.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "order_track")
public class OrderTrack extends IdBasedEntity{

	@Column(length = 256)
	private String notes;
	
	private Date updatedTime;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 48, nullable = false)
	private OrderStatus status;

	@ManyToOne
	@JoinColumn(name = "IDorder")
	private Order order;
	@Column(nullable=true, length=128)
	private String shipper;
	
	public String getShipper() {
		return shipper;
	}
	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	@Transient
	public String getUpdatedTimeOnForm() {
		DateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd 'T' hh:mm:ss", new Locale("vi", "VN"));
		return dateFormatter.format(this.updatedTime);
	}
	
	public void setUpdatedTimeOnForm (String dateString) {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd 'T' hh:mm:ss", new Locale("vi", "VN"));
		try {
			this.updatedTime = dateFormatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Transient
	public boolean isPicked() {
		return status == OrderStatus.PICKED;
	}

}
