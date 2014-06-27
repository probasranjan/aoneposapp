package com.aoneposapp.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.aoneposapp.LoginHomeActivity;
import com.aoneposapp.MenuActivity;
import com.aoneposapp.PosMainActivity;
import com.aoneposapp.ShowMsg;
import com.aoneposapp.starprinter.PrinterFunctions;
import com.epson.eposprint.Builder;
import com.epson.eposprint.EposException;
import com.epson.eposprint.Print;

public class Parameters {
	static DatabaseForDemo demodbParam = new DatabaseForDemo(MyApplication.getAppContext());
	static SQLiteDatabase dbforloginlogoutWrite12 = demodbParam.getWritableDatabase();
	static SQLiteDatabase dbforloginlogoutRead12 = demodbParam.getReadableDatabase();
	public static String usertype = "admin";
	public static String userid;
	static Print printer1;
	public static Timer timer=null;
	public static String store_id = "";
	public static Context printerContext;
	public static boolean PrinterBool=true;
	//public static String sessionid;
	public static String paymentprocesstype = "Mercury Pay";//Express Manual
	public static String usertypeloginvalue="";
	public static String invoiceid_mercury="";
	public static String logintime="";
	public static String sessionidforloginlogout="";
	static DecimalFormat df = new DecimalFormat("#.##");
	static char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	static char[] numberr = "1234567890".toCharArray();
	/*public static String DemoUrl="http://www.mydata.ws/aoneposws/webservicedemo/";
	public static String OriginalUrl="http://www.mydata.ws/aoneposws/webserviceoriginal/";*/
	public static String DemoUrl="";
	//public static String OriginalUrl="http://192.168.1.6/xampp/sites/aone/crm/ws/sridevitech/";
	public static String OriginalUrl="";
//	public static String MyTempLocalServerURL = "http://development.dev01.vijaywebsolutions.com/ABCBurger/";
	public static String MyTempLocalServerURL = "http://www.mydata.ws/aoneposws/kababmenu/";
	public static String URL_UploadCSVForSynchronize = MyTempLocalServerURL+"synchronization.php";
//	public static String OriginalUrl="http://192.168.2.122/ABCBurger/";
//	public static String OriginalUrl="http://www.mydata.ws/aoneposws/webserviceoriginal/";
	
	//tables that to be Synchronize
	public static final String[] table_names = {
			"admin_details",
			"alternate_sku",
			"category_details",
			"customer_extended_info_table",
			"customer_general_info_table",
			"customer_shipping_table",
			"customer_stores_table",
			"customer_table",
//			"delete_queries",
			"department_details",
			"employee_payroll",
			"employee_permissions",
			"employee_personal",
			"employee_store",
			"employee_table",
//			"employees",
			"inventorytable",
			"invoice_items_table",
			"invoice_total_table",
			"login_logout_table",
			"mercury_pay_table",
//			"mobile_loggedin_users",
			"modifier_table",
			"optional_info_table",
			"ordering_info_table",
			"split_invoice_table",
			"stock_modification_history",
			"store_details",
			"vendor"};
	
	public static boolean dbbooleanvalue=false;
	public static String default_userName = "admin";
	public static String default_passWord="admin";
	public static String mSearchItemId;
	public static boolean mSearchItemStatus;
	public static boolean menufinish;
	//public static boolean mfindServer_url;
	public static double Cridetamount=0.00; 
	public static String sentAmount="0.00";
	public static String loggedinusertype="";
	public static String mercury_result="";
	public static boolean inventory_permission;
	public static boolean reports_permission;
	public static boolean settings_permission;
	public static boolean customer_permission;
	public static boolean allow_exit_permission;
	public static boolean delete_items_permission;
	public static boolean end_transaction_permission;
	public static boolean force_credit_cards_permission;
	public static boolean invoice_discounts_permission;
	public static boolean invoice_price_change_permission;
	public static boolean vendor_payouts_permission;
	public static boolean void_invoices_permission;
	public static boolean hold_print_permission;
	public static boolean end_cash_transaction_permission;
	
	public static boolean stores_permission;
	public static boolean profile_permission;
	public static boolean employee_permission;
	
	public static boolean hai;
	
	public static String randomValue() {

		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		return output;

	}
	public static String randomSession() {

		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			char c = chars[random.nextInt(numberr.length)];
			sb.append(c);
		}
		String output = sb.toString();
		return output;

	}
	/*public static String randomInvoice() {

		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			char c = chars[random.nextInt(numberr.length)];
			sb.append(c);
		}
		String output = sb.toString();
		return output;
	}*/
	
	public static String generateRandomNumber() {
		// TODO Auto-generated method stub
    	 Random randomGenerator = new Random();
 	    
 	      int randomInt = randomGenerator.nextInt(100000);
 	      String num = Integer.toString(randomInt);
 	    return num;
	}
	
	public static String currentTime() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = df.format(c.getTime());
		return formattedDate;
	}
	public static boolean isNetworkAvailable(Context context) 
	{
	    ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

	    if (connectivity != null) 
	    {
	        NetworkInfo[] info = connectivity.getAllNetworkInfo();
	        if (info != null) 
	        {
	            for (int i = 0; i < info.length; i++) 
	            {
	                Log.i("Class", info[i].getState().toString());
	                if (info[i].getState() == NetworkInfo.State.CONNECTED) 
	                {
	                	 Log.i("NetworkInfo.State", "CONNECTED");
	                    return true;
	                }
	            }
	        }
	    }
	    return false;
	}
	public static String MD5(String md5) {
		   try {
		        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		        byte[] array = md.digest(md5.getBytes());
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < array.length; ++i) {
		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
		       }
		        return sb.toString();
		    } catch (java.security.NoSuchAlgorithmException e) {
		    }
		    return null;
		}
	
	//hari
	public static boolean isTableExists(SQLiteDatabase db, String tableName)
	{
	    if (tableName == null || db == null || !db.isOpen())
	    {
	        return false;
	    }
	    Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
	    if (!cursor.moveToFirst())
	    {
	        return false;
	    }else{
	    int count = cursor.getInt(0);
	    cursor.close();
	    if(count > 0){
	    	Cursor cursor1 = db.rawQuery("SELECT * FROM "+tableName,null);
	    	
	    	 if (!cursor1.moveToFirst())
	 	    {
	 	        return false;
	 	    }
	    	 int count1 = cursor1.getInt(0);
	    	 Log.v("hhhh",""+count1);
	 	    cursor1.close();
	 	   return count > 0;
	    }
	    return false;
	}
	    }
	public static String getPrinterName(SQLiteDatabase db,String dpName) {
		Cursor mCursor = db.rawQuery("select * from " + DatabaseForDemo.DEPARTMENT_TABLE, null);

		if (mCursor.getCount() > 0) {
			if (mCursor != null) {
				if (mCursor.moveToFirst()) {
					do {
						return	mCursor.getString(mCursor.getColumnIndex(DatabaseForDemo.PrinterForDept))+","+mCursor.getString(mCursor.getColumnIndex(DatabaseForDemo.TimeForDeptPrint));
					} while (mCursor.moveToNext());
				}
			}
		}
		mCursor.close();
		return "";
	}
	
	public static void methodForLogout(Context cccc){
		try{
			menufinish=true;
		String urlval = "";
		if(OriginalUrl.equals("")){
			System.out.println("there is no server url val");
			String logintime = Parameters.logintime;
			String logouttime = Parameters.currentTime();
			String format = "yyyy-MM-dd hh:mm:ss";
		    SimpleDateFormat sdf = new SimpleDateFormat(format);
		    Date dateObj1 = null,dateObj2=null;
			try {
				dateObj1 = sdf.parse(logouttime);
				dateObj2 = sdf.parse(logintime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(dateObj1);
		    System.out.println(dateObj2);
		    long diff = dateObj1.getTime() - dateObj2.getTime();
		    double diffInHours =Double.valueOf(df.format( diff / ((double) 1000 * 60 * 60)));
		    System.out.println("difference in hours:"+diffInHours);
		    double diffinminutes = Double.valueOf(df.format((diffInHours - (int)diffInHours)*60));
		    System.out.println("difference in minutes:"+diffinminutes);
			
			String query2 = "update "+DatabaseForDemo.LOGIN_LOGOUT_TABLE+" set "+DatabaseForDemo.LOGOUT_TIME+"=\""+logouttime+"\","
					+DatabaseForDemo.DIFF_MINUTES+"=\""+diffinminutes+"\","+DatabaseForDemo.DIFF_HOURS+"=\""+diffInHours+"\" where "+DatabaseForDemo.SESSIONIDVAL+"=\""
									+Parameters.sessionidforloginlogout+"\"";
			
			dbforloginlogoutWrite12.execSQL(query2);
			Parameters.logintime = "";
			Parameters.sessionidforloginlogout = "";
			Intent i = new Intent(MyApplication.getAppContext(),LoginHomeActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		i.putExtra("EXIT", true);
    		MyApplication.getAppContext().startActivity(i);
		}else{
			boolean isnet = isNetworkAvailable(MyApplication.getAppContext());
			if(isnet){
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						 DatabaseForDemo demodbParamw = new DatabaseForDemo(MyApplication.getAppContext());
						 SQLiteDatabase dbforloginlogoutWrite12w = demodbParam.getWritableDatabase();
						JsonPostMethod jsonpost = new JsonPostMethod();
						String kurlval = "?username="+userid+"&sessionid="
								+sessionidforloginlogout+"&deviceid="+randomValue()+"&logout=true";
						
						String responseproduct2 = jsonpost.postmethodforlogininget1(kurlval);
						System.out.println("response is:"+responseproduct2);
						if(responseproduct2.length()>3){
						try {
							JSONObject obj = new JSONObject(responseproduct2);
							JSONObject obj1 =obj.getJSONObject("response");
								String sessionid = obj1.getString("logout");
								if(sessionid.equals("true")){
									System.out.println("successfully logged out");
									
									
									String logintime = Parameters.logintime;
									String logouttime = Parameters.currentTime();
									String format = "yyyy-MM-dd hh:mm:ss";
								    SimpleDateFormat sdf = new SimpleDateFormat(format);
								    Date dateObj1 = null,dateObj2=null;
									try {
										dateObj1 = sdf.parse(logouttime);
										dateObj2 = sdf.parse(logintime);
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									System.out.println(dateObj1);
								    System.out.println(dateObj2);
								    long diff = dateObj1.getTime() - dateObj2.getTime();
								    double diffInHours =Double.valueOf(df.format( diff / ((double) 1000 * 60 * 60)));
								    System.out.println("difference in hours:"+diffInHours);
								    double diffinminutes = Double.valueOf(df.format((diffInHours - (int)diffInHours)*60));
								    System.out.println("difference in minutes:"+diffinminutes);
									
									String query2 = "update "+DatabaseForDemo.LOGIN_LOGOUT_TABLE+" set "+DatabaseForDemo.LOGOUT_TIME+"=\""+logouttime+"\","
											+DatabaseForDemo.DIFF_MINUTES+"=\""+diffinminutes+"\","+DatabaseForDemo.DIFF_HOURS+"=\""+diffInHours+"\" where "+DatabaseForDemo.SESSIONIDVAL+"=\""
															+Parameters.sessionidforloginlogout+"\"";
									
									dbforloginlogoutWrite12w.execSQL(query2);
									/*Parameters.logintime = "";
									Parameters.sessionidforloginlogout = "";*/
								}
								demodbParamw.close();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						}
					}
				}).start();
				Intent i = new Intent(MyApplication.getAppContext(),LoginHomeActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        		i.putExtra("EXIT", true);
        		MyApplication.getAppContext().startActivity(i);
			}else{
				

				ContentValues contentValues1 = new ContentValues();
				contentValues1.put(DatabaseForDemo.QUERY_TYPE,  "userlogout");
				contentValues1.put(DatabaseForDemo.PENDING_USER_ID, userid);
				contentValues1.put(DatabaseForDemo.PAGE_URL,  "user-login.php");
				contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING, "");
				contentValues1.put(DatabaseForDemo.CURRENT_TIME_PENDING,currentTime());
				contentValues1.put(DatabaseForDemo.PARAMETERS, urlval);
				dbforloginlogoutWrite12.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null, contentValues1);
				
			}
		}				
		}catch(Exception e){
			System.out.println(e);
		}
		demodbParam.close();
	}
	public static boolean URLIsReachable(String urlString)
	{
	    try
	    {
	        URL url = new URL(urlString);
	        HttpURLConnection urlConnection = (HttpURLConnection) url
	                .openConnection();
	      int  responseCode = urlConnection.getResponseCode();
	        urlConnection.disconnect();
	        return responseCode != 200;
	    } catch (MalformedURLException e)
	    {
	        e.printStackTrace();
	        return false;
	    } catch (IOException e)
	    {
	        e.printStackTrace();
	        return false;
	    }
	}
	public static String printTextgetFromDatabase(final Context context,String printerid) {
		boolean forprintid = false;
		String printreturnString="";
			String selectQuery = "SELECT  * FROM "
					+ DatabaseForDemo.PRINTER_TABLE; 
			Cursor mCursor1 = dbforloginlogoutWrite12.rawQuery(selectQuery, null);
			if (mCursor1 != null) {
				if (mCursor1.getCount() > 0) {
					if (mCursor1.moveToFirst()) {
						do {
							if (printerid .equals(mCursor1.getString(mCursor1 .getColumnIndex(DatabaseForDemo.PRINTER_ID)))) {
							 printreturnString=""+mCursor1.getString(mCursor1 .getColumnIndex(DatabaseForDemo.PRINTER_TEXT));
								forprintid = true;
							}

						} while (mCursor1.moveToNext());
					}
					if (forprintid) {

					} else {
						if (mCursor1.moveToFirst()) {
							 printreturnString=""+mCursor1.getString(mCursor1
										.getColumnIndex(DatabaseForDemo.PRINTER_TEXT));
						}
					}
				}
			}
			mCursor1.close();
			demodbParam.close();
return printreturnString;
	}
	
	public static void theardRunForPrint() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Timer timer = new Timer(false);
				TimerTask timerTask = new TimerTask() {
					@Override
					public void run() {
try{
						String sql = "select * from "
								+ DatabaseForDemo.COMMANDS_PRINTER_TABLE;
						Cursor mCursor = dbforloginlogoutRead12.rawQuery(sql, null);
						if (mCursor != null) {
							if (mCursor.getCount() > 0) {
								if (mCursor.moveToFirst()) {
									do {
										String catid = mCursor.getString(mCursor
												.getColumnIndex(DatabaseForDemo.COMMANDS_TIME));
										if(catid.length()>0){
										}else{
											catid="0";
										}
										long tine = Long.valueOf(catid);
										long noww = System.currentTimeMillis();
										String check = mCursor.getString(mCursor
												.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
										if (noww > tine) {
											String printerid = mCursor.getString(mCursor
													.getColumnIndex(DatabaseForDemo.COMMANDS_PRINTER_NAME));

											String itemname =mCursor.getString(mCursor
															.getColumnIndex(DatabaseForDemo.COMMANDS_ITEM_NAME));
											String holdid =mCursor.getString(mCursor
													.getColumnIndex(DatabaseForDemo.COMMANDS_HOLDID));
													String msg= (mCursor.getString(mCursor
															.getColumnIndex(DatabaseForDemo.COMMANDS_MESSAGE)) + "\n");
											if (printerid.equals("None")) {

											} else {
												Message msgObj = handler
														.obtainMessage();
												Bundle b = new Bundle();
												b.putString("message", ""
														+ msg);
												b.putString("itemname", ""
														+ itemname);
												b.putString("id", ""
														+ printerid);
												b.putString("holdid", ""
														+ holdid);
												
												msgObj.setData(b);
												handler.sendMessage(msgObj);
											}
											String where = DatabaseForDemo.UNIQUE_ID
													+ "=?";
											dbforloginlogoutWrite12.delete(
													DatabaseForDemo.COMMANDS_PRINTER_TABLE,
													where,
													new String[] { check });
										}
									} while (mCursor.moveToNext());
								}
							}
							mCursor.close();
						}
}catch (NumberFormatException e) {
	// TODO: handle exception
	e.getLocalizedMessage();
}catch(Exception e1){
	e1.getLocalizedMessage();
}
					}
				};
				timer.schedule(timerTask, 0, 20000);
			}

			private final Handler handler = new Handler() {

				@Override
				public void handleMessage(Message msg) {
					try{
					String note = msg.getData().getString("message");
					String itemname = msg.getData().getString("itemname");
					String idvalue = msg.getData().getString("id");
					String holdid = msg.getData().getString("holdid");
					String itemPrintText="\t Kitchen \n"	+
							               " "+currentTime()+
							               "\n \n Sever: "+userid+
							               "\n Station: "+store_id+
							               "\n Item Name: "+itemname+
							               "\n"+note+
							               "\n Invoice Id: "+holdid+"\n \n";
					
					if ((null != itemname)) {
						ArrayList<String> printVal = new ArrayList<String>();
						if (isTableExists(dbforloginlogoutRead12,
								DatabaseForDemo.PRINTER_TABLE)) {
							String selectQuery = "SELECT  * FROM "
									+ DatabaseForDemo.PRINTER_TABLE + " where "
									+ DatabaseForDemo.PRINTER_ID + "=\""
									+ idvalue + "\"";
							Cursor mCursor1 = dbforloginlogoutRead12.rawQuery(selectQuery, null);
							int count = mCursor1.getColumnCount();
							System.out.println(count);
							if (mCursor1 != null) {
								if (mCursor1.getCount() > 0) {
									if (mCursor1.moveToFirst()) {
										do {
											for (int i = 0; i < count; i++) {
												String value = mCursor1
														.getString(i);
												printVal.add(value);
											}
										} while (mCursor1.moveToNext());
									}
								}
							}
							mCursor1.close();
						}
						if (printVal.size() > 14) {
							if (printVal.get(15).equals("EPSON")) {
								Builder builder = null;
								String method = "";
								try {
									// create builder
									method = "Builder";
									try {
										builder = new Builder(""
												+ printVal.get(12), 0,
												printerContext);
									} catch (Exception e) {
e.getLocalizedMessage();
									}
									method = "addTextFont";
									builder.addTextFont(Integer
											.parseInt(printVal.get(2)));

									method = "addTextAlign";
									builder.addTextAlign(Integer
											.parseInt(printVal.get(3)));
									method = "addTextLineSpace";
									builder.addTextLineSpace(Integer
											.parseInt(printVal.get(4)));

									method = "addTextLang";
									builder.addTextLang(Integer
											.parseInt(printVal.get(5)));

									method = "addTextSize";
									builder.addTextSize(
											Integer.parseInt(printVal.get(6)) + 1,
											Integer.parseInt(printVal.get(7)) + 1);

									method = "addTextStyle";
									builder.addTextStyle(Builder.FALSE,
											Integer.parseInt(printVal.get(9)),
											Integer.parseInt(printVal.get(8)),
											Builder.COLOR_1);

									method = "addTextPosition";
									builder.addTextPosition(Integer
											.parseInt(printVal.get(10)));

									method = "addText";
									builder.addText("\t" + printVal.get(1)
											+ " \n " + itemPrintText);

									method = "addFeedUnit";
									builder.addFeedUnit(Integer
											.parseInt(printVal.get(11)));
									builder.addCut(Builder.CUT_FEED);
									// send builder data
									int[] status = new int[1];
									int[] battery = new int[1];

									try {

										int deviceType = Print.DEVTYPE_TCP;

										try {
											printer1 = new Print(
													printerContext);
											printer1.openPrinter(deviceType, ""
													+ printVal.get(13),
													Print.TRUE, 1000);

										} catch (Exception e) {
											ShowMsg.showException(e,
													"openPrinter",
													printerContext);
											return;
										}
										printer1.sendData(builder,
												Constants.SEND_TIMEOUT, status,
												battery);
										printer1.closePrinter();
									} catch (EposException e) {
										ShowMsg.showStatus(e.getErrorStatus(),
												e.getPrinterStatus(),
												e.getBatteryStatus(),
												printerContext);
										printer1.closePrinter();
									}
								} catch (Exception e) {
									ShowMsg.showException(e, method,
											printerContext);
									try {
										if(printer1!=null)
										printer1.closePrinter();
									} catch (EposException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									printer1 = null;
								}

								// remove builder
								if (builder != null) {
									try {
										builder.clearCommandBuffer();
										builder = null;
									} catch (Exception e) {
										builder = null;
									}
								}

								try {
									printer1.closePrinter();
								} catch (EposException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								printer1 = null;
							} else {
								try {
									String commandType  = "Raster";
									String typp="TCP:";
									if(printVal.get(2).equals("BT:")){
										typp="BT:";
									}
						        	PrinterFunctions.PrintSampleReceipt(printerContext,  typp+""+printVal.get(13),   "",  commandType, printerContext.getResources(), "3inch (78mm)",  "\t  " + printVal.get(1)+ " \n " + itemPrintText);
								} catch (Exception e) {
									System.out.println(e);
								}
							}
						} else {
							Toast.makeText(printerContext,
									"Set The Printer Settings", 1000).show();
						}

					}
					}catch (Exception e) {
						// TODO: handle exception
						e.getLocalizedMessage();
					}
				}
			};
		}).start();
	}
	public static void stopTimer(){
		timer.cancel();
		timer=null;
		Log.e("timer is","stoped");
	}
public static void ServerSyncTimer(){
	if(timer==null){
		timer = new Timer(false);
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				Log.e("timer is"," Start");
Log.e("timer","executed");
Log.i("timer","executed");
Log.d("timer","executed");
Log.v("timer","executed");
Log.w("timer","executed");
Log.wtf("timer","executed");
				if (OriginalUrl.equals("")) {
					System.out.println("there is no server url val");
				} else {
					boolean isnet =isNetworkAvailable(MyApplication.getAppContext());
					if (isnet) {
						ServerSyncClass synccall = new ServerSyncClass();
						System.out.println("class object is created");
						try {
							synccall.generalMethod();
						} catch (ParseException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						System.out.println("method is executed");

					} else {

					}
			}
			}
		};
		Parameters.timer.schedule(timerTask, 0, 200000);
	}else{
		Log.e("allready","timer is running");
	}
	}
}
