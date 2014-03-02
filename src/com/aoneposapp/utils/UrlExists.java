	package com.aoneposapp.utils;

	import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

	public class UrlExists extends AsyncTask<String, Void, Boolean>{
	    ProgressDialog progressDialog ;
	    getUrlListener getJSONListener;
	    Context curContext;
	    public UrlExists(Context context, getUrlListener listener){
	        this.getJSONListener = listener;
	        curContext = context;
	    }
	    public static String convertStreamToString(InputStream is) {
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


	    public static boolean connect(String url)
	    {
	    	  try {
	              URL url1 = new URL(url);
	              URLConnection urlConnection = url1.openConnection();

	              HttpURLConnection.setFollowRedirects(false);
	              HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
	              httpURLConnection.setRequestMethod("HEAD");

	              if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
	                  System.out.println("URL Exist");
	                  return true;
	              } else {
	                  System.out.println("URL not Exists");
	                  return false;
	              }
	          } 
	          catch(UnknownHostException unknownHostException){
	              System.out.println("UnkownHost");
	              return false;
	          }
	          catch (Exception e) {
	              System.out.println(e);
	              return false;
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
	    protected Boolean doInBackground(String... urls) {
	        return connect(urls[0]);
	    }

	    @Override
	    protected void onPostExecute(Boolean boo ) {
	    	getJSONListener.getUrlVaild(boo);
	        progressDialog.dismiss();
	    }
	}