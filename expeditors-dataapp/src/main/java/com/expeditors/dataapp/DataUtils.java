package com.expeditors.dataapp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataUtils extends APIResponse{

	private static final Logger LOGGER = LoggerFactory.getLogger(DataUtils.class);



	public static Map<String, List<DataAppPojo>> groupObjects(List<Address> datalist){
		Map<String, List<DataAppPojo>> groupedData = new HashMap<String, List<DataAppPojo>>();
		for (Address data: datalist) {
			String key = data.getAddress();

			if (groupedData.get(key) == null) {
				groupedData.put(key, new ArrayList<DataAppPojo>());
			}

			for(DataAppPojo dataPojo : data.getListData()){
				groupedData.get(key).add(dataPojo);
			}
		}
		return groupedData;
	}



	public static Map<String, List<DataAppPojo>> readGrouped(Map<String, List<DataAppPojo>> dataList){

		Map<String, List<DataAppPojo>> returnData = new HashMap<>();
		String addressVal = "";
		Set<String> dataSet = dataList.keySet();
		boolean flag =true;

		for (String address: dataSet) {
			addressVal = address;
			List<DataAppPojo> dataObjs = dataList.get(address);
			List<DataAppPojo> dataObjsToReturn = new ArrayList<>();

			for (DataAppPojo data : dataObjs) {	
				if(data.getAge() <19){
					flag = false;

				} else {
					dataObjsToReturn.add(data);
					returnData.put(addressVal, dataObjsToReturn);
					flag = true;
				}
			}
		}
		return returnData;
	}


	public static String createResponse(Map<String, List<DataAppPojo>> dataList, String name) throws JSONException {


		String openBracket = "[";
		String response = "";
		String addressVal = "";
		Set<String> dataSet = dataList.keySet();
		StringBuilder sb = new StringBuilder();
		StringBuilder sbfinal = new StringBuilder();
		int total = 0;
		int counter = 1;
		
		Map <String, Integer> houseHoldiD = new HashMap<>();
		
		
		for (String address: dataSet) {

			addressVal = address;
			if (houseHoldiD.get(addressVal) == null) {
				houseHoldiD.put(addressVal, counter);
				counter++;
			}
			


			List<DataAppPojo> dataObjs = dataList.get(address);
			total = dataObjs.size();

			for (DataAppPojo data : dataObjs) {	
				
				data.setHouseHoldGroupId(houseHoldiD.get(addressVal));
				response = response+"{"
						+ "\"Household Group ID\": " + "\"" + data.getHouseHoldGroupId() + "\""
						+ "," + "\"Total number of household occupants\": " + "\"" + total + "\""
						+ "," + "\"First Name\": " + "\"" + data.getFirstName() + "\""
						+ "," + "\"Last Name\": " + "\"" + data.getLastName() + "\""
						+ "," + "\"Address\": " + "\"" + addressVal + "\""
						+ "," + "\"Age\": " + "\"" + data.getAge() + "\""
						+ "},";	
				counter ++;
			}

			sb.append(response);
			response= "";
		}

		sb.setCharAt(sb.length()-1, ']');
		response = openBracket + sb.toString();
		sbfinal.append(response);
		return (sortOrder (sbfinal.toString(), name));
	}


	public static String sortOrder( String sb, String name) throws JSONException{

		JSONArray jsonArr = new JSONArray(sb);
		JSONArray sortedArray = new JSONArray();

		List<JSONObject> jsonVals = new ArrayList<JSONObject>();
		for (int i = 0; i < jsonArr.length(); i++) {
			jsonVals.add(jsonArr.getJSONObject(i));

		}
		Collections.sort( jsonVals, new Comparator<JSONObject>() {
			private  final String KEY_NAME = name;

			@Override
			public int compare(JSONObject arrOne, JSONObject arrTwo) {
				String jsonOne = new String();
				String jsonTwo = new String();

				try {
					jsonOne = (String) arrOne.get(KEY_NAME);
					jsonTwo = (String) arrTwo.get(KEY_NAME);
				} 
				catch (JSONException e) {
				}
				
				if(sortAscending){
					return jsonOne.compareTo(jsonTwo);
				} else  {
					return - (jsonOne.compareTo(jsonTwo));

				}
			}
		});

		for (int i = 0; i < jsonArr.length(); i++) {
			sortedArray.put(jsonVals.get(i));
		}

		return sortedArray.toString();

	}

	public static String writeToFile(String response){
		String outputFormat = String.format("%-10s", " Household Group ID") +"|"
							+ String.format("%-30s", " Total number of household occupants") +"|"
							+ String.format("%-15s", " First Name") +"|"
							+ String.format("%-15s", " Last Name")  +"|"
							+ String.format("%-40s", " Address") +"|"
							+  " Age"+ "\n";
		try {								
			JSONArray jsonArr = new JSONArray(response);

			List<JSONObject> jsonValues = new ArrayList<JSONObject>();
			for (int i = 0; i < jsonArr.length(); i++) {
				jsonValues.add(jsonArr.getJSONObject(i));
			}

			for(JSONObject json : jsonValues){

				outputFormat = outputFormat +" " + String.format("%-18s",json.get("Household Group ID"))  +"|"
						+" " + String.format("%-35s",json.get("Total number of household occupants")) +"|"
						+" " + String.format("%-14s",json.get("First Name")) +"|"
						+" " + String.format("%-14s",json.get("Last Name")) +"|"
						+" " + String.format("%-39s",json.get("Address")) +"|"
						+" " + json.get("Age") + "\n";
			}
			
			System.out.printf(outputFormat);

			BufferedWriter out = new BufferedWriter(new FileWriter("Response.txt"));
			out.write(outputFormat);
			out.close();
			LOGGER.info("Written suuccesfuly");
		}
		catch (Exception e)
		{	
			LOGGER.info("Exception " + e.getMessage());			

		} 
		return outputFormat;
	}
}
