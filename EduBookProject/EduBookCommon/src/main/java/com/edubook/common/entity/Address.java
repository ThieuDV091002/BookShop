package com.edubook.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer IDdiachi;
	@Column(length = 12, nullable = false)
	private String sodienthoai;
	@Column(length = 256, nullable = false)
	private String diachi;
	@Column(length = 64, nullable = false)
	private String hoten;
	
	@ManyToOne
	@JoinColumn(name = "IDtaikhoan")
	private Account account;
	@Column(name = "diachi_macdinh")
	private boolean defaultAddress;
	
	public Integer getIDdiachi() {
		return IDdiachi;
	}
	public void setIDdiachi(Integer iDdiachi) {
		IDdiachi = iDdiachi;
	}
	public String getSodienthoai() {
		return sodienthoai;
	}
	public void setSodienthoai(String sodienthoai) {
		this.sodienthoai = sodienthoai;
	}
	public String getDiachi() {
		return diachi;
	}
	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}
	public String getHoten() {
		return hoten;
	}
	public void setHoten(String hoten) {
		this.hoten = hoten;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public boolean isDefaultAddress() {
		return defaultAddress;
	}
	public void setDefaultAddress(boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}
	@Override
	public String toString() {
		return "Address [IDdiachi=" + IDdiachi + ", sodienthoai=" + sodienthoai + ", diachi=" + diachi + ", hoten="
				+ hoten + ", defaultAddress=" + defaultAddress + "]";
	}
	
	
}
