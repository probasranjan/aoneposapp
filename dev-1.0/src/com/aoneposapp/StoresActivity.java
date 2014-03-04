package com.aoneposapp;

import java.util.ArrayList;
import java.util.HashMap;

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
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.net.ParseException;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aoneposapp.adapters.DetailsShowingArrayAdapter;
import com.aoneposapp.adapters.StoresEditAdapter;
import com.aoneposapp.utils.DatabaseForDemo;
import com.aoneposapp.utils.JsonPostMethod;
import com.aoneposapp.utils.Parameters;
import com.aoneposapp.utils.ServerSyncClass;

public class StoresActivity extends Activity implements
		android.view.View.OnClickListener, StoresEditAdapter.OnWidgetItemClicked {
	Button tab;
	SlidingDrawer slidingDrawer;
	private ImageView slideButton, image0, image1, image2, image3, image4, image5,
			image6, image7, image8, logout, logout2;
	int height;
	int wwidth;
	private Button b4, addcat, viewcat, headlineforedit;
	LinearLayout ll_add, ll_view;
	private EditText store_name, store_email, store_number, store_street,
	store_id, store_postalcode ;
	private TextView error;
private AutoCompleteTextView store_country, store_city,store_state;
private Spinner store_tax, store_currency;
private CheckBox store_discount;
private Button save,reset;
String dataval="";
private String[] fontsiz = { "Small", "Medium", "Large" };
ArrayList<String> citynames=new ArrayList<String>();
ArrayList<String> countrynames=new ArrayList<String>();
ArrayList<String> statenames=new ArrayList<String>();
ArrayList<String> idnames=new ArrayList<String>();

String uniqe_id1="";
String created = "";
 String modify=Parameters.currentTime();
ListView list;
Bundle savedInstanceState;
ArrayList<String> id_data = new ArrayList<String>();
ArrayList<String> desc_data = new ArrayList<String>();
ArrayList<String> total_id_data = new ArrayList<String>();
ArrayList<String> total_desc_data = new ArrayList<String>();
int pagenum = 1;
String datavalforedit = "";
public static int pagecount = 3;
public static int testforid = 1;
int stLoop = 0;
int endLoop = 0;
int totalcount = 0;
private LinearLayout ll;
int j;
DatabaseForDemo sqlDBStore;
SQLiteDatabase dbforloginlogoutWriteStore,dbforloginlogoutReadStore;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stores_activity);
		sqlDBStore=new DatabaseForDemo(StoresActivity.this);
		dbforloginlogoutWriteStore = sqlDBStore.getWritableDatabase();
		dbforloginlogoutReadStore = sqlDBStore.getReadableDatabase();
		Parameters.printerContext=StoresActivity.this;
		Parameters.ServerSyncTimer();
		try{
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
		image2.setBackgroundResource(R.drawable.storesactive);
		logout.setBackgroundResource(R.drawable.logoutnormal);
		TextView loginnameempid = (TextView)findViewById(R.id.loginnameval);
		loginnameempid.setText(Parameters.usertypeloginvalue);
		image0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(StoresActivity.this,
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
					Intent intent1 = new Intent(StoresActivity.this,
							InventoryActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							StoresActivity.this,
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

			}
		});
		image3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Parameters.customer_permission) {
					Intent intent1 = new Intent(StoresActivity.this,
							CustomerActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							StoresActivity.this,
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
				Intent intent1 = new Intent(StoresActivity.this,
						EmployeeActivity.class);
				startActivity(intent1);
				finish();
			} else {
				showAlertDialog(
						StoresActivity.this,
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
					Intent intent1 = new Intent(StoresActivity.this,
							ReportsActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							StoresActivity.this,
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
					Intent intent1 = new Intent(StoresActivity.this,
							SettingsActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							StoresActivity.this,
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
				Intent intent1 = new Intent(StoresActivity.this,
						ContactsActivity.class);
				startActivity(intent1);
				finish();
			}
		});
		image8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Parameters.profile_permission) {
					Intent intent1 = new Intent(StoresActivity.this,
							ProfileActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							StoresActivity.this,
							"Sorry",
							"You are not authenticated to perform this operation.",
							false);
				}
			}
		});

		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Parameters.methodForLogout(StoresActivity.this);
				finish();
			}
		});

		logout2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Parameters.methodForLogout(StoresActivity.this);
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
		
		addcat = (Button) findViewById(R.id.addcat);
		viewcat = (Button) findViewById(R.id.viewcat);
		ll_add = (LinearLayout) findViewById(R.id.add_ll);
		ll_view = (LinearLayout) findViewById(R.id.view_ll);
		
		addcat.setBackgroundResource(R.drawable.highlightedtopmenuitem);
		viewcat.setBackgroundResource(R.drawable.toprightmenu);
		ll_add.setVisibility(View.VISIBLE);
		ll_view.setVisibility(View.GONE);
		addcat.setTextColor(Color.BLACK);
		viewcat.setTextColor(Color.WHITE);
		
		list = (ListView) findViewById(R.id.listView1);
		ll = (LinearLayout) findViewById(R.id.linearLayout1);
		headlineforedit = (Button) findViewById(R.id.button2);
		headlineforedit.setText("Stores Details");
		list.setItemsCanFocus(false);
		
		addcat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addcat.setBackgroundResource(R.drawable.highlightedtopmenuitem);
				viewcat.setBackgroundResource(R.drawable.toprightmenu);
				ll_add.setVisibility(View.VISIBLE);
				ll_view.setVisibility(View.GONE);
				addcat.setTextColor(Color.BLACK);
				viewcat.setTextColor(Color.WHITE);
			}
		});

		viewcat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addcat.setBackgroundResource(R.drawable.toprightmenu);
				viewcat.setBackgroundResource(R.drawable.highlightedtopmenuitem);
				ll_add.setVisibility(View.GONE);
				ll_view.setVisibility(View.VISIBLE);
				addcat.setTextColor(Color.WHITE);
				viewcat.setTextColor(Color.BLACK);
				listUpdate();
			}
		});
		
		if (Parameters.isTableExists(dbforloginlogoutReadStore, DatabaseForDemo.STORE_TABLE)) {
			Cursor cursor1 = dbforloginlogoutReadStore.rawQuery("SELECT DISTINCT "+DatabaseForDemo.STORE_CITY+" FROM "+DatabaseForDemo.STORE_TABLE,null);

			if (cursor1 != null) {
				if (cursor1.moveToFirst()) {
					do {
						String cityname = cursor1.getString(cursor1
								.getColumnIndex(DatabaseForDemo.STORE_CITY));
						if(cityname.length()>1)
						citynames.add(cityname);
						
					} while (cursor1.moveToNext());
				}
				Log.v("city" ,""+citynames);
			}
			cursor1.close();
			Cursor cursor2 = dbforloginlogoutReadStore.rawQuery("SELECT DISTINCT "+DatabaseForDemo.STORE_COUNTRY+" FROM "+DatabaseForDemo.STORE_TABLE,null);

			if (cursor2 != null) {
				if (cursor2.moveToFirst()) {
					do {
						String cityname = cursor2.getString(cursor2
								.getColumnIndex(DatabaseForDemo.STORE_COUNTRY));
						Log.v("statenames" ,""+cityname);
						if(cityname.length()>1)
							countrynames.add(cityname);
						
					} while (cursor2.moveToNext());
				}
				Log.v("countrynames" ,""+countrynames);
				
				
			}
			cursor2.close();
			Cursor cursor3 = dbforloginlogoutReadStore.rawQuery("SELECT DISTINCT "+DatabaseForDemo.STORE_ID+" FROM "+DatabaseForDemo.STORE_TABLE,null);

			if (cursor3 != null) {
				if (cursor3.moveToFirst()) {
					do {
						String id = cursor3.getString(cursor3
								.getColumnIndex(DatabaseForDemo.STORE_ID));
						Log.v("idnames" ,""+id);
						if(id!=null)
						idnames.add(id);
						
					} while (cursor3.moveToNext());
				}
				Log.v("idnames" ,""+idnames);
				
				
			}
			cursor3.close();
			Cursor cursor4 = dbforloginlogoutReadStore.rawQuery("SELECT DISTINCT "+DatabaseForDemo.STORE_STATE+" FROM "+DatabaseForDemo.STORE_TABLE,null);

			if (cursor4 != null) {
				if (cursor4.moveToFirst()) {
					do {
						String cityname = cursor4.getString(cursor4
								.getColumnIndex(DatabaseForDemo.STORE_STATE));
						Log.v("statenames" ,""+cityname);
						if(cityname.length()>1)
						statenames.add(cityname);
						
					} while (cursor4.moveToNext());
				}
				Log.v("statenames" ,""+statenames);
				
				
			}
			cursor4.close();
		}
		store_name = (EditText) findViewById(R.id.store_name);
		store_id = (EditText) findViewById(R.id.store_id);
		store_email = (EditText) findViewById(R.id.store_email);
		store_number = (EditText) findViewById(R.id.store_number);
		store_street = (EditText) findViewById(R.id.street);
		store_postalcode = (EditText) findViewById(R.id.postalcode);
		store_city = (AutoCompleteTextView) findViewById(R.id.city);
		store_country = (AutoCompleteTextView) findViewById(R.id.country);
		store_state = (AutoCompleteTextView) findViewById(R.id.state);
		store_state.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(store_state.getWindowToken(), 0);
				}
				return false;
			}
		});
		store_tax = (Spinner) findViewById(R.id.store_tax);
		store_currency = (Spinner) findViewById(R.id.store_currency);
		store_discount = (CheckBox) findViewById(R.id.store_discount);
		save = (Button) findViewById(R.id.store_update);
		reset = (Button) findViewById(R.id.store_reset);
		error=(TextView) findViewById(R.id.iderror);
		
		ArrayAdapter<String> adaptercity = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, citynames);
		store_city.setThreshold(1);
		store_city.setAdapter(adaptercity);
		ArrayAdapter<String> adaptercoun = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, countrynames);
		store_country.setThreshold(1);
		store_country.setAdapter(adaptercoun);
		ArrayAdapter<String> adapterstate = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, statenames);
		store_state.setThreshold(1);
		store_state.setAdapter(adapterstate);
		
		ArrayAdapter<String> sizeadapter = new ArrayAdapter<String>(
				StoresActivity.this,
				android.R.layout.simple_dropdown_item_1line, fontsiz);
		store_tax.setAdapter(sizeadapter);
		store_currency.setAdapter(sizeadapter);

		store_id.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus){
					error.setText("");
				}
			}
		});
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ArrayList<String> storearray = new ArrayList<String>();
				ArrayList<String> storearrayid = new ArrayList<String>();
				String idd=store_id.getText().toString().trim();
				if (Parameters.isTableExists(dbforloginlogoutReadStore, DatabaseForDemo.STORE_TABLE)) {
			
				Cursor mCursor = dbforloginlogoutReadStore.rawQuery(
						"select * from " + DatabaseForDemo.STORE_TABLE, null);
				System.out.println(mCursor);
				if (mCursor != null) {
					if (mCursor.moveToFirst()) {
						do {
							String storename = mCursor.getString(mCursor
									.getColumnIndex(DatabaseForDemo.STORE_NAME));
							String storeid = mCursor.getString(mCursor
									.getColumnIndex(DatabaseForDemo.STORE_ID));
							storearray.add(storename);
							storearrayid.add(storeid);
						} while (mCursor.moveToNext());
					}
				}
				mCursor.close();
				}
				if(store_name.getText().toString().trim().length()>3){
if(idd.length()>3){
	if(storearray.indexOf(store_name.getText().toString().trim())==-1){
	if(storearrayid.indexOf(idd)==-1){
		String strRandom=Parameters.randomValue();
				ContentValues contentValues = new ContentValues();
				contentValues.put(DatabaseForDemo.STORE_NAME, store_name
						.getText().toString().trim());
				contentValues.put(DatabaseForDemo.STORE_ID, store_id.getText()
						.toString().trim());
				contentValues.put(DatabaseForDemo.STORE_EMAIL, store_email
						.getText().toString().trim());
				contentValues.put(DatabaseForDemo.STORE_NUMBER, store_number
						.getText().toString().trim());
				contentValues.put(DatabaseForDemo.STORE_STREET, store_street
						.getText().toString().trim());
				contentValues.put(DatabaseForDemo.STORE_POSTAL, store_postalcode
						.getText().toString().trim());
				contentValues.put(DatabaseForDemo.STORE_CITY, store_city
						.getText().toString().trim());
				contentValues.put(DatabaseForDemo.STORE_COUNTRY, store_country
						.getText().toString().trim());
				contentValues.put(DatabaseForDemo.STORE_STATE, store_state
						.getText().toString().trim());
				contentValues.put(DatabaseForDemo.STORE_DEFAULTTAX, store_tax
						.getSelectedItem().toString());
				contentValues.put(DatabaseForDemo.STORE_DISCOUNT, ""
						+ store_discount.isChecked());
				contentValues.put(DatabaseForDemo.STORE_CURRENCY, store_currency
						.getSelectedItem().toString());
				contentValues.put(DatabaseForDemo.UNIQUE_ID,  strRandom);
				contentValues.put(DatabaseForDemo.CREATED_DATE, Parameters.currentTime());
				contentValues.put(DatabaseForDemo.MODIFIED_DATE,  Parameters.currentTime());
				contentValues.put(DatabaseForDemo.MODIFIED_IN, "Local");
				Log.i("select", "" + contentValues);

				dbforloginlogoutWriteStore.insert(DatabaseForDemo.STORE_TABLE, null,
						contentValues);
				Toast.makeText(StoresActivity.this, "Saved", 1000)
						.show();
				contentValues.clear();
				error.setText("");
			//	if(Parameters.mfindServer_url){
				try {
				JSONObject data = new JSONObject();
				JSONObject jsonobj = new JSONObject();
				jsonobj.put(DatabaseForDemo.STORE_NAME, store_name
						.getText().toString().trim());
				jsonobj.put(DatabaseForDemo.STORE_ID, store_id.getText()
						.toString().trim());
				jsonobj.put(DatabaseForDemo.STORE_EMAIL, store_email
						.getText().toString().trim());
				jsonobj.put(DatabaseForDemo.STORE_NUMBER, store_number
						.getText().toString().trim());
				jsonobj.put(DatabaseForDemo.STORE_STREET, store_street
						.getText().toString().trim());
				jsonobj.put(DatabaseForDemo.STORE_POSTAL, store_postalcode
						.getText().toString().trim());
				jsonobj.put(DatabaseForDemo.STORE_CITY, store_city
						.getText().toString().trim());
				jsonobj.put(DatabaseForDemo.STORE_COUNTRY, store_country
						.getText().toString().trim());
				jsonobj.put(DatabaseForDemo.STORE_STATE, store_state
						.getText().toString().trim());
				jsonobj.put(DatabaseForDemo.STORE_DEFAULTTAX, store_tax
						.getSelectedItem().toString());
				jsonobj.put(DatabaseForDemo.STORE_DISCOUNT, ""
						+ store_discount.isChecked());
				jsonobj.put(DatabaseForDemo.STORE_CURRENCY, store_currency
						.getSelectedItem().toString());
				jsonobj.put(DatabaseForDemo.UNIQUE_ID,  strRandom);
				jsonobj.put(DatabaseForDemo.CREATED_DATE, Parameters.currentTime());
				jsonobj.put(DatabaseForDemo.MODIFIED_DATE,  Parameters.currentTime());
				jsonobj.put(DatabaseForDemo.MODIFIED_IN, "Local");
				
				
				JSONArray fields = new JSONArray();
				fields.put(0, jsonobj);
					data.put("fields", fields);
					dataval = data.toString();
					System.out.println("data val is: "+dataval);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(Parameters.OriginalUrl.equals("")){
					System.out.println("there is no server url val");
				}else{
			boolean isnet = Parameters.isNetworkAvailable(getApplicationContext());
			if(isnet){
				new Thread(new Runnable() {
				    @Override
					public void run() {
        				JsonPostMethod jsonpost = new JsonPostMethod();
        				String response = jsonpost.postmethodfordirect("admin", "abcdefg", DatabaseForDemo.STORE_TABLE, Parameters.currentTime(), Parameters.currentTime(), dataval, "");
        				System.out.println("response test is: "+response);
        				if(response.length()>1&&response!=null){
        				String servertiem = null;
        				try {
							JSONObject obj = new JSONObject(response);
							JSONObject responseobj = obj.getJSONObject("response");
							servertiem = responseobj.getString("server-time");
							System.out.println("servertime is:"+servertiem);
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
								
								dbforloginlogoutWriteStore.execSQL(deletequery);
								dbforloginlogoutWriteStore.execSQL(insertquery);
								System.out.println("queries executed"+ii);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}catch(SQLiteException e1){
							e1.printStackTrace();
						}
        			
    					String select = "select *from "+DatabaseForDemo.MISCELLANEOUS_TABLE;
    					Cursor cursor = dbforloginlogoutReadStore.rawQuery(select, null);
    					if(cursor.getCount()>0){
    						dbforloginlogoutWriteStore.execSQL("update "+DatabaseForDemo.MISCELLANEOUS_TABLE+" set "+DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL+"=\""+servertiem+"\"");
    						cursor.close();
    					}else{
    						cursor.close();
    						ContentValues contentValues1 = new ContentValues();
        					contentValues1.put(DatabaseForDemo.MISCEL_STORE,  "store1");
        					contentValues1.put(DatabaseForDemo.MISCEL_PAGEURL, "");
        					contentValues1.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL, Parameters.currentTime());
        					contentValues1.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL, Parameters.currentTime());
        					dbforloginlogoutWriteStore.insert(DatabaseForDemo.MISCELLANEOUS_TABLE, null, contentValues1);
        					
    					}
        				}else{
//        					Toast.makeText(EmployeeActivity.this, "Server Error", 1000).show();
							Log.v("errorr", "server");
        				}
        				dataval = "";
        				
				    }
				  }).start();
			}else{
				
				ContentValues contentValues1 = new ContentValues();
				contentValues1.put(DatabaseForDemo.QUERY_TYPE,  "insert");
				contentValues1.put(DatabaseForDemo.PENDING_USER_ID, Parameters.userid);
				contentValues1.put(DatabaseForDemo.PAGE_URL,  "saveinfo.php");
				contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING, DatabaseForDemo.STORE_TABLE);
				contentValues1.put(DatabaseForDemo.CURRENT_TIME_PENDING, Parameters.currentTime());
				contentValues1.put(DatabaseForDemo.PARAMETERS, dataval);
				dbforloginlogoutWriteStore.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null, contentValues1);
				dataval = "";
			}
				
				}	
		//}
	}else{
		Toast.makeText(getApplicationContext(), "Id already exists. Please enter a different Id", Toast.LENGTH_SHORT).show();
		error.setText("*");
	}
	}else{
		Toast.makeText(getApplicationContext(), "Name already exists. Please enter a different Name", Toast.LENGTH_SHORT).show();
		error.setText("*");
	}
}else{
	error.setText("*");
	Toast.makeText(getApplicationContext(), "Enter Id above 3 characters", Toast.LENGTH_SHORT).show();
	
}
				}else{
					Toast.makeText(getApplicationContext(),
							"Enter Name above 3 characters", Toast.LENGTH_SHORT).show();
				}
			}
		});
		reset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				store_name.setText(""); store_email.setText(""); store_number.setText(""); store_street.setText("");
				store_id.setText(""); store_postalcode.setText(""); 
				store_country.setText(""); store_city.setText("");store_state.setText("");
				store_discount.setChecked(false);
				store_tax.setSelection(0); store_currency.setSelection(0);
			}
		});
		}
		catch (SQLiteException e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (Exception e1) {
			// TODO: handle exception
			e1.printStackTrace();
		}

	}
	
	public void count(int j) {
		testforid = 1;
		stLoop = (j - 1) * pagecount;
		endLoop = j * pagecount;
		if (endLoop >= totalcount) {
			endLoop = totalcount;
		}
		Log.v("lop", stLoop + "" + endLoop);
		id_data = getData(stLoop, endLoop, total_id_data);
		desc_data = getData(stLoop, endLoop, total_desc_data);
		Log.v("lop", "" + desc_data);
		System.out.println("vlaues_first size:" + id_data.size());
		System.out.println("vlaues_first size:" + desc_data.size());

		StoresEditAdapter adapter = new StoresEditAdapter(this, id_data,
				desc_data);
		adapter.setListener(this);
		list.setAdapter(adapter);
	}

	public ArrayList<String> getData(int offset, int limit,
			ArrayList<String> list) {
		ArrayList<String> newList = new ArrayList<String>(limit);
		int end = limit;

		if (end > list.size()) {
			end = list.size();
		}
		newList.addAll(list.subList(offset, end));
		return newList;
	}

	@Override
	protected void onResume() {
		super.onResume();
		onCreate(savedInstanceState);
		Parameters.printerContext=StoresActivity.this;
	}

	@Override
	public void onEditClicked(View v, final String id, String desc) {
		// TODO Auto-generated method stub
		ArrayList<String> citynames=new ArrayList<String>();
		ArrayList<String> countrynames=new ArrayList<String>();
		ArrayList<String> statenames=new ArrayList<String>();
		final ArrayList<String> idnames=new ArrayList<String>();
		final AlertDialog alertDialog1 = new AlertDialog.Builder(
				StoresActivity.this).create();
		final EditText store_name, store_email, store_number, store_street, store_id, store_postalcode;
		final AutoCompleteTextView store_country, store_city,store_state;
		final Spinner store_tax, store_currency;
		final CheckBox store_discount;
		Button save,reset;
		String[] fontsiz = { "Small", "Medium", "Large" };
		LayoutInflater mInflater = LayoutInflater.from(StoresActivity.this);
		View layout = mInflater.inflate(R.layout.application_settings, null);
		store_name = (EditText) layout.findViewById(R.id.store_name);
		store_id = (EditText) layout.findViewById(R.id.store_id);
		store_email = (EditText) layout.findViewById(R.id.store_email);
		store_number = (EditText) layout.findViewById(R.id.store_number);
		store_street = (EditText) layout.findViewById(R.id.street);
		store_postalcode = (EditText) layout.findViewById(R.id.postalcode);
		store_city = (AutoCompleteTextView) layout.findViewById(R.id.city);
		store_country = (AutoCompleteTextView) layout.findViewById(R.id.country);
		store_state = (AutoCompleteTextView) layout.findViewById(R.id.state);
		store_state.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(store_state.getWindowToken(), 0);
				}
				return false;
			}
		});
		store_tax = (Spinner) layout.findViewById(R.id.store_tax);
		store_currency = (Spinner) layout.findViewById(R.id.store_currency);
		store_discount = (CheckBox) layout.findViewById(R.id.store_discount);
		save = (Button) layout.findViewById(R.id.store_update);
		reset = (Button) layout.findViewById(R.id.store_reset);

		if (Parameters.isTableExists(dbforloginlogoutReadStore, DatabaseForDemo.STORE_TABLE)) {
			Cursor cursor1 = dbforloginlogoutReadStore.rawQuery("SELECT DISTINCT "
					+ DatabaseForDemo.STORE_CITY + " FROM "
					+ DatabaseForDemo.STORE_TABLE, null);

			if (cursor1 != null) {
				if (cursor1.moveToFirst()) {
					do {
						String cityname = cursor1.getString(cursor1
								.getColumnIndex(DatabaseForDemo.STORE_CITY));
						if (cityname.length() > 1)
							citynames.add(cityname);

					} while (cursor1.moveToNext());
				}
				Log.v("city", "" + citynames);
			}
			cursor1.close();
			Cursor cursor2 = dbforloginlogoutReadStore.rawQuery("SELECT DISTINCT "
					+ DatabaseForDemo.STORE_COUNTRY + " FROM "
					+ DatabaseForDemo.STORE_TABLE, null);

			if (cursor2 != null) {
				if (cursor2.moveToFirst()) {
					do {
						String cityname = cursor2.getString(cursor2
								.getColumnIndex(DatabaseForDemo.STORE_COUNTRY));
						Log.v("statenames", "" + cityname);
						if (cityname.length() > 1)
							countrynames.add(cityname);

					} while (cursor2.moveToNext());
				}
				Log.v("countrynames", "" + countrynames);

			}
			cursor2.close();
			Cursor cursor3 = dbforloginlogoutReadStore.rawQuery("SELECT DISTINCT "
					+ DatabaseForDemo.STORE_ID + " FROM "
					+ DatabaseForDemo.STORE_TABLE, null);

			if (cursor3 != null) {
				if (cursor3.moveToFirst()) {
					do {
						String id11 = cursor3.getString(cursor3
								.getColumnIndex(DatabaseForDemo.STORE_ID));
						Log.v("idnames", "" + id11);
						if (id11 != null)
							idnames.add(id11);

					} while (cursor3.moveToNext());
				}
				Log.v("idnames", "" + idnames);

			}
			cursor3.close();
			Cursor cursor4 = dbforloginlogoutReadStore.rawQuery("SELECT DISTINCT "
					+ DatabaseForDemo.STORE_STATE + " FROM "
					+ DatabaseForDemo.STORE_TABLE, null);

			if (cursor4 != null) {
				if (cursor4.moveToFirst()) {
					do {
						String cityname = cursor4.getString(cursor4
								.getColumnIndex(DatabaseForDemo.STORE_STATE));
						Log.v("statenames", "" + cityname);
						if (cityname.length() > 1)
							statenames.add(cityname);

					} while (cursor4.moveToNext());
				}
				Log.v("statenames", "" + statenames);

			}
			cursor4.close();

		}
		ArrayAdapter<String> sizeadapter = new ArrayAdapter<String>(
				alertDialog1.getContext(),
				android.R.layout.simple_dropdown_item_1line, fontsiz);
		Log.v("adapter", "" + sizeadapter.getCount());
		Log.v("list", "" + store_tax);
		store_tax.setAdapter(sizeadapter);
		store_currency.setAdapter(sizeadapter);
		Cursor mCursor = dbforloginlogoutReadStore.rawQuery("select * from "
				+ DatabaseForDemo.STORE_TABLE + " where "
				+ DatabaseForDemo.STORE_ID + "=\"" + id + "\"", null);
		System.out.println(mCursor);
		if (mCursor != null) {
			int count = mCursor.getColumnCount();
			System.out.println(count);
			if (mCursor.moveToFirst()) {
				do {
					String[] data = new String[count];
					for (int i = 1; i < count; i++) {
						System.out.println(mCursor.getString(i).length());
						data[i] = mCursor.getString(i);
					}
					store_name.setText(data[1]);
					store_id.setText(data[2]);
					store_email.setText(data[3]);
					store_number.setText(data[4]);
					store_street.setText(data[5]);
					store_city.setText(data[6]);
					store_postalcode.setText(data[7]);
					store_country.setText(data[8]);
					store_state.setText(data[9]);
					
				//	dfttax=data[10];
				//	discount=data[11];
					//currency=data[12];
					uniqe_id1=data[13];
					created=data[14];
					
					ArrayAdapter myAdap1 = (ArrayAdapter) store_tax
							.getAdapter();
					int val = myAdap1.getPosition(data[10]);
					store_tax.setSelection(val);
					if (data[11].equals("true")) {
						store_discount.setChecked(true);
					} else {
						store_discount.setChecked(false);
					}

					ArrayAdapter myAdap2 = (ArrayAdapter) store_currency
							.getAdapter();
					int val1 = myAdap2.getPosition(data[12]);
					store_currency.setSelection(val1);
				} while (mCursor.moveToNext());

			}
		}
		mCursor.close();

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.v("uu", "ggg");
				String idd = store_id.getText().toString().trim();
				if (store_name.getText().toString().trim().length() > 3) {
					if (idd.length() > 3) {
						dbforloginlogoutWriteStore.execSQL("update "
									+ DatabaseForDemo.STORE_TABLE
									+ " set "
									+ DatabaseForDemo.STORE_NAME
									+ "=\""
									+ store_name.getText().toString().trim()
									+ "\", "
									+ DatabaseForDemo.STORE_EMAIL
									+ "=\""
									+ store_email.getText().toString().trim()
									+ "\", "
									+ DatabaseForDemo.STORE_NUMBER
									+ "=\""
									+ store_number.getText().toString().trim()
									+ "\", "
									+ DatabaseForDemo.STORE_ID
									+ "=\""
									+ store_id.getText().toString().trim()
									+ "\", "
									+ DatabaseForDemo.STORE_POSTAL
									+ "=\""
									+ store_postalcode.getText().toString()
											.trim()
									+ "\", "
									+ DatabaseForDemo.STORE_COUNTRY
									+ "=\""
									+ store_country.getText().toString().trim()
									+ "\", "
									+ DatabaseForDemo.STORE_STREET
									+ "=\""
									+ store_street.getText().toString().trim()
									+ "\", "
									+ DatabaseForDemo.MODIFIED_DATE
									+ "=\""
									+modify
									+ "\", "
									+ DatabaseForDemo.STORE_DEFAULTTAX
									+ "=\""
									+ store_tax.getSelectedItem().toString()
									+ "\", "
									+ DatabaseForDemo.STORE_CURRENCY
									+ "=\""
									+ store_currency.getSelectedItem()
											.toString() + "\", "
									+ DatabaseForDemo.STORE_CITY + "=\""
									+ store_city.getText().toString().trim()
									+ "\", " + DatabaseForDemo.STORE_STATE + "=\""
									+ store_state.getText().toString().trim()
									+ "\", " + DatabaseForDemo.STORE_DISCOUNT
									+ "=\"" + store_discount.isChecked()
									+ "\" where " + DatabaseForDemo.STORE_ID
									+ "=\"" + id + "\"");
							Toast.makeText(alertDialog1.getContext(), "Saved",
									1000).show();
							id_data.clear();
							desc_data.clear();
							total_id_data.clear();
							total_desc_data.clear();
							onCreate(savedInstanceState);
							// listUpdate();
							alertDialog1.dismiss();
						//	if (Parameters.mfindServer_url) {
								try {
									JSONObject data = new JSONObject();
									JSONObject jsonobj = new JSONObject();

									jsonobj.put(DatabaseForDemo.STORE_NAME,
									 store_name.getText().toString().trim());
									jsonobj.put(DatabaseForDemo.STORE_EMAIL,
											store_email.getText().toString().trim());
									jsonobj.put(DatabaseForDemo.STORE_NUMBER,
											store_number.getText().toString().trim());
									jsonobj.put(DatabaseForDemo.STORE_ID,
											store_id.getText().toString().trim());
									jsonobj.put(DatabaseForDemo.STORE_POSTAL,
											store_postalcode.getText().toString().trim());
									jsonobj.put(DatabaseForDemo.STORE_COUNTRY,
											store_country.getText().toString().trim());
									jsonobj.put(DatabaseForDemo.STORE_STREET,
											store_street.getText().toString().trim());
									jsonobj.put(DatabaseForDemo.STORE_DEFAULTTAX,
											store_tax.getSelectedItem().toString());
									jsonobj.put(DatabaseForDemo.STORE_CURRENCY,
											store_currency.getSelectedItem().toString());
									jsonobj.put(DatabaseForDemo.STORE_CITY,
											store_city.getText().toString().trim());
									jsonobj.put(DatabaseForDemo.STORE_STATE,
											store_state.getText().toString().trim());
									jsonobj.put(DatabaseForDemo.STORE_DISCOUNT,
											store_discount.getText().toString().trim());
									jsonobj.put(DatabaseForDemo.UNIQUE_ID, uniqe_id1);
									jsonobj.put(DatabaseForDemo.CREATED_DATE, created);
									jsonobj.put(DatabaseForDemo.MODIFIED_DATE, modify);
									jsonobj.put(DatabaseForDemo.MODIFIED_IN, "Local");
									
									JSONArray fields = new JSONArray();
									fields.put(0, jsonobj);
									data.put("fields", fields);
									datavalforedit = data.toString();
									System.out.println("data val is:" + datavalforedit);
								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								if(Parameters.OriginalUrl.equals("")){
									System.out.println("there is no server url val");
								}else{
								boolean isnet = Parameters
										.isNetworkAvailable(getApplicationContext());
								if (isnet) {
									new Thread(new Runnable() {
										@Override
										public void run() {
											JsonPostMethod jsonpost = new JsonPostMethod();
											String response = jsonpost.postmethodfordirect("admin",
													"abcdefg", DatabaseForDemo.STORE_TABLE,
													Parameters.currentTime(),
													Parameters.currentTime(), datavalforedit, "true");
											System.out.println("response test is:" + response);
											String servertiem = null;
											try {
												JSONObject obj = new JSONObject(response);
												JSONObject responseobj = obj
														.getJSONObject("response");
												servertiem = responseobj.getString("server-time");
												System.out.println("servertime is:" + servertiem);
												JSONArray array = obj
														.getJSONArray("insert-queries");
												System.out
														.println("array list length for insert is:"
																+ array.length());
												JSONArray array2 = obj
														.getJSONArray("delete-queries");
												System.out
														.println("array2 list length for delete is:"
																+ array2.length());
												for (int jj = 0, ii = 0; jj < array2.length()
														&& ii < array.length(); jj++, ii++) {
													String deletequerytemp = array2.getString(jj);
													String deletequery1 = deletequerytemp.replace(
															"'", "\"");
													String deletequery = deletequery1.replace(
															"\\\"", "'");
													System.out.println("delete query" + jj
															+ " is :" + deletequery);

													String insertquerytemp = array.getString(ii);
													String insertquery1 = insertquerytemp.replace(
															"'", "\"");
													String insertquery = insertquery1.replace(
															"\\\"", "'");
													System.out.println("delete query" + jj
															+ " is :" + insertquery);

													dbforloginlogoutWriteStore.execSQL(deletequery);
													dbforloginlogoutWriteStore.execSQL(insertquery);
													System.out.println("queries executed" + ii);
												}
											} catch (JSONException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}

											String select = "select *from "
													+ DatabaseForDemo.MISCELLANEOUS_TABLE;
											Cursor cursor = dbforloginlogoutReadStore.rawQuery(select, null);
											if (cursor.getCount() > 0) {
												dbforloginlogoutWriteStore.execSQL("update "
														+ DatabaseForDemo.MISCELLANEOUS_TABLE
														+ " set "
														+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
														+ "=\"" + servertiem + "\"");
												cursor.close();
											} else {
												cursor.close();
												ContentValues contentValues1 = new ContentValues();
												contentValues1.put(DatabaseForDemo.MISCEL_STORE,
														"store1");
												contentValues1
														.put(DatabaseForDemo.MISCEL_PAGEURL,
																"");
												contentValues1.put(
														DatabaseForDemo.MISCEL_UPDATE_LOCAL,
														Parameters.currentTime());
												contentValues1.put(
														DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL,
														Parameters.currentTime());
												dbforloginlogoutWriteStore.insert(DatabaseForDemo.MISCELLANEOUS_TABLE,
														null, contentValues1);
											}
											datavalforedit = "";
										}
									}).start();
								} else {
									
									ContentValues contentValues1 = new ContentValues();
									contentValues1.put(DatabaseForDemo.QUERY_TYPE, "update");
									contentValues1.put(DatabaseForDemo.PENDING_USER_ID, Parameters.userid);
									contentValues1.put(DatabaseForDemo.PAGE_URL, "saveinfo.php");
									contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING,
											DatabaseForDemo.STORE_TABLE);
									contentValues1.put(DatabaseForDemo.CURRENT_TIME_PENDING,
											Parameters.currentTime());
									contentValues1.put(DatabaseForDemo.PARAMETERS, datavalforedit);
									dbforloginlogoutWriteStore.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
											contentValues1);
									datavalforedit = "";
								}
								}

							//}
					
					} else {
						Toast.makeText(getApplicationContext(),
								"Enter Id above 4 characters",
								Toast.LENGTH_SHORT).show();

					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Enter Name above 4 characters", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
reset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				store_name.setText(""); store_email.setText(""); store_number.setText(""); store_street.setText("");
				store_id.setText(""); store_postalcode.setText(""); 
				store_country.setText("");
				store_city.setText("");
				store_state.setText("");
				store_discount.setChecked(false);
				store_tax.setSelection(0); store_currency.setSelection(0);
			}
		});
		alertDialog1.setView(layout);
		alertDialog1.show();
	}

	@Override
	public void onDeleteClicked(View v, final String id) {
		// TODO Auto-generated method stub
		final AlertDialog alertDialog1 = new AlertDialog.Builder(
				StoresActivity.this).create();
		LayoutInflater mInflater = LayoutInflater.from(StoresActivity.this);
		View layout = mInflater.inflate(R.layout.delete_popup, null);
		Button ok = (Button) layout.findViewById(R.id.ok);
		Button cancel = (Button) layout.findViewById(R.id.cancel);

		alertDialog1.setTitle("Delete");

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String name="";
				
				String selectQuery = "SELECT  * FROM " + DatabaseForDemo.STORE_TABLE+" where "+DatabaseForDemo.STORE_ID+"=\""+id+"\";";

				Cursor mCursor = dbforloginlogoutReadStore.rawQuery(selectQuery, null);

				if (mCursor != null) {
					if (mCursor.moveToFirst()) {
						do {
							 name = mCursor.getString(mCursor
									.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
						} while (mCursor.moveToNext());
					}
				}
				
				
				
				String where = DatabaseForDemo.STORE_ID + "=?";
				dbforloginlogoutWriteStore.delete(DatabaseForDemo.STORE_TABLE, where,
						new String[] { id });

				Toast.makeText(StoresActivity.this,
						"Store deleted successfully", Toast.LENGTH_SHORT)
						.show();
				mCursor.close();
				id_data.clear();
				desc_data.clear();
				total_id_data.clear();
				total_desc_data.clear();
				onCreate(savedInstanceState);
				// listUpdate();
				alertDialog1.dismiss();
				try {
					JSONArray unique_ids = new JSONArray();
					
						unique_ids.put(0,name);
						datavalforedit = unique_ids.toString();
					
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if(Parameters.OriginalUrl.equals("")){
					System.out.println("there is no server url val");
				}else{
				boolean isnet = Parameters.isNetworkAvailable(getApplicationContext());
				if (isnet) {
					new Thread(new Runnable() {
						@Override
						public void run() {
				
							JsonPostMethod jsonpost = new JsonPostMethod();
							String response = jsonpost.postmethodfordirectdelete(
									"admin", "abcdefg", DatabaseForDemo.STORE_TABLE,
									Parameters.currentTime(), Parameters.currentTime(),
									datavalforedit);
							System.out.println("response test is:" + response);
							String servertiem = null;
							try {
								JSONObject obj = new JSONObject(response);
								JSONObject responseobj = obj.getJSONObject("response");
								servertiem = responseobj.getString("server-time");
								System.out.println("servertime is:" + servertiem);
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							String select = "select *from "
									+ DatabaseForDemo.MISCELLANEOUS_TABLE;
							Cursor cursor = dbforloginlogoutReadStore.rawQuery(select, null);
							if (cursor.getCount() > 0) {
								dbforloginlogoutWriteStore.execSQL("update "
										+ DatabaseForDemo.MISCELLANEOUS_TABLE + " set "
										+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
										+ "=\"" + servertiem + "\"");
								cursor.close();
							} else {
								cursor.close();
								ContentValues contentValues1 = new ContentValues();
								contentValues1.put(DatabaseForDemo.MISCEL_STORE,
										"store1");
								contentValues1
										.put(DatabaseForDemo.MISCEL_PAGEURL,
												"");
								contentValues1.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL,
										Parameters.currentTime());
								contentValues1.put(
										DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL,
										Parameters.currentTime());
								dbforloginlogoutWriteStore.insert(DatabaseForDemo.MISCELLANEOUS_TABLE, null,
										contentValues1);
							}
							datavalforedit = "";
						}
					}).start();
				} else {
					
							
					ContentValues contentValues1 = new ContentValues();
					contentValues1.put(DatabaseForDemo.QUERY_TYPE, "delete");
					contentValues1.put(DatabaseForDemo.PENDING_USER_ID, Parameters.userid);
					contentValues1.put(DatabaseForDemo.PAGE_URL, "deleteinfo.php");
					contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING, DatabaseForDemo.STORE_TABLE);
					contentValues1.put(DatabaseForDemo.CURRENT_TIME_PENDING,
							Parameters.currentTime());
					contentValues1.put(DatabaseForDemo.PARAMETERS, datavalforedit);
					dbforloginlogoutWriteStore.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
							contentValues1);
					datavalforedit = "";
				}
				}
			}
			
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				alertDialog1.dismiss();
			}
		});
		alertDialog1.setView(layout);
		alertDialog1.show();
	}

	public void listUpdate() {
		total_id_data.clear();
		total_desc_data.clear();
		id_data.clear();
		desc_data.clear();
		ll.removeAllViews();
		String selectQuery = "SELECT  * FROM " + DatabaseForDemo.STORE_TABLE;

		Cursor mCursor = dbforloginlogoutReadStore.rawQuery(selectQuery, null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String name = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.STORE_NAME));
					name = name
							+ "^"
							+ mCursor.getString(mCursor
									.getColumnIndex(DatabaseForDemo.STORE_ID));
					total_id_data.add(name);
					String city = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.STORE_CITY));
					city = city
							+ ","
							+ mCursor
									.getString(mCursor
											.getColumnIndex(DatabaseForDemo.STORE_COUNTRY));
					city = city
							+ ","
							+ mCursor
									.getString(mCursor
											.getColumnIndex(DatabaseForDemo.STORE_STATE));
					total_desc_data.add(city);
				} while (mCursor.moveToNext());
			}
			mCursor.close();
			totalcount = total_id_data.size();
			System.out.println("total count ve is:" + totalcount);

			int to = totalcount / pagecount;
			int too = totalcount % pagecount;
			int i = to;
			if (too != 0) {
				i = to + 1;
			}
			count(1);
			ll.setWeightSum(i);
			System.out.println("total count value :" + i);
			for (j = 1; j <= i; j++) {
				final Button btn = new Button(this);
				btn.setText("" + j);
				btn.setId(j);
				btn.setBackgroundResource(R.drawable.pnormal);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
						1f);

				LinearLayout space = new LinearLayout(this);
				LayoutParams lp1 = new LayoutParams(1,
						LayoutParams.WRAP_CONTENT);
				ll.addView(btn, lp);
				ll.addView(space, lp1);
				((Button) findViewById(1))
						.setBackgroundResource(R.drawable.pactive);
				((Button) findViewById(1)).setTextColor(Color.WHITE);
				btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						for (int l = 1; l <= j - 1; l++) {
							((Button) findViewById(l))
									.setBackgroundResource(R.drawable.pnormal);
							((Button) findViewById(l))
									.setTextColor(Color.BLACK);
							System.out.println("total btn is:" + l);
						}
						btn.setBackgroundResource(R.drawable.pactive);
						btn.setTextColor(Color.WHITE);
						System.out.println("total count id is:" + btn.getId());
						count(Integer.parseInt(btn.getText().toString().trim()));
						testforid = Integer.parseInt(btn.getText().toString()
								.trim());
					}

				});

			}

		}
	}

	@Override
	public void onViewClicked(View v, final String id) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		final AlertDialog alertDialog1 = new AlertDialog.Builder(
				StoresActivity.this).create();
		LayoutInflater mInflater = LayoutInflater.from(StoresActivity.this);
		View layout = mInflater.inflate(R.layout.view_details, null);
		ListView listview = (ListView) layout.findViewById(R.id.listView1);
		ArrayList<HashMap<String, String>> listData = new ArrayList<HashMap<String, String>>();
		Cursor mCursor = dbforloginlogoutReadStore.rawQuery("select * from "
				+ DatabaseForDemo.STORE_TABLE + " where "
				+ DatabaseForDemo.STORE_ID + "=\"" + id + "\"", null);
		System.out.println(mCursor);
		if (mCursor != null) {
			int count = mCursor.getColumnCount();
			System.out.println(count);
			if (mCursor.moveToFirst()) {
				do {
					for (int i = 1; i < count; i++) {
						HashMap<String, String> map = new HashMap<String, String>();
						System.out.println(mCursor.getString(i).length());
						map.put("key", mCursor.getColumnName(i));
						map.put("value", mCursor.getString(i));
						listData.add(map);
					}
				} while (mCursor.moveToNext());
			}
			mCursor.close();
			Log.v("adapter", "" + listData.size());
			DetailsShowingArrayAdapter adapter1 = new DetailsShowingArrayAdapter(
					StoresActivity.this, listData, R.layout.list_item,
					new String[] { "key", "value" }, new int[] {
							R.id.textView_id, R.id.textView_name });
			Log.v("adapter", "" + adapter1.getCount());
			Log.v("list", "" + listview);
			// listview.addHeaderView(header);
			listview.setAdapter(adapter1);
			
			
		} else {
			Toast.makeText(getApplicationContext(), "This is no profile data",
					Toast.LENGTH_SHORT).show();
		}

		alertDialog1.setView(layout);
		alertDialog1.show();

	}

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
		sqlDBStore.close();
		super.onDestroy();
	}
}
