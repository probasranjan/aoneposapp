package com.aoneposapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ParseException;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.aoneposapp.adapters.DetailsShowingArrayAdapter;
import com.aoneposapp.utils.DatabaseForDemo;
import com.aoneposapp.utils.GetJSONListener;
import com.aoneposapp.utils.JSONclient;
import com.aoneposapp.utils.JsonPostMethod;
import com.aoneposapp.utils.Parameters;

@SuppressWarnings("deprecation")
public class ProfileActivity extends Activity implements
		android.view.View.OnClickListener {
	Button tab;
	Button cancelforpwd, saveforpwd;
	EditText new_pwd, retype_pwd;
String result="",success="";
	SlidingDrawer slidingDrawer;
	ImageView slideButton, image0, image1, image2, image3, image4, image5,
			image6, image7, image8, logout, logout2;
	int height;
	int wwidth;
	Button b4, create, editpwd, editprofile, viewprofile;
	LinearLayout displaylayout3, displaylayout1, inflatinglayout3, inflatinglayout1;
	
	Button saveforedit, cancelforedit;
	EditText useridforedit, firstname, lastname, phoneforedit, emailforedit, addressforedit;
	String datavalforedit="",passWord="",createdtime="",unq_id="",serverPass="";
	
	EditText userid, pass, confirmposs, first, last, phone, email,
	address;
Button save, cancel;
public final Pattern USER_PATTERN = Pattern
	.compile("[a-zA-Z0-9+._+]{4,25}");
public final Pattern PHONE_PATTERN = Pattern.compile("[0-9+-+]{10,15}");
public final Pattern PASSWORD_PATTERN = Pattern
	.compile("[a-zA-Z0-9]{4,25}");
public final Pattern EMAIL_ADDRESS_PATTERN = Pattern
	.compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
			+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
			+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");
String dataval = "";
ListView list;
public static ArrayList<HashMap<String, String>> listData = new ArrayList<HashMap<String, String>>();
DatabaseForDemo sqlDBProfile;
SQLiteDatabase dbforloginlogoutWriteProfile ,dbforloginlogoutReadProfile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_activity);
		Parameters.printerContext=ProfileActivity.this;
		sqlDBProfile=new DatabaseForDemo(ProfileActivity.this);
		dbforloginlogoutWriteProfile = sqlDBProfile.getWritableDatabase();
		dbforloginlogoutReadProfile = sqlDBProfile.getReadableDatabase();
		slideButton = (ImageView) findViewById(R.id.slideButton);
		slidingDrawer = (SlidingDrawer) findViewById(R.id.SlidingDrawer);
		image0 = (ImageView) findViewById(R.id.image0);
		image1 = (ImageView) findViewById(R.id.image1);
		image2 = (ImageView) findViewById(R.id.image2);
		image3 = (ImageView) findViewById(R.id.image3);
		image4 = (ImageView) findViewById(R.id.image4);
		image5 = (ImageView) findViewById(R.id.image5);
		image6 = (ImageView) findViewById(R.id.image6);
		image7 = (ImageView) findViewById(R.id.image7);
		image8 = (ImageView) findViewById(R.id.image8);
		logout = (ImageView) findViewById(R.id.logout);
		logout2 = (ImageView) findViewById(R.id.logout2);
		image8.setBackgroundResource(R.drawable.pro1);
		logout.setBackgroundResource(R.drawable.logoutnormal);
		TextView loginnameempid = (TextView)findViewById(R.id.loginnameval);
		loginnameempid.setText(Parameters.usertypeloginvalue);
		image0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(ProfileActivity.this,
						PosMainActivity.class);
				startActivity(intent1);
				finish();
			}
		});
		image1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Parameters.inventory_permission) {
					Intent intent1 = new Intent(ProfileActivity.this,
							InventoryActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							ProfileActivity.this,
							"Sorry",
							"You are not authenticated to perform this operation.",
							false);
				}
			}
		});
		image2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Parameters.stores_permission){
				Intent intent1 = new Intent(ProfileActivity.this,
						StoresActivity.class);
				startActivity(intent1);
				finish();
			} else {
				showAlertDialog(
						ProfileActivity.this,
						"Sorry",
						"You are not authenticated to perform this operation.",
						false);
			}
			}
		});
		image3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Parameters.customer_permission) {
					Intent intent1 = new Intent(ProfileActivity.this,
							CustomerActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							ProfileActivity.this,
							"Sorry",
							"You are not authenticated to perform this operation.",
							false);
				}
			}
		});
		image4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Parameters.employee_permission){
				Intent intent1 = new Intent(ProfileActivity.this,
						EmployeeActivity.class);
				startActivity(intent1);
				finish();
			} else {
				showAlertDialog(
						ProfileActivity.this,
						"Sorry",
						"You are not authenticated to perform this operation.",
						false);
			}
			}
		});
		image5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Parameters.reports_permission) {
					Intent intent1 = new Intent(ProfileActivity.this,
							ReportsActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							ProfileActivity.this,
							"Sorry",
							"You are not authenticated to perform this operation.",
							false);
				}
			}
		});
		image6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Parameters.settings_permission) {
					Intent intent1 = new Intent(ProfileActivity.this,
							SettingsActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							ProfileActivity.this,
							"Sorry",
							"You are not authenticated to perform this operation.",
							false);
				}
			}
		});
		image7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(ProfileActivity.this,
						ContactsActivity.class);
				startActivity(intent1);
				finish();
			}
		});
		image8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Parameters.methodForLogout(ProfileActivity.this);
				finish();
			}
		});
		logout2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Parameters.methodForLogout(ProfileActivity.this);
				finish();
			}
		});
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		height = displaymetrics.heightPixels;
		wwidth = displaymetrics.widthPixels;
		slideButton.setPadding(0, 0, 0, height - 40);
		// Toast.makeText(getApplicationContext(), ""+height, 2000).show();
		slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				// slideButton.setBackgroundResource(R.drawable.icon);
				slideButton.setImageResource(R.drawable.arrowleft);
			}
		});

		slidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {
				slideButton.setImageResource(R.drawable.arrow);
				// slideButton.setBackgroundResource(R.drawable.icon);
			}
		});
		
		displaylayout3 = (LinearLayout)findViewById(R.id.toptabview3);
		displaylayout1 = (LinearLayout)findViewById(R.id.toptabview1);
		inflatinglayout3 = (LinearLayout)findViewById(R.id.inflatingLayoutfor3);
		inflatinglayout1 = (LinearLayout)findViewById(R.id.inflatingLayoutfor1);
		viewprofile = (Button)findViewById(R.id.viewprofile);
		editprofile = (Button)findViewById(R.id.editprofile);
		editpwd = (Button)findViewById(R.id.editpwd);
		
		String query = "select * from " + DatabaseForDemo.ADMIN_TABLE;
		Cursor miscur = dbforloginlogoutReadProfile.rawQuery(query, null);
		System.out.println("if is miscur.getCount() "+miscur.getCount());
		if (miscur.getCount() > 0 ) {
			System.out.println("if is executed");
			displaylayout3.setVisibility(View.VISIBLE);
			inflatinglayout3.setVisibility(View.VISIBLE);
			list = (ListView)findViewById(R.id.listView1);
			updatedata();
			System.out.println("if is ended");
			miscur.close();
		} else {
			miscur.close();
			System.out.println("else is executed");
			displaylayout1.setVisibility(View.VISIBLE);
			inflatinglayout1.setVisibility(View.VISIBLE);
			
			userid = (EditText) findViewById(R.id.p_id_edit);
			pass = (EditText) findViewById(R.id.p_pass_edit);
			confirmposs = (EditText) findViewById(R.id.p_confirm_edit);
			first = (EditText) findViewById(R.id.p_first_edit);
			last = (EditText) findViewById(R.id.p_last_edit);
			phone = (EditText) findViewById(R.id.p_phone_edit);
			email = (EditText) findViewById(R.id.p_email_edit);
			address = (EditText) findViewById(R.id.p_address_edit);
			save = (Button) findViewById(R.id.p_save);
			cancel = (Button) findViewById(R.id.p_cancel);
			address.setOnEditorActionListener(new OnEditorActionListener() {
				@Override
				public boolean onEditorAction(TextView v, int actionId,
						KeyEvent event) {
					if (actionId == EditorInfo.IME_ACTION_DONE) {
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(address.getWindowToken(), 0);
					}
					return false;
				}
			});
			save.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String username = userid.getText().toString();
					Cursor cursor = dbforloginlogoutReadProfile.rawQuery(
							"select *from "+DatabaseForDemo.ADMIN_TABLE+" where userid=\"" + username
									+ "\"", null);
					System.out.println("demo cursor count is:" + cursor.getCount());

					Cursor cursoremployee =dbforloginlogoutReadProfile.rawQuery(
							"select *from " + DatabaseForDemo.EMPLOYEE_TABLE
									+ " where " + DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID
									+ "=\"" + username +"\"", null);
					System.out.println("employee cursor count is:"
							+ cursoremployee.getCount());
					
					if(cursor.getCount()>0 || cursoremployee.getCount()>0){
						Toast.makeText(ProfileActivity.this, "User already exist. Please choose another username.", Toast.LENGTH_LONG).show();
						cursor.close();
						cursoremployee.close();
					}else{
						cursor.close();
						cursoremployee.close();
					if (validetions()) {
						
						String random = Parameters.randomValue();
						String now_date = Parameters.currentTime();
						String passwordMd5 = Parameters.MD5(pass.getText().toString().trim());
						Log.v("passwordMd5",""+passwordMd5);
						
						ContentValues contentValues = new ContentValues();
						contentValues.put(DatabaseForDemo.USERID, userid.getText().toString()
								.trim());
						contentValues.put(DatabaseForDemo.PASSWORD, passwordMd5);
						contentValues.put(DatabaseForDemo.FIRSTNAME, first.getText()
								.toString().trim());
						contentValues.put(DatabaseForDemo.LASTNAME, last.getText().toString()
								.trim());
						contentValues.put(DatabaseForDemo.PHONENUMBER, phone.getText()
								.toString().trim());
						contentValues.put(DatabaseForDemo.EMAIL, email.getText().toString()
								.trim());
						contentValues.put(DatabaseForDemo.ADDRESS, address.getText()
								.toString().trim());
						contentValues.put(DatabaseForDemo.UNIQUE_ID, random);
						contentValues.put(DatabaseForDemo.CREATED_DATE, now_date);
						contentValues.put(DatabaseForDemo.MODIFIED_DATE, now_date);
						contentValues.put(DatabaseForDemo.MODIFIED_IN, "Local");
						Log.i("select", "" + contentValues);
						dbforloginlogoutWriteProfile.insert(DatabaseForDemo.ADMIN_TABLE, null,
								contentValues);
						JSONObject jsonobj = new JSONObject();
						JSONObject data = new JSONObject();
						JSONArray fields = new JSONArray();

						try {
							jsonobj.put(DatabaseForDemo.USERID, userid.getText().toString()
									.trim());
							jsonobj.put(DatabaseForDemo.PASSWORD, passwordMd5);
							jsonobj.put(DatabaseForDemo.FIRSTNAME, first.getText().toString()
									.trim());
							
							jsonobj.put(DatabaseForDemo.LASTNAME, last.getText().toString()
									.trim());
							jsonobj.put(DatabaseForDemo.PHONENUMBER, phone.getText()
									.toString().trim());
							jsonobj.put(DatabaseForDemo.EMAIL, email.getText().toString()
									.trim());
							jsonobj.put(DatabaseForDemo.ADDRESS, address.getText().toString()
									.trim());
							jsonobj.put(DatabaseForDemo.UNIQUE_ID, random);
							jsonobj.put(DatabaseForDemo.CREATED_DATE, now_date);
							jsonobj.put(DatabaseForDemo.MODIFIED_DATE, now_date);
							jsonobj.put(DatabaseForDemo.MODIFIED_IN, "Local");
							fields.put(0, jsonobj);

							data.put("fields",fields);
							dataval = data.toString();
							System.out.println("dataval is:"+dataval);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
							
							if(Parameters.OriginalUrl.equals("")){
								System.out.println("there is no server url val");
								Parameters.methodForLogout(ProfileActivity.this);
								finish();
								Toast.makeText(ProfileActivity.this, "Saved",
										Toast.LENGTH_SHORT).show();
							}else{
								if (Parameters.isNetworkAvailable(getApplicationContext())) {

							final JsonPostMethod value = new JsonPostMethod();
							Thread trd=	new Thread(new Runnable() {
							    @Override
								public void run() {		
							    	String servertiem = null;
							String response = value.postmethodforcreateprofile("01", "admin", dataval);
							System.out.println("response value is:"+response);
							JSONObject obj;
							try {
								obj = new JSONObject(response);
							
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
								
								dbforloginlogoutWriteProfile.execSQL(deletequery);
								dbforloginlogoutWriteProfile.execSQL(insertquery);
							
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							    }
							  });
							trd.start();
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Parameters.methodForLogout(ProfileActivity.this);
							finish();
							Toast.makeText(ProfileActivity.this, "Saved",
									Toast.LENGTH_SHORT).show();
							}else{
								Parameters.methodForLogout(ProfileActivity.this);
								finish();
								Toast.makeText(ProfileActivity.this, "Saved",
										Toast.LENGTH_SHORT).show();
								ContentValues contentValues1 = new ContentValues();
								contentValues1.put(DatabaseForDemo.QUERY_TYPE,  "createadminprofile");
								contentValues1.put(DatabaseForDemo.PENDING_USER_ID, Parameters.userid);
								contentValues1.put(DatabaseForDemo.PAGE_URL,  "create-admin-profile.php");
								contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING, DatabaseForDemo.ADMIN_TABLE);
								contentValues1.put(DatabaseForDemo.CURRENT_TIME_PENDING, Parameters.currentTime());
								contentValues1.put(DatabaseForDemo.PARAMETERS, dataval);
								dbforloginlogoutWriteProfile.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null, contentValues1);
							}
							}
							
					} else {
						Toast.makeText(ProfileActivity.this, "Try Again",
								Toast.LENGTH_SHORT).show();
					}
					}
				}
			});
			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					userid.setText("");
					pass.setText("");
					first.setText("");
					last.setText("");
					email.setText("");
					phone.setText("");
					address.setText("");
					confirmposs.setText("");
				}
			});

			System.out.println("else is ended");
		}
		
		System.out.println("database is closed");
		viewprofile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inflatinglayout3.removeAllViews();
				viewprofile.setBackgroundResource(R.drawable.highlightedtopmenuitem);
				editprofile.setBackgroundResource(R.drawable.toprightmenu);
				editpwd.setBackgroundResource(R.drawable.toprightmenu);
				viewprofile.setTextColor(Color.BLACK);
				editprofile.setTextColor(Color.WHITE);
				editpwd.setTextColor(Color.WHITE);
				LayoutInflater inflater = getLayoutInflater();
				final View layout = inflater.inflate(R.layout.view_details,
						(ViewGroup) v.findViewById(R.id.inflatingLayoutfor3));
				list = (ListView)layout.findViewById(R.id.listView1);
				updatedata();
				inflatinglayout3.addView(layout);
			}
		});
		
		System.out.println("view profile is called");
		
		editpwd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inflatinglayout3.removeAllViews();
				viewprofile.setBackgroundResource(R.drawable.toprightmenu);
				editprofile.setBackgroundResource(R.drawable.toprightmenu);
				editpwd.setBackgroundResource(R.drawable.highlightedtopmenuitem);
				viewprofile.setTextColor(Color.WHITE);
				editprofile.setTextColor(Color.WHITE);
				editpwd.setTextColor(Color.BLACK);
				LayoutInflater inflater = getLayoutInflater();
				final View layout = inflater.inflate(R.layout.edit_pwd_details,
						(ViewGroup) v.findViewById(R.id.inflatingLayoutfor3));
				cancelforpwd = (Button)layout.findViewById(R.id.pass_cancel);
				saveforpwd = (Button)layout.findViewById(R.id.pass_save);
				new_pwd = (EditText)layout.findViewById(R.id.newpwd_edit);
				retype_pwd = (EditText)layout.findViewById(R.id.retypepwd_edit);
				retype_pwd.setOnEditorActionListener(new OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_DONE) {
							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(retype_pwd.getWindowToken(), 0);
						}
						return false;
					}
				});
				cancelforpwd.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						new_pwd.setText("");
						retype_pwd.setText("");
					}
				});
				
				saveforpwd.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						InputMethodManager imm = (InputMethodManager)getSystemService(
							      Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(retype_pwd.getWindowToken(), 0);
						String ataval="";
						
						final String new_password = new_pwd.getText().toString();
						System.out.println(new_password);
						final String retype_password = retype_pwd.getText().toString();
						System.out.println(retype_password);
						
						if(new_password.equals(retype_password)){
							if(Parameters.isNetworkAvailable(ProfileActivity.this)){
								    	 ataval=Parameters.OriginalUrl+"change-password.php?userid="+Parameters.userid+"&edituserid="+Parameters.userid +"&sessionid="+Parameters.sessionidforloginlogout
													+"&newpassword="+Parameters.MD5(new_password)+"&Currentsystemtime="+Parameters.currentTime().replace(" ", "%20");
								    	System.out.println(ataval);
								    	
								        JSONclient client = new JSONclient(ProfileActivity.this, l);
									        client.execute(ataval);	    	
									   
							}else{
							ContentValues contentValues1 = new ContentValues();
							contentValues1.put(DatabaseForDemo.QUERY_TYPE,  "EditPassword");
							contentValues1.put(DatabaseForDemo.PENDING_USER_ID, Parameters.userid);
							contentValues1.put(DatabaseForDemo.PAGE_URL,  "change-password.php");
							contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING, DatabaseForDemo.ADMIN_TABLE);
							contentValues1.put(DatabaseForDemo.CURRENT_TIME_PENDING, Parameters.currentTime());
							contentValues1.put(DatabaseForDemo.PARAMETERS, ataval);
							dbforloginlogoutWriteProfile.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null, contentValues1);
							
							}
							for(int i=0;i<100;i++){
								
							}
							try {
								Thread.sleep(3000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}else{
							showAlertDialog(ProfileActivity.this, "Edit Password", "Your new password and retype password should be match", false);
						}
					}
				});

				inflatinglayout3.addView(layout);
			}
		});
		
		System.out.println("edit profile is called");
		
		editprofile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inflatinglayout3.removeAllViews();
				viewprofile.setBackgroundResource(R.drawable.toprightmenu);
				editprofile.setBackgroundResource(R.drawable.highlightedtopmenuitem);
				editpwd.setBackgroundResource(R.drawable.toprightmenu);
				viewprofile.setTextColor(Color.WHITE);
				editprofile.setTextColor(Color.BLACK);
				editpwd.setTextColor(Color.WHITE);
				LayoutInflater inflater = getLayoutInflater();
				final View layout = inflater.inflate(R.layout.edit_profile_details,
						(ViewGroup) v.findViewById(R.id.inflatingLayoutfor3));
				saveforedit = (Button)layout.findViewById(R.id.save);
				cancelforedit = (Button)layout.findViewById(R.id.cancel);
				useridforedit = (EditText)layout.findViewById(R.id.p_id_edit);
				firstname = (EditText)layout.findViewById(R.id.p_first_edit);
				lastname = (EditText)layout.findViewById(R.id.p_last_edit);
				phoneforedit = (EditText)layout.findViewById(R.id.p_phone_edit);
				emailforedit = (EditText)layout.findViewById(R.id.p_email_edit);
				addressforedit = (EditText)layout.findViewById(R.id.p_address_edit);
				dataUpdate();
				addressforedit.setOnEditorActionListener(new OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_DONE) {
							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(addressforedit.getWindowToken(), 0);
						}
						return false;
					}
				});
				saveforedit.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String useridval = useridforedit.getText().toString();
						String firstnameval = firstname.getText().toString();
						String lastnameval = lastname.getText().toString();
						String phoneval = phoneforedit.getText().toString();
						String emailval = emailforedit.getText().toString();
						String addressval = addressforedit.getText().toString();
						
							
							 try {
			     				JSONObject data = new JSONObject();
			     				JSONObject jsonobj = new JSONObject();	
			     				JSONArray fields = new JSONArray();
			     				
			     				jsonobj.put(DatabaseForDemo.USERID, useridval);
			     				jsonobj.put(DatabaseForDemo.PASSWORD, passWord);
								jsonobj.put(DatabaseForDemo.FIRSTNAME, firstnameval);
								jsonobj.put(DatabaseForDemo.LASTNAME, lastnameval);
								jsonobj.put(DatabaseForDemo.PHONENUMBER,phoneval);
								jsonobj.put(DatabaseForDemo.EMAIL, emailval);
								jsonobj.put(DatabaseForDemo.ADDRESS, addressval);
								jsonobj.put(DatabaseForDemo.SERVER_PASSWORD, serverPass);
								jsonobj.put(DatabaseForDemo.UNIQUE_ID, unq_id);
								jsonobj.put(DatabaseForDemo.CREATED_DATE, createdtime);
								jsonobj.put(DatabaseForDemo.MODIFIED_DATE, Parameters.currentTime());
								jsonobj.put(DatabaseForDemo.MODIFIED_IN, "Local");
								fields.put(0, jsonobj);
								data.put("fields", fields);
			 						datavalforedit = data.toString();
			 						System.out.println("data val is:"+datavalforedit);
			 					} catch (JSONException e1) {
			 						// TODO Auto-generated catch block
			 						e1.printStackTrace();
			 					}
			 			    
			 			    if(Parameters.OriginalUrl.equals("")){
						    		System.out.println("there is no url val");
						    	}else{
						    		boolean isnet = Parameters
											.isNetworkAvailable(getApplicationContext());
			 			    if(isnet){
			 			    	
			 			    new Thread(new Runnable() {
			 				    @Override
									public void run() {
			 				    	
			         				JsonPostMethod jsonpost = new JsonPostMethod();
			         				String response = jsonpost.postmethodfordirect(""+Parameters.usertype, ""+Parameters.sessionidforloginlogout, DatabaseForDemo.ADMIN_TABLE, Parameters.currentTime(), Parameters.currentTime(), datavalforedit, "true");
			         				System.out.println("response test is:"+response);
			         				try {
											JSONObject obj = new JSONObject(response);
											JSONArray array = obj.getJSONArray("insert-queries");
											System.out.println("array list length for insert is:"+array.length());
											JSONArray array2 = obj.getJSONArray("delete-queries");
											System.out.println("array2 list length for delete is:"+array2.length());
											for(int jj = 0,ii = 0; jj<array2.length() && ii<array.length(); jj++,ii++){
												String deletequerytemp = array2.getString(jj);
												String deletequery1 = deletequerytemp.replace("'", "\"");
												String deletequery = deletequery1.replace("\\\"", "'");
												System.out.println("delete query"+jj+" is :"+deletequery);
												
												String insertquerytemp = array.getString(ii);
												String insertquery1 = insertquerytemp.replace("'", "\"");
												String insertquery = insertquery1.replace("\\\"", "'");
												System.out.println("delete query"+jj+" is :"+insertquery);
												
												dbforloginlogoutWriteProfile.execSQL(deletequery);
												dbforloginlogoutWriteProfile.execSQL(insertquery);
												System.out.println("queries executed"+ii);
											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
			     				//	String select = "select *from "+DatabaseForDemo.MISCELLANEOUS_TABLE;
			     				//	Cursor cursor = db.rawQuery(select, null);
			     					/*if(cursor.getCount()>0){
			     						db.execSQL("update "+DatabaseForDemo.MISCELLANEOUS_TABLE+" set "+DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL+"='"++"'");
			     					}else{
			     						ContentValues contentValues1 = new ContentValues();
			         					contentValues1.put(DatabaseForDemo.MISCEL_STORE,  "store1");
			         					contentValues1.put(DatabaseForDemo.MISCEL_PAGEURL, "");
			         					contentValues1.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL, Parameters.currentTime());
			         					contentValues1.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL, Parameters.currentTime());
			         					db.insert(DatabaseForDemo.MISCELLANEOUS_TABLE, null, contentValues1);
			     						demodb.close();
			     					}*/
			 				    }
			 				  }).start();
			 			    
			 			    }else{
			 			    	
			 			    }
			 			   dbforloginlogoutWriteProfile.execSQL("update "+DatabaseForDemo.ADMIN_TABLE+" set "+DatabaseForDemo.FIRSTNAME+"=\""+firstnameval
									+"\", "+DatabaseForDemo.LASTNAME+"=\""+lastnameval
									+"\", "+DatabaseForDemo.PHONENUMBER+"=\""+phoneval
									+"\", "+DatabaseForDemo.EMAIL+"=\""+emailval
									+"\", "+DatabaseForDemo.ADDRESS+"=\""+addressval
									+ "\", "+DatabaseForDemo.MODIFIED_DATE+"=\""+ Parameters.currentTime() 
									+ "\", "+DatabaseForDemo.MODIFIED_IN+"=\"Local\" " 
									+"where "+DatabaseForDemo.USERID+"=\""+useridval+"\"");
							Toast.makeText(getApplicationContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
						    	}
			 			    }
				});
				
				cancelforedit.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						useridforedit.setText("");
						firstname.setText("");
						lastname.setText("");
						phoneforedit.setText("");
						emailforedit.setText("");
						addressforedit.setText("");
					}
				});
				inflatinglayout3.addView(layout);
			}
		});
		
		System.out.println("edit pwd is called");
	}
	
	public void dataUpdate(){
		
		//Cursor mCursor = demodb.getReadableDatabase().rawQuery("select * from "+DatabaseForDemo.ADMIN_TABLE+" where "+DatabaseForDemo.USERID+"='"+Parameters.usertype+"'", null);
		Cursor mCursor = dbforloginlogoutReadProfile.rawQuery("select * from "+DatabaseForDemo.ADMIN_TABLE+" where userid=\""+Parameters.usertypeloginvalue+"\"", null);
		System.out.println(mCursor);
		int count = mCursor.getColumnCount();
		System.out.println(count);
		if(mCursor!=null){
			if(mCursor.getCount()>0){
			if(mCursor.moveToFirst()){
				do{
					String[] data = new String[count];
					for(int i=0; i<count; i++){
						String value = mCursor.getString(i);
						data[i] = value;
					}
					useridforedit.setText(data[1]);
					passWord=data[2];
					firstname.setText(data[3]);
					lastname.setText(data[4]);
					phoneforedit.setText(data[5]);
					emailforedit.setText(data[6]);
					addressforedit.setText(data[7]);
					serverPass=data[8];
					unq_id=data[9];
					createdtime=data[10];
				}while(mCursor.moveToNext());
		}
			}
		
		}
		mCursor.close();
}
	
	public void updatedata(){
		listData.clear();
		
			Cursor mCursor = dbforloginlogoutReadProfile.rawQuery("select * from "+DatabaseForDemo.ADMIN_TABLE+" where userid=\""+Parameters.usertypeloginvalue+"\"", null);
			System.out.println(mCursor);
			if(mCursor!=null){
				int count = mCursor.getColumnCount();
				System.out.println(count);
				if(mCursor.getCount()>0){
				if(mCursor.moveToFirst()){
					do{
						for(int i=1; i<count; i++){
							HashMap<String, String> map = new HashMap<String, String>();
							//System.out.println(mCursor.getString(i).length());
						//	if(mCursor.getColumnName(i).equals(DatabaseForDemo.PASSWORD) || mCursor.getColumnName(i).equals(DatabaseForDemo.UNIQUE_ID) || mCursor.getColumnName(i).equals(DatabaseForDemo.MODIFIED_DATE) || mCursor.getColumnName(i).equals(DatabaseForDemo.MODIFIED_IN) || mCursor.getColumnName(i).equals(DatabaseForDemo.CREATED_DATE)){
								
						//	}else{
							if(mCursor.getColumnName(i).equals("userid")){
							map.put("key","User Id");
							map.put("value", mCursor.getString(i));
							listData.add(map);
							}
							if(mCursor.getColumnName(i).equals("firstname")){
								map.put("key","First Name");
								map.put("value", mCursor.getString(i));
								listData.add(map);
								}
							if(mCursor.getColumnName(i).equals("lastname")){
								map.put("key","Last Name");
								map.put("value", mCursor.getString(i));
								listData.add(map);
								}
							if(mCursor.getColumnName(i).equals("phonenumber")){
								map.put("key","Phone Number");
								map.put("value", mCursor.getString(i));
								listData.add(map);
								}
							if(mCursor.getColumnName(i).equals("email")){
								map.put("key","Email");
								map.put("value", mCursor.getString(i));
								listData.add(map);
								}
							if(mCursor.getColumnName(i).equals("address")){
								map.put("key","Address");
								map.put("value", mCursor.getString(i));
								listData.add(map);
								}
						//	}
						}
					}while(mCursor.moveToNext());
			}
			
			DetailsShowingArrayAdapter adapter = new DetailsShowingArrayAdapter(ProfileActivity.this, listData, R.layout.list_item,
	                new String[] {"key", "value" }, new int[] {R.id.textView_id, R.id.textView_name });
			list.setAdapter(adapter);
				}else{
					Toast.makeText(getApplicationContext(), "There is no profile", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(getApplicationContext(), "There is no profile", Toast.LENGTH_SHORT).show();
			}
			mCursor.close();
	
	}

	
	Boolean validetions() {
		String mail = userid.getText().toString().trim();
		if (checkUser(mail)) {
			//Toast.makeText(CreateProfileActivity.this, "Valid userid Addresss",
				//	Toast.LENGTH_SHORT).show();
			mail = pass.getText().toString().trim();
			if (checkPassword(mail)) {
				//Toast.makeText(CreateProfileActivity.this, "Valid pass Addresss",
					//	Toast.LENGTH_SHORT).show();
				if (mail.equals(confirmposs.getText().toString().trim())) {
					//Toast.makeText(CreateProfileActivity.this, "equal",
						//	Toast.LENGTH_SHORT).show();
					mail = phone.getText().toString().trim();
					if (checkPhone(mail)) {
						//Toast.makeText(CreateProfileActivity.this,
							//	"valid phone number", Toast.LENGTH_SHORT)
								//.show();
						mail = email.getText().toString().trim();
						if (checkEmail(mail)) {
							//Toast.makeText(CreateProfileActivity.this,
								//	"Valid Email Addresss", Toast.LENGTH_SHORT)
									//.show();
							return true;
						} else {
							Toast.makeText(ProfileActivity.this,
									"Invalid Email Addresss",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(ProfileActivity.this,
								"Invalid phone number", Toast.LENGTH_SHORT)
								.show();
					}

				} else {
					Toast.makeText(ProfileActivity.this,"Your entered password and confirm password must be same",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(ProfileActivity.this, "Invalid pass Addresss",
						Toast.LENGTH_SHORT).show();

			}
		} else {
			Toast.makeText(ProfileActivity.this, "Invalid userid Addresss",
					Toast.LENGTH_SHORT).show();

		}
		return false;

	}

	private boolean checkEmail(String mail) {
		return EMAIL_ADDRESS_PATTERN.matcher(mail).matches();
	}

	private boolean checkUser(String mail) {
		return USER_PATTERN.matcher(mail).matches();
	}

	private boolean checkPassword(String mail) {
		return PASSWORD_PATTERN.matcher(mail).matches();
	}

	private boolean checkPhone(String mail) {
		return PASSWORD_PATTERN.matcher(mail).matches();
	}

	GetJSONListener l = new GetJSONListener() {

		@Override
		public void onRemoteCallComplete(JSONObject jsonFromNet) {
			// TODO Auto-generated method stub
			// showDatafromJSONObject(jsonFromNet);
			Log.v("josn", "" + jsonFromNet);

		}

	};

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "clicked on" + arg0.getId(),
				500).show();
	}

	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message,
			Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting alert dialog icon
		// alertDialog.setIcon((status) ? R.drawable.success :
		// "No Internet Connection");

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		sqlDBProfile.close();
		super.onDestroy();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Parameters.printerContext=ProfileActivity.this;
		super.onResume();
	}
}
