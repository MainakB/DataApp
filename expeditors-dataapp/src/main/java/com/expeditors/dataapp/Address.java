package com.expeditors.dataapp;

import java.util.List;

public class Address {
	
	String address = null;
	List<DataAppPojo> listData = null;
	int totalOccupants = 0;
	


	public Address(String address, List<DataAppPojo> listData) {
		super();
		this.address = address;
		this.listData = listData;
	}

	
	public List<DataAppPojo> getListData() {
		return listData;
	}

	public String getAddress() {
		return address;
	}
	
	public int getTotalOccupants() {
		return totalOccupants;
	}


	public void setTotalOccupants(int totalOccupants) {
		this.totalOccupants = totalOccupants;
	}

}
