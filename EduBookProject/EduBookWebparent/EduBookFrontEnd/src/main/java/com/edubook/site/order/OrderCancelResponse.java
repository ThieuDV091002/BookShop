package com.edubook.site.order;

public class OrderCancelResponse {

	private Integer IDorder;
	
	public OrderCancelResponse() {
	}

	public OrderCancelResponse(Integer IDorder) {
		this.IDorder = IDorder;
	}

	public Integer getIDorder() {
		return IDorder;
	}

	public void setIDorder(Integer IDorder) {
		this.IDorder = IDorder;
	}
	
}
