package com.edubook.site.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.edubook.common.entity.Account;
import com.edubook.site.customer.CustomerRepository;

public class CustomerUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String tendangnhap) throws UsernameNotFoundException {
		Account account = repo.getAccountByTendangnhap(tendangnhap);
		if(account == null) {
			throw new UsernameNotFoundException("Không tìm thấy tài khoản với tên đăng nhập" + tendangnhap);
		}
		return new CustomerUserDetails(account);
	}

}
