package com.edubook.site.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edubook.common.entity.Account;
import com.edubook.common.entity.Order;
import com.edubook.common.entity.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Integer>{

	@Query("SELECT DISTINCT o FROM Order o JOIN o.orderDetails od JOIN od.book b "
			+ "WHERE o.account.IDtaikhoan = ?2 "
			+ "AND (b.tensach LIKE %?1% OR o.orderStatus LIKE %?1%)")
	public Page<Order> findAll(String keyword, Integer IDtaikhoan, Pageable pageable);
	
	@Query("SELECT o FROM Order o WHERE o.account.IDtaikhoan = ?1")
	public Page<Order> findAll(Integer IDtaikhoan, Pageable pageable);
	
	public Order findByIDorderAndAccount(Integer IDorder, Account account);
	
	@Query("SELECT COUNT(o) FROM Order o WHERE o.IDorder = ?1 AND o.account.IDtaikhoan = ?2"
			+ " AND o.orderStatus = ?3")
	public Long countByOrderAndAccountAndOrderStatus(Integer IDorder, Integer IDtaikhoan, OrderStatus status);
}
