package com.expeditors.dataapp;

public class DataAppPojo {
	String firstName = null;
	String lastName = null;
	int age = 0;
	int houseHoldGroupId = 0;



	public DataAppPojo(String firstName, String lastName, int age) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}


	public String getFirstName() {
		return firstName;
	}


	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public int getHouseHoldGroupId() {
		return houseHoldGroupId;
	}


	public void setHouseHoldGroupId(int houseHoldGroupId) {
		this.houseHoldGroupId = houseHoldGroupId;
	}


}
