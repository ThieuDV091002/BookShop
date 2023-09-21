package com.edubook.site.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edubook.common.entity.OrderDetail;
import com.edubook.common.entity.OrderStatus;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{

	@Query("SELECT COUNT(d) FROM OrderDetail d JOIN OrderTrack t ON d.order.IDorder = t.order.IDorder"
			+ " WHERE d.book.IDsach = ?1 AND d.order.account.IDtaikhoan = ?2 "
			+ " AND t.status = ?3")
	public Long countByBookAndAccountAndOrderStatus(Integer IDsach, Integer IDtaikhoan, OrderStatus status);
}
