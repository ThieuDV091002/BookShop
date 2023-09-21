package com.edubook.site.order;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.edubook.common.entity.Account;
import com.edubook.common.entity.Book;
import com.edubook.common.entity.Order;
import com.edubook.common.entity.OrderDetail;
import com.edubook.site.customer.CustomerService;
import com.edubook.site.request.RequestService;
import com.edubook.site.review.ReviewService;

@Controller
public class OrderController {

	@Autowired
	private OrderService service;
	@Autowired
	private CustomerService customerService;
	@Autowired 
	private ReviewService reviewService;
	@Autowired 
	private RequestService requestService;
	
	@GetMapping("/order")
	public String listFirstPage(Model model, HttpServletRequest request) {
		return listOrdersByPage(model, request, 1, null);
	}
	
	@GetMapping("/order-search")
	public String listOrderPage(Model model, HttpServletRequest request) {
		return listOrdersByPage(model, request, 1, null);
	}
	
	@GetMapping("/order-page-{pageNum}")
	public String listOrdersByPage(Model model, HttpServletRequest request,
						@PathVariable(name = "pageNum") int pageNum,String keyword) {
		Account account = getAuthenticatedAccount(request);
		
		Page<Order> page = service.listByPage(pageNum, keyword, account);
		List<Order> listOrders = page.getContent();
		
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("listOrders", listOrders);
		model.addAttribute("keyword", keyword);
		
		long startCount = (pageNum - 1) * OrderService.ORDER_PER_PAGE + 1;
		
		long endCount = startCount + OrderService.ORDER_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("endCount", endCount);
		model.addAttribute("startCount", startCount);
		
		setOrderRequestableStatus(account, listOrders);
		
		return "order/order";		
	}
	
	private void setOrderRequestableStatus(Account account, List<Order> listOrders) {
		Iterator<Order> iterator = listOrders.iterator();
		while(iterator.hasNext()) {
			Order order = iterator.next();
			boolean didCustomerRequest = requestService.didCustomerRequest(account, order.getIDorder());
			order.setRequestedByCustomer(didCustomerRequest);
			if(!didCustomerRequest) {
				boolean canCustomerRequest = requestService.canCustomerRequest(account, order.getIDorder());
				order.setCustomerCanRequest(canCustomerRequest);
			}
		}
	}

	@GetMapping("/order-detail-{IDorder}")
	public String viewOrderDetails(Model model,
			@PathVariable(name = "IDorder") Integer IDorder, HttpServletRequest request) {
		Account account = getAuthenticatedAccount(request);
		Order order = service.getOrder(IDorder, account);
		
		setBookReviewableStatus(account, order);
		
		model.addAttribute("order", order);
		model.addAttribute("account", account);
		
		return "order/order_detail";
	}
	
	private void setBookReviewableStatus(Account account, Order order) {
		Iterator<OrderDetail> iterator = order.getOrderDetails().iterator();
		while(iterator.hasNext()) {
			OrderDetail orderDetail = iterator.next();
			Book book = orderDetail.getBook();
			Integer IDsach = book.getIDsach();
			
			boolean didCustomerReviewProduct = reviewService.didCustomerReviewBook(account, IDsach);
			book.setReviewedByCustomer(didCustomerReviewProduct);
			
			if (!didCustomerReviewProduct) {
				boolean canCustomerReviewProduct = reviewService.canCustomerReviewBook(account, IDsach);
				book.setCustomerCanReview(canCustomerReviewProduct);
			}	
		}
	}
	
	@GetMapping("/order-cancel-{IDorder}")
	public String viewOrderCancel(Model model,
			@PathVariable(name = "IDorder") Integer IDorder, HttpServletRequest request) {
		Account account = getAuthenticatedAccount(request);
		Order order = service.getOrder(IDorder, account);
		
		model.addAttribute("pageTitle", "Hủy đơn hàng (ID: " + order.getIDorder() + ")");
		model.addAttribute("order", order);
		model.addAttribute("account", account);
		
		return "order/order_cancel";
	}
	
	@PostMapping("/cancel-save")
	public String saveOrder(Order order, HttpServletRequest request) {
		Account account = getAuthenticatedAccount(request);
		Order orderInDB = service.getOrder(order.getIDorder(), account);
		service.updateStatus(orderInDB);
		return "redirect:order";
	}
	
	private Account getAuthenticatedAccount(HttpServletRequest request) {
		String customerName = getUsernameOfAuthenticatedCustomer(request);
		Account account = customerService.getAccountByUsername(customerName);
		String customerEmail = account.getEmail();
		return customerService.getAccountByEmail(customerEmail);
	}
	
	public static String getUsernameOfAuthenticatedCustomer(HttpServletRequest request) {
	    Object principal = request.getUserPrincipal();
	    String customerName = null;
	    if (principal instanceof UsernamePasswordAuthenticationToken) {
	        customerName = request.getUserPrincipal().getName();
	    }
	    return customerName;
	}
}
