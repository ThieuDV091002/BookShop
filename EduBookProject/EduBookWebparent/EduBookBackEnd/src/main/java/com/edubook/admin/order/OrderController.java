package com.edubook.admin.order;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edubook.common.entity.Order;
import com.edubook.common.entity.OrderStatus;
import com.edubook.common.entity.OrderTrack;

@Controller
public class OrderController {

	@Autowired
	private OrderService service;
	
	@GetMapping("/order")
	public String listFirstPage(Model model) {
		return listByPage(1, model, null);
	}
	
	@GetMapping("/order-page-{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("keyword")String keyword) {
		Page<Order> page = service.listByPage(pageNum, keyword);
		List<Order> listOrders = page.getContent();
		
		long startCount = (pageNum - 1)*OrderService.ORDER_PER_PAGE + 1;
		long endCount = startCount + OrderService.ORDER_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listOrders", listOrders);
		model.addAttribute("keyword", keyword);
		return "orders/order";
	}
	
	@GetMapping("/order-detail-{IDorder}")
	public String viewAccountDetail(@PathVariable(name = "IDorder") Integer IDorder, 
			Model model, 
			RedirectAttributes redirectAttributes) {
		try {
			Order order = service.get(IDorder);
			model.addAttribute("order", order);
			return "orders/order_detail";
		}catch(OrderNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:order";
		}
	}
	
	@GetMapping("/order-edit-{IDorder}")
	public String editOrder(@PathVariable("IDorder") Integer IDorder, Model model, RedirectAttributes ra,
			HttpServletRequest request) {
		try {
			Order order = service.get(IDorder);;
			
			model.addAttribute("pageTitle", "Edit Order (ID: " + IDorder + ")");
			model.addAttribute("order", order);
			
			return "orders/order_form";
			
		} catch (OrderNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:order";
		}
		
	}	
	
	@PostMapping("/order-save")
	public String saveOrder(Order order, HttpServletRequest request, RedirectAttributes ra) {
		updateOrderTracks(order, request);
		service.save(order);
		ra.addFlashAttribute("message", "Đơn hàng (ID: " + order.getIDorder() + ") đã được cập nhật thành công");
		return "redirect:order";
	}

	private void updateOrderTracks(Order order, HttpServletRequest request) {
		String trackStatus = request.getParameter("trackStatus");
		String trackNotes = request.getParameter("trackNotes");
		
		List<OrderTrack> orderTracks = order.getOrderTracks();
		
		int trackSize = orderTracks.size();
		OrderTrack trackRecord = new OrderTrack();
		
		trackRecord.setId(trackSize+1);
		trackRecord.setOrder(order);
		trackRecord.setStatus(OrderStatus.valueOf(trackStatus));
		trackRecord.setNotes(trackNotes);
		trackRecord.setUpdatedTime(new Date());
		
		orderTracks.add(trackRecord);
	}
}
