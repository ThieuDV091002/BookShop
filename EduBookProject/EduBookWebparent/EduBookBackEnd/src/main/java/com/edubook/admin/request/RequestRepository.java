package com.edubook.admin.request;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edubook.common.entity.Request;

public interface RequestRepository extends JpaRepository<Request, Integer>{

	@Query("SELECT r FROM Request r WHERE r.account.hoten LIKE %?1% OR"
			+ " r.requestStatus LIKE %?1% OR r.order.IDorder LIKE %?1% OR"
			+ " r.reason LIKE %?1%")
	public Page<Request> findAll(String keyword, Pageable pageable);
	
	public List<Request> findAll();
}
