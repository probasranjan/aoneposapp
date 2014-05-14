package com.aoneposapp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.aoneposapp.utils.AsyncTaskInterface;
import com.aoneposapp.utils.AsyncTaskUtility;
import com.aoneposapp.utils.DatabaseForDemo;
import com.aoneposapp.utils.GetJSONListener;
import com.aoneposapp.utils.JSONforDemoData;
import com.aoneposapp.utils.JsonPostWithAsnk;
import com.aoneposapp.utils.LoginReturnJson;
import com.aoneposapp.utils.Parameters;

@SuppressLint("NewApi")
public class LoginHomeActivity extends Activity {
	private EditText userName, passWord;
	private ImageView loginButton;
	private String uName = " ", pWord = " ", status = "a",status_first="w";
	Button filebutton;
	//	String urlval = "";
	String name, cost, no, dp, vlu, pri, st, tax, vndrNo, vndrName, qty, price,
	catid;
	TextView tv1, tv2, tv3;
	ImageView imageView;
	public final Pattern EMAIL_ADDRESS_PATTERN = Pattern
			.compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");
	RelativeLayout imageLayout, loginLayout, topView;
	String data = "      Retail Cash Register \n \n"
			+ "   AonePos Software and Database Version  4.3Jelly Bean \n"
			+ "   Production Version : 4.3 Jelly Bean   \n \n"
			+ "         Designed and Developed By \n"
			+ "                   AonePos \n \n"
			+ "   RCR 2012 20012-2013 AonePos \n \n"
			+ "   Model: Retail Cash Register Copyright 2012-2014 AonePos  \n \n";

	String data1 = " \n NTEP Ceritificate of Conformance # \n"
			+ "      View Cerificate: PDF  \n \n"
			+ "Operating System : Android 4.3 Jelly Bean \n" + "Tablet Name:"
			+ "Store ID :  01";
	public static ArrayList<NameValuePair> arr = new ArrayList<NameValuePair>();
	boolean isOpened = false;

	DatabaseForDemo sqlA1;
	SQLiteDatabase dbforloginlogoutWrite,dbforloginlogoutRead;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_home);
		Parameters.printerContext=LoginHomeActivity.this;
		System.gc();
		Parameters.menufinish=false;
		try{
			DigitalClock dc = (DigitalClock) findViewById(R.id.digitalClock1);
			imageLayout = (RelativeLayout) findViewById(R.id.imageLayout);
			loginLayout = (RelativeLayout) findViewById(R.id.loginlayout);
			topView = (RelativeLayout) findViewById(R.id.topview);
			Parameters.ServerSyncTimer();
			sqlA1 = new DatabaseForDemo(LoginHomeActivity.this);
//			sqlA1.open();
			dbforloginlogoutWrite = sqlA1.getWritableDatabase();
			dbforloginlogoutRead = sqlA1.getReadableDatabase();
			sqlA1.onUpgrade(dbforloginlogoutWrite, 1, 4);

			final View activityRootView = getWindow().getDecorView().findViewById( android.R.id.content);
			activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(
					new OnGlobalLayoutListener() {
						@Override
						public void onGlobalLayout() {

							int heightDiff = activityRootView.getRootView() .getHeight() - activityRootView.getHeight();
							if (heightDiff > 100) {
								Log.v("hari", "keybooasfdsdk");
								topView.setVisibility(View.GONE);
								isOpened = true;
							} else if (isOpened == true) {
								Log.v("hari", "ramaseetha");
								isOpened = false;
								topView.setVisibility(View.VISIBLE);
							}
						}
					});

			String paymenttypevalis = "select "+DatabaseForDemo.PaymentProcessorName+" from "+DatabaseForDemo.PaymentProcessorPreferences;
			Cursor cursordedf = dbforloginlogoutRead.rawQuery(paymenttypevalis, null);
			// startManagingCursor(cursordedf);
			if(cursordedf!=null){
				if(cursordedf.getCount()>0){
					if(cursordedf.moveToFirst()){
						do{
							if(cursordedf.isNull(cursordedf.getColumnIndex(DatabaseForDemo.PaymentProcessorName))){
								Parameters.paymentprocesstype = "Express Manual";
							}else{
								Parameters.paymentprocesstype = cursordedf.getString(cursordedf.getColumnIndex(DatabaseForDemo.PaymentProcessorName));
							}
						}while(cursordedf.moveToNext());
					}
				}
			}
			cursordedf.close();
			boolean isurlval = false;
			String query = "select " + DatabaseForDemo.MISCEL_PAGEURL + " from " + DatabaseForDemo.MISCELLANEOUS_TABLE;
			Cursor miscur879 = dbforloginlogoutRead.rawQuery(query, null);
			if (miscur879 != null) 
			{
				if (miscur879.getCount() > 0) 
				{
					if (miscur879.moveToFirst()) 
					{
						do 
						{
							if (miscur879 .isNull(miscur879 .getColumnIndex(DatabaseForDemo.MISCEL_PAGEURL))) 
							{
								isurlval = false;
							} 
							else
							{
								String urlval = miscur879.getString(miscur879.getColumnIndex(DatabaseForDemo.MISCEL_PAGEURL));
								isurlval = true;
								Parameters.OriginalUrl = urlval;
								System.out.println("url val is:" + urlval);
							}
						} while (miscur879.moveToNext());
					}
				}
			}
			miscur879.close();
			String selectQuery = "SELECT  * FROM " + DatabaseForDemo.INVENTORY_TABLE;
			Cursor mCursors201 = dbforloginlogoutRead.rawQuery(selectQuery, null);
			if (mCursors201.getCount()>0 || isurlval == true) {

			}else{ 
				String atavffal="asdf";
				JSONforDemoData client = new JSONforDemoData(LoginHomeActivity.this);
				client.execute(atavffal);	    	
			}
			mCursors201.close();
			Log.v("ddd", "sitharam");
			loginButton = (ImageView) findViewById(R.id.imageButton);
			filebutton = (Button) findViewById(R.id.filebutton);
			userName = (EditText) findViewById(R.id.userName);
			passWord = (EditText) findViewById(R.id.passWord);
			passWord.setOnEditorActionListener(new OnEditorActionListener() 
			{
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) 
				{
					if (actionId == EditorInfo.IME_ACTION_DONE) 
					{
						loginMainMethod();
					}
					return false;
				}
			});

			tv1 = (TextView) findViewById(R.id.textView1);
			tv2 = (TextView) findViewById(R.id.textView2);
			tv3 = (TextView) findViewById(R.id.textView3);
			imageView = (ImageView) findViewById(R.id.companylogo);
			File image = new File("/mnt/sdcard/harinath/hari.jpg");
			if (image.exists()) 
			{
				imageView.setImageBitmap(BitmapFactory.decodeFile(image .getAbsolutePath()));
			}

			loginButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					loginMainMethod();
				}

			});

			filebutton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					final AlertDialog alertDialog2 = new AlertDialog.Builder(
							LoginHomeActivity.this,
							android.R.style.Theme_Translucent_NoTitleBar).create();
					LayoutInflater mInflater1 = LayoutInflater .from(LoginHomeActivity.this);
					View layout1 = mInflater1.inflate(R.layout.options_popup, null);
					final ImageView nameR = (ImageView) layout1
							.findViewById(R.id.imageView1);
					final ImageView emailR = (ImageView) layout1
							.findViewById(R.id.imageView2);
					final ImageView phoneR = (ImageView) layout1
							.findViewById(R.id.imageView3);

					nameR.setOnClickListener(new OnClickListener() {

						@Override		
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							nameR.setBackgroundResource(R.drawable.registerbuttonpressed);
							alertDialog2.dismiss();
							final AlertDialog alertDialog2 = new AlertDialog.Builder(
									LoginHomeActivity.this).create();
							LayoutInflater mInflater1 = LayoutInflater
									.from(LoginHomeActivity.this);
							View layout1 = mInflater1.inflate(
									R.layout.register_xml, null);
							Button accept = (Button) layout1
									.findViewById(R.id.button1);
							Button decline = (Button) layout1
									.findViewById(R.id.button2);
							final EditText nameR = (EditText) layout1
									.findViewById(R.id.nameR);
							final EditText emailR = (EditText) layout1
									.findViewById(R.id.emailR);
							final EditText phoneR = (EditText) layout1
									.findViewById(R.id.phoneR);
							final EditText companyR = (EditText) layout1
									.findViewById(R.id.companyR);
							// WebView licence=(WebView)
							// layout1.findViewById(R.id.licence);

							// licence.loadUrl("file:///android_asset/register.html");

							accept.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub

									String name = nameR.getText().toString().trim();
									String email = emailR.getText().toString()
											.trim();
									String phone = phoneR.getText().toString()
											.trim();
									String company = companyR.getText().toString()
											.trim();
									if (name.length() > 3) {
										if (checkEmail(email)) {
											if (company.length() > 3) {
												String url = "http://www.aonepos.com/customer-register.php";
												JsonPostWithAsnk client1 = new JsonPostWithAsnk(
														LoginHomeActivity.this, l,
														name, email, phone, company);
												client1.execute(url);

												alertDialog2.dismiss();
											} else {
												Toast.makeText(
														LoginHomeActivity.this,
														"Enter Full Company Name",
														Toast.LENGTH_SHORT).show();
											}
										} else {
											Toast.makeText(LoginHomeActivity.this,
													"Invalid Email Addresss",
													Toast.LENGTH_SHORT).show();
										}
									} else {
										Toast.makeText(getApplicationContext(),
												"Enter Name above 4 Characters",
												2000).show();
									}

								}
							});
							decline.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
									alertDialog2.dismiss();
								}
							});
							alertDialog2.setView(layout1);
							alertDialog2.getWindow().setSoftInputMode(
									WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
							alertDialog2.show();

						}
					});
					emailR.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							emailR.setBackgroundResource(R.drawable.aboutbuttonpressed);
							alertDialog2.dismiss();
							final AlertDialog alertDialog1 = new AlertDialog.Builder(
									LoginHomeActivity.this).create();
							LayoutInflater mInflater = LayoutInflater
									.from(LoginHomeActivity.this);
							View layout = mInflater
									.inflate(R.layout.about_us, null);
							Button ok = (Button) layout.findViewById(R.id.okkk);
							TextView viewlicense = (TextView) layout
									.findViewById(R.id.viewlicense);
							TextView topnormaltext = (TextView) layout
									.findViewById(R.id.topnormaltext);
							TextView Buttomtext = (TextView) layout
									.findViewById(R.id.Buttomtext);
							topnormaltext.setText(data);
							Buttomtext.setText(data1);
							viewlicense.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
									final AlertDialog alertDialog2 = new AlertDialog.Builder(
											LoginHomeActivity.this).create();
									LayoutInflater mInflater1 = LayoutInflater
											.from(LoginHomeActivity.this);
									View layout1 = mInflater1.inflate(
											R.layout.licence_agreement, null);
									Button accept = (Button) layout1
											.findViewById(R.id.accept);
									Button decline = (Button) layout1
											.findViewById(R.id.decline);
									WebView licence = (WebView) layout1
											.findViewById(R.id.licence);

									licence.loadUrl("file:///android_asset/register.html");

									accept.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View arg0) {
											// TODO Auto-generated method stub
											alertDialog2.dismiss();
										}
									});
									decline.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View arg0) {
											// TODO Auto-generated method stub
											alertDialog2.dismiss();
										}
									});
									alertDialog2.setView(layout1);
									alertDialog2.show();
								}
							});
							ok.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
									alertDialog1.dismiss();
								}
							});
							alertDialog1.setView(layout);
							alertDialog1.show();
						}
					});
					phoneR.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							alertDialog2.dismiss();
							finish();

						}
					});
					alertDialog2.setView(layout1);
					alertDialog2.show();
					// filemenu.setVisibility(View.VISIBLE);
				}
			});
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		try{		
			Parameters.printerContext=LoginHomeActivity.this;
			imageView = (ImageView) findViewById(R.id.companylogo);
			File image = new File("/mnt/sdcard/harinath/hari.jpg");
			if (image.exists()) {
				imageView.setImageBitmap(BitmapFactory.decodeFile(image
						.getAbsolutePath()));
			}
			Parameters.menufinish=false;
//			sqlA1 = new DatabaseForDemo(LoginHomeActivity.this);
//			dbforloginlogoutWrite = sqlA1.getWritableDatabase();
//			dbforloginlogoutRead = sqlA1.getReadableDatabase();
			super.onResume();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	GetJSONListener l = new GetJSONListener() {

		@Override
		public void onRemoteCallComplete(JSONObject jsonFromNet) {

			if(jsonFromNet.length()>2){
				Log.v("josn", "" + jsonFromNet);
				toastForRegister(jsonFromNet);
			}

		}

		private void toastForRegister(JSONObject jsonFromNet) {
			// TODO Auto-generated method stub
			if(jsonFromNet!=null&&jsonFromNet.length()>2){
				try {
					JSONObject job = jsonFromNet.getJSONObject("response");
					String msg = job.getString("status");
					Toast.makeText(getApplicationContext(), "" + msg,
							Toast.LENGTH_LONG).show();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		private void jsonParseFormServer(JSONObject jsonFromNet) {
			// TODO Auto-generated method stub
			if(jsonFromNet!=null&&jsonFromNet.length()>2){ 
				try {
					JSONArray deletequeries = jsonFromNet
							.getJSONArray("delete-queries");
					for (int i = 0; i < deletequeries.length(); i++) {
						String dQueries = deletequeries.getString(i);
						Log.v("DQ", dQueries);

						dbforloginlogoutWrite.execSQL(dQueries);

						Toast.makeText(LoginHomeActivity.this, "deleted", 1000)
						.show();
					}
					JSONArray insertqueries = jsonFromNet
							.getJSONArray("insert-queries");
					for (int i = 0; i < insertqueries.length(); i++) {
						String iQueries = insertqueries.getString(i);
						Log.v("DQ", iQueries);

						dbforloginlogoutWrite.execSQL(iQueries);

						Toast.makeText(LoginHomeActivity.this, "inserted", 1000)
						.show();
					}
					JSONObject response = jsonFromNet.getJSONObject("response");
					String severtime = response.getString("server-time");
					String timeinSeconds = response
							.getString("time-difference-in-seconds");
					Log.v("DQ" + severtime, timeinSeconds);
					// // String severtime=response.getString("server-time");
					// String severtime=response.getString("server-time");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				//			Toast.makeText(EmployeeActivity.this, "Server Error", 1000).show();
				Log.v("errorr", "server");
			}
		}
	};

	@Override
	public void onBackPressed() {

	};

	private boolean checkEmail(String mail) {
		return EMAIL_ADDRESS_PATTERN.matcher(mail).matches();
	}

	private void orDatabase() {
		String cat_id = "", dp_dp = "", dp_id = "", food = "";

		String selectQuery = "SELECT  * FROM "
				+ DatabaseForDemo.INVENTORY_TABLE;
		Cursor mCursors202 = dbforloginlogoutWrite.rawQuery(selectQuery, null);
		if (mCursors202 != null) {
			Log.v("gfdkg", "super333");
			String server_Url = "nnn";
			if (mCursors202.getCount() < 10) {
				Log.v("gfdkg", "super2");
				int p = 0;
				try {

					POIFSFileSystem myFileSystem = new POIFSFileSystem( getAssets().open("department.xls"));
					HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
					HSSFSheet mySheet = myWorkBook.getSheetAt(0);
					Iterator<Row> rowIter = mySheet.rowIterator();
					while (rowIter.hasNext()) {
						HSSFRow myRow = (HSSFRow) rowIter.next();
						Iterator<Cell> cellIter = myRow.cellIterator();
						if (p <= 13) {
							p++;
							int i = 0;

							while (cellIter.hasNext()) {
								HSSFCell myCell = (HSSFCell) cellIter.next();
								Log.e("  myCell", "" + myCell);
								if (i == 0)
									cat_id = "" + myCell;
								if (i == 1)
									dp_id = "" + myCell;
								if (i == 2)
									dp_dp = "" + myCell;
								if (i == 3)
									food = "" + myCell;

								i++;
							}
							String random = Parameters.randomValue();
							String now_date = Parameters.currentTime();

							ContentValues contentValues = new ContentValues();

							contentValues.put(DatabaseForDemo.UNIQUE_ID, Parameters.randomValue());
							contentValues.put(DatabaseForDemo.CREATED_DATE, Parameters.currentTime());
							contentValues.put(DatabaseForDemo.MODIFIED_DATE, Parameters.currentTime());
							contentValues.put(DatabaseForDemo.MODIFIED_IN, "Local");
							contentValues.put(DatabaseForDemo.DepartmentID, dp_id);
							contentValues.put(DatabaseForDemo.DepartmentDesp, dp_dp);
							contentValues.put( DatabaseForDemo.CategoryForDepartment, cat_id);
							contentValues.put( DatabaseForDemo.FoodstampableForDept, "no");
							contentValues.put(DatabaseForDemo.TaxValForDept, "");
							contentValues.put(DatabaseForDemo.CHECKED_VALUE, "true");
							dbforloginlogoutWrite.insert( DatabaseForDemo.DEPARTMENT_TABLE, null, contentValues);
						}
					}

				} catch (Exception ex) {
				}

			} else {

			}
		}
		mCursors202.close();
	}

	private void orDatabaseCat() {
		String cat_id = "", cat_cat = "";

		String selectQuery = "SELECT  * FROM "
				+ DatabaseForDemo.INVENTORY_TABLE;
		Cursor mCursors203 = dbforloginlogoutWrite.rawQuery(selectQuery, null);
		if (mCursors203 != null) {
			Log.v("gfdkg", "super333");
			if (mCursors203.getCount() < 10) {
				Log.v("gfdkg", "super2");
				int p = 0;
				try {

					POIFSFileSystem myFileSystem = new POIFSFileSystem(
							getAssets().open("categoryaaa.xls"));
					Log.e(" before myFileSystem", "" + myFileSystem);
					// Create a workbookusing the File System
					HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
					Log.e(" before myWorkBook", "" + myWorkBook);
					HSSFSheet mySheet = myWorkBook.getSheetAt(0);
					Iterator<Row> rowIter = mySheet.rowIterator();
					while (rowIter.hasNext()) {
						HSSFRow myRow = (HSSFRow) rowIter.next();
						Iterator<Cell> cellIter = myRow.cellIterator();
						if (p <= 10) {
							p++;
							int i = 0;

							while (cellIter.hasNext()) {
								HSSFCell myCell = (HSSFCell) cellIter.next();
								Log.e("  myCell", "" + myCell);
								if (i == 0)
									cat_id = "" + myCell;
								if (i == 1)
									cat_cat = "" + myCell;
								i++;
							}

							ContentValues contentValues = new ContentValues();
							contentValues.put(DatabaseForDemo.UNIQUE_ID, Parameters.randomValue());
							contentValues.put(DatabaseForDemo.CREATED_DATE, Parameters.currentTime());
							contentValues.put(DatabaseForDemo.MODIFIED_DATE, Parameters.currentTime());
							contentValues.put(DatabaseForDemo.MODIFIED_IN, "Local");
							contentValues.put(DatabaseForDemo.CategoryId, cat_id);
							contentValues.put(DatabaseForDemo.CategoryDesp, cat_cat);
							dbforloginlogoutWrite.insert( DatabaseForDemo.CATEGORY_TABLE, null, contentValues);
						}
					}

				} catch (Exception ex) {
				}

			} else {

			}
		}
		mCursors203.close();
	}
	/**
	 * 
	 */
	void loginFunction() 
	{
		Log.d("LoginHomeActivity","loginFunction() Enter");
		try{
			Parameters.logintime = Parameters.currentTime();
			uName = userName.getText().toString().trim();
			pWord = passWord.getText().toString().trim();
			Parameters.sessionidforloginlogout ="";
			if(uName.length()==0 || pWord.length()==0)
			{
				Toast.makeText(getApplicationContext(), "Enter Username and password", 1000).show();
				return;
			}
			if (Parameters.OriginalUrl.equals("")) 
			{
				System.out.println("there is no server url val");
				if (uName != null || pWord != null) 
				{
					Cursor cursorlogin = dbforloginlogoutRead.rawQuery(
							"select * from " + DatabaseForDemo.ADMIN_TABLE+" where "+DatabaseForDemo.USERID+"='"+uName+"' and "
									+DatabaseForDemo.PASSWORD+"='"+Parameters.MD5(pWord)+"';", null);
					if (cursorlogin.getCount() > 0)
					{
						Log.d("sss", "dbforloginlogoutRead.close()");
						
						Intent intent = new Intent(LoginHomeActivity.this, MenuActivity.class);
						startActivity(intent);
						Parameters.loggedinusertype = "admin";
						Parameters.usertype="admin";
						Parameters.userid = uName;
						Parameters.usertypeloginvalue = uName;
						adminPermissions(true);
						insertLoginTime();

					} 
					else 
					{
						Log.d("sss", "else1");
						Cursor cursoremp = dbforloginlogoutRead.rawQuery(
								"select * from " + DatabaseForDemo.EMPLOYEE_TABLE+" where "+DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID+"='"+uName+"' and "
										+DatabaseForDemo.EMPLOYEE_PASSWORD+"='"+Parameters.MD5(pWord)+"';",
										null);
						if (cursoremp.getCount() > 0) 
						{
							Log.d("sss", "else2");
							Intent intent = new Intent(LoginHomeActivity.this, MenuActivity.class);
							startActivity(intent);
							permissions();
							insertLoginTime();
							Parameters.loggedinusertype = "employee";
							Parameters.usertype="employee";
							Parameters.userid = uName;
							Parameters.usertypeloginvalue = uName;
							setEmployeePermissions();

						}
						else
						{
							Log.d("sss", "else3");
							if (uName.equals("01") && pWord.equals("admin")) 
							{
								Log.d("sss", "else4");
								Parameters.loggedinusertype = "admin";
								Parameters.usertype="admin";
								Parameters.userid = uName;
								Parameters.usertypeloginvalue = uName;
								adminPermissions(true);
								insertLoginTime();
								permissions();
								Log.d("sss", "mmmmmmm");
								Intent intent = new Intent(LoginHomeActivity.this, MenuActivity.class);
								Log.d("sss", "nnnnnnnnnn");
								startActivity(intent);
//								onDestroy();
								LoginHomeActivity.this.finish();
								Log.d("sss", "oooooooo");
								
							} 
							else 
							{
								Toast.makeText(getApplicationContext(), "Enter correct Username and password", 1000).show();
							}
						}
						Log.d("sss", "111111");
						cursoremp.close();// close the cursor
					}
					cursorlogin.close();// close the cursor
					Log.d("sss", "22222222");
					
				}
				else 
				{
					Toast.makeText(getApplicationContext(), "Enter correct Username and password", 1000).show();
				}
			} 
			else 
			{
				System.out.println("server url val: "+Parameters.OriginalUrl);

				if (Parameters.isNetworkAvailable(LoginHomeActivity.this))
				{
//					String ataval=Parameters.OriginalUrl+"user-login.php?username="+uName+"&password="+Parameters.MD5(pWord)+"&deviceid="
//							+Parameters.randomValue()+"&systemtime="+Parameters.currentTime()+"&Currentsystemtime=".replace(" ", "%20");
//					System.out.println("ataval: "+ataval);

//					JSONLoginFunction client = new JSONLoginFunction(LoginHomeActivity.this, forlogin);
//					Log.d("sss", "ppppppppppp");
//					client.execute(ataval);	  
					
					// get the server response
					
					HashMap<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("username",uName );
					hashMap.put("password",Parameters.MD5(pWord) );
					hashMap.put("deviceid",Parameters.randomValue() );
					hashMap.put("systemtime", Parameters.currentTime());
					hashMap.put("Currentsystemtime", Parameters.currentTime());
					System.out.println("PARAM: "+hashMap);
					System.out.println("Parameters.OriginalUrl: "+Parameters.OriginalUrl);
					new AsyncTaskUtility(LoginHomeActivity.this, hashMap, Parameters.OriginalUrl+"user-login.php").getJSONResponse(new AsyncTaskInterface() 
					{
						@Override
						public void getJSONObjectFromAsynTask(JSONObject json) 
						{
							LoginformServer(json.toString());
						}
					});
				}
				else
				{
					System.out.println("There is no net connection");
					if (uName != null || pWord != null) {
						Cursor cursorlogin23 = dbforloginlogoutRead.rawQuery(
								"select * from " + DatabaseForDemo.ADMIN_TABLE+" where "+DatabaseForDemo.USERID+"='"+uName+"' and "
										+DatabaseForDemo.PASSWORD+"='"+Parameters.MD5(pWord)+"';", null);
						if (cursorlogin23.getCount() > 0) 
						{
							Intent intent = new Intent(LoginHomeActivity.this, MenuActivity.class);
							startActivity(intent);
							saveinpendingQry();
							permissions();
							Parameters.loggedinusertype = "admin";
							Parameters.usertype="admin";
							Parameters.userid = uName;
							Parameters.usertypeloginvalue = uName;
							adminPermissions(true);
							insertLoginTime();
							permissions();
						}
						else 
						{
							Cursor cursoremp23 = dbforloginlogoutRead.rawQuery( "select * from " + DatabaseForDemo.EMPLOYEE_TABLE+" where "+DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID+"='"+uName+"' and " +DatabaseForDemo.EMPLOYEE_PASSWORD+"='"+Parameters.MD5(pWord)+"';", null);
							if (cursoremp23.getCount() > 0) 
							{
								Intent intent = new Intent(LoginHomeActivity.this, MenuActivity.class);
								startActivity(intent);
								saveinpendingQry();
								permissions();
								insertLoginTime();
								Parameters.loggedinusertype = "employee";
								Parameters.usertype="employee";
								Parameters.userid = uName;
								Parameters.usertypeloginvalue = uName;
								setEmployeePermissions();

							}
							else
							{
								Cursor cursorlogin0123 = dbforloginlogoutRead.rawQuery( "select * from " + DatabaseForDemo.ADMIN_TABLE, null);
								if (cursorlogin0123.getCount() > 0) 
								{
									Toast.makeText(getApplicationContext(), "Enter correct Username and password", 1000).show();
								}
								else
								{
									if (uName.equals("01") && pWord.equals("admin")) 
									{
										Parameters.loggedinusertype = "admin";
										Parameters.usertype="admin";
										Parameters.userid = uName;
										Parameters.usertypeloginvalue = uName;
										adminPermissions(true);
										insertLoginTime();
										permissions();
										Intent intent = new Intent(LoginHomeActivity.this, MenuActivity.class);
										onDestroy();
										startActivity(intent);
										saveinpendingQry();
									}
									else 
									{
										Toast.makeText(getApplicationContext(), "Enter correct Username and password", 1000).show();
									}
								}
								cursorlogin0123.close();
							}
							cursoremp23.close();
						}
						cursorlogin23.close();
					} 
					else 
					{
						Toast.makeText(getApplicationContext(), "Enter correct Username and password", 1000).show();
					}
				}
			}
			Log.d("sss", "jjjjjjjjj");
		}catch(Exception e){
			e.printStackTrace();
		}
		Log.d("LoginHomeActivity","loginFunction() Exit");
		Log.d("sss", "ffffffffff");
	}
	LoginReturnJson forlogin = new LoginReturnJson() {

		@Override
		public void logintoserverinterface(JSONObject jsontext) {
			// TODO Auto-generated method stub
			LoginformServer(jsontext.toString());
		}
	};
	void LoginformServer(String response){
		try{
			String servertime = "";
			if(response!=null&&response.length()>2){ 
				try {
					JSONObject jsonObj = new JSONObject(response);
					JSONObject responseobj = jsonObj .getJSONObject("response");
					servertime = responseobj.getString("server-time");
					status_first = jsonObj.getString("server-user-exists");
					status = jsonObj.getString("login-status");
					if(status_first.equals("true")){
						if(status.equals("true")){
							Parameters.sessionidforloginlogout = jsonObj.getString("sessionid");
							Parameters.loggedinusertype = jsonObj.getString("user-type");
							Parameters.usertype=jsonObj.getString("user-type");
						}
					}
					System.out.println("response value is:" + jsonObj.toString(4));
					System.out.println("response value is:" + status);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					System.out.println("servertime is:harinath" + servertime);
					e.printStackTrace();
				}

				String select = "select *from " + DatabaseForDemo.MISCELLANEOUS_TABLE;

				Cursor mCursors204 = dbforloginlogoutWrite.rawQuery(select, null);
				if (mCursors204.getCount() > 0) {
					dbforloginlogoutWrite.execSQL("update "
							+ DatabaseForDemo.MISCELLANEOUS_TABLE
							+ " set "
							+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
							+ "=\"" + servertime + "\"");

				} else {

					ContentValues contentValues1 = new ContentValues();
					contentValues1.put(DatabaseForDemo.MISCEL_STORE,
							"store1");
					contentValues1.put(DatabaseForDemo.MISCEL_PAGEURL,
							Parameters.OriginalUrl);
					contentValues1.put(
							DatabaseForDemo.MISCEL_UPDATE_LOCAL,
							Parameters.currentTime());
					contentValues1.put(
							DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL,
							Parameters.currentTime());
					dbforloginlogoutWrite.insert(DatabaseForDemo.MISCELLANEOUS_TABLE,
							null, contentValues1);

				}
				mCursors204.close();
				Log.d("sss", "6666666");
			}else{
				Toast.makeText(getApplicationContext(), "Server Error", 1000).show();
			}

			if(status_first.equals("true")){
				System.out.println("status_first true");
				if (status.equals("true")) {
					System.out.println("status true");
					insertLoginTime();
					if(Parameters.usertype.equals("admin")){
						Parameters.userid = uName;
						Parameters.usertypeloginvalue = uName;
						adminPermissions(true);
						permissions();
						Intent intent = new Intent(LoginHomeActivity.this,
								MenuActivity.class);
						startActivity(intent);

					}else{
						Parameters.userid = uName;
						Parameters.usertypeloginvalue = uName;
						setEmployeePermissions();
						permissions();
						Intent intent = new Intent(LoginHomeActivity.this,
								MenuActivity.class);
						startActivity(intent);
					}

				} else {
					Toast.makeText(getApplicationContext(),
							"Enter correct Username and password", 1000).show();
				}
			}else {
				System.out.println("status_first flase");
				if (uName != null || pWord != null) {
					Log.v("1","1");
					Cursor cursorlogin1 = dbforloginlogoutRead.rawQuery(
							"select * from " + DatabaseForDemo.ADMIN_TABLE+" where "+DatabaseForDemo.USERID+"='"+uName+"' and "
									+DatabaseForDemo.PASSWORD+"='"+Parameters.MD5(pWord)+"';", null);
					// startManagingCursor(cursorlogin1);
					if (cursorlogin1.getCount() > 0) {
						Intent intent = new Intent(LoginHomeActivity.this,
								MenuActivity.class);
						startActivity(intent);
						permissions();
						Parameters.loggedinusertype = "admin";
						Parameters.usertype="admin";
						Parameters.userid = uName;
						Parameters.usertypeloginvalue = uName;
						adminPermissions(true);
						insertLoginTime();
						permissions();
					} else {
						Cursor cursoremp1 = dbforloginlogoutRead.rawQuery(
								"select * from " + DatabaseForDemo.EMPLOYEE_TABLE+" where "+DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID+"='"+uName+"' and "
										+DatabaseForDemo.EMPLOYEE_PASSWORD+"='"+Parameters.MD5(pWord)+"';",
										null);
						if (cursoremp1.getCount() > 0) {
							Intent intent = new Intent(LoginHomeActivity.this,
									MenuActivity.class);
							startActivity(intent);
							permissions();
							insertLoginTime();
							Parameters.loggedinusertype = "employee";
							Parameters.usertype="employee";
							Parameters.userid = uName;
							Parameters.usertypeloginvalue = uName;
							setEmployeePermissions();

						}else{
							Log.v("133","133");
							Cursor cursorlogin011 = dbforloginlogoutRead.rawQuery(
									"select * from " + DatabaseForDemo.ADMIN_TABLE, null);
							if (cursorlogin011.getCount() > 0) {
								Toast.makeText(getApplicationContext(),
										"Enter correct Username and password", 1000).show();
							}else{
								if (uName.equals("01") && pWord.equals("admin")) {
									Parameters.loggedinusertype = "admin";
									Parameters.usertype="admin";
									Parameters.userid = uName;
									Parameters.usertypeloginvalue = uName;
									adminPermissions(true);
									insertLoginTime();
									permissions();
									Log.v("1vcb","1vcb");
									Intent intent = new Intent(LoginHomeActivity.this,
											MenuActivity.class);
									startActivity(intent);
									Log.v("1zzz","zzz1");
								} else {
									Toast.makeText(getApplicationContext(),
											"Enter correct Username and password", 1000).show();
								}
							}
							cursorlogin011.close();
							Log.d("sss", "77777777");
						}
						cursoremp1.close();
						Log.d("sss", "888888888");
					}
					cursorlogin1.close();
					Log.d("sss", "999999999");
				} else {
					Toast.makeText(getApplicationContext(),
							"Enter correct Username and password", 1000).show();
				}
			}
		}catch(Exception ee){
			ee.printStackTrace();
		}
	}
	@Override
	protected void onStop()
	{
		super.onStop();
//		Log.d("sss", "dbforloginlogoutRead: "+dbforloginlogoutRead);
//		Log.d("sss", "dbforloginlogoutWrite: "+dbforloginlogoutWrite);
//		Log.d("sss", "sqlA1: "+sqlA1);
//		Log.d("sss", "onStop()");
//		dbforloginlogoutRead.close();
//		dbforloginlogoutWrite.close();
//		sqlA1.close();
//		sqlA1=null;
//		Log.d("sss", "dbforloginlogoutRead: "+dbforloginlogoutRead);
//		Log.d("sss", "dbforloginlogoutWrite: "+dbforloginlogoutWrite);
//		Log.d("sss", "sqlA1: "+sqlA1);
	}

	@Override
	protected void onDestroy() {
		Log.d("sss", "onDestroy() method");
//		sqlA1.close();
		super.onDestroy();
//		dbforloginlogoutRead.close();
//		dbforloginlogoutWrite.close();
		if(sqlA1!=null)
		{
			sqlA1.close();
		}
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_CENTER:
			Toast.makeText(getApplicationContext(), "Ehhhhhhhhhhhhhhhhhh", 1000) .show();
			return true;
		case KeyEvent.KEYCODE_ENTER:
			loginMainMethod();
			return true;
		case KeyEvent.KEYCODE_J:
			return true;
		case KeyEvent.KEYCODE_K:
			return true;
		default:
			return super.onKeyUp(keyCode, event);
		}
	}

	void micellaneousTable() {
		try{
			String selectQuery = "SELECT  * FROM " + DatabaseForDemo.MISCELLANEOUS_TABLE;
			Cursor mCursors34 = dbforloginlogoutWrite.rawQuery(selectQuery, null);
			if (mCursors34 != null) {
				Log.v("gfdkg", "super333....." + mCursors34.getCount());
				String server_Url = "nnn";
				if (mCursors34.getCount() < 1) {

					ContentValues contentValues = new ContentValues();
					contentValues.put(DatabaseForDemo.MISCEL_STORE, "Store1");
					contentValues.put(DatabaseForDemo.MISCEL_PAGEURL, Parameters.OriginalUrl);
					contentValues.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL, Parameters.currentTime());
					contentValues.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL, Parameters.currentTime());
					Log.i("select", "" + contentValues);
					dbforloginlogoutWrite.insert(DatabaseForDemo.MISCELLANEOUS_TABLE, null, contentValues);
					contentValues.clear();
				} else {
					if (mCursors34.moveToFirst()) {

						String url = mCursors34.getString(mCursors34 .getColumnIndex(DatabaseForDemo.MISCEL_PAGEURL));
						if (url.length() > 3) {
							Parameters.OriginalUrl = url;
						}
					}
				}
			}
			mCursors34.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	void loginMainMethod() {
		try{
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(passWord.getWindowToken(), 0);
			String query = "select " + DatabaseForDemo.MISCEL_PAGEURL + " from " + DatabaseForDemo.MISCELLANEOUS_TABLE;
			Cursor miscurq123 = dbforloginlogoutRead.rawQuery(query, null);

			if (miscurq123 != null) {
				if (miscurq123.getCount() > 0) 
				{
					if (miscurq123.moveToFirst()) 
					{
						do
						{
							if (miscurq123.isNull(miscurq123.getColumnIndex(DatabaseForDemo.MISCEL_PAGEURL)))
							{
								Parameters.OriginalUrl = "";
								Log.d("sss","loginMainMethod()-if Parameters.OriginalUrl:"+Parameters.OriginalUrl);
							}
							else 
							{
								Parameters.OriginalUrl = miscurq123 .getString(miscurq123 .getColumnIndex(DatabaseForDemo.MISCEL_PAGEURL));
								Log.d("sss","loginMainMethod()-else Parameters.OriginalUrl:"+Parameters.OriginalUrl);
							}
						} while (miscurq123.moveToNext());
					}
				}
			}
			miscurq123.close();
			loginFunction();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		Log.d("sss","llllllllll");
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		userName.setText("");
		passWord.setText("");
	}
	void printerDemoData(){
		try{
			ContentValues contentValues = new ContentValues();
			contentValues.put(DatabaseForDemo.PRINTER_TEXT, "      AONEPOS" );
			contentValues.put(DatabaseForDemo.PRINTER_UNIT, "30" );
			contentValues.put(DatabaseForDemo.PRINTER_SPACING, "30");
			contentValues.put(DatabaseForDemo.PRINTER_FONT, "0" );
			contentValues.put(DatabaseForDemo.PRINTER_ALIGN, "0"  );
			contentValues.put(DatabaseForDemo.PRINTER_LANGUAGE, "0"  );
			contentValues.put(DatabaseForDemo.PRINTER_WSIZE, "1"   );
			contentValues.put(DatabaseForDemo.PRINTER_HSIZE, "1"  );
			contentValues.put(DatabaseForDemo.PRINTER_BOLD, "0" );
			contentValues.put(DatabaseForDemo.PRINTER_UNDERLINE, "0" );
			contentValues.put(DatabaseForDemo.PRINTER_XPOSITION, "0" );
			contentValues.put(DatabaseForDemo.PRINTER_TYPE, "EPSON" );
			contentValues.put(DatabaseForDemo.PRINTER_NAME, "TM-T82II");
			contentValues.put(DatabaseForDemo.PRINTER_IP, "192.168.1.168");
			contentValues.put(DatabaseForDemo.PRINTER_ID,"printer1" );
			dbforloginlogoutWrite.insert(DatabaseForDemo.PRINTER_TABLE, null, contentValues);
			contentValues.clear();

			contentValues.put(DatabaseForDemo.PRINTER_TEXT, "      AONEPOS" );
			contentValues.put(DatabaseForDemo.PRINTER_UNIT, "30" );
			contentValues.put(DatabaseForDemo.PRINTER_SPACING, "30");
			contentValues.put(DatabaseForDemo.PRINTER_FONT, "0" );
			contentValues .put(DatabaseForDemo.PRINTER_ALIGN, "0"  );
			contentValues .put(DatabaseForDemo.PRINTER_LANGUAGE, "0"  );
			contentValues .put(DatabaseForDemo.PRINTER_WSIZE, "1"   );
			contentValues .put(DatabaseForDemo.PRINTER_HSIZE, "1"  );
			contentValues.put(DatabaseForDemo.PRINTER_BOLD, "0" );
			contentValues.put(DatabaseForDemo.PRINTER_UNDERLINE, "0" );
			contentValues.put(DatabaseForDemo.PRINTER_XPOSITION, "0" );
			contentValues.put(DatabaseForDemo.PRINTER_TYPE, "STAR" );
			contentValues.put(DatabaseForDemo.PRINTER_NAME, "TSP100");
			contentValues.put(DatabaseForDemo.PRINTER_IP, "192.168.1.12");
			contentValues.put(DatabaseForDemo.PRINTER_ID,"printer2" );
			dbforloginlogoutWrite.insert(DatabaseForDemo.PRINTER_TABLE, null, contentValues);
		}catch(SQLiteException e){
			e.printStackTrace();
		}
	}
	void insertLoginTime(){
		try{
			ContentValues values = new ContentValues();
			values.put(DatabaseForDemo.LOGIN_EMPLOYEE_NAME, uName);
			values.put(DatabaseForDemo.LOGIN_EMPLOYEE_ID, uName);
			values.put(DatabaseForDemo.LOGIN_TIME, Parameters.logintime);
			values.put(DatabaseForDemo.LOGOUT_TIME, "");
			values.put(DatabaseForDemo.DIFF_MINUTES, "00");
			values.put(DatabaseForDemo.DIFF_HOURS, "00");
			values.put(DatabaseForDemo.WAGES, "");
			values.put(DatabaseForDemo.CREATED_DATE, Parameters.currentTime());
			values.put(DatabaseForDemo.MODIFIED_DATE, Parameters.currentTime());
			values.put(DatabaseForDemo.MODIFIED_IN, "Local");
			values.put(DatabaseForDemo.SESSIONIDVAL, Parameters.sessionidforloginlogout);
			dbforloginlogoutWrite.insert(DatabaseForDemo.LOGIN_LOGOUT_TABLE, null, values);
		}catch(SQLiteException e){
			e.printStackTrace();
		}
	}
	void setEmployeePermissions()
	{
		try
		{
			Cursor cursoremployeepermission =dbforloginlogoutRead.rawQuery(
					"select *from "
							+ DatabaseForDemo.EMP_PERMISSIONS_TABLE
							+ " where "
							+ DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID
							+ "=\"" + uName + "\"", null);
			if (cursoremployeepermission != null) {
				if (cursoremployeepermission.getCount()>0) {
					if (cursoremployeepermission.moveToFirst()) {
						do {
							String inventory = cursoremployeepermission .getString(cursoremployeepermission .getColumnIndex(DatabaseForDemo.EMP_INVENTORY));
							if (inventory.equals("Enable")) {
								Parameters.inventory_permission = true;
							} else {
								Parameters.inventory_permission = false;
							}
							System.out .println("inventory permission is:" + Parameters.inventory_permission);
							String customer = cursoremployeepermission .getString(cursoremployeepermission .getColumnIndex(DatabaseForDemo.EMP_CUSTOMERS));
							if (customer.equals("Enable")) {
								Parameters.customer_permission = true;
							} else {
								Parameters.customer_permission = false;
							}
							System.out .println("customer_permission permission is:" + Parameters.customer_permission);
							String reports = cursoremployeepermission .getString(cursoremployeepermission .getColumnIndex(DatabaseForDemo.EMP_REPORTS));
							if (reports.equals("Enable")) {
								Parameters.reports_permission = true;
							} else {
								Parameters.reports_permission = false;
							}
							System.out.println("reports permission is:" + Parameters.reports_permission);
							String discounts = cursoremployeepermission .getString(cursoremployeepermission .getColumnIndex(DatabaseForDemo.EMP_DISCOUNTS));
							if (discounts.equals("Enable")) {
								Parameters.invoice_discounts_permission = true;
							} else {
								Parameters.invoice_discounts_permission = false;
							}
							System.out .println("invoice_discounts_permission permission is:" + Parameters.invoice_discounts_permission);
							String settings = cursoremployeepermission .getString(cursoremployeepermission .getColumnIndex(DatabaseForDemo.EMP_SETTINGS));
							if (settings.equals("Enable")) {
								Parameters.settings_permission = true;
							} else {
								Parameters.settings_permission = false;
							}
							System.out.println("settings_permission permission is:"+ Parameters.settings_permission);
							String pricechange = cursoremployeepermission.getString(cursoremployeepermission.getColumnIndex(DatabaseForDemo.EMP_PRICE));
							if (pricechange.equals("Enable")) {
								Parameters.invoice_price_change_permission = true;
							} else {
								Parameters.invoice_price_change_permission = false;
							}
							System.out.println("invoice_price_change_permission permission is:"+ Parameters.invoice_price_change_permission);
							String allowexit = cursoremployeepermission .getString(cursoremployeepermission .getColumnIndex(DatabaseForDemo.EMP_EXIT));
							if (allowexit.equals("Enable")) {
								Parameters.allow_exit_permission = true;
							} else {
								Parameters.allow_exit_permission = false;
							}
							System.out
							.println("allow_exit_permission permission is:"
									+ Parameters.allow_exit_permission);
							String payouts = cursoremployeepermission .getString(cursoremployeepermission .getColumnIndex(DatabaseForDemo.EMP_PAYOUTS));
							if (payouts.equals("Enable")) {
								Parameters.vendor_payouts_permission = true;
							} else {
								Parameters.vendor_payouts_permission = false;
							}
							System.out .println("vendor_payouts_permission permission is:" + Parameters.vendor_payouts_permission);
							String delete = cursoremployeepermission .getString(cursoremployeepermission .getColumnIndex(DatabaseForDemo.EMP_DELETE));
							if (delete.equals("Enable")) {
								Parameters.delete_items_permission = true;
							} else {
								Parameters.delete_items_permission = false;
							}
							System.out .println("delete_items_permission permission is:" + Parameters.delete_items_permission);
							String voiddata = cursoremployeepermission .getString(cursoremployeepermission .getColumnIndex(DatabaseForDemo.EMP_VOID));
							if (voiddata.equals("Enable")) {
								Parameters.void_invoices_permission = true;
							} else {
								Parameters.void_invoices_permission = false;
							}
							System.out .println("void_invoices_permission permission is:" + Parameters.void_invoices_permission);
							String transactions = cursoremployeepermission .getString(cursoremployeepermission .getColumnIndex(DatabaseForDemo.EMP_TRANSACTIONS));
							if (transactions.equals("Enable")) {
								Parameters.end_transaction_permission = true;
							} else {
								Parameters.end_transaction_permission = false;
							}
							System.out .println("end_transaction_permission permission is:" + Parameters.end_transaction_permission);
							String holdprint = cursoremployeepermission .getString(cursoremployeepermission .getColumnIndex(DatabaseForDemo.EMP_HOLDPRINTS));
							if (holdprint.equals("Enable")) {
								Parameters.hold_print_permission = true;
							} else {
								Parameters.hold_print_permission = false;
							}
							System.out .println("hold_print_permission permission is:" + Parameters.hold_print_permission);
							String credit = cursoremployeepermission .getString(cursoremployeepermission .getColumnIndex(DatabaseForDemo.EMP_CREDIT));
							if (credit.equals("Enable")) {
								Parameters.force_credit_cards_permission = true;
							} else {
								Parameters.force_credit_cards_permission = false;
							}
							System.out .println("force_credit_cards_permission permission is:" + Parameters.force_credit_cards_permission);
							String endcash = cursoremployeepermission .getString(cursoremployeepermission .getColumnIndex(DatabaseForDemo.EMP_ENDCASH));
							if (endcash.equals("Enable")) {
								Parameters.end_cash_transaction_permission = true;
							} else {
								Parameters.end_cash_transaction_permission = false;
							}
							System.out .println("end_cash_transaction_permission permission is:" + Parameters.end_cash_transaction_permission);

						} while (cursoremployeepermission.moveToNext());
					}
				}else{
					adminPermissions(false);
				}
			}else{
				adminPermissions(false);
			}
			cursoremployeepermission.close();
		}catch(SQLiteException e){
			e.printStackTrace();
		}catch (Exception e1) {
			// TODO: handle exception
			e1.printStackTrace();
		}
	}
	void saveinpendingQry()
	{
		try
		{
			String parameterval = "?username=" + uName
					+ "&password=" + Parameters.MD5(pWord) + "&deviceid="
					+ Parameters.randomValue() + "&systemtime="
					+ Parameters.currentTime().replace(" ", "%20");
			ContentValues contentValues1 = new ContentValues();
			contentValues1.put(DatabaseForDemo.QUERY_TYPE, "userlogin");
			contentValues1.put(DatabaseForDemo.PENDING_USER_ID, Parameters.userid);
			contentValues1.put(DatabaseForDemo.PAGE_URL, "user-login.php");
			contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING, "");
			contentValues1.put( DatabaseForDemo.CURRENT_TIME_PENDING, Parameters.currentTime());
			contentValues1.put(DatabaseForDemo.PARAMETERS, parameterval);
			dbforloginlogoutWrite.insert( DatabaseForDemo.PENDING_QUERIES_TABLE, null, contentValues1);
		}catch(SQLiteException e){
			e.printStackTrace();
		}catch (Exception e1) {
			// TODO: handle exception
			e1.printStackTrace();
		}
	}
	void permissions(){
		if(Parameters.usertype.equals("admin")){
			Parameters.stores_permission=true;
			Parameters.profile_permission=true;
			Parameters.employee_permission=true;
		}else{
			Parameters.stores_permission=false;
			Parameters.profile_permission=false;
			Parameters.employee_permission=false;
		}
	}
	void adminPermissions(boolean value){

		Parameters.inventory_permission = value;

		Parameters.reports_permission = value;

		Parameters.settings_permission = value;

		Parameters.customer_permission = value;

		Parameters.allow_exit_permission = value;

		Parameters.delete_items_permission = value;

		Parameters.end_transaction_permission = value;

		Parameters.force_credit_cards_permission = value;

		Parameters.invoice_discounts_permission = value;

		Parameters.vendor_payouts_permission = value;

		Parameters.void_invoices_permission = value;

		Parameters.hold_print_permission = value;

		Parameters.end_cash_transaction_permission = value;

		Parameters.invoice_price_change_permission = value;

	}

}
