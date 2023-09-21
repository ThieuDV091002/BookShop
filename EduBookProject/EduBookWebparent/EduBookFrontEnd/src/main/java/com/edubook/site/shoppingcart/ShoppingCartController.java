package com.edubook.site.shoppingcart;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.edubook.common.entity.Account;
import com.edubook.common.entity.ShoppingCart;
import com.edubook.site.customer.CustomerService;

@Controller
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService service;
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/cart")
	public String viewShoppingCart(Model model, HttpServletRequest request) {
		Account account = getAuthenticatedAccount(request);
		List<ShoppingCart> cartItems = service.listCartItems(account);
		int Total = 0;
		for(ShoppingCart item : cartItems) {
			Total += item.getSubTotal();
		}
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("Total", Total);
		return "cart/shopping_cart";
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
