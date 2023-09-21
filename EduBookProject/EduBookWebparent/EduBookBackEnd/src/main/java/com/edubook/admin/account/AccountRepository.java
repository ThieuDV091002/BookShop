package com.edubook.admin.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edubook.common.entity.Account;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Integer>{

	@Query("SELECT a FROM Account a WHERE a.email = :email")
	public Account getAccountByEmail(@Param("email") String email);
	
	@Query("SELECT a FROM Account a WHERE a.tendangnhap = :tendangnhap")
	public Account getAccountByTendangnhap(@Param("tendangnhap") String tendangnhap);
	
	@Query("SELECT a FROM Account a WHERE a.hoten LIKE %?1% OR a.email LIKE %?1% OR a.vaitro LIKE %?1% OR a.sodienthoai LIKE %?1%")
	public Page<Account> findAll(String keyword, Pageable pageable);
	
	@Query("UPDATE Account a SET a.trangthai = ?2 WHERE a.IDtaikhoan = ?1")
	@Modifying
	public void updateAccountStatus(Integer IDtaikhoan, boolean trangthai);
}
