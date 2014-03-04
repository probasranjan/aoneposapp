package com.aoneposapp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class JSONLoginFunction extends AsyncTask<String, Void, JSONObject> {
	ProgressDialog progressDialog;
	LoginReturnJson getJSONListener;
	Context curContext;

	public JSONLoginFunction(Context context, LoginReturnJson listener) {
		this.getJSONListener = listener;
		curContext = context;
	}

	public static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}
	private static final int CONNECTION_TIMEOUT = 5000; /* 5 seconds */

	// the timeout for waiting for data
	private static final int SOCKET_TIMEOUT = 5000; /* 5 seconds */

	// ----------- this is the one I am talking about:
	// the timeout until a ManagedClientConnection is got 
	// from ClientConnectionRequest
	private static final long MCC_TIMEOUT = 5000; /* 5 seconds */
	
	private static void setTimeouts(HttpParams params) {
	    params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 
	        CONNECTION_TIMEOUT);
	    params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, SOCKET_TIMEOUT);
	    params.setLongParameter(ConnManagerPNames.TIMEOUT, MCC_TIMEOUT);
	}
	public static JSONObject connect(String url) {
		
		 JSONObject json=null;
		// Prepare a request object
		HttpGet httpget = new HttpGet(url);
		setTimeouts(httpget.getParams());
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
		HttpConnectionParams.setSoTimeout(httpParameters, 10000);

      HttpClient httpclient = new DefaultHttpClient();
		// Execute the request
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();

			if (entity != null) {

				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);
				Log.e("result", "" + result);
				// A Simple JSONObject Creation
				  json=new JSONObject(result);
				//JSONObject json = new JSONObject(result);
				// Closing the input stream will trigger connection release
				instream.close();
				
			}

		}
		catch (SocketTimeoutException e1)
		{
		    e1.printStackTrace();
		}
		catch (ConnectTimeoutException e2)
		{
		    e2.printStackTrace();
		}catch (ClientProtocolException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (IOException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		} catch (JSONException e5) {
			// TODO Auto-generated catch block
			e5.printStackTrace();
		}

		  if(json!=null&&json.length()>1){
				return json;
			    }else{
			    	JSONObject json1 = null;
					try {
						json1 = new JSONObject("{response: }");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	return json1;
			    }
	}

	@Override
	public void onPreExecute() {
		progressDialog = new ProgressDialog(curContext);
		progressDialog.setMessage("Loading..Please wait..");
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();

	}

	@Override
	protected JSONObject doInBackground(String... urls) {
		return connect(urls[0]);
	}

	@Override
	protected void onPostExecute(JSONObject json) {
		try{
			progressDialog.dismiss();
		getJSONListener.logintoserverinterface(json);
		}catch(NullPointerException n){
			n.printStackTrace();
			JSONObject json3;
			try {
				json3 = new JSONObject("{response: }");
				getJSONListener.logintoserverinterface(json3);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JSONObject json3;
			try {
				json3 = new JSONObject("{response: }");
				getJSONListener.logintoserverinterface(json3);
			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
	}
}