package com.edubook.site.address;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.edubook.common.entity.Account;
import com.edubook.common.entity.Address;

public interface AddressRepository extends CrudRepository<Address, Integer>{
	
	public List<Address> findByAccount(Account account);
	
	@Query("SELECT a FROM Address a WHERE a.IDdiachi = ?1 AND a.account.IDtaikhoan = ?2")
	public Address findByIDAndAccount(Integer IDdiachi, Integer IDtaikhoan);
	
	@Query("DELETE FROM Address a WHERE a.IDdiachi = ?1 AND a.account.IDtaikhoan = ?2")
	@Modifying
	public void deleteByIDAndAccount(Integer IDdiachi, Integer IDtaikhoan);
	
	@Query("UPDATE Address a SET a.defaultAddress = true WHERE a.IDdiachi = ?1")
	@Modifying
	public void setDefaultAddress(Integer IDdiachi);
	
	@Query ("UPDATE Address a SET a.defaultAddress = false WHERE a.IDdiachi != ?1 AND a.account.IDtaikhoan = ?2")
	@Modifying
	public void setNonDefaultForOthers(Integer defaultAddressID, Integer IDtaikhoan);
	
	@Query("SELECT a FROM Address a WHERE a.account.IDtaikhoan = ?1 AND a.defaultAddress = true")
	public Address findDefaultByAccount(Integer IDtaikhoan);
}
