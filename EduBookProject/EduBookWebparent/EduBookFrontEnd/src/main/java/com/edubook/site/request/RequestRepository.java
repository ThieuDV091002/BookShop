package com.edubook.site.request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edubook.common.entity.Order;
import com.edubook.common.entity.Request;

public interface RequestRepository extends JpaRepository<Request, Integer>{

	@Query("SELECT r FROM Request r WHERE r.account.IDtaikhoan = ?1")
	public Page<Request> findByCustomer(Integer IDtaikhoan, Pageable pageable);
	
	@Query("SELECT r FROM Request r WHERE r.account.IDtaikhoan = ?1 AND ("
			+ "r.reason LIKE %?2% OR "
			+ "r.order.IDorder LIKE %?2%)")
	public Page<Request> findByCustomer(Integer IDtaikhoan, String keyword, Pageable pageable);
	
	@Query("SELECT r FROM Request r WHERE r.account.IDtaikhoan = ?1 AND r.IDrequest = ?2")
	public Request findByCustomerAndIDrequest(Integer IDtaikhoan, Integer IDreview);
	
	public Page<Request> findByOrder(Order order, Pageable pageable);
	
	@Query("SELECT COUNT(r.IDrequest) FROM Request r WHERE r.account.IDtaikhoan = ?1 AND "
			+ "r.order.IDorder = ?2")
	public Long countByCustomerAndOrder(Integer IDtaikhoan, Integer IDorder);
}
