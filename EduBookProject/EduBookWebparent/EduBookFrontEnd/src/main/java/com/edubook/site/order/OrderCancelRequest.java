package com.edubook.site.order;

public class OrderCancelRequest {

	private Integer IDorder;
	private String reason;
	private String note;

	public Integer getIDorder() {
		return IDorder;
	}

	public void setIDorder(Integer IDorder) {
		this.IDorder = IDorder;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public OrderCancelRequest(Integer IDorder, String reason, String note) {
		this.IDorder = IDorder;
		this.reason = reason;
		this.note = note;
	}

	public OrderCancelRequest() {
	}
	
}
