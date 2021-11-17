package com.sample.vo;

import java.util.Date;

public class Product {

	private int no;
	private String name;
	private String maker;
	private int price;
	private int discountPrice;
	private int stock;
	private String onSale;
	private Date createdDate;
	
	public Product() {}

	public Product(int no, String name, String maker, int price, int discountPrice, int stock, String onSale,
			Date createdDate) {
		super();
		this.no = no;
		this.name = name;
		this.maker = maker;
		this.price = price;
		this.discountPrice = discountPrice;
		this.stock = stock;
		this.onSale = onSale;
		this.createdDate = createdDate;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(int discountPrice) {
		this.discountPrice = discountPrice;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getOnSale() {
		return onSale;
	}

	public void setOnSale(String onSale) {
		this.onSale = onSale;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
