package com.aoneposapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.ParseException;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aoneposapp.adapters.ImageAdapterForCustomer;
import com.aoneposapp.adapters.ImageAdapterForSalesHistory;
import com.aoneposapp.adapters.SkuArrayAdapter;
import com.aoneposapp.utils.DatabaseForDemo;
import com.aoneposapp.utils.JsonPostMethod;
import com.aoneposapp.utils.Parameters;

@SuppressWarnings("deprecation")
public class CustomerActivity extends Activity implements
		android.view.View.OnClickListener, SkuArrayAdapter.OnWidgetItemClicked,
		ImageAdapterForCustomer.OnWidgetItemClicked {
	Button tab;
	SlidingDrawer slidingDrawer;
	ImageView slideButton;
	ImageView image0;
	ImageView image1;
	ImageView image2;
	ImageView image3;
	ImageView image4;
	ImageView image5;
	ImageView image6;
	ImageView image7;
	ImageView image8;
	ImageView logout;
	ImageView logout2;
	int height;
	int wwidth;
	String currentTimeforEdit,modifiedTimeforEdit;
	ListView listforhistory;
	Button b4, addcat, viewcat, generalinfo, extendedinfo, shippinginfo,
			historyinfo, notesinfo, storesinfo, savecat, cancelcat;
	LinearLayout ll_add, ll_view, ll;
	EditText custnum, firstname, lastname, email;
	String generalcompanyname = "", generalprimaryphone = "",
			generalalternatephone = "", generaladdress1 = "",
			generalstate = "", generalcity = "", generalcountry = "",
			generalzipcode = "", generaladdress2 = "", birthdayval = "";
	String shippingcompanyname = "", shippingphone = "",
			shippingfirstname = "", shippingaddress = "",
			shippinglastname = "", shippingstate = "", shippingcity = "",
			shippingcountry = "", shippingzipcode = "", shippingextended = "",
			cardtypepositionstring = "";
	String creditcardtype = "", cardnumberval = "", expirationval = "",
			drivinglicenseval = "", expdateval = "", mobileval = "",
			faxval = "", customernotes = "";
	int cardtypeposition = 0;
	String dataval, dataval1, dataval2, dataval3, dataval4, datavalforcustomer;
	ContentValues general_info, extended_info, shipping_info;
	ListView storelist, list;
	ArrayList<String> storesspinnerarray = new ArrayList<String>();
	ArrayList<String> storesspinnerarrayfordisplay = new ArrayList<String>();
	DatabaseForDemo sqlDBCustomer;
	SQLiteDatabase dbforloginlogoutWriteCustomer,dbforloginlogoutReadCustomer;
	int pagenum = 1;
	public static int pagecount = 10;
	public static int testforid = 1;
	int stLoop = 0;
	int endLoop = 0;
	int totalcount = 0;
	int j;

	ArrayList<String> customerno_data = new ArrayList<String>();
	ArrayList<String> firstname_data = new ArrayList<String>();
	ArrayList<String> lastname_data = new ArrayList<String>();
	ArrayList<String> address_data = new ArrayList<String>();
	ArrayList<String> company_data = new ArrayList<String>();
	ArrayList<String> phone_data = new ArrayList<String>();
	ArrayList<String> total_customerno_data = new ArrayList<String>();
	ArrayList<String> total_firstname_data = new ArrayList<String>();
	ArrayList<String> total_lastname_data = new ArrayList<String>();
	ArrayList<String> total_address_data = new ArrayList<String>();
	ArrayList<String> total_company_data = new ArrayList<String>();
	ArrayList<String> total_phone_data = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_activity);
		sqlDBCustomer=new DatabaseForDemo(CustomerActivity.this);
		dbforloginlogoutWriteCustomer = sqlDBCustomer.getWritableDatabase();
		dbforloginlogoutReadCustomer = sqlDBCustomer.getReadableDatabase();
		Parameters.ServerSyncTimer();
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
		image0.setOnClickListener(this);
		image1.setOnClickListener(this);
		image2.setOnClickListener(this);
		image3.setOnClickListener(this);
		image4.setOnClickListener(this);
		image5.setOnClickListener(this);
		image6.setOnClickListener(this);
		image7.setOnClickListener(this);
		image8.setOnClickListener(this);

		TextView loginnameempid = (TextView)findViewById(R.id.loginnameval);
		loginnameempid.setText(Parameters.usertypeloginvalue);
		image0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(CustomerActivity.this,
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
					Intent intent1 = new Intent(CustomerActivity.this,
							InventoryActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							CustomerActivity.this,
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
				Intent intent1 = new Intent(CustomerActivity.this,
						StoresActivity.class);
				startActivity(intent1);
				finish();
			} else {
				showAlertDialog(
						CustomerActivity.this,
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

			}
		});
		image4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Parameters.employee_permission){
				Intent intent1 = new Intent(CustomerActivity.this,
						EmployeeActivity.class);
				startActivity(intent1);
				finish();
			} else {
				showAlertDialog(
						CustomerActivity.this,
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
					Intent intent1 = new Intent(CustomerActivity.this,
							ReportsActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							CustomerActivity.this,
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
					Intent intent1 = new Intent(CustomerActivity.this,
							SettingsActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							CustomerActivity.this,
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
				Intent intent1 = new Intent(CustomerActivity.this,
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
					Intent intent1 = new Intent(CustomerActivity.this,
							ProfileActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							CustomerActivity.this,
							"Sorry",
							"You are not authenticated to perform this operation.",
							false);
				}
			}
		});

		savecat = (Button) findViewById(R.id.savecat);
		cancelcat = (Button) findViewById(R.id.cancelcat);
		addcat = (Button) findViewById(R.id.addcat);
		viewcat = (Button) findViewById(R.id.viewcat);
		ll_add = (LinearLayout) findViewById(R.id.add_ll);
		ll_view = (LinearLayout) findViewById(R.id.view_ll);

		generalinfo = (Button) findViewById(R.id.generalinfo);
		extendedinfo = (Button) findViewById(R.id.extendedinfo);
		shippinginfo = (Button) findViewById(R.id.shippinginfo);
		historyinfo = (Button) findViewById(R.id.historyinfo);
		notesinfo = (Button) findViewById(R.id.notesinfo);
		storesinfo = (Button) findViewById(R.id.storesinfo);

		custnum = (EditText) findViewById(R.id.cust_no);
		firstname = (EditText) findViewById(R.id.firstname);
		lastname = (EditText) findViewById(R.id.lastname);
		email = (EditText) findViewById(R.id.emailaddress);

		addcat.setBackgroundResource(R.drawable.highlightedtopmenuitem);
		viewcat.setBackgroundResource(R.drawable.toprightmenu);
		ll_add.setVisibility(View.VISIBLE);
		ll_view.setVisibility(View.GONE);
		addcat.setTextColor(Color.BLACK);
		viewcat.setTextColor(Color.WHITE);

		list = (ListView) findViewById(R.id.listView1);
		ll = (LinearLayout) findViewById(R.id.linearLayout1);
		Button heading = (Button) findViewById(R.id.button2);
		heading.setText("Customer Details");
		list.setItemsCanFocus(false);

		addcat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				generalcompanyname = "";
				generalprimaryphone = "";
				generalalternatephone = "";
				generaladdress1 = "";
				generalstate = "";
				generalcity = "";
				generalcountry = "";
				generalzipcode = "";
				generaladdress2 = "";
				birthdayval = "";
				shippingcompanyname = "";
				shippingphone = "";
				shippingfirstname = "";
				shippingaddress = "";
				shippinglastname = "";
				shippingstate = "";
				shippingcity = "";
				shippingcountry = "";
				shippingzipcode = "";
				shippingextended = "";
				cardtypepositionstring = "";
				creditcardtype = "";
				cardnumberval = "";
				expirationval = "";
				drivinglicenseval = "";
				expdateval = "";
				mobileval = "";
				faxval = "";
				customernotes = "";
				cardtypeposition = 0;
				storesspinnerarray.clear();
				storesspinnerarrayfordisplay.clear();
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
				listUpdateforcustomer();
			}
		});

		generalinfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String custno = custnum.getText().toString();
				if (custno.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please enter customer number.", Toast.LENGTH_SHORT)
							.show();
				} else {

					final Dialog dialog = new Dialog(CustomerActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.general_info_cust);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final EditText companyname = (EditText) dialog
							.findViewById(R.id.companyname);
					final EditText primaryphone = (EditText) dialog
							.findViewById(R.id.primaryphone);
					final EditText alternatephone = (EditText) dialog
							.findViewById(R.id.alternatephone);
					final EditText streetaddress = (EditText) dialog
							.findViewById(R.id.streetaddress);
					final EditText streetaddress2 = (EditText) dialog
							.findViewById(R.id.streetaddress2);
					final EditText birthday = (EditText) dialog
							.findViewById(R.id.birthday);
					final EditText city = (EditText) dialog
							.findViewById(R.id.city);
					final EditText state = (EditText) dialog
							.findViewById(R.id.state);
					final EditText country = (EditText) dialog
							.findViewById(R.id.country);
					final EditText zipcode = (EditText) dialog
							.findViewById(R.id.zipcode);

					companyname.setText(generalcompanyname);
					primaryphone.setText(generalprimaryphone);
					alternatephone.setText(generalalternatephone);
					streetaddress.setText(generaladdress1);
					streetaddress2.setText(generaladdress2);
					birthday.setText(birthdayval);
					city.setText(generalcity);
					state.setText(generalstate);
					country.setText(generalcountry);
					zipcode.setText(generalzipcode);

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							generalcompanyname =""+ companyname.getText()
									.toString();
							generalprimaryphone =""+ primaryphone.getText()
									.toString();
							generalalternatephone =""+ alternatephone.getText()
									.toString();
							generaladdress1 =""+ streetaddress.getText()
									.toString();
							generaladdress2 =""+ streetaddress2.getText()
									.toString();
							birthdayval =""+ birthday.getText().toString();
							generalcity =""+ city.getText().toString();
							generalstate = ""+state.getText().toString();
							generalcountry =""+ country.getText().toString();
							generalzipcode =""+ zipcode.getText().toString();

							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		extendedinfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String custno = custnum.getText().toString();
				if (custno.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please enter customer number.", Toast.LENGTH_SHORT)
							.show();
				} else {

					final Dialog dialog = new Dialog(CustomerActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.extended_info_cust);
					final Spinner cardtype = (Spinner) dialog
							.findViewById(R.id.cardtype);
					final EditText cardnumber = (EditText) dialog
							.findViewById(R.id.cardno);
					final EditText expiration = (EditText) dialog
							.findViewById(R.id.expiration);
					final EditText drivinglicense = (EditText) dialog
							.findViewById(R.id.driverslicense);
					final EditText expdate = (EditText) dialog
							.findViewById(R.id.expdate);
					final EditText mobile = (EditText) dialog
							.findViewById(R.id.mobile);
					final EditText fax = (EditText) dialog
							.findViewById(R.id.fax);
					final Button save = (Button) dialog
							.findViewById(R.id.savecat);
					final Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);

					cardtype.setSelection(cardtypeposition);
					cardnumber.setText(cardnumberval);
					expiration.setText(expirationval);
					drivinglicense.setText(drivinglicenseval);
					expdate.setText(expdateval);
					mobile.setText(mobileval);
					fax.setText(faxval);

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							cardtypepositionstring =""+ cardtype.getSelectedItem()
									.toString();
							cardtypeposition = cardtype
									.getSelectedItemPosition();
							cardnumberval =""+ cardnumber.getText().toString();
							expirationval =""+ expiration.getText().toString();
							drivinglicenseval =""+ drivinglicense.getText()
									.toString();
							expdateval =""+ expdate.getText().toString();
							mobileval =""+ mobile.getText().toString();
							faxval =""+ fax.getText().toString();

							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		shippinginfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String custno = custnum.getText().toString();
				if (custno.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please enter customer number.", Toast.LENGTH_SHORT)
							.show();
				} else {

					final Dialog dialog = new Dialog(CustomerActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.shipping_cust);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final EditText firstname = (EditText) dialog
							.findViewById(R.id.firstname);
					final EditText lastname = (EditText) dialog
							.findViewById(R.id.lastname);
					final EditText companyname = (EditText) dialog
							.findViewById(R.id.companyname);
					final EditText phoneno = (EditText) dialog
							.findViewById(R.id.phonenum);
					final EditText streetaddress = (EditText) dialog
							.findViewById(R.id.street);
					final EditText extendedaddress = (EditText) dialog
							.findViewById(R.id.extended);
					final EditText city = (EditText) dialog
							.findViewById(R.id.city);
					final EditText state = (EditText) dialog
							.findViewById(R.id.state);
					final EditText country = (EditText) dialog
							.findViewById(R.id.country);
					final EditText zipcode = (EditText) dialog
							.findViewById(R.id.zipcode);

					firstname.setText(shippingfirstname);
					lastname.setText(shippinglastname);
					companyname.setText(shippingcompanyname);
					phoneno.setText(shippingphone);
					streetaddress.setText(shippingaddress);
					extendedaddress.setText(shippingextended);
					city.setText(shippingcity);
					state.setText(shippingstate);
					country.setText(shippingcountry);
					zipcode.setText(shippingzipcode);

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							shippingfirstname =""+ firstname.getText().toString();
							shippinglastname =""+ lastname.getText().toString();
							shippingcompanyname =""+ companyname.getText()
									.toString();
							shippingphone =""+ phoneno.getText().toString();
							shippingaddress =""+ streetaddress.getText()
									.toString();
							shippingextended =""+ extendedaddress.getText()
									.toString();
							shippingcity =""+ city.getText().toString();
							shippingstate =""+ state.getText().toString();
							shippingcountry =""+ country.getText().toString();
							shippingzipcode =""+ zipcode.getText().toString();

							dialog.dismiss();

						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		historyinfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String custno = custnum.getText().toString();
				if (custno.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please enter customer number.", Toast.LENGTH_SHORT)
							.show();
				} else {

					final Dialog dialog = new Dialog(CustomerActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.history_cust);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					listforhistory = (ListView) dialog
							.findViewById(R.id.historylist);

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		notesinfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String custno = custnum.getText().toString();
				if (custno.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please enter customer number.", Toast.LENGTH_SHORT)
							.show();
				} else {

					final Dialog dialog = new Dialog(CustomerActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.notes_cust);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final EditText notes = (EditText) dialog
							.findViewById(R.id.notestext);

					notes.setText(customernotes);

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							customernotes = notes.getText().toString();
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		storesinfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String custno = custnum.getText().toString();
				if (custno.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please enter customer number.", Toast.LENGTH_SHORT)
							.show();
				} else {

					final Dialog dialog = new Dialog(CustomerActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.stores_cust);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final Spinner stores = (Spinner) dialog
							.findViewById(R.id.storesspinner);
					storelist = (ListView) dialog.findViewById(R.id.storeslist);

					storesspinnerarray.clear();
					storesspinnerarray.add("Select");
					
					
					String selectQuery = "SELECT  " + DatabaseForDemo.STORE_ID
							+ " FROM " + DatabaseForDemo.STORE_TABLE;
					Cursor mCursor = dbforloginlogoutReadCustomer.rawQuery(selectQuery, null);
					if (mCursor != null) {
						if (mCursor.moveToFirst()) {
							do {
								String storeid = mCursor.getString(mCursor
										.getColumnIndex(DatabaseForDemo.STORE_ID));
								storesspinnerarray.add(storeid);
							} while (mCursor.moveToNext());
						}
					}
					mCursor.close();
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							CustomerActivity.this,
							android.R.layout.simple_spinner_item,
							storesspinnerarray);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
					stores.setAdapter(adapter);

					if (storesspinnerarrayfordisplay != null) {
						SkuArrayAdapter adapterformod = new SkuArrayAdapter(
								CustomerActivity.this,
								storesspinnerarrayfordisplay);
						adapterformod.setListener(CustomerActivity.this);
						storelist.setAdapter(adapterformod);
					}

					stores.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub
							String storeinspinner = stores.getSelectedItem()
									.toString();
							if (storeinspinner.equals("")
									|| storeinspinner.equals("Select")) {

							} else {
								if (storesspinnerarrayfordisplay
										.contains(storeinspinner)) {
									Toast.makeText(getApplicationContext(),
											"Store already exist",
											Toast.LENGTH_SHORT).show();
								} else {
									storesspinnerarrayfordisplay
											.add(storeinspinner);
									if (storesspinnerarrayfordisplay != null) {
										SkuArrayAdapter adapterformod = new SkuArrayAdapter(
												CustomerActivity.this,
												storesspinnerarrayfordisplay);
										adapterformod
												.setListener(CustomerActivity.this);
										storelist.setAdapter(adapterformod);
									}

								}
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}
					});

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		cancelcat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				custnum.setText("");
				firstname.setText("");
				lastname.setText("");
				email.setText("");
				generalcompanyname = "";
				generalprimaryphone = "";
				generalalternatephone = "";
				generaladdress1 = "";
				generalstate = "";
				generalcity = "";
				generalcountry = "";
				generalzipcode = "";
				generaladdress2 = "";
				birthdayval = "";
				shippingcompanyname = "";
				shippingphone = "";
				shippingfirstname = "";
				shippingaddress = "";
				shippinglastname = "";
				shippingstate = "";
				shippingcity = "";
				shippingcountry = "";
				shippingzipcode = "";
				shippingextended = "";
				cardtypepositionstring = "";
				creditcardtype = "";
				cardnumberval = "";
				expirationval = "";
				drivinglicenseval = "";
				expdateval = "";
				mobileval = "";
				faxval = "";
				customernotes = "";
				cardtypeposition = 0;
				storesspinnerarray.clear();
				storesspinnerarrayfordisplay.clear();
			}
		});

		savecat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String custnumval = custnum.getText().toString();
				String firstnameval = firstname.getText().toString();
				String lastnameval = lastname.getText().toString();
				String emailval = email.getText().toString();
				String uniqueid = Parameters.randomValue();
				currentTimeforEdit=Parameters.currentTime();
				modifiedTimeforEdit=Parameters.currentTime();
				if (custnumval.equals("") || firstnameval.equals("")
						|| lastnameval.equals("")) {
					Toast.makeText(CustomerActivity.this,
							"Please enter details", Toast.LENGTH_SHORT)
							.show();
				} else {

					// if(Parameters.usertype.equals("demo")){

					
					String selectQuery = "SELECT  * FROM "
							+ DatabaseForDemo.CUSTOMER_TABLE + " where "
							+ DatabaseForDemo.CUSTOMER_NO + "=\"" + custnumval
							+ "\"";

					Cursor mCursor = dbforloginlogoutReadCustomer.rawQuery(selectQuery, null);
					if (mCursor.getCount() > 0) {
						Toast.makeText(CustomerActivity.this,
								"Customer already exist", Toast.LENGTH_SHORT)
								.show();
						
					} else {
						
						ContentValues contentValues = new ContentValues();
						contentValues.put(DatabaseForDemo.UNIQUE_ID, uniqueid);
						contentValues.put(DatabaseForDemo.CREATED_DATE,
								currentTimeforEdit);
						contentValues.put(DatabaseForDemo.MODIFIED_DATE,
								modifiedTimeforEdit);
						contentValues.put(DatabaseForDemo.MODIFIED_IN, "Local");
						contentValues.put(DatabaseForDemo.CUSTOMER_NO,
								custnumval);
						contentValues.put(DatabaseForDemo.CUSTOMER_LAST_NAME,
								lastnameval);
						contentValues.put(DatabaseForDemo.CUSTOMER_EMAIL,
								emailval);
						contentValues.put(DatabaseForDemo.CUSTOMER_NOTES,
								customernotes);
						contentValues.put(DatabaseForDemo.CUSTOMER_FIRST_NAME,
								firstnameval);
						dbforloginlogoutWriteCustomer.insert(DatabaseForDemo.CUSTOMER_TABLE, null,
								contentValues);

						general_info = new ContentValues();
						general_info.put(DatabaseForDemo.UNIQUE_ID, uniqueid);
						general_info.put(DatabaseForDemo.CREATED_DATE,
								currentTimeforEdit);
						general_info.put(DatabaseForDemo.MODIFIED_DATE,
								modifiedTimeforEdit);
						general_info.put(DatabaseForDemo.MODIFIED_IN, "Local");
						general_info.put(DatabaseForDemo.CUSTOMER_COMPANY_NAME,
								generalcompanyname);
						general_info.put(
								DatabaseForDemo.CUSTOMER_PRIMARY_PHONE,
								generalprimaryphone);
						general_info.put(
								DatabaseForDemo.CUSTOMER_ALTERNATE_PHONE,
								generalalternatephone);
						general_info.put(DatabaseForDemo.CUSTOMER_STREET1,
								generaladdress1);
						general_info.put(DatabaseForDemo.CUSTOMER_STREET2,
								generaladdress2);
						general_info.put(DatabaseForDemo.CUSTOMER_STATE,
								generalstate);
						general_info.put(DatabaseForDemo.CUSTOMER_CITY,
								generalcity);
						general_info.put(DatabaseForDemo.CUSTOMER_COUNTRY,
								generalcountry);
						general_info.put(DatabaseForDemo.CUSTOMER_ZIPCODE,
								generalzipcode);
						general_info.put(DatabaseForDemo.CUSTOMER_BIRTHDAY,
								birthdayval);
						general_info.put(DatabaseForDemo.CUSTOMER_NO, custnum
								.getText().toString());
						dbforloginlogoutWriteCustomer.insert(DatabaseForDemo.CUSTOMER_GENERAL_INFO_TABLE,
								null, general_info);

						extended_info = new ContentValues();
						extended_info.put(DatabaseForDemo.UNIQUE_ID, uniqueid);
						extended_info.put(DatabaseForDemo.CREATED_DATE,
								currentTimeforEdit);
						extended_info.put(DatabaseForDemo.MODIFIED_DATE,
								modifiedTimeforEdit);
						extended_info.put(DatabaseForDemo.MODIFIED_IN, "Local");
						extended_info.put(DatabaseForDemo.CREDIT_CARD_TYPE,
								cardtypepositionstring);
						extended_info.put(DatabaseForDemo.CREDIT_CARD_NUM,
								cardnumberval);
						extended_info.put(DatabaseForDemo.EXPIRATION,
								expirationval);
						extended_info.put(DatabaseForDemo.DRIVING_LICENSE,
								drivinglicenseval);
						extended_info.put(DatabaseForDemo.EXP_DATE, expdateval);
						extended_info.put(DatabaseForDemo.CUSTOMER_MOBILE,
								mobileval);
						extended_info.put(DatabaseForDemo.CUSTOMER_FAX, faxval);
						extended_info.put(DatabaseForDemo.CUSTOMER_NO, custnum
								.getText().toString());
						dbforloginlogoutWriteCustomer.insert(DatabaseForDemo.CUSTOMER_EXTENDED_INFO_TABLE,
								null, extended_info);

						shipping_info = new ContentValues();
						shipping_info.put(DatabaseForDemo.UNIQUE_ID, uniqueid);
						shipping_info.put(DatabaseForDemo.CREATED_DATE,
								currentTimeforEdit);
						shipping_info.put(DatabaseForDemo.MODIFIED_DATE,
								modifiedTimeforEdit);
						shipping_info.put(DatabaseForDemo.MODIFIED_IN, "Local");
						shipping_info.put(DatabaseForDemo.SHIPPING_FIRST_NAME,
								shippingfirstname);
						shipping_info.put(DatabaseForDemo.SHIPPING_LAST_NAME,
								shippinglastname);
						shipping_info.put(
								DatabaseForDemo.SHIPPING_COMPANY_NAME,
								shippingcompanyname);
						shipping_info.put(DatabaseForDemo.SHIPPING_PHONE,
								shippingphone);
						shipping_info.put(DatabaseForDemo.SHIPPING_STREET,
								shippingaddress);
						shipping_info.put(DatabaseForDemo.SHIPPING_EXTENDED,
								shippingextended);
						shipping_info.put(DatabaseForDemo.SHIPPING_CITY,
								shippingcity);
						shipping_info.put(DatabaseForDemo.SHIPPING_STATE,
								shippingstate);
						shipping_info.put(DatabaseForDemo.SHIPPING_COUNTRY,
								shippingcountry);
						shipping_info.put(DatabaseForDemo.SHIPPING_ZIPCODE,
								shippingzipcode);
						shipping_info.put(DatabaseForDemo.CUSTOMER_NO, custnum
								.getText().toString());
						dbforloginlogoutWriteCustomer.insert(DatabaseForDemo.CUSTOMER_SHIPPING_TABLE,
								null, shipping_info);

						for (int i = 0; i < storesspinnerarrayfordisplay.size(); i++) {
							ContentValues stores = new ContentValues();
							stores.put(DatabaseForDemo.UNIQUE_ID, uniqueid);
							stores.put(DatabaseForDemo.CREATED_DATE,
									currentTimeforEdit);
							stores.put(DatabaseForDemo.MODIFIED_DATE,
									modifiedTimeforEdit);
							stores.put(DatabaseForDemo.MODIFIED_IN, "Local");
							stores.put(DatabaseForDemo.CUSTOMER_NO, custnumval);
							stores.put(DatabaseForDemo.STORE_ID_CUSTOMER,
									storesspinnerarrayfordisplay.get(i));
							dbforloginlogoutWriteCustomer.insert(DatabaseForDemo.CUSTOMER_STORES_TABLE,
									null, stores);
						}

						Toast.makeText(CustomerActivity.this,
								"Customer inserted successfully",
								Toast.LENGTH_SHORT).show();

						try {
							JSONObject data = new JSONObject();
							JSONObject jsonobj = new JSONObject();
							jsonobj.put(DatabaseForDemo.UNIQUE_ID, uniqueid);
							jsonobj.put(DatabaseForDemo.CUSTOMER_NO, custnumval);
							jsonobj.put(DatabaseForDemo.CREATED_DATE,
									currentTimeforEdit);
							jsonobj.put(DatabaseForDemo.MODIFIED_DATE,
									modifiedTimeforEdit);
							jsonobj.put(DatabaseForDemo.CUSTOMER_LAST_NAME,
									lastnameval);
							jsonobj.put(DatabaseForDemo.CUSTOMER_EMAIL,
									emailval);
							jsonobj.put(DatabaseForDemo.CUSTOMER_NOTES,
									customernotes);
							jsonobj.put(DatabaseForDemo.CUSTOMER_FIRST_NAME,
									firstnameval);
							JSONArray fields = new JSONArray();
							fields.put(0, jsonobj);
							data.put("fields", fields);
							dataval = data.toString();
							System.out.println("data val is:" + dataval);
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {
							JSONObject data = new JSONObject();
							JSONObject jsonobj = new JSONObject();
							jsonobj.put(DatabaseForDemo.UNIQUE_ID, uniqueid);
							jsonobj.put(DatabaseForDemo.CUSTOMER_COMPANY_NAME,
									generalcompanyname);
							jsonobj.put(DatabaseForDemo.CUSTOMER_PRIMARY_PHONE,
									generalprimaryphone);
							jsonobj.put(DatabaseForDemo.CREATED_DATE,
									currentTimeforEdit);
							jsonobj.put(DatabaseForDemo.MODIFIED_DATE,
									modifiedTimeforEdit);
							jsonobj.put(
									DatabaseForDemo.CUSTOMER_ALTERNATE_PHONE,
									generalalternatephone);
							jsonobj.put(DatabaseForDemo.CUSTOMER_STREET1,
									generaladdress1);
							jsonobj.put(DatabaseForDemo.CUSTOMER_STREET2,
									generaladdress2);
							jsonobj.put(DatabaseForDemo.CUSTOMER_STATE,
									generalstate);
							jsonobj.put(DatabaseForDemo.CUSTOMER_CITY,
									generalcity);
							jsonobj.put(DatabaseForDemo.CUSTOMER_COUNTRY,
									generalcountry);
							jsonobj.put(DatabaseForDemo.CUSTOMER_ZIPCODE,
									generalzipcode);
							jsonobj.put(DatabaseForDemo.CUSTOMER_BIRTHDAY,
									birthdayval);
							jsonobj.put(DatabaseForDemo.CUSTOMER_NO, custnumval);
							JSONArray fields = new JSONArray();
							fields.put(0, jsonobj);
							data.put("fields", fields);
							dataval1 = data.toString();
							System.out.println("data val is:" + dataval1);
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {
							JSONObject data = new JSONObject();
							JSONObject jsonobj = new JSONObject();
							jsonobj.put(DatabaseForDemo.UNIQUE_ID, uniqueid);
							jsonobj.put(DatabaseForDemo.CREDIT_CARD_TYPE,
									cardtypepositionstring);
							jsonobj.put(DatabaseForDemo.CREDIT_CARD_NUM,
									cardnumberval);
							jsonobj.put(DatabaseForDemo.CREATED_DATE,
									currentTimeforEdit);
							jsonobj.put(DatabaseForDemo.MODIFIED_DATE,
									modifiedTimeforEdit);
							jsonobj.put(DatabaseForDemo.EXPIRATION,
									expirationval);
							jsonobj.put(DatabaseForDemo.DRIVING_LICENSE,
									drivinglicenseval);
							jsonobj.put(DatabaseForDemo.EXP_DATE, expdateval);
							jsonobj.put(DatabaseForDemo.CUSTOMER_MOBILE,
									mobileval);
							jsonobj.put(DatabaseForDemo.CUSTOMER_FAX, faxval);
							jsonobj.put(DatabaseForDemo.CUSTOMER_NO, custnumval);
							JSONArray fields = new JSONArray();
							fields.put(0, jsonobj);
							data.put("fields", fields);
							dataval2 = data.toString();
							System.out.println("data val is:" + dataval2);
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {
							JSONObject data = new JSONObject();
							JSONObject jsonobj = new JSONObject();
							jsonobj.put(DatabaseForDemo.UNIQUE_ID, uniqueid);
							jsonobj.put(DatabaseForDemo.SHIPPING_FIRST_NAME,
									shippingfirstname);
							jsonobj.put(DatabaseForDemo.SHIPPING_LAST_NAME,
									shippinglastname);
							jsonobj.put(DatabaseForDemo.SHIPPING_COMPANY_NAME,
									shippingcompanyname);
							jsonobj.put(DatabaseForDemo.CREATED_DATE,
									currentTimeforEdit);
							jsonobj.put(DatabaseForDemo.MODIFIED_DATE,
									modifiedTimeforEdit);
							jsonobj.put(DatabaseForDemo.SHIPPING_PHONE,
									shippingphone);
							jsonobj.put(DatabaseForDemo.SHIPPING_STREET,
									shippingaddress);
							jsonobj.put(DatabaseForDemo.SHIPPING_EXTENDED,
									shippingextended);
							jsonobj.put(DatabaseForDemo.SHIPPING_CITY,
									shippingcity);
							jsonobj.put(DatabaseForDemo.SHIPPING_STATE,
									shippingstate);
							jsonobj.put(DatabaseForDemo.SHIPPING_COUNTRY,
									shippingcountry);
							jsonobj.put(DatabaseForDemo.SHIPPING_ZIPCODE,
									shippingzipcode);
							jsonobj.put(DatabaseForDemo.CUSTOMER_NO, custnumval);
							JSONArray fields = new JSONArray();
							fields.put(0, jsonobj);
							data.put("fields", fields);
							dataval3 = data.toString();
							System.out.println("data val is:" + dataval3);
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {
							JSONObject data = new JSONObject();
							JSONObject jsonobj = new JSONObject();
							JSONArray fields = new JSONArray();

							for (int i = 0; i < storesspinnerarrayfordisplay
									.size(); i++) {
								fields.put(i, jsonobj);
								jsonobj.put(DatabaseForDemo.CUSTOMER_NO,
										custnumval);
								jsonobj.put(DatabaseForDemo.CREATED_DATE,
										currentTimeforEdit);
								jsonobj.put(DatabaseForDemo.MODIFIED_DATE,
										modifiedTimeforEdit);
								jsonobj.put(DatabaseForDemo.STORE_ID_CUSTOMER,
										storesspinnerarrayfordisplay.get(i));
								jsonobj.put(DatabaseForDemo.UNIQUE_ID, uniqueid);
							}
							data.put("fields", fields);
							dataval4 = data.toString();
							System.out.println("data val is:" + dataval4);
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						if (Parameters.OriginalUrl.equals("")) {
							System.out.println("there is no url val");
						} else {
							boolean isnet = Parameters.isNetworkAvailable(CustomerActivity.this);
							if (isnet) {

								new Thread(new Runnable() {
									@Override
									public void run() {

										JsonPostMethod jsonpost = new JsonPostMethod();
										String response = jsonpost
												.postmethodfordirect(
														"admin",
														"abcdefg",
														DatabaseForDemo.CUSTOMER_TABLE,
														Parameters
																.currentTime(),
														Parameters
																.currentTime(),
														dataval, "");
										System.out.println("response test is:"
												+ response);
										try {
											JSONObject obj = new JSONObject(
													response);
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
											for (int jj = 0, ii = 0; jj < array2
													.length()
													&& ii < array.length(); jj++, ii++) {
												String deletequerytemp = array2
														.getString(jj);
												String deletequery1 = deletequerytemp
														.replace("'", "\"");
												String deletequery = deletequery1
														.replace("\\\"", "'");
												System.out
														.println("delete query"
																+ jj + " is :"
																+ deletequery);

												String insertquerytemp = array
														.getString(ii);
												String insertquery1 = insertquerytemp
														.replace("'", "\"");
												String insertquery = insertquery1
														.replace("\\\"", "'");
												System.out
														.println("delete query"
																+ jj + " is :"
																+ insertquery);

												dbforloginlogoutWriteCustomer.execSQL(deletequery);
												dbforloginlogoutWriteCustomer.execSQL(insertquery);
												System.out
														.println("queries executed"
																+ ii);
											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										String responseproduct1 = jsonpost
												.postmethodfordirect(
														"admin",
														"abcdefg",
														DatabaseForDemo.CUSTOMER_GENERAL_INFO_TABLE,
														Parameters
																.currentTime(),
														Parameters
																.currentTime(),
														dataval1, "");
										System.out.println("response test is:"
												+ responseproduct1);
										try {
											JSONObject obj = new JSONObject(
													responseproduct1);
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
											for (int jj = 0, ii = 0; jj < array2
													.length()
													&& ii < array.length(); jj++, ii++) {
												String deletequerytemp = array2
														.getString(jj);
												String deletequery1 = deletequerytemp
														.replace("'", "\"");
												String deletequery = deletequery1
														.replace("\\\"", "'");
												System.out
														.println("delete query"
																+ jj + " is :"
																+ deletequery);

												String insertquerytemp = array
														.getString(ii);
												String insertquery1 = insertquerytemp
														.replace("'", "\"");
												String insertquery = insertquery1
														.replace("\\\"", "'");
												System.out
														.println("delete query"
																+ jj + " is :"
																+ insertquery);

												dbforloginlogoutWriteCustomer.execSQL(deletequery);
												dbforloginlogoutWriteCustomer.execSQL(insertquery);
												System.out
														.println("queries executed"
																+ ii);
											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										String responseproduct2 = jsonpost
												.postmethodfordirect(
														"admin",
														"abcdefg",
														DatabaseForDemo.CUSTOMER_EXTENDED_INFO_TABLE,
														Parameters
																.currentTime(),
														Parameters
																.currentTime(),
														dataval2, "");
										System.out.println("response test is:"
												+ responseproduct2);
										try {
											JSONObject obj = new JSONObject(
													responseproduct2);
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
											for (int jj = 0, ii = 0; jj < array2
													.length()
													&& ii < array.length(); jj++, ii++) {
												String deletequerytemp = array2
														.getString(jj);
												String deletequery1 = deletequerytemp
														.replace("'", "\"");
												String deletequery = deletequery1
														.replace("\\\"", "'");
												System.out
														.println("delete query"
																+ jj + " is :"
																+ deletequery);

												String insertquerytemp = array
														.getString(ii);
												String insertquery1 = insertquerytemp
														.replace("'", "\"");
												String insertquery = insertquery1
														.replace("\\\"", "'");
												System.out
														.println("delete query"
																+ jj + " is :"
																+ insertquery);

												dbforloginlogoutWriteCustomer.execSQL(deletequery);
												dbforloginlogoutWriteCustomer.execSQL(insertquery);
												System.out
														.println("queries executed"
																+ ii);
											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										String responseproduct3 = jsonpost
												.postmethodfordirect(
														"admin",
														"abcdefg",
														DatabaseForDemo.CUSTOMER_SHIPPING_TABLE,
														Parameters
																.currentTime(),
														Parameters
																.currentTime(),
														dataval3, "");
										System.out.println("response test is:"
												+ responseproduct3);
										try {
											JSONObject obj = new JSONObject(
													responseproduct3);
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
											for (int jj = 0, ii = 0; jj < array2
													.length()
													&& ii < array.length(); jj++, ii++) {
												String deletequerytemp = array2
														.getString(jj);
												String deletequery1 = deletequerytemp
														.replace("'", "\"");
												String deletequery = deletequery1
														.replace("\\\"", "'");
												System.out
														.println("delete query"
																+ jj + " is :"
																+ deletequery);

												String insertquerytemp = array
														.getString(ii);
												String insertquery1 = insertquerytemp
														.replace("'", "\"");
												String insertquery = insertquery1
														.replace("\\\"", "'");
												System.out
														.println("delete query"
																+ jj + " is :"
																+ insertquery);

												dbforloginlogoutWriteCustomer.execSQL(deletequery);
												dbforloginlogoutWriteCustomer.execSQL(insertquery);
												System.out
														.println("queries executed"
																+ ii);
											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										String responseproduct4 = jsonpost
												.postmethodfordirect(
														"admin",
														"abcdefg",
														DatabaseForDemo.CUSTOMER_STORES_TABLE,
														Parameters
																.currentTime(),
														Parameters
																.currentTime(),
														dataval4, "");
										System.out.println("response test is:"
												+ responseproduct4);
										String servertiem = null;
										try {
											JSONObject obj = new JSONObject(
													responseproduct4);
											JSONObject responseobj = obj
													.getJSONObject("response");
											servertiem = responseobj
													.getString("server-time");
											System.out.println("servertime is:"
													+ servertiem);
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
											for (int jj = 0, ii = 0; jj < array2
													.length()
													&& ii < array.length(); jj++, ii++) {
												String deletequerytemp = array2
														.getString(jj);
												String deletequery1 = deletequerytemp
														.replace("'", "\"");
												String deletequery = deletequery1
														.replace("\\\"", "'");
												System.out
														.println("delete query"
																+ jj + " is :"
																+ deletequery);

												String insertquerytemp = array
														.getString(ii);
												String insertquery1 = insertquerytemp
														.replace("'", "\"");
												String insertquery = insertquery1
														.replace("\\\"", "'");
												System.out
														.println("delete query"
																+ jj + " is :"
																+ insertquery);

												dbforloginlogoutWriteCustomer.execSQL(deletequery);
												dbforloginlogoutWriteCustomer.execSQL(insertquery);
												System.out
														.println("queries executed"
																+ ii);
											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										String select = "select *from "
												+ DatabaseForDemo.MISCELLANEOUS_TABLE;
										Cursor cursor = dbforloginlogoutReadCustomer.rawQuery(select,
												null);
										if (cursor.getCount() > 0) {
											dbforloginlogoutWriteCustomer.execSQL("update "
													+ DatabaseForDemo.MISCELLANEOUS_TABLE
													+ " set "
													+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
													+ "=\"" + servertiem + "\"");
											cursor.close();
										} else {
											cursor.close();
											ContentValues contentValues1 = new ContentValues();
											contentValues1
													.put(DatabaseForDemo.MISCEL_STORE,
															"store1");
											contentValues1
													.put(DatabaseForDemo.MISCEL_PAGEURL,
															"");
											contentValues1
													.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL,
															Parameters
																	.currentTime());
											contentValues1
													.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL,
															Parameters
																	.currentTime());
											dbforloginlogoutWriteCustomer.insert(
													DatabaseForDemo.MISCELLANEOUS_TABLE,
													null, contentValues1);
											
										}
										dataval = "";
										dataval1 = "";
										dataval2 = "";
										dataval3 = "";
										dataval4 = "";

									}
								}).start();

							} else {

								ContentValues contentValues1 = new ContentValues();
								contentValues1.put(DatabaseForDemo.QUERY_TYPE,
										"insert");
								contentValues1.put(
										DatabaseForDemo.PENDING_USER_ID,
										Parameters.userid);
								contentValues1.put(DatabaseForDemo.PAGE_URL,
										"saveinfo.php");
								contentValues1.put(
										DatabaseForDemo.TABLE_NAME_PENDING,
										DatabaseForDemo.CUSTOMER_TABLE);
								contentValues1.put(
										DatabaseForDemo.CURRENT_TIME_PENDING,
										Parameters.currentTime());
								contentValues1.put(DatabaseForDemo.PARAMETERS,
										dataval);
								dbforloginlogoutWriteCustomer.insert(
										DatabaseForDemo.PENDING_QUERIES_TABLE,
										null, contentValues1);

								ContentValues contentValues2 = new ContentValues();
								contentValues2.put(DatabaseForDemo.QUERY_TYPE,
										"insert");
								contentValues2.put(
										DatabaseForDemo.PENDING_USER_ID,
										Parameters.userid);
								contentValues2.put(DatabaseForDemo.PAGE_URL,
										"saveinfo.php");
								contentValues2
										.put(DatabaseForDemo.TABLE_NAME_PENDING,
												DatabaseForDemo.CUSTOMER_GENERAL_INFO_TABLE);
								contentValues2.put(
										DatabaseForDemo.CURRENT_TIME_PENDING,
										Parameters.currentTime());
								contentValues2.put(DatabaseForDemo.PARAMETERS,
										dataval1);
								dbforloginlogoutWriteCustomer.insert(
										DatabaseForDemo.PENDING_QUERIES_TABLE,
										null, contentValues2);

								ContentValues contentValues3 = new ContentValues();
								contentValues3.put(DatabaseForDemo.QUERY_TYPE,
										"insert");
								contentValues3.put(
										DatabaseForDemo.PENDING_USER_ID,
										Parameters.userid);
								contentValues3.put(DatabaseForDemo.PAGE_URL,
										"saveinfo.php");
								contentValues3
										.put(DatabaseForDemo.TABLE_NAME_PENDING,
												DatabaseForDemo.CUSTOMER_EXTENDED_INFO_TABLE);
								contentValues3.put(
										DatabaseForDemo.CURRENT_TIME_PENDING,
										Parameters.currentTime());
								contentValues3.put(DatabaseForDemo.PARAMETERS,
										dataval2);
								dbforloginlogoutWriteCustomer.insert(
										DatabaseForDemo.PENDING_QUERIES_TABLE,
										null, contentValues3);

								ContentValues contentValues4 = new ContentValues();
								contentValues4.put(DatabaseForDemo.QUERY_TYPE,
										"insert");
								contentValues4.put(
										DatabaseForDemo.PENDING_USER_ID,
										Parameters.userid);
								contentValues4.put(DatabaseForDemo.PAGE_URL,
										"saveinfo.php");
								contentValues4
										.put(DatabaseForDemo.TABLE_NAME_PENDING,
												DatabaseForDemo.CUSTOMER_SHIPPING_TABLE);
								contentValues4.put(
										DatabaseForDemo.CURRENT_TIME_PENDING,
										Parameters.currentTime());
								contentValues4.put(DatabaseForDemo.PARAMETERS,
										dataval3);
								dbforloginlogoutWriteCustomer.insert(
										DatabaseForDemo.PENDING_QUERIES_TABLE,
										null, contentValues4);

								ContentValues contentValues5 = new ContentValues();
								contentValues5.put(DatabaseForDemo.QUERY_TYPE,
										"insert");
								contentValues5.put(
										DatabaseForDemo.PENDING_USER_ID,
										Parameters.userid);
								contentValues5.put(DatabaseForDemo.PAGE_URL,
										"saveinfo.php");
								contentValues5.put(
										DatabaseForDemo.TABLE_NAME_PENDING,
										DatabaseForDemo.CUSTOMER_STORES_TABLE);
								contentValues5.put(
										DatabaseForDemo.CURRENT_TIME_PENDING,
										Parameters.currentTime());
								contentValues5.put(DatabaseForDemo.PARAMETERS,
										dataval4);
								dbforloginlogoutWriteCustomer.insert(
										DatabaseForDemo.PENDING_QUERIES_TABLE,
										null, contentValues5);
								dataval = "";
								dataval1 = "";
								dataval2 = "";
								dataval3 = "";
								dataval4 = "";
							}
						}

						custnum.setText("");
						firstname.setText("");
						lastname.setText("");
						email.setText("");
						generalcompanyname = "";
						generalprimaryphone = "";
						generalalternatephone = "";
						generaladdress1 = "";
						generalstate = "";
						generalcity = "";
						generalcountry = "";
						generalzipcode = "";
						generaladdress2 = "";
						birthdayval = "";
						shippingcompanyname = "";
						shippingphone = "";
						shippingfirstname = "";
						shippingaddress = "";
						shippinglastname = "";
						shippingstate = "";
						shippingcity = "";
						shippingcountry = "";
						shippingzipcode = "";
						shippingextended = "";
						cardtypepositionstring = "";
						creditcardtype = "";
						cardnumberval = "";
						expirationval = "";
						drivinglicenseval = "";
						expdateval = "";
						mobileval = "";
						faxval = "";
						customernotes = "";
						cardtypeposition = 0;
						storesspinnerarray.clear();
						storesspinnerarrayfordisplay.clear();
						general_info.clear();
						extended_info.clear();
						shipping_info.clear();

					}
					mCursor.close();
					/*
					 * }else{ Toast.makeText(CustomerActivity.this,
					 * "This is not demo login", Toast.LENGTH_SHORT).show(); }
					 */
				}
			}
		});

		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Parameters.methodForLogout(CustomerActivity.this);
				finish();
			}
		});
		logout2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Parameters.methodForLogout(CustomerActivity.this);
				finish();
			}
		});
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		height = displaymetrics.heightPixels;
		wwidth = displaymetrics.widthPixels;
		slideButton.setPadding(0, 0, 0, height - 40);
		slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				slideButton.setImageResource(R.drawable.arrowleft);
			}
		});

		slidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {

			@Override
			public void onDrawerClosed() {
				slideButton.setImageResource(R.drawable.arrow);
			}
		});
	}

	public void countforcustomer(int j) {
		testforid = 1;
		stLoop = (j - 1) * pagecount;
		endLoop = j * pagecount;
		if (endLoop >= totalcount) {
			endLoop = totalcount;
		}
		Log.v("lop", stLoop + "" + endLoop);
		customerno_data = getDataforcustomer(stLoop, endLoop,
				total_customerno_data);
		firstname_data = getDataforcustomer(stLoop, endLoop,
				total_firstname_data);
		lastname_data = getDataforcustomer(stLoop, endLoop, total_lastname_data);
		address_data = getDataforcustomer(stLoop, endLoop, total_address_data);
		company_data = getDataforcustomer(stLoop, endLoop, total_company_data);
		phone_data = getDataforcustomer(stLoop, endLoop, total_phone_data);
		Log.v("lop", "" + phone_data);
		System.out.println("vlaues_first size:" + phone_data.size());
		System.out.println("vlaues_first size:" + customerno_data.size());

		ImageAdapterForCustomer adapter = new ImageAdapterForCustomer(this,
				customerno_data, firstname_data, lastname_data, address_data,
				company_data, phone_data);
		adapter.setListener(this);
		list.setAdapter(adapter);
	}

	public ArrayList<String> getDataforcustomer(int offset, int limit,
			ArrayList<String> list) {
		ArrayList<String> newList = new ArrayList<String>(limit);
		int end = limit;

		if (end > list.size()) {
			end = list.size();
		}
		newList.addAll(list.subList(offset, end));
		return newList;
	}

	public void listUpdateforcustomer() {

		customerno_data.clear();
		firstname_data.clear();
		lastname_data.clear();
		address_data.clear();
		company_data.clear();
		phone_data.clear();
		total_customerno_data.clear();
		total_firstname_data.clear();
		total_lastname_data.clear();
		total_address_data.clear();
		total_company_data.clear();
		total_phone_data.clear();
		ll.removeAllViews();

		String selectQuery = "SELECT  * FROM " + DatabaseForDemo.CUSTOMER_TABLE;

		Cursor mCursor = dbforloginlogoutReadCustomer.rawQuery(selectQuery, null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String custno = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.CUSTOMER_NO));
					total_customerno_data.add(custno);
					String firstname = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.CUSTOMER_FIRST_NAME));
					total_firstname_data.add(firstname);
					String lastname = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.CUSTOMER_LAST_NAME));
					total_lastname_data.add(lastname);
				} while (mCursor.moveToNext());
			}
		}
		mCursor.close();
		for (int ii = 0; ii < total_customerno_data.size(); ii++) {
			String selectQueryforshipping = "SELECT  * FROM "
					+ DatabaseForDemo.CUSTOMER_GENERAL_INFO_TABLE + " where "
					+ DatabaseForDemo.CUSTOMER_NO + "=\""
					+ total_customerno_data.get(ii) + "\"";

			Cursor mCursorforshipping = dbforloginlogoutReadCustomer.rawQuery(selectQueryforshipping,
					null);

			if (mCursorforshipping.getCount() > 0) {
				if (mCursorforshipping != null) {
					if (mCursorforshipping.moveToFirst()) {
						do {
							String address = mCursorforshipping
									.getString(mCursorforshipping
											.getColumnIndex(DatabaseForDemo.CUSTOMER_STREET1));
							String address1 = mCursorforshipping
									.getString(mCursorforshipping
											.getColumnIndex(DatabaseForDemo.CUSTOMER_STREET2));
							String address2 = mCursorforshipping
									.getString(mCursorforshipping
											.getColumnIndex(DatabaseForDemo.CUSTOMER_CITY));
							total_address_data.add(address+" "+address1+" "+address2);
							String company = mCursorforshipping
									.getString(mCursorforshipping
											.getColumnIndex(DatabaseForDemo.CUSTOMER_COMPANY_NAME));
							total_company_data.add(company);
							String phone = mCursorforshipping
									.getString(mCursorforshipping
											.getColumnIndex(DatabaseForDemo.CUSTOMER_PRIMARY_PHONE));
							total_phone_data.add(phone);
						} while (mCursorforshipping.moveToNext());
					}
				}
			} else {
				total_address_data.add("");
				total_company_data.add("");
				total_phone_data.add("");
			}
			mCursorforshipping.close();
		}
		
		totalcount = total_customerno_data.size();
		System.out.println("total count value is:" + totalcount);

		int to = totalcount / pagecount;
		int too = totalcount % pagecount;
		int i = to;
		if (too != 0) {
			i = to + 1;
		}
		countforcustomer(1);
		ll.setWeightSum(i);

		for (j = 1; j <= i; j++) {
			final Button btn = new Button(this);
			btn.setText("" + j);
			btn.setId(j);
			btn.setBackgroundResource(R.drawable.pnormal);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f);

			LinearLayout space = new LinearLayout(this);
			LayoutParams lp1 = new LayoutParams(1, LayoutParams.WRAP_CONTENT);
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
						((Button) findViewById(l)).setTextColor(Color.BLACK);
					}
					btn.setBackgroundResource(R.drawable.pactive);
					btn.setTextColor(Color.WHITE);
					countforcustomer(Integer.parseInt(btn.getText().toString()
							.trim()));
					testforid = Integer.parseInt(btn.getText().toString()
							.trim());
				}

			});
		}

	}

	@Override
	public void onEditClickedforCustomer(View v, final String custno,
			String firstname, String lastname, String address, String company,
			String phone) {
		final AlertDialog alertDialog1 = new AlertDialog.Builder(this,android.R.style.Theme_Translucent_NoTitleBar).create();
		Log.e("tagdd", ".....gggggg");
		final EditText custnumed, firstnameed, lastnameed, emailed;
		Button save, cancel, generalinfoed, extendedinfoed, shippinged, historyed, notesed, storesed;

		LayoutInflater mInflater = LayoutInflater.from(this);
		View layout = mInflater.inflate(R.layout.customer, null);

		custnumed = (EditText) layout.findViewById(R.id.cust_no);
		custnumed.setEnabled(false);
		firstnameed = (EditText) layout.findViewById(R.id.firstname);
		lastnameed = (EditText) layout.findViewById(R.id.lastname);
		emailed = (EditText) layout.findViewById(R.id.emailaddress);
		save = (Button) layout.findViewById(R.id.savecat);
		cancel = (Button) layout.findViewById(R.id.cancelcat);
		generalinfoed = (Button) layout.findViewById(R.id.generalinfo);
		extendedinfoed = (Button) layout.findViewById(R.id.extendedinfo);
		shippinged = (Button) layout.findViewById(R.id.shippinginfo);
		historyed = (Button) layout.findViewById(R.id.historyinfo);
		notesed = (Button) layout.findViewById(R.id.notesinfo);
		storesed = (Button) layout.findViewById(R.id.storesinfo);
		storesspinnerarrayfordisplay.clear();

		// if(Parameters.usertype.equals("demo")){

		String selectQuery = "SELECT  * FROM " + DatabaseForDemo.CUSTOMER_TABLE
				+ " where " + DatabaseForDemo.CUSTOMER_NO + "=\"" + custno + "\"";

		Cursor mCursor = dbforloginlogoutReadCustomer.rawQuery(selectQuery, null);
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String custnoval = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.CUSTOMER_NO));
					custnumed.setText(custnoval);
					if (mCursor
							.isNull(mCursor
									.getColumnIndex(DatabaseForDemo.CREATED_DATE))) {
						currentTimeforEdit=Parameters.currentTime();
					} else {
						currentTimeforEdit = mCursor
								.getString(mCursor
										.getColumnIndex(DatabaseForDemo.CREATED_DATE));
					}
					
					 modifiedTimeforEdit=Parameters.currentTime();
					
					String firstnameval = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.CUSTOMER_FIRST_NAME));
					firstnameed.setText(firstnameval);

					String lastnameval = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.CUSTOMER_LAST_NAME));
					lastnameed.setText(lastnameval);

					if (mCursor.isNull(mCursor
							.getColumnIndex(DatabaseForDemo.CUSTOMER_EMAIL))) {
						emailed.setText("");
					} else {
						String emailval = mCursor
								.getString(mCursor
										.getColumnIndex(DatabaseForDemo.CUSTOMER_EMAIL));
						emailed.setText(emailval);
					}
					if (mCursor.isNull(mCursor
							.getColumnIndex(DatabaseForDemo.CUSTOMER_NOTES))) {
						customernotes = "";
					} else {
						customernotes = mCursor
								.getString(mCursor
										.getColumnIndex(DatabaseForDemo.CUSTOMER_NOTES));
					}
				} while (mCursor.moveToNext());
			}
		}
		mCursor.close();
		String selectQueryforgeneralinfo = "SELECT  * FROM "
				+ DatabaseForDemo.CUSTOMER_GENERAL_INFO_TABLE + " where "
				+ DatabaseForDemo.CUSTOMER_NO + "=\"" + custno + "\"";

		Cursor mCursorgeneral = dbforloginlogoutReadCustomer.rawQuery(selectQueryforgeneralinfo, null);
		if (mCursorgeneral != null) {
			if (mCursorgeneral.moveToFirst()) {
				do {
					if (mCursorgeneral
							.isNull(mCursorgeneral
									.getColumnIndex(DatabaseForDemo.CUSTOMER_COMPANY_NAME))) {
						generalcompanyname = "";
					} else {
						generalcompanyname = mCursorgeneral
								.getString(mCursorgeneral
										.getColumnIndex(DatabaseForDemo.CUSTOMER_COMPANY_NAME));
					}
					if (mCursorgeneral
							.isNull(mCursorgeneral
									.getColumnIndex(DatabaseForDemo.CUSTOMER_PRIMARY_PHONE))) {
						generalprimaryphone = "";
					} else {
						generalprimaryphone = mCursorgeneral
								.getString(mCursorgeneral
										.getColumnIndex(DatabaseForDemo.CUSTOMER_PRIMARY_PHONE));
					}
					if (mCursorgeneral
							.isNull(mCursorgeneral
									.getColumnIndex(DatabaseForDemo.CUSTOMER_ALTERNATE_PHONE))) {
						generalalternatephone = "";
					} else {
						generalalternatephone = mCursorgeneral
								.getString(mCursorgeneral
										.getColumnIndex(DatabaseForDemo.CUSTOMER_ALTERNATE_PHONE));
					}
					if (mCursorgeneral.isNull(mCursorgeneral
							.getColumnIndex(DatabaseForDemo.CUSTOMER_STREET1))) {
						generaladdress1 = "";
					} else {
						generaladdress1 = mCursorgeneral
								.getString(mCursorgeneral
										.getColumnIndex(DatabaseForDemo.CUSTOMER_STREET1));
					}
					if (mCursorgeneral.isNull(mCursorgeneral
							.getColumnIndex(DatabaseForDemo.CUSTOMER_STREET2))) {
						generaladdress2 = "";
					} else {
						generaladdress2 = mCursorgeneral
								.getString(mCursorgeneral
										.getColumnIndex(DatabaseForDemo.CUSTOMER_STREET2));
					}
					if (mCursorgeneral.isNull(mCursorgeneral
							.getColumnIndex(DatabaseForDemo.CUSTOMER_STATE))) {
						generalstate = "";
					} else {
						generalstate = mCursorgeneral
								.getString(mCursorgeneral
										.getColumnIndex(DatabaseForDemo.CUSTOMER_STATE));
					}
					if (mCursorgeneral.isNull(mCursorgeneral
							.getColumnIndex(DatabaseForDemo.CUSTOMER_CITY))) {
						generalcity = "";
					} else {
						generalcity = mCursorgeneral.getString(mCursorgeneral
								.getColumnIndex(DatabaseForDemo.CUSTOMER_CITY));
					}
					if (mCursorgeneral.isNull(mCursorgeneral
							.getColumnIndex(DatabaseForDemo.CUSTOMER_COUNTRY))) {
						generalcountry = "";
					} else {
						generalcountry = mCursorgeneral
								.getString(mCursorgeneral
										.getColumnIndex(DatabaseForDemo.CUSTOMER_COUNTRY));
					}
					if (mCursorgeneral.isNull(mCursorgeneral
							.getColumnIndex(DatabaseForDemo.CUSTOMER_ZIPCODE))) {
						generalzipcode = "";
					} else {
						generalzipcode = mCursorgeneral
								.getString(mCursorgeneral
										.getColumnIndex(DatabaseForDemo.CUSTOMER_ZIPCODE));
					}
					if (mCursorgeneral.isNull(mCursorgeneral
							.getColumnIndex(DatabaseForDemo.CUSTOMER_BIRTHDAY))) {
						birthdayval = "";
					} else {
						birthdayval = mCursorgeneral
								.getString(mCursorgeneral
										.getColumnIndex(DatabaseForDemo.CUSTOMER_BIRTHDAY));
					}
				} while (mCursorgeneral.moveToNext());
			}
		}
		mCursorgeneral.close();
		String selectQueryforextendedinfo = "SELECT  * FROM "
				+ DatabaseForDemo.CUSTOMER_EXTENDED_INFO_TABLE + " where "
				+ DatabaseForDemo.CUSTOMER_NO + "=\"" + custno + "\"";

		Cursor mCursorextended = dbforloginlogoutReadCustomer.rawQuery(selectQueryforextendedinfo, null);
		if (mCursorextended != null) {
			if (mCursorextended.moveToFirst()) {
				do {
					if (mCursorextended.isNull(mCursorextended
							.getColumnIndex(DatabaseForDemo.CREDIT_CARD_TYPE))) {
						cardtypepositionstring = "";
					} else {
						cardtypepositionstring = mCursorextended
								.getString(mCursorextended
										.getColumnIndex(DatabaseForDemo.CREDIT_CARD_TYPE));
					}
					if (mCursorextended.isNull(mCursorextended
							.getColumnIndex(DatabaseForDemo.CREDIT_CARD_NUM))) {
						cardnumberval = "";
					} else {
						cardnumberval = mCursorextended
								.getString(mCursorextended
										.getColumnIndex(DatabaseForDemo.CREDIT_CARD_NUM));
					}
					if (mCursorextended.isNull(mCursorextended
							.getColumnIndex(DatabaseForDemo.EXPIRATION))) {
						expirationval = "";
					} else {
						expirationval = mCursorextended
								.getString(mCursorextended
										.getColumnIndex(DatabaseForDemo.EXPIRATION));
					}
					if (mCursorextended.isNull(mCursorextended
							.getColumnIndex(DatabaseForDemo.DRIVING_LICENSE))) {
						drivinglicenseval = "";
					} else {
						drivinglicenseval = mCursorextended
								.getString(mCursorextended
										.getColumnIndex(DatabaseForDemo.DRIVING_LICENSE));
					}
					if (mCursorextended.isNull(mCursorextended
							.getColumnIndex(DatabaseForDemo.EXP_DATE))) {
						expdateval = "";
					} else {
						expdateval = mCursorextended.getString(mCursorextended
								.getColumnIndex(DatabaseForDemo.EXP_DATE));
					}
					if (mCursorextended.isNull(mCursorextended
							.getColumnIndex(DatabaseForDemo.CUSTOMER_MOBILE))) {
						mobileval = "";
					} else {
						mobileval = mCursorextended
								.getString(mCursorextended
										.getColumnIndex(DatabaseForDemo.CUSTOMER_MOBILE));
					}
					if (mCursorextended.isNull(mCursorextended
							.getColumnIndex(DatabaseForDemo.CUSTOMER_FAX))) {
						faxval = "";
					} else {
						faxval = mCursorextended.getString(mCursorextended
								.getColumnIndex(DatabaseForDemo.CUSTOMER_FAX));
					}
				} while (mCursorextended.moveToNext());
			}
		}
		mCursorextended.close();
		String selectQueryforshipping = "SELECT  * FROM "
				+ DatabaseForDemo.CUSTOMER_SHIPPING_TABLE + " where "
				+ DatabaseForDemo.CUSTOMER_NO + "=\"" + custno + "\"";

		Cursor mCursorshipping = dbforloginlogoutReadCustomer.rawQuery(selectQueryforshipping, null);
		if (mCursorshipping != null) {
			if (mCursorshipping.moveToFirst()) {
				do {
					if (mCursorshipping
							.isNull(mCursorshipping
									.getColumnIndex(DatabaseForDemo.SHIPPING_FIRST_NAME))) {
						shippingfirstname = "";
					} else {
						shippingfirstname = mCursorshipping
								.getString(mCursorshipping
										.getColumnIndex(DatabaseForDemo.SHIPPING_FIRST_NAME));
					}
					if (mCursorshipping
							.isNull(mCursorshipping
									.getColumnIndex(DatabaseForDemo.SHIPPING_LAST_NAME))) {
						shippinglastname = "";
					} else {
						shippinglastname = mCursorshipping
								.getString(mCursorshipping
										.getColumnIndex(DatabaseForDemo.SHIPPING_LAST_NAME));
					}
					if (mCursorshipping
							.isNull(mCursorshipping
									.getColumnIndex(DatabaseForDemo.SHIPPING_COMPANY_NAME))) {
						shippingcompanyname = "";
					} else {
						shippingcompanyname = mCursorshipping
								.getString(mCursorshipping
										.getColumnIndex(DatabaseForDemo.SHIPPING_COMPANY_NAME));
					}
					if (mCursorshipping.isNull(mCursorshipping
							.getColumnIndex(DatabaseForDemo.SHIPPING_PHONE))) {
						shippingphone = "";
					} else {
						shippingphone = mCursorshipping
								.getString(mCursorshipping
										.getColumnIndex(DatabaseForDemo.SHIPPING_PHONE));
					}
					if (mCursorshipping.isNull(mCursorshipping
							.getColumnIndex(DatabaseForDemo.SHIPPING_STREET))) {
						shippingaddress = "";
					} else {
						shippingaddress = mCursorshipping
								.getString(mCursorshipping
										.getColumnIndex(DatabaseForDemo.SHIPPING_STREET));
					}
					if (mCursorshipping.isNull(mCursorshipping
							.getColumnIndex(DatabaseForDemo.SHIPPING_EXTENDED))) {
						shippingextended = "";
					} else {
						shippingextended = mCursorshipping
								.getString(mCursorshipping
										.getColumnIndex(DatabaseForDemo.SHIPPING_EXTENDED));
					}
					if (mCursorshipping.isNull(mCursorshipping
							.getColumnIndex(DatabaseForDemo.SHIPPING_CITY))) {
						shippingcity = "";
					} else {
						shippingcity = mCursorshipping
								.getString(mCursorshipping
										.getColumnIndex(DatabaseForDemo.SHIPPING_CITY));
					}
					if (mCursorshipping.isNull(mCursorshipping
							.getColumnIndex(DatabaseForDemo.SHIPPING_STATE))) {
						shippingstate = "";
					} else {
						shippingstate = mCursorshipping
								.getString(mCursorshipping
										.getColumnIndex(DatabaseForDemo.SHIPPING_STATE));
					}
					if (mCursorshipping.isNull(mCursorshipping
							.getColumnIndex(DatabaseForDemo.SHIPPING_COUNTRY))) {
						shippingcountry = "";
					} else {
						shippingcountry = mCursorshipping
								.getString(mCursorshipping
										.getColumnIndex(DatabaseForDemo.SHIPPING_COUNTRY));
					}
					if (mCursorshipping.isNull(mCursorshipping
							.getColumnIndex(DatabaseForDemo.SHIPPING_ZIPCODE))) {
						shippingzipcode = "";
					} else {
						shippingzipcode = mCursorshipping
								.getString(mCursorshipping
										.getColumnIndex(DatabaseForDemo.SHIPPING_ZIPCODE));
					}
				} while (mCursorshipping.moveToNext());
			}
		}
		mCursorshipping.close();
		String selectQueryforstores = "SELECT  * FROM "
				+ DatabaseForDemo.CUSTOMER_STORES_TABLE + " where "
				+ DatabaseForDemo.CUSTOMER_NO + "=\"" + custno + "\"";

		Cursor mCursorstores = dbforloginlogoutReadCustomer.rawQuery(selectQueryforstores, null);
		if (mCursorstores != null) {
			if (mCursorstores.moveToFirst()) {
				do {
					String storeid = mCursorstores.getString(mCursorstores
							.getColumnIndex(DatabaseForDemo.STORE_ID_CUSTOMER));
					storesspinnerarrayfordisplay.add(storeid);
				} while (mCursorstores.moveToNext());
			}
		}
		mCursorstores.close();
		generalinfoed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String custno = custnumed.getText().toString();
				if (custno.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please enter customer number.", Toast.LENGTH_SHORT)
							.show();
				} else {

					final Dialog dialog = new Dialog(CustomerActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.general_info_cust);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final EditText companyname = (EditText) dialog
							.findViewById(R.id.companyname);
					final EditText primaryphone = (EditText) dialog
							.findViewById(R.id.primaryphone);
					final EditText alternatephone = (EditText) dialog
							.findViewById(R.id.alternatephone);
					final EditText streetaddress = (EditText) dialog
							.findViewById(R.id.streetaddress);
					final EditText streetaddress2 = (EditText) dialog
							.findViewById(R.id.streetaddress2);
					final EditText birthday = (EditText) dialog
							.findViewById(R.id.birthday);
					final EditText city = (EditText) dialog
							.findViewById(R.id.city);
					final EditText state = (EditText) dialog
							.findViewById(R.id.state);
					final EditText country = (EditText) dialog
							.findViewById(R.id.country);
					final EditText zipcode = (EditText) dialog
							.findViewById(R.id.zipcode);

					companyname.setText(generalcompanyname);
					primaryphone.setText(generalprimaryphone);
					alternatephone.setText(generalalternatephone);
					streetaddress.setText(generaladdress1);
					streetaddress2.setText(generaladdress2);
					birthday.setText(birthdayval);
					city.setText(generalcity);
					state.setText(generalstate);
					country.setText(generalcountry);
					zipcode.setText(generalzipcode);

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							generalcompanyname =""+ companyname.getText()
									.toString();
							generalprimaryphone =""+ primaryphone.getText()
									.toString();
							generalalternatephone =""+ alternatephone.getText()
									.toString();
							generaladdress1 =""+ streetaddress.getText()
									.toString();
							generaladdress2 =""+ streetaddress2.getText()
									.toString();
							birthdayval =""+ birthday.getText().toString();
							generalcity = ""+city.getText().toString();
							generalstate =""+ state.getText().toString();
							generalcountry =""+ country.getText().toString();
							generalzipcode = ""+zipcode.getText().toString();

							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		extendedinfoed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String custno = custnumed.getText().toString();
				if (custno.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please enter customer number.", Toast.LENGTH_SHORT)
							.show();
				} else {

					final Dialog dialog = new Dialog(CustomerActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.extended_info_cust);
					final Spinner cardtype = (Spinner) dialog
							.findViewById(R.id.cardtype);
					final EditText cardnumber = (EditText) dialog
							.findViewById(R.id.cardno);
					final EditText expiration = (EditText) dialog
							.findViewById(R.id.expiration);
					final EditText drivinglicense = (EditText) dialog
							.findViewById(R.id.driverslicense);
					final EditText expdate = (EditText) dialog
							.findViewById(R.id.expdate);
					final EditText mobile = (EditText) dialog
							.findViewById(R.id.mobile);
					final EditText fax = (EditText) dialog
							.findViewById(R.id.fax);
					final Button save = (Button) dialog
							.findViewById(R.id.savecat);
					final Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);

					String[] cardtypearray = getResources().getStringArray(
							R.array.creditcard_array);
					List<String> cardtypearraylist = Arrays
							.asList(cardtypearray);
					cardtypeposition = cardtypearraylist
							.indexOf(cardtypepositionstring);
					cardtype.setSelection(cardtypeposition);
					cardnumber.setText(cardnumberval);
					expiration.setText(expirationval);
					drivinglicense.setText(drivinglicenseval);
					expdate.setText(expdateval);
					mobile.setText(mobileval);
					fax.setText(faxval);

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							cardtypepositionstring =""+ cardtype.getSelectedItem()
									.toString();
							cardtypeposition = cardtype
									.getSelectedItemPosition();
							cardnumberval =""+ cardnumber.getText().toString();
							expirationval =""+ expiration.getText().toString();
							drivinglicenseval =""+ drivinglicense.getText()
									.toString();
							expdateval =""+ expdate.getText().toString();
							mobileval =""+ mobile.getText().toString();
							faxval =""+ fax.getText().toString();

							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		shippinged.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String custno = custnumed.getText().toString();
				if (custno.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please enter customer number.", Toast.LENGTH_SHORT)
							.show();
				} else {

					final Dialog dialog = new Dialog(CustomerActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.shipping_cust);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final EditText firstname = (EditText) dialog
							.findViewById(R.id.firstname);
					final EditText lastname = (EditText) dialog
							.findViewById(R.id.lastname);
					final EditText companyname = (EditText) dialog
							.findViewById(R.id.companyname);
					final EditText phoneno = (EditText) dialog
							.findViewById(R.id.phonenum);
					final EditText streetaddress = (EditText) dialog
							.findViewById(R.id.street);
					final EditText extendedaddress = (EditText) dialog
							.findViewById(R.id.extended);
					final EditText city = (EditText) dialog
							.findViewById(R.id.city);
					final EditText state = (EditText) dialog
							.findViewById(R.id.state);
					final EditText country = (EditText) dialog
							.findViewById(R.id.country);
					final EditText zipcode = (EditText) dialog
							.findViewById(R.id.zipcode);

					firstname.setText(shippingfirstname);
					lastname.setText(shippinglastname);
					companyname.setText(shippingcompanyname);
					phoneno.setText(shippingphone);
					streetaddress.setText(shippingaddress);
					extendedaddress.setText(shippingextended);
					city.setText(shippingcity);
					state.setText(shippingstate);
					country.setText(shippingcountry);
					zipcode.setText(shippingzipcode);

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							shippingfirstname =""+ firstname.getText().toString();
							shippinglastname =""+ lastname.getText().toString();
							shippingcompanyname =""+ companyname.getText()
									.toString();
							shippingphone =""+ phoneno.getText().toString();
							shippingaddress =""+ streetaddress.getText()
									.toString();
							shippingextended =""+ extendedaddress.getText()
									.toString();
							shippingcity =""+ city.getText().toString();
							shippingstate =""+ state.getText().toString();
							shippingcountry =""+ country.getText().toString();
							shippingzipcode =""+ zipcode.getText().toString();

							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		historyed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String custno = custnumed.getText().toString();
				if (custno.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please enter customer number.", Toast.LENGTH_SHORT)
							.show();
				} else {

					final Dialog dialog = new Dialog(CustomerActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.history_cust);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					listforhistory = (ListView) dialog
							.findViewById(R.id.historylist);
					
					ArrayList<String> dateandtimelist = new ArrayList<String>();
					ArrayList<String> storeidlist = new ArrayList<String>();
					ArrayList<String> invoicelist = new ArrayList<String>();
					ArrayList<String> quantitylist = new ArrayList<String>();
					ArrayList<String> pricelist = new ArrayList<String>();
					ArrayList<String> idlist = new ArrayList<String>();
					ArrayList<String> namelist = new ArrayList<String>();
					ArrayList<String> seriallist = new ArrayList<String>();
					ArrayList<String> invoiceidlistcust = new ArrayList<String>();

					System.out.println("customer name is padma:"+custno);
					String query = "SELECT *from "
							+ DatabaseForDemo.INVOICE_TOTAL_TABLE + " where "
							+ DatabaseForDemo.INVOICE_CUSTOMER + "=\""
							+ custno + "\"";
					Cursor cursor = dbforloginlogoutReadCustomer.rawQuery(query, null);
					System.out.println("cursor count val is:"+cursor.getCount());
					if (cursor.getCount() > 0) {
						if (cursor != null) {
							if (cursor.moveToFirst()) {
								do {
									if (cursor.isNull(cursor
											.getColumnIndex(DatabaseForDemo.INVOICE_ID))) {
										invoiceidlistcust.add("");
									} else {
										String storeid = cursor.getString(cursor
												.getColumnIndex(DatabaseForDemo.INVOICE_ID));
										invoiceidlistcust.add(storeid);
									}
								} while (cursor.moveToNext());
							}
						}
					}
					cursor.close();
					int i=1;
					for (int ii = 0; ii < invoiceidlistcust.size(); ii++) {
						String selectQueryforshipping = "SELECT  * FROM "
								+ DatabaseForDemo.INVOICE_ITEMS_TABLE
								+ " where " + DatabaseForDemo.INVOICE_ID + "=\""
								+ invoiceidlistcust.get(ii) + "\"";

						Cursor mCursorforshipping = dbforloginlogoutReadCustomer.rawQuery(
								selectQueryforshipping, null);
						if (mCursorforshipping.getCount() > 0) {
							if (mCursorforshipping != null) {
								if (mCursorforshipping.moveToFirst()) {
									do {
										if (mCursorforshipping.isNull(mCursorforshipping
												.getColumnIndex(DatabaseForDemo.CREATED_DATE))) {
											dateandtimelist.add("");
										} else {
											String dateandtime = mCursorforshipping.getString(mCursorforshipping
													.getColumnIndex(DatabaseForDemo.CREATED_DATE));
											dateandtimelist.add(dateandtime);
										}
										if (mCursorforshipping.isNull(mCursorforshipping
												.getColumnIndex(DatabaseForDemo.INVOICE_ID))) {
											invoicelist.add("");
										} else {
											String storeid = mCursorforshipping.getString(mCursorforshipping
													.getColumnIndex(DatabaseForDemo.INVOICE_ID));
											invoicelist.add(storeid);
										}
										if (mCursorforshipping.isNull(mCursorforshipping
												.getColumnIndex(DatabaseForDemo.INVOICE_ITEM_ID))) {
											idlist.add("");
										} else {
											String invoiceid = mCursorforshipping.getString(mCursorforshipping
													.getColumnIndex(DatabaseForDemo.INVOICE_ITEM_ID));
											idlist.add(invoiceid);
										}
										if (mCursorforshipping.isNull(mCursorforshipping
												.getColumnIndex(DatabaseForDemo.INVOICE_ITEM_NAME))) {
											namelist.add("");
										} else {
											String qtylist = mCursorforshipping.getString(mCursorforshipping
													.getColumnIndex(DatabaseForDemo.INVOICE_ITEM_NAME));
											namelist.add(qtylist);
										}
										if (mCursorforshipping.isNull(mCursorforshipping
												.getColumnIndex(DatabaseForDemo.INVOICE_QUANTITY))) {
											quantitylist.add("");
										} else {
											String price = mCursorforshipping.getString(mCursorforshipping
													.getColumnIndex(DatabaseForDemo.INVOICE_QUANTITY));
											quantitylist.add(price);
										}
										if (mCursorforshipping.isNull(mCursorforshipping
												.getColumnIndex(DatabaseForDemo.INVOICE_YOUR_COST))) {
											pricelist.add("");
										} else {
											String cost = mCursorforshipping.getString(mCursorforshipping
													.getColumnIndex(DatabaseForDemo.INVOICE_YOUR_COST));
											pricelist.add(cost);
										}
										if (mCursorforshipping.isNull(mCursorforshipping
												.getColumnIndex(DatabaseForDemo.INVOICE_STORE_ID))) {
											storeidlist.add("");
										} else {
											String cost = mCursorforshipping.getString(mCursorforshipping
													.getColumnIndex(DatabaseForDemo.INVOICE_STORE_ID));
											storeidlist.add(cost);
										}

										seriallist.add("" + i);
										i++;
									} while (mCursorforshipping.moveToNext());
								}
							}
						}
						mCursorforshipping.close();
					}

					ImageAdapterForSalesHistory adapter = new ImageAdapterForSalesHistory(
							CustomerActivity.this, dateandtimelist,
							invoicelist, idlist, namelist, quantitylist, pricelist,
							storeidlist, seriallist);
					listforhistory.setAdapter(adapter);

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		notesed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String custno = custnumed.getText().toString();
				if (custno.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please enter customer number.", Toast.LENGTH_SHORT)
							.show();
				} else {

					final Dialog dialog = new Dialog(CustomerActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.notes_cust);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final EditText notes = (EditText) dialog
							.findViewById(R.id.notestext);

					notes.setText(customernotes);

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							customernotes = notes.getText().toString();
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		storesed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String custno = custnumed.getText().toString();
				if (custno.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please enter customer number.", Toast.LENGTH_SHORT)
							.show();
				} else {

					final Dialog dialog = new Dialog(CustomerActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.stores_cust);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final Spinner stores = (Spinner) dialog
							.findViewById(R.id.storesspinner);
					storelist = (ListView) dialog.findViewById(R.id.storeslist);

					storesspinnerarray.clear();
					storesspinnerarray.add("Select");
					
					String selectQuery = "SELECT  " + DatabaseForDemo.STORE_ID
							+ " FROM " + DatabaseForDemo.STORE_TABLE;
					Cursor mCursor = dbforloginlogoutReadCustomer.rawQuery(selectQuery, null);
					if (mCursor != null) {
						if (mCursor.moveToFirst()) {
							do {
								String storeid = mCursor.getString(mCursor
										.getColumnIndex(DatabaseForDemo.STORE_ID));
								storesspinnerarray.add(storeid);
							} while (mCursor.moveToNext());
						}
					}
					mCursor.close();
					
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							CustomerActivity.this,
							android.R.layout.simple_spinner_item,
							storesspinnerarray);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
					stores.setAdapter(adapter);

					if (storesspinnerarrayfordisplay != null) {
						SkuArrayAdapter adapterformod = new SkuArrayAdapter(
								CustomerActivity.this,
								storesspinnerarrayfordisplay);
						adapterformod.setListener(CustomerActivity.this);
						storelist.setAdapter(adapterformod);
					}

					stores.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub
							String storeinspinner = stores.getSelectedItem()
									.toString();
							if (storeinspinner.equals("")
									|| storeinspinner.equals("Select")) {

							} else {
								if (storesspinnerarrayfordisplay
										.contains(storeinspinner)) {
									Toast.makeText(getApplicationContext(),
											"Store already exist",
											Toast.LENGTH_SHORT).show();
								} else {
									storesspinnerarrayfordisplay
											.add(storeinspinner);
									if (storesspinnerarrayfordisplay != null) {
										SkuArrayAdapter adapterformod = new SkuArrayAdapter(
												CustomerActivity.this,
												storesspinnerarrayfordisplay);
										adapterformod
												.setListener(CustomerActivity.this);
										storelist.setAdapter(adapterformod);
									}

								}
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}
					});

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (custnumed.getText().toString().equals("")
						|| firstnameed.getText().toString().equals("")
						|| lastnameed.getText().toString().equals("")) {
					Toast.makeText(CustomerActivity.this,
							"Please enter details", Toast.LENGTH_SHORT).show();
				} else {
					dbforloginlogoutWriteCustomer.execSQL("update "
							+ DatabaseForDemo.CUSTOMER_TABLE + " set "
							+ DatabaseForDemo.CREATED_DATE + "=\""
							+ currentTimeforEdit + "\", "
							+ DatabaseForDemo.MODIFIED_DATE + "=\""
							+ modifiedTimeforEdit+ "\", "
							+ DatabaseForDemo.MODIFIED_IN + "=\"" + "Local"
							+ "\", " + DatabaseForDemo.CUSTOMER_LAST_NAME + "=\""
							+ lastnameed.getText().toString() + "\", "
							+ DatabaseForDemo.CUSTOMER_EMAIL + "=\""
							+ emailed.getText().toString() + "\", "
							+ DatabaseForDemo.CUSTOMER_NOTES + "=\""
							+ customernotes + "\", "
							+ DatabaseForDemo.CUSTOMER_FIRST_NAME + "=\""
							+ firstnameed.getText().toString() + "\", "
							+ DatabaseForDemo.CUSTOMER_NO + "=\""
							+ custnumed.getText().toString() + "\" where "
							+ DatabaseForDemo.CUSTOMER_NO + "=\"" + custno + "\"");

					dbforloginlogoutWriteCustomer.execSQL("update "
							+ DatabaseForDemo.CUSTOMER_GENERAL_INFO_TABLE
							+ " set " + DatabaseForDemo.CREATED_DATE + "=\""
							+ currentTimeforEdit + "\", "
							+ DatabaseForDemo.MODIFIED_DATE + "=\""
							+ modifiedTimeforEdit + "\", "
							+ DatabaseForDemo.MODIFIED_IN + "=\"" + "Local"
							+ "\", " + DatabaseForDemo.CUSTOMER_COMPANY_NAME
							+ "=\"" + generalcompanyname + "\", "
							+ DatabaseForDemo.CUSTOMER_PRIMARY_PHONE + "=\""
							+ generalprimaryphone + "\", "
							+ DatabaseForDemo.CUSTOMER_ALTERNATE_PHONE + "=\""
							+ generalalternatephone + "\", "
							+ DatabaseForDemo.CUSTOMER_STREET1 + "=\""
							+ generaladdress1 + "\", "
							+ DatabaseForDemo.CUSTOMER_STREET2 + "=\""
							+ generaladdress2 + "\", "
							+ DatabaseForDemo.CUSTOMER_STATE + "=\""
							+ generalstate + "\", "
							+ DatabaseForDemo.CUSTOMER_CITY + "=\""
							+ generalcity + "\", "
							+ DatabaseForDemo.CUSTOMER_COUNTRY + "=\""
							+ generalcountry + "\", "
							+ DatabaseForDemo.CUSTOMER_ZIPCODE + "=\""
							+ generalzipcode + "\", "
							+ DatabaseForDemo.CUSTOMER_BIRTHDAY + "=\""
							+ birthdayval + "\", " + DatabaseForDemo.CUSTOMER_NO
							+ "=\"" + custnumed.getText().toString()
							+ "\" where " + DatabaseForDemo.CUSTOMER_NO + "=\""
							+ custno + "\"");

					dbforloginlogoutWriteCustomer.execSQL("update "
							+ DatabaseForDemo.CUSTOMER_EXTENDED_INFO_TABLE
							+ " set " + DatabaseForDemo.CREATED_DATE + "=\""
							+ currentTimeforEdit + "\", "
							+ DatabaseForDemo.MODIFIED_DATE + "=\""
							+ modifiedTimeforEdit + "\", "
							+ DatabaseForDemo.MODIFIED_IN + "=\"" + "Local"
							+ "\", " + DatabaseForDemo.CREDIT_CARD_TYPE + "=\""
							+ cardtypepositionstring + "\", "
							+ DatabaseForDemo.CREDIT_CARD_NUM + "=\""
							+ cardnumberval + "\", "
							+ DatabaseForDemo.EXPIRATION + "=\"" + expirationval
							+ "\", " + DatabaseForDemo.DRIVING_LICENSE + "=\""
							+ drivinglicenseval + "\", "
							+ DatabaseForDemo.EXP_DATE + "=\"" + expdateval
							+ "\", " + DatabaseForDemo.CUSTOMER_MOBILE + "=\""
							+ mobileval + "\", " + DatabaseForDemo.CUSTOMER_FAX
							+ "=\"" + faxval + "\", "
							+ DatabaseForDemo.CUSTOMER_NO + "=\""
							+ custnumed.getText().toString() + "\" where "
							+ DatabaseForDemo.CUSTOMER_NO + "=\"" + custno + "\"");

					dbforloginlogoutWriteCustomer.execSQL("update "
							+ DatabaseForDemo.CUSTOMER_SHIPPING_TABLE + " set "
							+ DatabaseForDemo.CREATED_DATE + "=\""
							+ currentTimeforEdit + "\", "
							+ DatabaseForDemo.MODIFIED_DATE + "=\""
							+modifiedTimeforEdit + "\", "
							+ DatabaseForDemo.MODIFIED_IN + "=\"" + "Local"
							+ "\", " + DatabaseForDemo.SHIPPING_FIRST_NAME
							+ "=\"" + shippingfirstname + "\", "
							+ DatabaseForDemo.SHIPPING_LAST_NAME + "=\""
							+ shippinglastname + "\", "
							+ DatabaseForDemo.SHIPPING_COMPANY_NAME + "=\""
							+ shippingcompanyname + "\", "
							+ DatabaseForDemo.SHIPPING_PHONE + "=\""
							+ shippingphone + "\", "
							+ DatabaseForDemo.SHIPPING_STREET + "=\""
							+ shippingaddress + "\", "
							+ DatabaseForDemo.SHIPPING_EXTENDED + "=\""
							+ shippingextended + "\", "
							+ DatabaseForDemo.SHIPPING_CITY + "=\""
							+ shippingcity + "\", "
							+ DatabaseForDemo.SHIPPING_STATE + "=\""
							+ shippingstate + "\", "
							+ DatabaseForDemo.SHIPPING_COUNTRY + "=\""
							+ shippingcountry + "\", "
							+ DatabaseForDemo.SHIPPING_ZIPCODE + "=\""
							+ shippingzipcode + "\", "
							+ DatabaseForDemo.CUSTOMER_NO + "=\""
							+ custnumed.getText().toString() + "\" where "
							+ DatabaseForDemo.CUSTOMER_NO + "=\"" + custno + "\"");

					String uniqueidforprodedit = "";
					String select = "select " + DatabaseForDemo.UNIQUE_ID
							+ " from " + DatabaseForDemo.CUSTOMER_STORES_TABLE
							+ " where " + DatabaseForDemo.CUSTOMER_NO + "=\""
							+ custno + "\"";
					Cursor selectcursor = dbforloginlogoutReadCustomer.rawQuery(select, null);
					if (selectcursor.getCount() > 0) {
						if (selectcursor != null) {
							if (selectcursor.moveToFirst()) {
								do {
									uniqueidforprodedit = selectcursor.getString(selectcursor
											.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
								} while (selectcursor.moveToNext());
							}
						}
					} else {

					}
					selectcursor.close();

					String whereforalternatesku = DatabaseForDemo.CUSTOMER_NO
							+ "=?";
					dbforloginlogoutWriteCustomer.delete(
							DatabaseForDemo.CUSTOMER_STORES_TABLE,
							whereforalternatesku, new String[] { custno });

					for (int i = 0; i < storesspinnerarrayfordisplay.size(); i++) {
						ContentValues store_info = new ContentValues();
						store_info.put(DatabaseForDemo.UNIQUE_ID,
								Parameters.randomValue());
						store_info.put(DatabaseForDemo.CREATED_DATE,
								currentTimeforEdit);
						store_info.put(DatabaseForDemo.MODIFIED_DATE,
								modifiedTimeforEdit);
						store_info.put(DatabaseForDemo.MODIFIED_IN, "Local");
						store_info.put(DatabaseForDemo.STORE_ID_CUSTOMER,
								storesspinnerarrayfordisplay.get(i));
						store_info.put(DatabaseForDemo.CUSTOMER_NO, custnumed
								.getText().toString());
						dbforloginlogoutWriteCustomer.insert(
								DatabaseForDemo.CUSTOMER_STORES_TABLE, null,
								store_info);
					}

					Toast.makeText(CustomerActivity.this,
							"Customer saved successfully", Toast.LENGTH_SHORT)
							.show();

					final ArrayList<JSONObject> getlist1 = new ArrayList<JSONObject>();
					String selectQuery1 = "SELECT  * FROM "
							+ DatabaseForDemo.CUSTOMER_TABLE + " where "
							+ DatabaseForDemo.CUSTOMER_NO + "=\"" + custno + "\"";

					Cursor mCursor1 = dbforloginlogoutReadCustomer.rawQuery(selectQuery1,
							null);
					if (mCursor1 != null) {
						if (mCursor1.moveToFirst()) {
							do {
								try {
									JSONObject jsonobj = new JSONObject();
									if (mCursor1.isNull(mCursor1
											.getColumnIndex(DatabaseForDemo.CUSTOMER_LAST_NAME))) {
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_LAST_NAME,
												"");
									} else {
										String lastname = mCursor1.getString(mCursor1
												.getColumnIndex(DatabaseForDemo.CUSTOMER_LAST_NAME));
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_LAST_NAME,
												lastname);
									}
									if (mCursor1.isNull(mCursor1
											.getColumnIndex(DatabaseForDemo.CUSTOMER_EMAIL))) {
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_EMAIL,
												"");
									} else {
										String email = mCursor1.getString(mCursor1
												.getColumnIndex(DatabaseForDemo.CUSTOMER_EMAIL));
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_EMAIL,
												email);
									}
									if (mCursor1.isNull(mCursor1
											.getColumnIndex(DatabaseForDemo.CUSTOMER_NOTES))) {
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_NOTES,
												"");
									} else {
										String notes = mCursor1.getString(mCursor1
												.getColumnIndex(DatabaseForDemo.CUSTOMER_NOTES));
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_NOTES,
												notes);
									}
									if (mCursor1.isNull(mCursor1
											.getColumnIndex(DatabaseForDemo.CUSTOMER_FIRST_NAME))) {
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_FIRST_NAME,
												"");
									} else {
										String firstname = mCursor1.getString(mCursor1
												.getColumnIndex(DatabaseForDemo.CUSTOMER_FIRST_NAME));
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_FIRST_NAME,
												firstname);
									}
									if (mCursor1.isNull(mCursor1
											.getColumnIndex(DatabaseForDemo.CUSTOMER_NO))) {
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_NO, "");
									} else {
										String custno = mCursor1.getString(mCursor1
												.getColumnIndex(DatabaseForDemo.CUSTOMER_NO));
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_NO,
												custno);
									}
									if (mCursor1.isNull(mCursor1
											.getColumnIndex(DatabaseForDemo.UNIQUE_ID))) {
										jsonobj.put(DatabaseForDemo.UNIQUE_ID,
												"");
									} else {
										String uniqueid = mCursor1.getString(mCursor1
												.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
										jsonobj.put(DatabaseForDemo.UNIQUE_ID,
												uniqueid);
									}
									getlist1.add(jsonobj);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} while (mCursor1.moveToNext());
						}
					}
					mCursor1.close();
					final ArrayList<JSONObject> getlist2 = new ArrayList<JSONObject>();
					String selectQuery2 = "SELECT  * FROM "
							+ DatabaseForDemo.CUSTOMER_GENERAL_INFO_TABLE
							+ " where " + DatabaseForDemo.CUSTOMER_NO + "=\""
							+ custno + "\"";

					Cursor mCursor2 = dbforloginlogoutReadCustomer.rawQuery(selectQuery2,
							null);
					if (mCursor2 != null) {
						if (mCursor2.moveToFirst()) {
							do {
								try {
									JSONObject jsonobj = new JSONObject();
									if (mCursor2.isNull(mCursor2
											.getColumnIndex(DatabaseForDemo.CUSTOMER_COMPANY_NAME))) {
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_COMPANY_NAME,
												"");
									} else {
										String lastname = mCursor2.getString(mCursor2
												.getColumnIndex(DatabaseForDemo.CUSTOMER_COMPANY_NAME));
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_COMPANY_NAME,
												lastname);
									}
									if (mCursor2.isNull(mCursor2
											.getColumnIndex(DatabaseForDemo.CUSTOMER_PRIMARY_PHONE))) {
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_PRIMARY_PHONE,
												"");
									} else {
										String email = mCursor2.getString(mCursor2
												.getColumnIndex(DatabaseForDemo.CUSTOMER_PRIMARY_PHONE));
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_PRIMARY_PHONE,
												email);
									}
									if (mCursor2.isNull(mCursor2
											.getColumnIndex(DatabaseForDemo.CUSTOMER_ALTERNATE_PHONE))) {
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_ALTERNATE_PHONE,
												"");
									} else {
										String notes = mCursor2.getString(mCursor2
												.getColumnIndex(DatabaseForDemo.CUSTOMER_ALTERNATE_PHONE));
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_ALTERNATE_PHONE,
												notes);
									}
									if (mCursor2.isNull(mCursor2
											.getColumnIndex(DatabaseForDemo.CUSTOMER_STREET1))) {
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_STREET1,
												"");
									} else {
										String firstname = mCursor2.getString(mCursor2
												.getColumnIndex(DatabaseForDemo.CUSTOMER_STREET1));
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_STREET1,
												firstname);
									}
									if (mCursor2.isNull(mCursor2
											.getColumnIndex(DatabaseForDemo.CUSTOMER_STREET2))) {
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_STREET2,
												"");
									} else {
										String custno = mCursor2.getString(mCursor2
												.getColumnIndex(DatabaseForDemo.CUSTOMER_STREET2));
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_STREET2,
												custno);
									}
									if (mCursor2.isNull(mCursor2
											.getColumnIndex(DatabaseForDemo.CUSTOMER_STATE))) {
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_STATE,
												"");
									} else {
										String custno = mCursor2.getString(mCursor2
												.getColumnIndex(DatabaseForDemo.CUSTOMER_STATE));
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_STATE,
												custno);
									}
									if (mCursor2.isNull(mCursor2
											.getColumnIndex(DatabaseForDemo.CUSTOMER_CITY))) {
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_CITY,
												"");
									} else {
										String custno = mCursor2.getString(mCursor2
												.getColumnIndex(DatabaseForDemo.CUSTOMER_CITY));
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_CITY,
												custno);
									}
									if (mCursor2.isNull(mCursor2
											.getColumnIndex(DatabaseForDemo.CUSTOMER_COUNTRY))) {
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_COUNTRY,
												"");
									} else {
										String custno = mCursor2.getString(mCursor2
												.getColumnIndex(DatabaseForDemo.CUSTOMER_COUNTRY));
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_COUNTRY,
												custno);
									}
									if (mCursor2.isNull(mCursor2
											.getColumnIndex(DatabaseForDemo.CUSTOMER_ZIPCODE))) {
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_ZIPCODE,
												"");
									} else {
										String custno = mCursor2.getString(mCursor2
												.getColumnIndex(DatabaseForDemo.CUSTOMER_ZIPCODE));
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_ZIPCODE,
												custno);
									}
									if (mCursor2.isNull(mCursor2
											.getColumnIndex(DatabaseForDemo.CUSTOMER_BIRTHDAY))) {
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_BIRTHDAY,
												"");
									} else {
										String custno = mCursor2.getString(mCursor2
												.getColumnIndex(DatabaseForDemo.CUSTOMER_BIRTHDAY));
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_BIRTHDAY,
												custno);
									}
									if (mCursor2.isNull(mCursor2
											.getColumnIndex(DatabaseForDemo.CUSTOMER_NO))) {
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_NO, "");
									} else {
										String custno = mCursor2.getString(mCursor2
												.getColumnIndex(DatabaseForDemo.CUSTOMER_NO));
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_NO,
												custno);
									}
									if (mCursor2.isNull(mCursor2
											.getColumnIndex(DatabaseForDemo.UNIQUE_ID))) {
										jsonobj.put(DatabaseForDemo.UNIQUE_ID,
												"");
									} else {
										String uniqueid = mCursor2.getString(mCursor2
												.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
										jsonobj.put(DatabaseForDemo.UNIQUE_ID,
												uniqueid);
									}
									getlist2.add(jsonobj);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} while (mCursor2.moveToNext());
						}
					}
					mCursor2.close();
					final ArrayList<JSONObject> getlist3 = new ArrayList<JSONObject>();
					String selectQuery3 = "SELECT  * FROM "
							+ DatabaseForDemo.CUSTOMER_EXTENDED_INFO_TABLE
							+ " where " + DatabaseForDemo.CUSTOMER_NO + "=\""
							+ custno + "\"";

					Cursor mCursor3 = dbforloginlogoutReadCustomer.rawQuery(selectQuery3,
							null);
					if (mCursor3 != null) {
						if (mCursor3.moveToFirst()) {
							do {
								try {
									JSONObject jsonobj = new JSONObject();
									if (mCursor3.isNull(mCursor3
											.getColumnIndex(DatabaseForDemo.CREDIT_CARD_TYPE))) {
										jsonobj.put(
												DatabaseForDemo.CREDIT_CARD_TYPE,
												"");
									} else {
										String lastname = mCursor3.getString(mCursor3
												.getColumnIndex(DatabaseForDemo.CREDIT_CARD_TYPE));
										jsonobj.put(
												DatabaseForDemo.CREDIT_CARD_TYPE,
												lastname);
									}
									if (mCursor3.isNull(mCursor3
											.getColumnIndex(DatabaseForDemo.CREDIT_CARD_NUM))) {
										jsonobj.put(
												DatabaseForDemo.CREDIT_CARD_NUM,
												"");
									} else {
										String email = mCursor3.getString(mCursor3
												.getColumnIndex(DatabaseForDemo.CREDIT_CARD_NUM));
										jsonobj.put(
												DatabaseForDemo.CREDIT_CARD_NUM,
												email);
									}
									if (mCursor3.isNull(mCursor3
											.getColumnIndex(DatabaseForDemo.EXPIRATION))) {
										jsonobj.put(DatabaseForDemo.EXPIRATION,
												"");
									} else {
										String notes = mCursor3.getString(mCursor3
												.getColumnIndex(DatabaseForDemo.EXPIRATION));
										jsonobj.put(DatabaseForDemo.EXPIRATION,
												notes);
									}
									if (mCursor3.isNull(mCursor3
											.getColumnIndex(DatabaseForDemo.DRIVING_LICENSE))) {
										jsonobj.put(
												DatabaseForDemo.DRIVING_LICENSE,
												"");
									} else {
										String firstname = mCursor3.getString(mCursor3
												.getColumnIndex(DatabaseForDemo.DRIVING_LICENSE));
										jsonobj.put(
												DatabaseForDemo.DRIVING_LICENSE,
												firstname);
									}
									if (mCursor3.isNull(mCursor3
											.getColumnIndex(DatabaseForDemo.EXP_DATE))) {
										jsonobj.put(DatabaseForDemo.EXP_DATE,
												"");
									} else {
										String custno = mCursor3.getString(mCursor3
												.getColumnIndex(DatabaseForDemo.EXP_DATE));
										jsonobj.put(DatabaseForDemo.EXP_DATE,
												custno);
									}
									if (mCursor3.isNull(mCursor3
											.getColumnIndex(DatabaseForDemo.CUSTOMER_MOBILE))) {
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_MOBILE,
												"");
									} else {
										String custno = mCursor3.getString(mCursor3
												.getColumnIndex(DatabaseForDemo.CUSTOMER_MOBILE));
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_MOBILE,
												custno);
									}
									if (mCursor3.isNull(mCursor3
											.getColumnIndex(DatabaseForDemo.CUSTOMER_FAX))) {
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_FAX,
												"");
									} else {
										String custno = mCursor3.getString(mCursor3
												.getColumnIndex(DatabaseForDemo.CUSTOMER_FAX));
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_FAX,
												custno);
									}
									if (mCursor3.isNull(mCursor3
											.getColumnIndex(DatabaseForDemo.CUSTOMER_NO))) {
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_NO, "");
									} else {
										String custno = mCursor3.getString(mCursor3
												.getColumnIndex(DatabaseForDemo.CUSTOMER_NO));
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_NO,
												custno);
									}
									if (mCursor3.isNull(mCursor3
											.getColumnIndex(DatabaseForDemo.UNIQUE_ID))) {
										jsonobj.put(DatabaseForDemo.UNIQUE_ID,
												"");
									} else {
										String uniqueid = mCursor3.getString(mCursor3
												.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
										jsonobj.put(DatabaseForDemo.UNIQUE_ID,
												uniqueid);
									}
									getlist3.add(jsonobj);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} while (mCursor3.moveToNext());
						}
					}
					mCursor3.close();
					final ArrayList<JSONObject> getlist4 = new ArrayList<JSONObject>();
					String selectQuery4 = "SELECT  * FROM "
							+ DatabaseForDemo.CUSTOMER_SHIPPING_TABLE
							+ " where " + DatabaseForDemo.CUSTOMER_NO + "=\""
							+ custno + "\"";

					Cursor mCursor4 = dbforloginlogoutReadCustomer.rawQuery(selectQuery4,
							null);
					if (mCursor4 != null) {
						if (mCursor4.moveToFirst()) {
							do {
								try {
									JSONObject jsonobj = new JSONObject();
									if (mCursor4.isNull(mCursor4
											.getColumnIndex(DatabaseForDemo.SHIPPING_FIRST_NAME))) {
										jsonobj.put(
												DatabaseForDemo.SHIPPING_FIRST_NAME,
												"");
									} else {
										String lastname = mCursor4.getString(mCursor4
												.getColumnIndex(DatabaseForDemo.SHIPPING_FIRST_NAME));
										jsonobj.put(
												DatabaseForDemo.SHIPPING_FIRST_NAME,
												lastname);
									}
									if (mCursor4.isNull(mCursor4
											.getColumnIndex(DatabaseForDemo.SHIPPING_LAST_NAME))) {
										jsonobj.put(
												DatabaseForDemo.SHIPPING_LAST_NAME,
												"");
									} else {
										String email = mCursor4.getString(mCursor4
												.getColumnIndex(DatabaseForDemo.SHIPPING_LAST_NAME));
										jsonobj.put(
												DatabaseForDemo.SHIPPING_LAST_NAME,
												email);
									}
									if (mCursor4.isNull(mCursor4
											.getColumnIndex(DatabaseForDemo.SHIPPING_COMPANY_NAME))) {
										jsonobj.put(
												DatabaseForDemo.SHIPPING_COMPANY_NAME,
												"");
									} else {
										String notes = mCursor4.getString(mCursor4
												.getColumnIndex(DatabaseForDemo.SHIPPING_COMPANY_NAME));
										jsonobj.put(
												DatabaseForDemo.SHIPPING_COMPANY_NAME,
												notes);
									}
									if (mCursor4.isNull(mCursor4
											.getColumnIndex(DatabaseForDemo.SHIPPING_PHONE))) {
										jsonobj.put(
												DatabaseForDemo.SHIPPING_PHONE,
												"");
									} else {
										String firstname = mCursor4.getString(mCursor4
												.getColumnIndex(DatabaseForDemo.SHIPPING_PHONE));
										jsonobj.put(
												DatabaseForDemo.SHIPPING_PHONE,
												firstname);
									}
									if (mCursor4.isNull(mCursor4
											.getColumnIndex(DatabaseForDemo.SHIPPING_STREET))) {
										jsonobj.put(
												DatabaseForDemo.SHIPPING_STREET,
												"");
									} else {
										String custno = mCursor4.getString(mCursor4
												.getColumnIndex(DatabaseForDemo.SHIPPING_STREET));
										jsonobj.put(
												DatabaseForDemo.SHIPPING_STREET,
												custno);
									}
									if (mCursor4.isNull(mCursor4
											.getColumnIndex(DatabaseForDemo.SHIPPING_EXTENDED))) {
										jsonobj.put(
												DatabaseForDemo.SHIPPING_EXTENDED,
												"");
									} else {
										String custno = mCursor4.getString(mCursor4
												.getColumnIndex(DatabaseForDemo.SHIPPING_EXTENDED));
										jsonobj.put(
												DatabaseForDemo.SHIPPING_EXTENDED,
												custno);
									}
									if (mCursor4.isNull(mCursor4
											.getColumnIndex(DatabaseForDemo.SHIPPING_CITY))) {
										jsonobj.put(
												DatabaseForDemo.SHIPPING_CITY,
												"");
									} else {
										String custno = mCursor4.getString(mCursor4
												.getColumnIndex(DatabaseForDemo.SHIPPING_CITY));
										jsonobj.put(
												DatabaseForDemo.SHIPPING_CITY,
												custno);
									}
									if (mCursor4.isNull(mCursor4
											.getColumnIndex(DatabaseForDemo.SHIPPING_STATE))) {
										jsonobj.put(
												DatabaseForDemo.SHIPPING_STATE,
												"");
									} else {
										String custno = mCursor4.getString(mCursor4
												.getColumnIndex(DatabaseForDemo.SHIPPING_STATE));
										jsonobj.put(
												DatabaseForDemo.SHIPPING_STATE,
												custno);
									}
									if (mCursor4.isNull(mCursor4
											.getColumnIndex(DatabaseForDemo.SHIPPING_COUNTRY))) {
										jsonobj.put(
												DatabaseForDemo.SHIPPING_COUNTRY,
												"");
									} else {
										String custno = mCursor4.getString(mCursor4
												.getColumnIndex(DatabaseForDemo.SHIPPING_COUNTRY));
										jsonobj.put(
												DatabaseForDemo.SHIPPING_COUNTRY,
												custno);
									}
									if (mCursor4.isNull(mCursor4
											.getColumnIndex(DatabaseForDemo.SHIPPING_ZIPCODE))) {
										jsonobj.put(
												DatabaseForDemo.SHIPPING_ZIPCODE,
												"");
									} else {
										String custno = mCursor4.getString(mCursor4
												.getColumnIndex(DatabaseForDemo.SHIPPING_ZIPCODE));
										jsonobj.put(
												DatabaseForDemo.SHIPPING_ZIPCODE,
												custno);
									}
									if (mCursor4.isNull(mCursor4
											.getColumnIndex(DatabaseForDemo.CUSTOMER_NO))) {
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_NO, "");
									} else {
										String custno = mCursor4.getString(mCursor4
												.getColumnIndex(DatabaseForDemo.CUSTOMER_NO));
										jsonobj.put(
												DatabaseForDemo.CUSTOMER_NO,
												custno);
									}
									if (mCursor4.isNull(mCursor4
											.getColumnIndex(DatabaseForDemo.UNIQUE_ID))) {
										jsonobj.put(DatabaseForDemo.UNIQUE_ID,
												"");
									} else {
										String uniqueid = mCursor4.getString(mCursor4
												.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
										jsonobj.put(DatabaseForDemo.UNIQUE_ID,
												uniqueid);
									}
									getlist4.add(jsonobj);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} while (mCursor4.moveToNext());
						}
					}
					mCursor4.close();

					try {
						JSONObject data = new JSONObject();
						JSONArray fields = new JSONArray();
						for (int i = 0; i < getlist1.size(); i++) {
							fields.put(i, getlist1.get(i));
							data.put("fields", fields);
							dataval = data.toString();
							System.out.println("data val is:" + dataval);
						}
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {
						JSONObject data = new JSONObject();
						JSONArray fields = new JSONArray();
						for (int i = 0; i < getlist2.size(); i++) {
							fields.put(i, getlist2.get(i));
							data.put("fields", fields);
							dataval1 = data.toString();
							System.out.println("data val is:" + dataval1);
						}
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {
						JSONObject data = new JSONObject();
						JSONArray fields = new JSONArray();
						for (int i = 0; i < getlist3.size(); i++) {
							fields.put(i, getlist3.get(i));
							data.put("fields", fields);
							dataval2 = data.toString();
							System.out.println("data val is:" + dataval2);
						}
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {
						JSONObject data = new JSONObject();
						JSONArray fields = new JSONArray();
						for (int i = 0; i < getlist4.size(); i++) {
							fields.put(i, getlist4.get(i));
							data.put("fields", fields);
							dataval3 = data.toString();
							System.out.println("data val is:" + dataval3);
						}
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {
						JSONArray unique_ids = new JSONArray();
						unique_ids.put(0, uniqueidforprodedit);
						datavalforcustomer = unique_ids.toString();
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {
						JSONObject data = new JSONObject();
						JSONObject jsonobj = new JSONObject();
						JSONArray fields = new JSONArray();

						for (int i = 0; i < storesspinnerarrayfordisplay.size(); i++) {
							fields.put(i, jsonobj);
							jsonobj.put(DatabaseForDemo.STORE_ID_CUSTOMER,
									storesspinnerarrayfordisplay.get(i));
							jsonobj.put(DatabaseForDemo.CUSTOMER_NO, custno);
							jsonobj.put(DatabaseForDemo.UNIQUE_ID,
									uniqueidforprodedit);
						}
						data.put("fields", fields);
						dataval4 = data.toString();
						System.out.println("data val is:" + dataval4);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if (Parameters.OriginalUrl.equals("")) {
						System.out.println("there is no url val");
					} else {
						boolean isnet = Parameters.isNetworkAvailable(CustomerActivity.this);
						if (isnet) {
							new Thread(new Runnable() {
								@Override
								public void run() {

									JsonPostMethod jsonpost = new JsonPostMethod();
									String response = jsonpost
											.postmethodfordirect(
													"admin",
													"abcdefg",
													DatabaseForDemo.CUSTOMER_TABLE,
													Parameters.currentTime(),
													Parameters.currentTime(),
													dataval, "true");
									System.out.println("response test is:"
											+ response);
									try {
										JSONObject obj = new JSONObject(
												response);
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
										for (int jj = 0, ii = 0; jj < array2
												.length()
												&& ii < array.length(); jj++, ii++) {
											String deletequerytemp = array2
													.getString(jj);
											String deletequery1 = deletequerytemp
													.replace("'", "\"");
											String deletequery = deletequery1
													.replace("\\\"", "'");
											System.out.println("delete query"
													+ jj + " is :"
													+ deletequery);

											String insertquerytemp = array
													.getString(ii);
											String insertquery1 = insertquerytemp
													.replace("'", "\"");
											String insertquery = insertquery1
													.replace("\\\"", "'");
											System.out.println("delete query"
													+ jj + " is :"
													+ insertquery);

											dbforloginlogoutWriteCustomer.execSQL(deletequery);
											dbforloginlogoutWriteCustomer.execSQL(insertquery);
											System.out
													.println("queries executed"
															+ ii);

										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									String responseproduct1 = jsonpost
											.postmethodfordirect(
													"admin",
													"abcdefg",
													DatabaseForDemo.CUSTOMER_GENERAL_INFO_TABLE,
													Parameters.currentTime(),
													Parameters.currentTime(),
													dataval1, "true");
									System.out.println("response test is:"
											+ responseproduct1);
									try {
										JSONObject obj = new JSONObject(
												responseproduct1);
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
										for (int jj = 0, ii = 0; jj < array2
												.length()
												&& ii < array.length(); jj++, ii++) {
											String deletequerytemp = array2
													.getString(jj);
											String deletequery1 = deletequerytemp
													.replace("'", "\"");
											String deletequery = deletequery1
													.replace("\\\"", "'");
											System.out.println("delete query"
													+ jj + " is :"
													+ deletequery);

											String insertquerytemp = array
													.getString(ii);
											String insertquery1 = insertquerytemp
													.replace("'", "\"");
											String insertquery = insertquery1
													.replace("\\\"", "'");
											System.out.println("delete query"
													+ jj + " is :"
													+ insertquery);

											dbforloginlogoutWriteCustomer.execSQL(deletequery);
											dbforloginlogoutWriteCustomer.execSQL(insertquery);
											System.out
													.println("queries executed"
															+ ii);

										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									String responseproduct2 = jsonpost
											.postmethodfordirect(
													"admin",
													"abcdefg",
													DatabaseForDemo.CUSTOMER_EXTENDED_INFO_TABLE,
													Parameters.currentTime(),
													Parameters.currentTime(),
													dataval2, "true");
									System.out.println("response test is:"
											+ responseproduct2);
									try {
										JSONObject obj = new JSONObject(
												responseproduct2);
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
										for (int jj = 0, ii = 0; jj < array2
												.length()
												&& ii < array.length(); jj++, ii++) {
											String deletequerytemp = array2
													.getString(jj);
											String deletequery1 = deletequerytemp
													.replace("'", "\"");
											String deletequery = deletequery1
													.replace("\\\"", "'");
											System.out.println("delete query"
													+ jj + " is :"
													+ deletequery);

											String insertquerytemp = array
													.getString(ii);
											String insertquery1 = insertquerytemp
													.replace("'", "\"");
											String insertquery = insertquery1
													.replace("\\\"", "'");
											System.out.println("delete query"
													+ jj + " is :"
													+ insertquery);

											dbforloginlogoutWriteCustomer.execSQL(deletequery);
											dbforloginlogoutWriteCustomer.execSQL(insertquery);
											System.out
													.println("queries executed"
															+ ii);

										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									String responseproduct4 = jsonpost
											.postmethodfordirect(
													"admin",
													"abcdefg",
													DatabaseForDemo.CUSTOMER_SHIPPING_TABLE,
													Parameters.currentTime(),
													Parameters.currentTime(),
													dataval3, "true");
									System.out.println("response test is:"
											+ responseproduct4);

									String servertiem = null;
									try {
										JSONObject obj = new JSONObject(
												response);
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
										for (int jj = 0, ii = 0; jj < array2
												.length()
												&& ii < array.length(); jj++, ii++) {
											String deletequerytemp = array2
													.getString(jj);
											String deletequery1 = deletequerytemp
													.replace("'", "\"");
											String deletequery = deletequery1
													.replace("\\\"", "'");
											System.out.println("delete query"
													+ jj + " is :"
													+ deletequery);

											String insertquerytemp = array
													.getString(ii);
											String insertquery1 = insertquerytemp
													.replace("'", "\"");
											String insertquery = insertquery1
													.replace("\\\"", "'");
											System.out.println("delete query"
													+ jj + " is :"
													+ insertquery);

											dbforloginlogoutWriteCustomer.execSQL(deletequery);
											dbforloginlogoutWriteCustomer.execSQL(insertquery);
											System.out
													.println("queries executed"
															+ ii);

										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									String responseforsku = jsonpost
											.postmethodfordirectdelete(
													"admin",
													"abcdefg",
													DatabaseForDemo.CUSTOMER_STORES_TABLE,
													Parameters.currentTime(),
													Parameters.currentTime(),
													datavalforcustomer);
									System.out.println("response test is:"
											+ responseforsku);

									String responseproduct3 = jsonpost
											.postmethodfordirect(
													"admin",
													"abcdefg",
													DatabaseForDemo.CUSTOMER_STORES_TABLE,
													Parameters.currentTime(),
													Parameters.currentTime(),
													dataval4, "");
									System.out.println("response test is:"
											+ responseproduct3);
									try {
										JSONObject obj = new JSONObject(
												responseproduct3);
										JSONObject responseobj = obj
												.getJSONObject("response");
										servertiem = responseobj
												.getString("server-time");
										System.out.println("servertime is:"
												+ servertiem);
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
										for (int jj = 0, ii = 0; jj < array2
												.length()
												&& ii < array.length(); jj++, ii++) {
											String deletequerytemp = array2
													.getString(jj);
											String deletequery1 = deletequerytemp
													.replace("'", "\"");
											String deletequery = deletequery1
													.replace("\\\"", "'");
											System.out.println("delete query"
													+ jj + " is :"
													+ deletequery);

											String insertquerytemp = array
													.getString(ii);
											String insertquery1 = insertquerytemp
													.replace("'", "\"");
											String insertquery = insertquery1
													.replace("\\\"", "'");
											System.out.println("delete query"
													+ jj + " is :"
													+ insertquery);

											dbforloginlogoutWriteCustomer.execSQL(deletequery);
											dbforloginlogoutWriteCustomer.execSQL(insertquery);
											System.out
													.println("queries executed"
															+ ii);

										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									String select = "select *from "
											+ DatabaseForDemo.MISCELLANEOUS_TABLE;
									Cursor cursor = dbforloginlogoutReadCustomer.rawQuery(select, null);
									if (cursor.getCount() > 0) {
										dbforloginlogoutWriteCustomer.execSQL("update "
												+ DatabaseForDemo.MISCELLANEOUS_TABLE
												+ " set "
												+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
												+ "=\"" + servertiem + "\"");
										
									} else {
										
										ContentValues contentValues1 = new ContentValues();
										contentValues1.put(
												DatabaseForDemo.MISCEL_STORE,
												"store1");
										contentValues1.put(
												DatabaseForDemo.MISCEL_PAGEURL,
												"");
										contentValues1
												.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL,
														Parameters
																.currentTime());
										contentValues1
												.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL,
														Parameters
																.currentTime());
										dbforloginlogoutWriteCustomer.insert(
												DatabaseForDemo.MISCELLANEOUS_TABLE,
												null, contentValues1);
									}
									cursor.close();

									dataval = "";
									dataval1 = "";
									dataval2 = "";
									dataval3 = "";
									dataval4 = "";
									datavalforcustomer = "";
									
								}
							}).start();
						} else {
							ContentValues contentValues1 = new ContentValues();
							contentValues1.put(DatabaseForDemo.QUERY_TYPE,
									"update");
							contentValues1.put(DatabaseForDemo.PENDING_USER_ID,
									Parameters.userid);
							contentValues1.put(DatabaseForDemo.PAGE_URL,
									"saveinfo.php");
							contentValues1.put(
									DatabaseForDemo.TABLE_NAME_PENDING,
									DatabaseForDemo.CUSTOMER_TABLE);
							contentValues1.put(
									DatabaseForDemo.CURRENT_TIME_PENDING,
									Parameters.currentTime());
							contentValues1.put(DatabaseForDemo.PARAMETERS,
									dataval);
							dbforloginlogoutWriteCustomer.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
									null, contentValues1);

							ContentValues contentValues2 = new ContentValues();
							contentValues2.put(DatabaseForDemo.QUERY_TYPE,
									"update");
							contentValues2.put(DatabaseForDemo.PENDING_USER_ID,
									Parameters.userid);
							contentValues2.put(DatabaseForDemo.PAGE_URL,
									"saveinfo.php");
							contentValues2
									.put(DatabaseForDemo.TABLE_NAME_PENDING,
											DatabaseForDemo.CUSTOMER_GENERAL_INFO_TABLE);
							contentValues2.put(
									DatabaseForDemo.CURRENT_TIME_PENDING,
									Parameters.currentTime());
							contentValues2.put(DatabaseForDemo.PARAMETERS,
									dataval1);
							dbforloginlogoutWriteCustomer.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
									null, contentValues2);

							ContentValues contentValues3 = new ContentValues();
							contentValues3.put(DatabaseForDemo.QUERY_TYPE,
									"update");
							contentValues3.put(DatabaseForDemo.PENDING_USER_ID,
									Parameters.userid);
							contentValues3.put(DatabaseForDemo.PAGE_URL,
									"saveinfo.php");
							contentValues3
									.put(DatabaseForDemo.TABLE_NAME_PENDING,
											DatabaseForDemo.CUSTOMER_EXTENDED_INFO_TABLE);
							contentValues3.put(
									DatabaseForDemo.CURRENT_TIME_PENDING,
									Parameters.currentTime());
							contentValues3.put(DatabaseForDemo.PARAMETERS,
									dataval2);
							dbforloginlogoutWriteCustomer.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
									null, contentValues3);

							ContentValues contentValues4 = new ContentValues();
							contentValues4.put(DatabaseForDemo.QUERY_TYPE,
									"update");
							contentValues4.put(DatabaseForDemo.PENDING_USER_ID,
									Parameters.userid);
							contentValues4.put(DatabaseForDemo.PAGE_URL,
									"saveinfo.php");
							contentValues4.put(
									DatabaseForDemo.TABLE_NAME_PENDING,
									DatabaseForDemo.CUSTOMER_SHIPPING_TABLE);
							contentValues4.put(
									DatabaseForDemo.CURRENT_TIME_PENDING,
									Parameters.currentTime());
							contentValues4.put(DatabaseForDemo.PARAMETERS,
									dataval3);
							dbforloginlogoutWriteCustomer.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
									null, contentValues4);

							ContentValues contentValues5 = new ContentValues();
							contentValues5.put(DatabaseForDemo.QUERY_TYPE,
									"delete");
							contentValues5.put(DatabaseForDemo.PENDING_USER_ID,
									Parameters.userid);
							contentValues5.put(DatabaseForDemo.PAGE_URL,
									"deleteinfo.php");
							contentValues5.put(
									DatabaseForDemo.TABLE_NAME_PENDING,
									DatabaseForDemo.CUSTOMER_STORES_TABLE);
							contentValues5.put(
									DatabaseForDemo.CURRENT_TIME_PENDING,
									Parameters.currentTime());
							contentValues5.put(DatabaseForDemo.PARAMETERS,
									datavalforcustomer);
							dbforloginlogoutWriteCustomer.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
									null, contentValues5);

							ContentValues contentValues6 = new ContentValues();
							contentValues6.put(DatabaseForDemo.QUERY_TYPE,
									"insert");
							contentValues6.put(DatabaseForDemo.PENDING_USER_ID,
									Parameters.userid);
							contentValues6.put(DatabaseForDemo.PAGE_URL,
									"saveinfo.php");
							contentValues6.put(
									DatabaseForDemo.TABLE_NAME_PENDING,
									DatabaseForDemo.CUSTOMER_STORES_TABLE);
							contentValues6.put(
									DatabaseForDemo.CURRENT_TIME_PENDING,
									Parameters.currentTime());
							contentValues6.put(DatabaseForDemo.PARAMETERS,
									dataval4);
							dbforloginlogoutWriteCustomer.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
									null, contentValues6);

							dataval = "";
							dataval1 = "";
							dataval2 = "";
							dataval3 = "";
							dataval4 = "";
							datavalforcustomer = "";
						}
					}

					generalcompanyname = "";
					generalprimaryphone = "";
					generalalternatephone = "";
					generaladdress1 = "";
					generalstate = "";
					generalcity = "";
					generalcountry = "";
					generalzipcode = "";
					generaladdress2 = "";
					birthdayval = "";
					shippingcompanyname = "";
					shippingphone = "";
					shippingfirstname = "";
					shippingaddress = "";
					shippinglastname = "";
					shippingstate = "";
					shippingcity = "";
					shippingcountry = "";
					shippingzipcode = "";
					shippingextended = "";
					cardtypepositionstring = "";
					creditcardtype = "";
					cardnumberval = "";
					expirationval = "";
					drivinglicenseval = "";
					expdateval = "";
					mobileval = "";
					faxval = "";
					customernotes = "";
					cardtypeposition = 0;
					storesspinnerarray.clear();
					storesspinnerarrayfordisplay.clear();
					alertDialog1.dismiss();
					listUpdateforcustomer();
				}
				
			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alertDialog1.dismiss();
			}
		});
		alertDialog1.setView(layout);
		alertDialog1.show();
	}

	@Override
	public void onDeleteClickedforCustomer(View v, final String id) {
		final AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
		LayoutInflater mInflater = LayoutInflater.from(this);
		View layout = mInflater.inflate(R.layout.delete_popup, null);
		Button ok = (Button) layout.findViewById(R.id.ok);
		Button cancel = (Button) layout.findViewById(R.id.cancel);

		alertDialog1.setTitle("Delete");

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				final ArrayList<String> getlist = new ArrayList<String>();
				String selectQueryforinstantpo = "SELECT  * FROM "
						+ DatabaseForDemo.CUSTOMER_TABLE + " where "
						+ DatabaseForDemo.CUSTOMER_NO + "=\"" + id + "\"";

				Cursor mCursorforvendor1 = dbforloginlogoutReadCustomer.rawQuery(
						selectQueryforinstantpo, null);
				if (mCursorforvendor1 != null) {
					if (mCursorforvendor1.moveToFirst()) {
						do {
							String uniqueid = mCursorforvendor1.getString(mCursorforvendor1
									.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
							getlist.add(uniqueid);
						} while (mCursorforvendor1.moveToNext());
					}
				}
				mCursorforvendor1.close();
				String where = DatabaseForDemo.CUSTOMER_NO + "=?";
				dbforloginlogoutWriteCustomer.delete(DatabaseForDemo.CUSTOMER_TABLE, where,
						new String[] { id });

				String whereforgeneralinfo = DatabaseForDemo.CUSTOMER_NO + "=?";
				dbforloginlogoutWriteCustomer.delete(
						DatabaseForDemo.CUSTOMER_GENERAL_INFO_TABLE,
						whereforgeneralinfo, new String[] { id });

				String whereforextendedinfo = DatabaseForDemo.CUSTOMER_NO
						+ "=?";
				dbforloginlogoutWriteCustomer.delete(
						DatabaseForDemo.CUSTOMER_EXTENDED_INFO_TABLE,
						whereforextendedinfo, new String[] { id });

				String whereforshipping = DatabaseForDemo.CUSTOMER_NO + "=?";
				dbforloginlogoutWriteCustomer.delete(DatabaseForDemo.CUSTOMER_SHIPPING_TABLE,
						whereforshipping, new String[] { id });

				String whereforstores = DatabaseForDemo.CUSTOMER_NO + "=?";
				dbforloginlogoutWriteCustomer.delete(DatabaseForDemo.CUSTOMER_STORES_TABLE,
						whereforstores, new String[] { id });

				Toast.makeText(CustomerActivity.this,
						"Customer deleted successfully", Toast.LENGTH_SHORT)
						.show();

				try {
					JSONArray unique_ids = new JSONArray();
					for (int i = 0; i < getlist.size(); i++) {
						unique_ids.put(i, getlist.get(i));
						dataval = unique_ids.toString();
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					JSONArray unique_ids = new JSONArray();
					for (int i = 0; i < getlist.size(); i++) {
						unique_ids.put(i, getlist.get(i));
						dataval1 = unique_ids.toString();
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					JSONArray unique_ids = new JSONArray();
					for (int i = 0; i < getlist.size(); i++) {
						unique_ids.put(i, getlist.get(i));
						dataval2 = unique_ids.toString();
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					JSONArray unique_ids = new JSONArray();
					for (int i = 0; i < getlist.size(); i++) {
						unique_ids.put(i, getlist.get(i));
						dataval3 = unique_ids.toString();
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					JSONArray unique_ids = new JSONArray();
					for (int i = 0; i < getlist.size(); i++) {
						unique_ids.put(i, getlist.get(i));
						dataval4 = unique_ids.toString();
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (Parameters.OriginalUrl.equals("")) {
					System.out.println("there is no url val");
				} else {
					boolean isnet = Parameters.isNetworkAvailable(CustomerActivity.this);
					if (isnet) {
						new Thread(new Runnable() {
							@Override
							public void run() {

								JsonPostMethod jsonpost = new JsonPostMethod();
								String response = jsonpost
										.postmethodfordirectdelete("admin",
												"abcdefg",
												DatabaseForDemo.CUSTOMER_TABLE,
												Parameters.currentTime(),
												Parameters.currentTime(),
												dataval);
								System.out.println("response test is:"
										+ response);
								String response1 = jsonpost
										.postmethodfordirectdelete(
												"admin",
												"abcdefg",
												DatabaseForDemo.CUSTOMER_GENERAL_INFO_TABLE,
												Parameters.currentTime(),
												Parameters.currentTime(),
												dataval1);
								System.out.println("response test is:"
										+ response1);

								String response2 = jsonpost
										.postmethodfordirectdelete(
												"admin",
												"abcdefg",
												DatabaseForDemo.CUSTOMER_EXTENDED_INFO_TABLE,
												Parameters.currentTime(),
												Parameters.currentTime(),
												dataval2);
								System.out.println("response test is:"
										+ response2);

								String response3 = jsonpost
										.postmethodfordirectdelete(
												"admin",
												"abcdefg",
												DatabaseForDemo.CUSTOMER_SHIPPING_TABLE,
												Parameters.currentTime(),
												Parameters.currentTime(),
												dataval3);
								System.out.println("response test is:"
										+ response3);

								String response4 = jsonpost
										.postmethodfordirectdelete(
												"admin",
												"abcdefg",
												DatabaseForDemo.CUSTOMER_STORES_TABLE,
												Parameters.currentTime(),
												Parameters.currentTime(),
												dataval4);
								System.out.println("response test is:"
										+ response4);

								String servertiem = null;
								try {
									JSONObject obj = new JSONObject(response);
									JSONObject responseobj = obj
											.getJSONObject("response");
									servertiem = responseobj
											.getString("server-time");
									System.out.println("servertime is:"
											+ servertiem);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								String select = "select *from "
										+ DatabaseForDemo.MISCELLANEOUS_TABLE;
								Cursor cursor = dbforloginlogoutReadCustomer.rawQuery(select, null);
								if (cursor.getCount() > 0) {
									dbforloginlogoutWriteCustomer.execSQL("update "
											+ DatabaseForDemo.MISCELLANEOUS_TABLE
											+ " set "
											+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
											+ "=\"" + servertiem + "\"");
									
								} else {
									
									ContentValues contentValues1 = new ContentValues();
									contentValues1.put(
											DatabaseForDemo.MISCEL_STORE,
											"store1");
									contentValues1.put(
											DatabaseForDemo.MISCEL_PAGEURL, "");
									contentValues1
											.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL,
													Parameters.currentTime());
									contentValues1
											.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL,
													Parameters.currentTime());
									dbforloginlogoutWriteCustomer.insert(
											DatabaseForDemo.MISCELLANEOUS_TABLE,
											null, contentValues1);
									
								}
								cursor.close();
								dataval = "";
								dataval1 = "";
								dataval2 = "";
								dataval3 = "";
								dataval4 = "";
							}
						}).start();
					} else {
						
						

						ContentValues contentValues1 = new ContentValues();
						contentValues1
								.put(DatabaseForDemo.QUERY_TYPE, "delete");
						contentValues1.put(DatabaseForDemo.PENDING_USER_ID,
								Parameters.userid);
						contentValues1.put(DatabaseForDemo.PAGE_URL,
								"deleteinfo.php");
						contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING,
								DatabaseForDemo.CUSTOMER_TABLE);
						contentValues1.put(
								DatabaseForDemo.CURRENT_TIME_PENDING,
								Parameters.currentTime());
						contentValues1.put(DatabaseForDemo.PARAMETERS, dataval);
						dbforloginlogoutWriteCustomer.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
								contentValues1);

						ContentValues contentValues2 = new ContentValues();
						contentValues2
								.put(DatabaseForDemo.QUERY_TYPE, "delete");
						contentValues2.put(DatabaseForDemo.PENDING_USER_ID,
								Parameters.userid);
						contentValues2.put(DatabaseForDemo.PAGE_URL,
								"deleteinfo.php");
						contentValues2.put(DatabaseForDemo.TABLE_NAME_PENDING,
								DatabaseForDemo.CUSTOMER_GENERAL_INFO_TABLE);
						contentValues2.put(
								DatabaseForDemo.CURRENT_TIME_PENDING,
								Parameters.currentTime());
						contentValues2
								.put(DatabaseForDemo.PARAMETERS, dataval1);
						dbforloginlogoutWriteCustomer.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
								contentValues2);

						ContentValues contentValues3 = new ContentValues();
						contentValues3
								.put(DatabaseForDemo.QUERY_TYPE, "delete");
						contentValues3.put(DatabaseForDemo.PENDING_USER_ID,
								Parameters.userid);
						contentValues3.put(DatabaseForDemo.PAGE_URL,
								"deleteinfo.php");
						contentValues3.put(DatabaseForDemo.TABLE_NAME_PENDING,
								DatabaseForDemo.CUSTOMER_EXTENDED_INFO_TABLE);
						contentValues3.put(
								DatabaseForDemo.CURRENT_TIME_PENDING,
								Parameters.currentTime());
						contentValues3
								.put(DatabaseForDemo.PARAMETERS, dataval2);
						dbforloginlogoutWriteCustomer.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
								contentValues3);

						ContentValues contentValues4 = new ContentValues();
						contentValues4
								.put(DatabaseForDemo.QUERY_TYPE, "delete");
						contentValues4.put(DatabaseForDemo.PENDING_USER_ID,
								Parameters.userid);
						contentValues4.put(DatabaseForDemo.PAGE_URL,
								"deleteinfo.php");
						contentValues4.put(DatabaseForDemo.TABLE_NAME_PENDING,
								DatabaseForDemo.CUSTOMER_SHIPPING_TABLE);
						contentValues4.put(
								DatabaseForDemo.CURRENT_TIME_PENDING,
								Parameters.currentTime());
						contentValues4
								.put(DatabaseForDemo.PARAMETERS, dataval3);
						dbforloginlogoutWriteCustomer.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
								contentValues4);

						ContentValues contentValues5 = new ContentValues();
						contentValues5
								.put(DatabaseForDemo.QUERY_TYPE, "delete");
						contentValues5.put(DatabaseForDemo.PENDING_USER_ID,
								Parameters.userid);
						contentValues5.put(DatabaseForDemo.PAGE_URL,
								"deleteinfo.php");
						contentValues5.put(DatabaseForDemo.TABLE_NAME_PENDING,
								DatabaseForDemo.CUSTOMER_STORES_TABLE);
						contentValues5.put(
								DatabaseForDemo.CURRENT_TIME_PENDING,
								Parameters.currentTime());
						contentValues5
								.put(DatabaseForDemo.PARAMETERS, dataval4);
						dbforloginlogoutWriteCustomer.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
								contentValues5);
						dataval = "";
						dataval1 = "";
						dataval2 = "";
						dataval3 = "";
						dataval4 = "";
					}
				}
				listUpdateforcustomer();
				alertDialog1.dismiss();
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

	@Override
	public void onDeleteClickedforsku(View v, String storeid) {
		storesspinnerarrayfordisplay.remove(storeid);
		if (storesspinnerarrayfordisplay != null) {
			SkuArrayAdapter skuadapter = new SkuArrayAdapter(
					getApplicationContext(), storesspinnerarrayfordisplay);
			skuadapter.setListener(CustomerActivity.this);
			storelist.setAdapter(skuadapter);
		}
		Toast.makeText(CustomerActivity.this, "Store deleted successfully",
				Toast.LENGTH_SHORT).show();
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
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
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
		sqlDBCustomer.close();
		super.onDestroy();
	}
}
