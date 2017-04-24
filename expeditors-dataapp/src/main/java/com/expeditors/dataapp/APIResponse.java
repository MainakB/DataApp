package com.expeditors.dataapp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * API response details.
 * Created by Mainak Basu
 */
public class APIResponse {

	static boolean sortAscending ;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(APIResponse.class);

		
	/**
	 * return info as response
	 *
	 * @param request body
	 * @return
	 */
	public String getData( String name, String sortType) throws Exception{
		
		if(sortType.equalsIgnoreCase("DESC")){
			sortAscending = false;
		} else {
			sortAscending = true;
		}
		
		List<Address> dataList = new ArrayList<>();
		String response = "";
		String file_name = "/DATA.txt";
		Address addressPojo;
		BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(file_name)));
		try {

			String line = br.readLine();
			while (line != null) {
				List<DataAppPojo> dataAppPojoList = new ArrayList<>();
				DataAppPojo dataAppPojo;
				String [] str = line.split(",");
				int i =0;
				String first_name = str[i].replace("\"", "");
				String last_name = str[i+1].replace("\"", "");
				String address = "";
				int age = Integer.parseInt(str[(str.length - 1 )].replace("\"", ""));

				for(int j = i+2; j< str.length-1; j++){
					if(!(address =="")){
						address = address + ',';
					}
					address = address + str[j].replace("\"", "");
				}
				dataAppPojo = new DataAppPojo(first_name, last_name, age);
				dataAppPojoList.add(dataAppPojo);
				addressPojo = new Address(address, dataAppPojoList);
				dataList.add(addressPojo);
				line = br.readLine();
			}
		} finally {
			br.close();
		}

		LOGGER.info("Response : " + response);
		response = DataUtils.createResponse(DataUtils.readGrouped(DataUtils.groupObjects(dataList)), name);	
		response = DataUtils.writeToFile(response);
		return response;		
	}
	
	

}
