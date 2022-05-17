package com.data.extraction.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * This class will provide JSON common utility functionalities...
 * 
 * @author Om Prakash
 */
public class JSONUtils {

	/**
	 * This method is to read JSON URL and get loaded data as string 
	 * 
	 * First preference to get data from JSON URL 
	 * @param url
	 * @return string
	 */
	public static String readJSONFromUrlWithOkhttp(String url) {

		String jsonResponse = null;
		try {
			Thread.sleep(1000);
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder().url(url).get().addHeader("cache-control", "no-cache").build();
			jsonResponse = client.newCall(request).execute().body().string();

		} catch(Exception e) {
			e.printStackTrace();
		}
		return jsonResponse;
	}


	/**
	 * This method will provide to read JSON URL and get loaded data as string 
	 * 
	 * Second preference to get data from JSON URL 
	 * @throws IOException,JSONException
	 * @param url
	 * @return String
	 */

	public static String readJsonFromUrlWithInputStram(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json.toString();
		} finally {
			is.close();
		}
	}

	/**
	 * This method is meant for providing reading with character stream -
	 * internally utilized by readJsonFromUrl()
	 * 
	 * @param Character stream object
	 * 
	 * @throws IOException
	 * @return String
	 */

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}


	/**
	 * This method is to save JSON as a text file
	 * 
	 * @param output,file path
	 * @return void
	 */
	public static void saveJSONAsaTextfile(String output, String filepath) {
		Writer writer;
		try {
			writer = new FileWriter(filepath);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(output);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is to Get List of inner JSON Objects Which contains in the Given JSON Object
	 * 
	 * @param jsonObject,specificString
	 * @return List<JSONObject>
	 */
	public static List<JSONObject> getInnerJSONArrayJSONObjectList(JSONObject jsonObject,String specificString) {		
		List<JSONObject> innerJSONObjectList = new ArrayList<JSONObject>();
		JSONArray innerJSONArray = jsonObject.getJSONArray(specificString);
		if(innerJSONArray!=null){
			int arraySize = innerJSONArray.length();
			if (arraySize > 0) {
				for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
					try {
						JSONObject innerJsonObject = innerJSONArray.getJSONObject(arrayIndex);

						innerJSONObjectList.add(innerJsonObject);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}


		return innerJSONObjectList;
	}

	/**
	 * This method is to Get Specific JSON Object Which contains in the Given JSON Object
	 * 
	 * @param jsonObject,specificString
	 * @return JSONObject
	 */
	public static JSONObject getSpecificJSONObject(JSONObject jsonObject, String propertyName) {
		JSONObject specificJSONObject = null;
		try {
			if (isValid(jsonObject, propertyName)) {
				specificJSONObject = (JSONObject) jsonObject.get(propertyName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return specificJSONObject;
	}

	/**
	 * This method is to Get List of Strings Which contains in the Given the JSON Object
	 * 
	 * @param jsonObject,specificString
	 * @return List<String>
	 */
	public static List<String> getSpecificFeildFromJSONArray(JSONObject jsonObject, String specificString) {
		List<String> specificFeildList = null;
		try {
			specificFeildList = new ArrayList<String>();
			if (isValid(jsonObject, specificString)) {
				JSONArray jsonArray = jsonObject.getJSONArray(specificString);
				int arraySize = jsonArray.length();
				if (arraySize > 0) {
					for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
						try {
							specificFeildList.add(jsonArray.getString((arrayIndex)));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return specificFeildList;
	}

	/**
	 * This method is to Get List of Strings Which contains in the Given the JSON Object
	 * 
	 * @param jsonObject,specificString
	 * @return data
	 */
	public static String getInnerJSONArrayJSONObjectDataList(JSONObject jsonObject, String propertyName) {
		String data=null;
		List<String> innerJSONObjectList = new ArrayList<>();
		if (isValid(jsonObject, propertyName)) {
			JSONArray propertyNameArray = jsonObject.getJSONArray(propertyName);
			if (propertyNameArray != null) {
				int arraySize = propertyNameArray.length();
				if (arraySize > 0) {
					for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
						try {
							//JSONObject innerJsonObject = propertyNameArray.getJSONObject(arrayIndex);
							String str = propertyNameArray.getString(arrayIndex);
							innerJSONObjectList.add(str);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					data=innerJSONObjectList.stream().collect(Collectors.joining(","));
				}
			}
		}
		return data;
	}


	/**
	 * This method is to Get Particular String comma separated Which contains in inner inner jsonObject 
	 * 
	 * @param jsonObject,specificString
	 * @return string
	 */
	public static String getInnerJsonObjectCommaSepStrings(JSONObject jsonObject,String innerObject,
			String innerInnerObject) {
		String data=null;
		List<String> innerJSONObjectList = new ArrayList<>();
		if(jsonObject.has(innerObject)) {
			List<JSONObject> innerJsonObjectList=JSONUtils.getInnerJSONArrayJSONObjectList(jsonObject, innerObject);		
			if (innerJsonObjectList!=null && innerJsonObjectList.size()>0) {
				for (JSONObject innerJsonObject : innerJsonObjectList) {
					try {
						if(innerJsonObject.has(innerInnerObject)) {
							String str=JSONUtils.getSpecificFeildFromJSONObject(innerJsonObject,innerInnerObject);
							innerJSONObjectList.add(str);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				data=innerJSONObjectList.stream().collect(Collectors.joining(","));
			}
		}
		return data;
	}


	/**
	 * This method is to Get Particular String Which contains in the Given JSON Object 
	 * 
	 * @param jsonObject,specificString
	 * @return string
	 */
	public static String getSpecificFeildFromJSONObject(JSONObject jsonObject, String specificString) {
		String propertyName = "";
		try {
			if (isValid(jsonObject, specificString)) {
				propertyName = jsonObject.get(specificString).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return propertyName;
	}

	/**
	 * This method is meant for validating property for a provided JSON Object
	 * 
	 * @param jsonObject,proeprtyName
	 * @return boolean
	 */
	public static boolean isValid(JSONObject jsonObject, String propertyName) {
		boolean valid = false;
		try {
			if (propertyName != null) {
				if (jsonObject.has(propertyName) && !jsonObject.get(propertyName).toString().equalsIgnoreCase("null")
						&& !jsonObject.get(propertyName).toString().equalsIgnoreCase(""))
					valid = true;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			valid = false;
		}
		return valid;
	}

	//2 new methods added 

	/**
	 * This method is to get Comma Separated Feilds Which contains in the Given the JSON Array
	 * 
	 * @param jsonObject,specificString
	 * @return String
	 */
	public static String getCommaSeparatedFeildsFromJSONArray(JSONObject jsonObject, String specificString) {
		List<String> specificFeildList = null;
		try {
			specificFeildList = new ArrayList<String>();
			if (isValid(jsonObject, specificString)) {
				JSONArray jsonArray = jsonObject.getJSONArray(specificString);
				int arraySize = jsonArray.length();
				if (arraySize > 0) {
					for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
						try {
							String data=(String) jsonArray.get((arrayIndex));
							specificFeildList.add(data);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return specificFeildList.stream().collect(Collectors.joining(","));
	}

	/**
	 * This method is to get List Of String Which contains in the Given the JSON Object
	 * 
	 * @param jsonObject,specificString
	 * @return  List<String>
	 */
	public static List<String> getAllFieldsFromJSONArray(JSONObject jsonObject, String specificString) {
		List<String> innerJSONObjectList = new ArrayList<String>();
		if(jsonObject!=null){
			JSONArray innerJSONArray = jsonObject.getJSONArray(specificString);
			int arraySize = innerJSONArray.length();
			if (arraySize > 0) {
				for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
					try {
						innerJSONObjectList.add((String) innerJSONArray.get(arrayIndex));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return innerJSONObjectList;		
	}

	/**
	 * This method is to Get Particular String   Which contains in inner inner jsonObject 
	 * 
	 * @param jsonObject,specificString
	 * @return string
	 */
	public static String getInnerInnerJsonObjectAsString(JSONObject jsonObject,String innerObject,
			String innerInnerObject) {
		String data=null;
		List<String> innerJSONObjectList = new ArrayList<>();
		List<JSONObject> innerJsonObjectList=JSONUtils.getInnerJSONArrayJSONObjectList(jsonObject, innerObject);
		if (innerJsonObjectList!=null && innerJsonObjectList.size()>0) {
			for (JSONObject innerJsonObject : innerJsonObjectList) {
				try {
					String str=JSONUtils.getSpecificFeildFromJSONObject(innerJsonObject,innerInnerObject);
					innerJSONObjectList.add(str);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			data=innerJSONObjectList.stream().collect(Collectors.joining(","));
		}
		return data;
	}




}
