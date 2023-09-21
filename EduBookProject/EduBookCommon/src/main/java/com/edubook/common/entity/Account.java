package com.edubook.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TaiKhoan")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer IDtaikhoan;
	@Column(length = 32, nullable = false)
	private String tendangnhap;
	@Column(length = 64, nullable = false)
	private String matkhau;
	@Column(length = 64, nullable = false)
	private String hoten;
	@Column(length = 128, nullable = false, unique = true)
	private String email;
	@Column(length = 10, nullable = false)
	private String sodienthoai;
	@Column(length = 256, nullable = false)
	private String diachi;
	@Column(length = 32, nullable = false)
	private String vaitro;
	@Column(length = 12)
	private String cccd;
	@Column(length = 64)
	private String coso;
	@Column(length = 12)
	private String banglai;
	private boolean trangthai;
	
	public Integer getIDtaikhoan() {
		return IDtaikhoan;
	}
	public void setIDtaikhoan(Integer iDtaikhoan) {
		IDtaikhoan = iDtaikhoan;
	}
	public String getTendangnhap() {
		return tendangnhap;
	}
	public void setTendangnhap(String tendangnhap) {
		this.tendangnhap = tendangnhap;
	}
	public String getMatkhau() {
		return matkhau;
	}
	public void setMatkhau(String matkhau) {
		this.matkhau = matkhau;
	}
	public String getHoten() {
		return hoten;
	}
	public void setHoten(String hoten) {
		this.hoten = hoten;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getVaitro() {
		return vaitro;
	}
	public void setVaitro(String vaitro) {
		this.vaitro = vaitro;
	}
	public String getCccd() {
		return cccd;
	}
	public void setCccd(String cccd) {
		this.cccd = cccd;
	}
	public String getCoso() {
		return coso;
	}
	public void setCoso(String coso) {
		this.coso = coso;
	}
	public String getBanglai() {
		return banglai;
	}
	public void setBanglai(String banglai) {
		this.banglai = banglai;
	}
	public boolean isTrangthai() {
		return trangthai;
	}
	public void setTrangthai(boolean trangthai) {
		this.trangthai = trangthai;
	}

}
