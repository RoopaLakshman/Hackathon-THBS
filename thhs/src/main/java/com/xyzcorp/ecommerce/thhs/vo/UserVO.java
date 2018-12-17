package com.xyzcorp.ecommerce.thhs.vo;

import com.opencsv.bean.CsvBindByName;

public class UserVO {

	@CsvBindByName
	private String userId;

	@CsvBindByName
	private String userName;

	@CsvBindByName
	private String password;
	
	@CsvBindByName
	private String name;

	@CsvBindByName
	private String address;

	@CsvBindByName
	private String phoneNumber;

	@CsvBindByName
	private String preference;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPreference() {
		return preference;
	}

	public void setPreference(String preference) {
		this.preference = preference;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", name=" + name + ", address=" + address
				+ ", phoneNumber=" + phoneNumber + ", preference=" + preference + "]";
	}

}
