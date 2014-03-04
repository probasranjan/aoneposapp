package com.aoneposapp.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ParseException;

public class ServerSyncClass {
	static DatabaseForDemo demodbDB = new DatabaseForDemo(MyApplication.getAppContext());
	static SQLiteDatabase dbforloginlogoutWrite123 = demodbDB.getWritableDatabase();
	SQLiteDatabase dbforloginlogoutRead123 = demodbDB.getReadableDatabase();
	String localSystemTimeString, previousServerSyncTimeString;
	long diff;
    
	public void generalMethod() throws ParseException, java.text.ParseException{
		System.out.println("general method is called in serversync");
		
		String query = "select "+DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL+ " from "+DatabaseForDemo.MISCELLANEOUS_TABLE;
		Cursor cursorwqwqw = dbforloginlogoutRead123.rawQuery(query, null);
		
		if(cursorwqwqw.getCount()>0){
			if(cursorwqwqw!=null){
				if(cursorwqwqw.moveToFirst()){
				do{
					if(cursorwqwqw.isNull(cursorwqwqw.getColumnIndex(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL))){
						previousServerSyncTimeString = "previousday";
					}else{
					String servertime = cursorwqwqw.getString(cursorwqwqw.getColumnIndex(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL));
					if(servertime.length()<1||servertime.equals("")||servertime==null){
						previousServerSyncTimeString = "previousday";
						}else{
							previousServerSyncTimeString=servertime;
						}
					}
				}while(cursorwqwqw.moveToNext());
				}
			}
		}else{
			previousServerSyncTimeString = "previousday";
		}
		cursorwqwqw.close();
		localSystemTimeString = Parameters.currentTime();
		System.out.println("server time is in serversync:"+previousServerSyncTimeString);
		System.out.println("local time is in serversync:"+localSystemTimeString);
		
		if(previousServerSyncTimeString.equals("previousday")){
			pendingclear();
			new Thread(new Runnable() {
			    @Override
				public void run() {
			JsonPostMethod postmethod = new JsonPostMethod();
			String response = postmethod.postmethodforbackup(localSystemTimeString, previousServerSyncTimeString, "abcedefg", "true");
			System.out.println("response is in serversync:"+response);
			backupcall(response);
			    }
			}).start();
		}else{
		String format = "yyyy-MM-dd hh:mm:ss";

	    SimpleDateFormat sdf = new SimpleDateFormat(format);

	    Date dateObj1;
		try {
			dateObj1 = sdf.parse(localSystemTimeString);
			 Date dateObj2 = sdf.parse(previousServerSyncTimeString);
			    System.out.println(dateObj1);
			    System.out.println(dateObj2);

			    diff = dateObj1.getTime() - dateObj2.getTime();
			    System.out.println("difference is:"+diff);
		} catch (ParseException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	   
	    /* double diffInHours = diff / ((double) 1000 * 60 * 60);
	    System.out.println(diffInHours);
	    System.out.println("Hours " + (int)diffInHours);
	    System.out.println("Minutes " + (diffInHours - (int)diffInHours)*60 );
	    System.out.println("Seconds " + (diffInHours - (int)diffInHours)*60*60 );*/
		
		new Thread(new Runnable() {
		    @Override
			public void run() {
		    	if(diff<6000){
		    	}else{
		    		pendingclear();
		    		JsonPostMethod postmethod = new JsonPostMethod();
		    		String response = postmethod.postmethodforbackup(localSystemTimeString, previousServerSyncTimeString, "abcedefg", "");
		    		System.out.println("response is in serversync:"+response);
		    		backupcall(response);
		    	}
		    	
		    }
		}).start();
		}
		demodbDB.close();
	}
	
	public void pendingclear(){
		System.out.println("pending clear is called in serversync");
		
		
		String selectQuerytotal = "SELECT  * FROM " + DatabaseForDemo.PENDING_QUERIES_TABLE+" where "+DatabaseForDemo.PENDING_USER_ID+"=\""
									+Parameters.userid+"\" ORDER BY "+DatabaseForDemo.CURRENT_TIME_PENDING+" ASC" ;
		Cursor cursortotale = dbforloginlogoutRead123.rawQuery(selectQuerytotal, null);
		int count = cursortotale.getCount();
		cursortotale.close();
		int offset = 0;
		for(; offset<count;){
		
		System.out.println("offset value is:"+offset);
		String selectQuery = "SELECT  * FROM " + DatabaseForDemo.PENDING_QUERIES_TABLE+" where "+DatabaseForDemo.PENDING_USER_ID+"=\""
								+Parameters.userid+"\" ORDER BY "+DatabaseForDemo.CURRENT_TIME_PENDING+" ASC limit 100 offset "+offset;
		if(count<100){
			offset = offset+count;
		}else{
			offset = offset+100;
			count = count - offset;
		}
		Cursor mCursorsesdf1 = dbforloginlogoutRead123.rawQuery(selectQuery, null);
		ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		String querytype, userid = null, pageurl, tablename = null, time = null, parameter = null;
		if(mCursorsesdf1!=null){
			if(mCursorsesdf1.moveToFirst()){
				do{
					try{
					HashMap<String, String> map = new HashMap<String, String>();	
					querytype =  mCursorsesdf1.getString(mCursorsesdf1.getColumnIndex(DatabaseForDemo.QUERY_TYPE));
					map.put("querytype", querytype);
					userid = mCursorsesdf1.getString(mCursorsesdf1.getColumnIndex(DatabaseForDemo.PENDING_USER_ID));
					map.put("userid", userid);
					pageurl = mCursorsesdf1.getString(mCursorsesdf1.getColumnIndex(DatabaseForDemo.PAGE_URL));
					map.put("pageurl", pageurl);
					tablename = mCursorsesdf1.getString(mCursorsesdf1.getColumnIndex(DatabaseForDemo.TABLE_NAME_PENDING));
					map.put("tablename", tablename);
					time = mCursorsesdf1.getString(mCursorsesdf1.getColumnIndex(DatabaseForDemo.CURRENT_TIME_PENDING));
					map.put("time", time);
					parameter = mCursorsesdf1.getString(mCursorsesdf1.getColumnIndex(DatabaseForDemo.PARAMETERS));
					map.put("parameter", parameter);
					data.add(map);
					}catch(Exception e){
						
					}
				}while(mCursorsesdf1.moveToNext());
		}
			
			JsonPostMethod jsonpost = new JsonPostMethod();
			for(int i=0; i<data.size();i++){
				if(data.get(i).get("querytype").equals("createadminprofile")){
					String responseproduct2 = jsonpost.postmethodforcreateprofile("01", "admin", data.get(i).get("parameter"));
					System.out.println("response is:"+responseproduct2);
					
					JSONObject obj;
					try {
						obj = new JSONObject(responseproduct2);
					
					String insertquery = obj.getString("insert-query");
					String deletequery = obj.getString("delete-query");
					String status = obj.getString("status");
					String message = obj.getString("message");
					
						String deletequery1 = deletequery.replace("'", "\"");
						String deletequery2 = deletequery1.replace("\\\"", "'");
						System.out.println("delete query is :"+deletequery2);
						
						String insertquery1 = insertquery.replace("'", "\"");
						String insertquery2 = insertquery1.replace("\\\"", "'");
						System.out.println("delete query is :"+insertquery2);
						
						dbforloginlogoutWrite123.execSQL(deletequery);
						dbforloginlogoutWrite123.execSQL(insertquery);
						System.out.println("queries executed");
					
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}catch (SQLException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				
				if(data.get(i).get("querytype").equals("userlogin")){
					String responseproduct2 = jsonpost.postmethodforlogininget1(data.get(i).get("parameter"));
					System.out.println("response is:"+responseproduct2);
					
					try {
						JSONObject obj = new JSONObject(responseproduct2);
						String loginstatus = obj.getString("login-status");
						if(loginstatus.equals("true")){
							String sessionid = obj.getString("sessionid");
							Parameters.sessionidforloginlogout = sessionid;
							String useridval = obj.getString("userid");
							Parameters.userid = useridval;
						}else{
							String message = obj.getString("message");
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if(data.get(i).get("querytype").equals("userlogout")){
					String responseproduct2 = jsonpost.postmethodforlogininget1(data.get(i).get("parameter"));
					System.out.println("response is:"+responseproduct2);
					
					try {
						JSONObject obj = new JSONObject(responseproduct2);
						String loginstatus = obj.getString("response");
						
							String sessionid = obj.getString("logout");
							if(sessionid.equals("true")){
								System.out.println("successfully logged out");
							}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		
			if(data.get(i).get("querytype").equals("insert")){
				String responseproduct2 = jsonpost.postmethodfordirect(data.get(i).get("userid"), "abcdefg", data.get(i).get("tablename"), data.get(i).get("time"), Parameters.currentTime(), data.get(i).get("parameter"), "");
				System.out.println("response is:"+responseproduct2);
				try {
					JSONObject obj = new JSONObject(responseproduct2);
					JSONArray array = obj.getJSONArray("insert-queries");
					System.out.println("array list length for insert is:"+array.length());
					JSONArray array2 = obj.getJSONArray("delete-queries");
					System.out.println("array2 list length for delete is:"+array2.length());
					for(int jj = 0,ii = 0; jj<array2.length() && ii<array.length(); jj++,ii++){
						try{
						String deletequerytemp = array2.getString(jj);
						String deletequery1 = deletequerytemp.replace("'", "\"");
						String deletequery = deletequery1.replace("\\\"", "'");
						System.out.println("delete query"+jj+" is :"+deletequery);
						
						String insertquerytemp = array.getString(ii);
						String insertquery1 = insertquerytemp.replace("'", "\"");
						String insertquery = insertquery1.replace("\\\"", "'");
						System.out.println("delete query"+jj+" is :"+insertquery);
						
						dbforloginlogoutWrite123.execSQL(deletequery);
						dbforloginlogoutWrite123.execSQL(insertquery);
						System.out.println("queries executed"+ii);
						}catch (SQLException eq) {
							// TODO: handle exception
							eq.printStackTrace();
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(data.get(i).get("querytype").equals("update")){
				String responseproduct2 = jsonpost.postmethodfordirect(data.get(i).get("userid"), "abcdefg", data.get(i).get("tablename"), data.get(i).get("time"), Parameters.currentTime(), data.get(i).get("parameter"), "true");
				System.out.println("response is:"+responseproduct2);
				try {
					JSONObject obj = new JSONObject(responseproduct2);
					JSONArray array = obj.getJSONArray("insert-queries");
					System.out.println("array list length for insert is:"+array.length());
					JSONArray array2 = obj.getJSONArray("delete-queries");
					System.out.println("array2 list length for delete is:"+array2.length());
					for(int jj = 0,ii = 0; jj<array2.length() && ii<array.length(); jj++,ii++){
						try{
						String deletequerytemp = array2.getString(jj);
						String deletequery1 = deletequerytemp.replace("'", "\"");
						String deletequery = deletequery1.replace("\\\"", "'");
						System.out.println("delete query"+jj+" is :"+deletequery);
						
						String insertquerytemp = array.getString(ii);
						String insertquery1 = insertquerytemp.replace("'", "\"");
						String insertquery = insertquery1.replace("\\\"", "'");
						System.out.println("delete query"+jj+" is :"+insertquery);
						
						dbforloginlogoutWrite123.execSQL(deletequery);
						dbforloginlogoutWrite123.execSQL(insertquery);
						System.out.println("queries executed"+ii);
						}catch (SQLException eq) {
							// TODO: handle exception
							eq.printStackTrace();
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(data.get(i).get("querytype").equals("delete")){
				String responseproduct2 = jsonpost.postmethodfordirectdelete(data.get(i).get("userid"), "abcdefg", data.get(i).get("tablename"), data.get(i).get("time"), Parameters.currentTime(), data.get(i).get("parameter"));
				System.out.println("response is:"+responseproduct2);
			}
			}
		}
		try{
		mCursorsesdf1.close();
		String truncatequery = "Delete from "+DatabaseForDemo.PENDING_QUERIES_TABLE;
		dbforloginlogoutWrite123.execSQL(truncatequery);
		}catch (SQLException eq) {
			// TODO: handle exception
			eq.printStackTrace();
		}
		}
		demodbDB.close();
	}
	
	public void backupcall(String response){
		System.out.println("backupcall is called");
		try {
			JSONObject obj = new JSONObject(response);
			boolean urlval = obj.has("next-url");
			System.out.println("url val is:"+urlval);
			String nexturl = obj.getString("next-url");
			System.out.println("next rul val is:"+nexturl);
			//rowcount = obj.getString("actual-row-count");
			//System.out.println("row count is:"+rowcount);
			String server_time = obj.getString("server-time");
			try{
			String select = "select *from "+DatabaseForDemo.MISCELLANEOUS_TABLE;
			Cursor cursorzz = dbforloginlogoutRead123.rawQuery(select, null);
			if(cursorzz.getCount()>0){
				try{
				dbforloginlogoutWrite123.execSQL("update "+DatabaseForDemo.MISCELLANEOUS_TABLE+" set "+DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL+"=\""+server_time+"\"");
				cursorzz.close();
				}catch (SQLException eq) {
					// TODO: handle exception
					eq.printStackTrace();
				}
			}else{
				try{
				cursorzz.close();
				ContentValues contentValues1 = new ContentValues();
				contentValues1.put(DatabaseForDemo.MISCEL_STORE,  "store1");
				contentValues1.put(DatabaseForDemo.MISCEL_PAGEURL, Parameters.OriginalUrl+"saveinfo.php");
				contentValues1.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL, Parameters.currentTime());
				contentValues1.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL, Parameters.currentTime());
				dbforloginlogoutWrite123.insert(DatabaseForDemo.MISCELLANEOUS_TABLE, null, contentValues1);
				}catch (SQLException eq) {
					// TODO: handle exception
					eq.printStackTrace();
				}
			}
			}catch (SQLException eq) {
				// TODO: handle exception
				eq.printStackTrace();
			}
			JSONArray queriesarray = obj.getJSONArray("queries-array");
			for(int i=0; i<queriesarray.length(); i++){
				try{
				String querytemp = queriesarray.getString(i);
				System.out.println("query value is:"+querytemp);
				String query1 = querytemp.replace("'", "\"");
				String query = query1.replace("\\\"", "'");
				System.out.println("delete query"+i+" is :"+query);
				
				dbforloginlogoutWrite123.execSQL(query);
				System.out.println("queries executed"+i);
				}catch (SQLException eq) {
					// TODO: handle exception
					eq.printStackTrace();
				}
			}
			if(nexturl.equals("null")){
				
			}else{
				JsonPostMethod postmethod = new JsonPostMethod();
				String responsetext = postmethod.postmethodforbackupnexttime(nexturl);
				backupcall(responsetext);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		demodbDB.close();
	}
	
}
