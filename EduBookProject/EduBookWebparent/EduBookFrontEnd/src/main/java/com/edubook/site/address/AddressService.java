package com.edubook.site.address;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edubook.common.entity.Account;
import com.edubook.common.entity.Address;

@Service
@Transactional
public class AddressService {

	@Autowired
	private AddressRepository repo;
	
	public List<Address> listAddressbook(Account account){
		return repo.findByAccount(account);
	}
	
	public void save(Address address) {
		repo.save(address);
	}
	
	public Address get(Integer IDdiachi, Integer IDtaikhoan) {
		return repo.findByIDAndAccount(IDdiachi, IDtaikhoan);
	}
	
	public void delete(Integer IDdiachi, Integer IDtaikhoan) {
		repo.deleteByIDAndAccount(IDdiachi, IDtaikhoan);
	}
	
	public void setDefaultAddress(Integer defaultAddressID, Integer Idtaikhoan) {
		if(defaultAddressID > 0) {
			repo.setDefaultAddress(defaultAddressID);
		}
		repo.setNonDefaultForOthers(defaultAddressID, Idtaikhoan);
	}

	public Address getDefaultAddress(Account account) {
		return repo.findDefaultByAccount(account.getIDtaikhoan());
	}
}
