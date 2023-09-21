package com.edubook.site.shoppingcart;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.edubook.common.entity.Account;
import com.edubook.common.entity.Book;
import com.edubook.common.entity.ShoppingCart;

public interface CartRepository extends CrudRepository<ShoppingCart, Integer> {

	public List<ShoppingCart> findByAccount(Account account);
	
	public ShoppingCart findByAccountAndBook(Account account, Book book);
	
	@Modifying
	@Query("UPDATE ShoppingCart s SET s.quantity = ?1 WHERE s.account.IDtaikhoan = ?2 AND s.book.IDsach = ?3")
	public void updateQuantity(Integer quantity, Integer IDtaikhoan, Integer IDsach);
	
	@Modifying
	@Query("DELETE FROM ShoppingCart s WHERE s.account.IDtaikhoan = ?1 AND s.book.IDsach = ?2")
	public void deleteByAccountAndBook(Integer IDtaikhoan, Integer IDsach);
	
	@Query("DELETE ShoppingCart s WHERE s.account.IDtaikhoan = ?1")
	@Modifying
	public void deleteByAccount(Integer IDtaikhoan);
}
