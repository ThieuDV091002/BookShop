package com.edubook.admin.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edubook.common.entity.Order;

@RestController
public class OrderRestController {

	@Autowired
	private OrderService service;
	
	@PostMapping("order-update-{IDorder}-{status}")
	public Response updatestatus(@PathVariable("IDorder") Integer IDorder, @PathVariable("status") String status,
			RedirectAttributes ra, Authentication authentication) throws OrderNotFoundException {
		String username = authentication.getName();
		service.updateStatus(IDorder, status, username);
		Order order = service.get(IDorder);
		ra.addFlashAttribute("message", "Trạng thái đơn hàng đã được cập nhật thành công!");
		return new Response(IDorder, status);
	}
	
}

class Response {
	private Integer IDorder;
	private String status;

	public Response(Integer iDorder, String status) {
		this.IDorder = iDorder;
		this.status = status;
	}

	public Integer getIDorder() {
		return IDorder;
	}

	public void setIDorder(Integer iDorder) {
		IDorder = iDorder;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}