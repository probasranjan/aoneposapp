package com.aoneposapp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class JsonParser 
{
	private InputStream is = null;
	private JSONObject jObj = null;
	private String json = "";
	
	private String WEBSERVICE_URL;
	private String WEBSERVICE_MIDDLE="/index.php/";
	private String WEBSERVICE_EXT = "/format/json";
	
	/**
	 * Parameterized Constructor
	 * @param WEBSERVICE_URL
	 */
	public JsonParser() 
	{
	}
	
	/**
	 * This is used to get json object
	 * @param url
	 * @return
	 */
	public JSONObject getJsonObjectFromUrl(String url) 
	{

		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();			

			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
			
			jObj = new JSONObject(json);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// return JSON String
		return jObj;

	}
	
	/**
	 * This is used to get json object
	 * @param url
	 * @return
	 */
	public JSONObject getJsonObjectFromWebservice(String webServiceName) 
	{
		// Making HTTP request
		try {
			
			System.out.println(WEBSERVICE_URL+WEBSERVICE_MIDDLE+webServiceName+WEBSERVICE_EXT);
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(WEBSERVICE_URL+WEBSERVICE_MIDDLE+webServiceName+WEBSERVICE_EXT);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
			
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
			
			
			jObj = new JSONObject(json);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// return JSON String
		return jObj;

	}
	
	/**
	 * This is used to get json object
	 * @param urlString
	 * @param jsonString
	 * @return
	 */
	public JSONObject getJsonObjectFromWebservice(String urlString, HashMap<String, String> map)
	{
		// Making HTTP request
		// defaultHttpClient
		try
		{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			//			HttpPost httpPost = new HttpPost(url);
			HttpPost httpPost = new HttpPost(urlString);
			//============================================
			ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
			for (Map.Entry<String, ?> entry : map.entrySet()) 
			{
			    Object value = entry.getValue();
			    if (value instanceof Collection) 
			    {
			        Collection<?> values = (Collection<?>) value;
			        for (Object v : values) 
			        {
			            parameters.add(new BasicNameValuePair(entry.getKey(), v == null ? null : String.valueOf(v)));
			        }
			    } 
			    else 
			    {
			        parameters.add(new BasicNameValuePair(entry.getKey(), value == null ? null : String.valueOf(value)));
			    }
			}
			//========================================================
			
			List<NameValuePair> nameValuePairs = parameters;//new ArrayList<NameValuePair>();   
//			nameValuePairs.add(new BasicNameValuePair("userid","santosh_kushare@vijaywebsolutions.com"));
//			nameValuePairs.add(new BasicNameValuePair("password","santosh"));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse httpResponse = httpClient.execute(httpPost);

			HttpEntity httpEntity = httpResponse.getEntity();

			is = httpEntity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) 
			{
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();

			System.out.println(" messege for debuging"+json);
			jObj = new JSONObject(json);



			// return JSON String
			System.out.println("Json Response : "+jObj);
		}
		catch (HttpHostConnectException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return jObj;
	}
}
