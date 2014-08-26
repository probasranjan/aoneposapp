package net.authorize.android.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class Parser {

	public InputStream is = null;
	public JSONObject jObj = null;
	public String json = "";
	
	// constructor
	public Parser() {

	}
	
	/**
	 * This is used to get json object parse from webservice 
	 * @param webserviceUrl name of webservice
	 * @return
	 */
	public JSONObject getJSONFromUrl(String webserviceUrl) {

		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(webserviceUrl);
			System.out.println("Complete URL : "+webserviceUrl);
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
			System.out.println("Result : "+json);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return JSON String
		return jObj;
	}
	
	/**
	 * This is used to get json object parse from webservice 
	 * @param webserviceUrl name of webservice
	 * @return
	 */
	public JSONObject getJsonObject1(String webserviceUrl)throws HttpHostConnectException,Exception
	{
		// defaultHttpClient
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(webserviceUrl);
		System.out.println("Complete URL : "+webserviceUrl);
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
		System.out.println("Result : "+json);
			
		// return JSON String
		return jObj;
	}
	
	/**
	 * This is used to get json object parse from webservice 
	 * @param url name of webservice
	 * @return
	 */
	public JSONObject getJsonObject(String webServiceName)throws HttpHostConnectException,Exception
	{
		// defaultHttpClient
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String URL = "http://198.61.212.88/hkweb/index.php/"+webServiceName+"/format/json";
		HttpPost httpPost = new HttpPost(URL);
		System.out.println("Complete URL : "+URL);
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
		System.out.println("Result : "+json);
			
		// return JSON String
		return jObj;
	}
	
	/**
	 * This is used to get JSON object parse from webservice
	 * @param webserviceName name of webservice
	 * @param parameter parameter to webservice
	 * @return
	 */
	public JSONObject getJsonObject(String webserviceName,String parameter)throws HttpHostConnectException,Exception
	{
		// Making HTTP request
		// defaultHttpClient
		DefaultHttpClient httpClient = new DefaultHttpClient();
		System.out.println("Parameter : "+parameter);
		String url = "http://198.61.212.88/hkweb/index.php/"+webserviceName+"/format/json";
		System.out.println("Complete URL : "+url);
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();   
	    nameValuePairs.add(new BasicNameValuePair("arg",parameter));
		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs)); 
		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity httpEntity = httpResponse.getEntity();
		is = httpEntity.getContent();			

		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		is.close();
		json = sb.toString();
		System.out.println("Result : "+json);
		jObj = new JSONObject(json);
		
		// return JSON String
		return jObj;
	}
}
