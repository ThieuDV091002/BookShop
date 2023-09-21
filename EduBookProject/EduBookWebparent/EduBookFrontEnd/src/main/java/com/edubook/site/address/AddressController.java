package com.edubook.site.address;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edubook.common.entity.Account;
import com.edubook.common.entity.Address;
import com.edubook.site.customer.CustomerService;

@Controller
public class AddressController {

	@Autowired
	private AddressService service;
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/address_book")
	public String showAddressBook(Model model, HttpServletRequest request) {
		Account account = getAuthenticatedAccount(request);
		List<Address> listAddresses = service.listAddressbook(account);
		
		boolean usePrimaryAddressAsDefault = true;
		for (Address address : listAddresses) {
			if (address.isDefaultAddress()) {
				usePrimaryAddressAsDefault = false;
				break;
			}
		}
		
		model.addAttribute("usePrimaryAddressAsDefault", usePrimaryAddressAsDefault);
		model.addAttribute("listAddresses", listAddresses);
		model.addAttribute("account", account);
		return "address/address";
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
	
	@GetMapping("/address-new")
	public String newAddress(Model model) {
		Address address = new Address();
		model.addAttribute("address", address);
		model.addAttribute("pageTitle", "Thêm địa chỉ mới");
		return "address/address_form";
	}
	
	@PostMapping("/address-save")
	public String saveAddress(Address address, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Account account = getAuthenticatedAccount(request);
		address.setAccount(account);
		service.save(address);
		return "redirect:/address_book";
	}
	
	@GetMapping("/address-edit-{IDdiachi}")
	public String editAddress(@PathVariable("IDdiachi") Integer IDdiachi, Model model, 
			HttpServletRequest request) {
		Account account = getAuthenticatedAccount(request);
		Address address = service.get(IDdiachi, account.getIDtaikhoan());
		model.addAttribute("address", address);
		model.addAttribute("pageTitle", "Sửa thông tin địa chỉ(ID: " + IDdiachi + ")");
		return "address/address_form";
	}
	
	@GetMapping("/address-delete-{IDdiachi}")
	public String deleteAddress(@PathVariable("IDdiachi") Integer IDdiachi, HttpServletRequest request) {
		Account account = getAuthenticatedAccount(request);
		service.delete(IDdiachi, account.getIDtaikhoan());
		return "redirect:/address_book";
	}
	
	@GetMapping("/address-default-{IDdiachi}")
	public String setDefaultAddress(@PathVariable("IDdiachi") Integer IDdiachi,
	HttpServletRequest request) {
		Account account = getAuthenticatedAccount(request);
		service.setDefaultAddress (IDdiachi, account.getIDtaikhoan());
		return "redirect:/address_book";
	}
	
}
