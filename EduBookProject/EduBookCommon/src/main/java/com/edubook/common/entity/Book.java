package com.edubook.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sach")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer IDsach;
	@Column(length = 128, nullable = false)
	private String tacgia;
	@Column(length = 64)
	private String hinhanh;
	@Column(length = 8, nullable = false)
	private Integer namxuatban;
	@Column(length = 128, nullable = false)
	private String tensach;
	@Column(length = 128, nullable = false)
	private String nxb;
	@Column(length = 5120, nullable = false)
	private String mota;
	@Column(length = 128, nullable = false)
	private String theloai;
	@Column(length = 8, nullable = false)
	private Integer soluongkho;
	@Column(length = 8, nullable = false)
	private Integer dongia;
	@Column(length = 8)
	private Integer soluongban;
	
	private int reviewCount;
	private float averageRating;
	
	public Book(Integer iDsach) {
		this.IDsach = iDsach;
	}
	public Book() {
		
	}
	public Integer getIDsach() {
		return IDsach;
	}
	public void setIDsach(Integer iDsach) {
		IDsach = iDsach;
	}
	public String getTacgia() {
		return tacgia;
	}
	public void setTacgia(String tacgia) {
		this.tacgia = tacgia;
	}
	public String getHinhanh() {
		return hinhanh;
	}
	public void setHinhanh(String hinhanh) {
		this.hinhanh = hinhanh;
	}
	public Integer getNamxuatban() {
		return namxuatban;
	}
	public void setNamxuatban(Integer namxuatban) {
		this.namxuatban = namxuatban;
	}
	public String getTensach() {
		return tensach;
	}
	public void setTensach(String tensach) {
		this.tensach = tensach;
	}
	public String getNxb() {
		return nxb;
	}
	public void setNxb(String nxb) {
		this.nxb = nxb;
	}
	public String getMota() {
		return mota;
	}
	public void setMota(String mota) {
		this.mota = mota;
	}
	public String getTheloai() {
		return theloai;
	}
	public void setTheloai(String theloai) {
		this.theloai = theloai;
	}
	public Integer getSoluongkho() {
		return soluongkho;
	}
	public void setSoluongkho(Integer soluongkho) {
		this.soluongkho = soluongkho;
	}
	public Integer getDongia() {
		return dongia;
	}
	public void setDongia(Integer dongia) {
		this.dongia = dongia;
	}
	public Integer getSoluongban() {
		return soluongban;
	}
	public void setSoluongban(Integer soluongban) {
		this.soluongban = soluongban;
	}
	
	public int getReviewCount() {
		return reviewCount;
	}
	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}
	public float getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(float averageRating) {
		this.averageRating = averageRating;
	}
	@Transient
	public String getBookPhotoPath() {
		if(hinhanh == null) return "image/id467907-125.png";
		return "/book-photos/" + this.IDsach + "/" + this.hinhanh;
	}
	
	@Transient
	private boolean customerCanReview;
	@Transient
	private boolean reviewedByCustomer;

	public boolean isCustomerCanReview() {
		return customerCanReview;
	}
	public void setCustomerCanReview(boolean customerCanReview) {
		this.customerCanReview = customerCanReview;
	}
	public boolean isReviewedByCustomer() {
		return reviewedByCustomer;
	}
	public void setReviewedByCustomer(boolean reviewedByCustomer) {
		this.reviewedByCustomer = reviewedByCustomer;
	}
	
}
