package com.aoneposapp.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class JsonPostMethod {
	
	public String userid, sessionid, tablename, systemtime, Currentsystemtime, data, responseText, update, currenttime, previous_server_time, 
					previousday, username, password, deviceid;
	
	public String postmethodfordirect(String userid, String sessionid, String tablename, String systemtime, String Currentsystemtime, String data, String update){
		this.userid = Parameters.userid;
		this.sessionid = Parameters.sessionidforloginlogout;
		this.tablename = tablename;
		this.systemtime = systemtime;
		this.Currentsystemtime = Currentsystemtime;
		this.data = data;
		this.update = update;
		
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(Parameters.OriginalUrl+"saveinfo.php");
	    Log.v("response",""+Parameters.OriginalUrl+"saveinfo.php");
	    
	    try {
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        nameValuePairs.add(new BasicNameValuePair("userid", userid));
	        nameValuePairs.add(new BasicNameValuePair("sessionid", sessionid));
	        nameValuePairs.add(new BasicNameValuePair("tablename", tablename));
	        nameValuePairs.add(new BasicNameValuePair("systemtime", systemtime));
	        nameValuePairs.add(new BasicNameValuePair("Currentsystemtime", Currentsystemtime));
	        nameValuePairs.add(new BasicNameValuePair("data", data));
	        nameValuePairs.add(new BasicNameValuePair("update", update));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        responseText = EntityUtils.toString(entity);
	        Log.v("response",""+responseText);
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    } 
	    if(responseText!=null&&responseText.length()>1){
		return responseText;
	    }else{
	    	return "{response: }";
	    }
	}
	
	public String postmethodfordirectdelete(String userid, String sessionid, String tablename, String systemtime, String Currentsystemtime, String data){
		this.userid = userid;
		this.sessionid = sessionid;
		this.tablename = tablename;
		this.systemtime = systemtime;
		this.Currentsystemtime = Currentsystemtime;
		this.data = data;
		
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(Parameters.OriginalUrl+"deleteinfo.php");
	    
	    try {
	    	
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        nameValuePairs.add(new BasicNameValuePair("userid", userid));
	        nameValuePairs.add(new BasicNameValuePair("sessionid", sessionid));
	        nameValuePairs.add(new BasicNameValuePair("tablename", tablename));
	        nameValuePairs.add(new BasicNameValuePair("systemtime", systemtime));
	        nameValuePairs.add(new BasicNameValuePair("Currentsystemtime", Currentsystemtime));
	        nameValuePairs.add(new BasicNameValuePair("unique_ids", data));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        responseText = EntityUtils.toString(entity);
	        
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    } 
	    if(responseText!=null&&responseText.length()>1){
			return responseText;
		    }else{
		    	return "{response: }";
		    }
	}
	
	public String postmethodforbackup(String currenttime, String previous_server_time, String sessionid, String previousday){
		this.currenttime = currenttime;
		this.previous_server_time = previous_server_time;
		this.sessionid = sessionid;
		this.previousday = previousday;
		
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(Parameters.OriginalUrl+"backup.php");
		System.out.println("response value is:"+Parameters.OriginalUrl+"backup.php");
	    try {
	    	
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        nameValuePairs.add(new BasicNameValuePair("Currentsystemtime", currenttime));
	        nameValuePairs.add(new BasicNameValuePair("previous-sync-time", previous_server_time));
	        nameValuePairs.add(new BasicNameValuePair("sessionid", sessionid));
	        nameValuePairs.add(new BasicNameValuePair("previousday", previousday));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            Log.v(""+currenttime," 1    "+previous_server_time);
            Log.v(""+sessionid,""+previousday);
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        responseText = EntityUtils.toString(entity);
	        Log.v(""+sessionid,""+responseText);
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    } 
	    
	    if(responseText!=null&&responseText.length()>1){
			return responseText;
		    }else{
		    	return "{response: }";
		    }
	}
	
	public String postmethodforbackupnexttime(String nexturl){
		
		HttpClient httpclient = new DefaultHttpClient();
	    HttpGet httppost = new HttpGet(nexturl);

	    HttpResponse response;
		try {
			response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
		    responseText = EntityUtils.toString(entity);
		    Log.v("resss",responseText );
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		 if(responseText!=null&&responseText.length()>1){
				return responseText;
			    }else{
			    	return "{response: }";
			    }
	}
	
public String postmethodforlogininget(String username, String pwd, String deviceid, String systemtime){
		
		HttpClient httpclient = new DefaultHttpClient();
		String url = Parameters.OriginalUrl+"user-login.php?username="+username+"&password="+pwd+"&deviceid="+deviceid+"&systemtime="+systemtime.replace(" ", "%20");
		System.out.println("url is:"+url);
	    HttpGet httppost = new HttpGet(url);

	    HttpResponse response;
		try {
			response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
		    responseText = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		 if(responseText!=null&&responseText.length()>1){
				return responseText;
			    }else{
			    	return "{response: }";
			    }
	}

public String postmethodfortaxediting(String query){
	
	HttpClient httpclient = new DefaultHttpClient();
	String url = Parameters.OriginalUrl+"execute.php";
	System.out.println("999url is:"+url);
    HttpPost httppost = new HttpPost(url);
    
    try {
    	
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("query-string", query));
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        responseText = EntityUtils.toString(entity);
        
    } catch (ClientProtocolException e) {
        // TODO Auto-generated catch block
    } catch (IOException e) {
        // TODO Auto-generated catch block
    } 
    
	 if(responseText!=null&&responseText.length()>1){
			return responseText;
		    }else{
		    	return "{response: }";
		    }
}

public String postmethodforlogininget1(String url){
	
	HttpClient httpclient = new DefaultHttpClient();
	System.out.println("url is: "+Parameters.OriginalUrl+"user-login.php"+url);
    HttpGet httppost = new HttpGet(Parameters.OriginalUrl+"user-login.php"+url);

    HttpResponse response;
	try {
		response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
	    responseText = EntityUtils.toString(entity);
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
	 if(responseText!=null&&responseText.length()>1){
			return responseText;
		    }else{
		    	return "{response: }";
		    }
}
	
	public String postmethodforlogin(String username, String password, String deviceid, String systemtime){
		this.username = username;
		this.password = password;
		this.deviceid = deviceid;
		this.systemtime = systemtime;
		
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(Parameters.OriginalUrl+"user-login.php");
	    
	    try {
	    	
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        nameValuePairs.add(new BasicNameValuePair("username", username));
	        nameValuePairs.add(new BasicNameValuePair("password", password));
	        nameValuePairs.add(new BasicNameValuePair("deviceid", deviceid));
	        nameValuePairs.add(new BasicNameValuePair("systemtime", systemtime));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        responseText = EntityUtils.toString(entity);
	        
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    } 
	    
	    if(responseText!=null&&responseText.length()>1){
			return responseText;
		    }else{
		    	return "{response: }";
		    }
	}
	public String paswordEdit(String username, String password, String deviceid, String systemtime){
		this.username = username;
		this.password = password;
		this.deviceid = deviceid;
		this.systemtime = systemtime;
		
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(Parameters.OriginalUrl+"change-password.php");
	    
	    try {
	    	
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        nameValuePairs.add(new BasicNameValuePair("username", username));
	        nameValuePairs.add(new BasicNameValuePair("sessionid", password));
	        nameValuePairs.add(new BasicNameValuePair("password", deviceid));
	        nameValuePairs.add(new BasicNameValuePair("Currentsystemtime", systemtime));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        responseText = EntityUtils.toString(entity);
	        
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    } 
	    
	    if(responseText!=null&&responseText.length()>1){
			return responseText;
		    }else{
		    	return "{response: }";
		    }
	}
	public String postmethodforcreateprofile(String username, String password, String deviceid){
		this.username = username;
		this.password = password;
		this.deviceid = deviceid;
	//	this.systemtime = systemtime;
		
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(Parameters.OriginalUrl+"create-admin-profile.php");
		System.out.println("response value is:"+Parameters.OriginalUrl+"create-admin-profile.php");
	    try {
	    	
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        nameValuePairs.add(new BasicNameValuePair("defaultusername", username));
	        nameValuePairs.add(new BasicNameValuePair("defaultpassword", password));
	        nameValuePairs.add(new BasicNameValuePair("data", deviceid));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        responseText = EntityUtils.toString(entity);
	        System.out.println("response value is:"+responseText);
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    } 
	    
	    if(responseText!=null&&responseText.length()>1){
			return responseText;
		    }else{
		    	return "{response: }";
		    }
	}
	
	public static final String md5(final String s) {
	    try {
	        // Create MD5 Hash
	        MessageDigest digest = java.security.MessageDigest
	                .getInstance("MD5");
	        digest.update(s.getBytes());
	        byte messageDigest[] = digest.digest();

	        // Create Hex String
	        StringBuffer hexString = new StringBuffer();
	        for (int i = 0; i < messageDigest.length; i++) {
	            String h = Integer.toHexString(0xFF & messageDigest[i]);
	            while (h.length() < 2)
	                h = "0" + h;
	            hexString.append(h);
	        }
	        return hexString.toString();

	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return "";
	}

}
