package com.aoneposapp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class JsonPostWithAsnk extends AsyncTask<String, Void, JSONObject>{
    ProgressDialog progressDialog ;
    GetJSONListener getJSONListener;
    static Context curContext;
    static String name;
    static String email;
    static String phone;
    static String company;
    public JsonPostWithAsnk(Context context, GetJSONListener listener,String name,String email ,String phone,String company){
        this.getJSONListener = listener;
        curContext = context;
        JsonPostWithAsnk.name=name;
        JsonPostWithAsnk.email=email;
        JsonPostWithAsnk.phone=phone;
        JsonPostWithAsnk.company=company;
    }
    private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
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


    public static JSONObject connect(String url)
    {
    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(14);
    	
    	  HttpClient httpclient = new DefaultHttpClient();
    	  //192.168.1.9/xampp/sites/aone/crm/customer:registration.php
   HttpPost httppost = new HttpPost("http://www.aonepos.com/customer-register.php");
    	//  HttpPost httppost = new HttpPost("http://192.168.1.13/xampp/sites/aone/crm/customer-registration.php?name="+name+"&email="+email+"&company="+company+"&phone="+phone);
    	  // http://192.168.1.2/xampp/sites/aone/crm/serverurl.php?companyid=
   //  Log.v("ddf","http://192.168.1.9/xampp/sites/aone/crm/customer-registration.php?name="+name+"&email="+email+"&company="+company+"&phone="+phone);
  	    try {
  	      nameValuePairs.add(new BasicNameValuePair("name", ""+name));
  	      nameValuePairs.add(new BasicNameValuePair("email", ""+email));
  	    nameValuePairs.add(new BasicNameValuePair("phone", ""+phone));
  	  nameValuePairs.add(new BasicNameValuePair("company", ""+company));
  	    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

  	        // Execute HTTP Post Request
  	        HttpResponse response = httpclient.execute(httppost);
  	        HttpEntity entity = response.getEntity();

  			if (entity != null) {
  			    // A Simple JSON Response Read
  			    InputStream instream = entity.getContent();
  			    String result= convertStreamToString(instream);
  	Log.v("output",""+result);
  	 JSONObject json;
	try {
		json = new JSONObject(result);
		 return json;
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	    // Closing the input stream will trigger connection release
	    instream.close();
  			}
  	    } catch (ClientProtocolException e) {
  	        // TODO Auto-generated catch block
  	    } catch (IOException e) {
  	        // TODO Auto-generated catch block
  	    }
		   
		


        return null;
    }
    @Override
    public void onPreExecute() {
        progressDialog = new ProgressDialog(curContext);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        return connect(urls[0]);
    }

    @Override
    protected void onPostExecute(JSONObject json ) {
        getJSONListener.onRemoteCallComplete(json);
        progressDialog.dismiss();
    }

}
