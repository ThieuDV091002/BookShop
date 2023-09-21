package com.edubook.site.shoppingcart;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edubook.common.entity.Account;
import com.edubook.site.customer.CustomerService;

@RestController
public class ShoppingCartRestController {

	@Autowired 
	private ShoppingCartService cartService;
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/cart-add-{IDsach}")
	public String addBookToCart(@PathVariable("IDsach") Integer IDsach, Integer quantity, 
			HttpServletRequest request) {
		quantity =1;
		Account account = getAuthenticatedAccount(request);
		Integer updatedQuantity = cartService.addBook(IDsach, quantity, account);
		return "Sản phẩm đã được thêm vào giỏ hàng!";
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
	
	@PostMapping("cart-update-{IDsach}-{quantity}")
	public String updateQuanity(@PathVariable("IDsach") Integer IDsach, 
			@PathVariable("quantity") Integer quantity, HttpServletRequest request) {
		Account account = getAuthenticatedAccount(request);
		int subTotal = cartService.updateQuantity(IDsach, quantity, account);
		return String.valueOf(subTotal);
	}
	
	@DeleteMapping("/cart-remove-{IDsach}")
	public String removeBook(@PathVariable("IDsach") Integer IDsach, HttpServletRequest request) {
		Account account = getAuthenticatedAccount(request);
		cartService.removeBook(IDsach, account);
		return "Sách đã được xóa khỏi giỏ hàng của bạn";
	}
	
}
