package com.edubook.admin.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.edubook.common.entity.Order;

public interface OrderRepository extends PagingAndSortingRepository<Order, Integer>{
	
	@Query("SELECT o FROM Order o WHERE o.hoten LIKE %?1% OR o.sodienthoai LIKE %?1% OR o.diachi LIKE %?1% OR o.paymentMethod LIKE %?1% OR o.orderStatus LIKE %?1%")
	public Page<Order> findAll(String keyword, Pageable pageable);
}
